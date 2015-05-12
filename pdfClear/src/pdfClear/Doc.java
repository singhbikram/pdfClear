package pdfClear;
import java.util.concurrent.Semaphore;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Doc extends Thread implements DocInterface
{
    private int outType;
    private String workFolder;
    private String sourceFolder;
    private String basename;
    private int pageCount;
    private PreProcess prep;
    private ProcessPage procstru;
    private PostProcess postp;
    private Semaphore available;


    //constructor
     public Doc(String workfolder, String sourcefolder, String donefolder,int outType, String basename, Semaphore ava)
    {
        workFolder = workfolder;
        sourceFolder = sourcefolder;
        this.basename = basename;
        available = ava;
        prep = new PreProcess(workFolder);
        procstru = new ProcessPage(workFolder);
        postp = new PostProcess(outType,workFolder, donefolder);
    }


    public void run()
    {
        //TODO: copy basename.pdf?? from source folder into work folder
        File sourceFile = new File(sourceFolder + basename + ".pdf");
        File destFile = new File(workFolder + basename + ".pdf");
        try {
                        Files.copy(sourceFile.toPath(), destFile.toPath() );//,REPLACE_EXISTING,COPY_ATTRIBUTES
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }

        //sends the  basename, returns an array or pages, each an array of Word objects
        ArrayList<ArrayList<Word>> wordLists= prep.doDoc(basename);
        //sends the words to structure the page.
        ArrayList<ArrayList<WordBlock>> wordBlocks = new ArrayList<ArrayList<WordBlock>>();
        for (ArrayList<Word> wordList: wordLists){
                wordBlocks.add( procstru.processPage(wordList));
        }
        //sends the finished doc to be cleaned up and output
        postp.outputFile(wordLists,wordBlocks, basename);
        available.release();
    }
}
