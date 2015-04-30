package pdfClear;
package default package;
public class Doc
{
    private int outType;
    private String workFolder;
    private String sourceFolder;
    private int pageCount;
    
    //this is just a test comment

    //constructor
    public Doc(String workfolder, String sourcefolder)
    {
        workFolder = workfolder;
        sourceFolder = sourcefolder;
        PreProcess prep = new PreProcess(workFolder);
        ProcessStructure procstru = new ProcessStructure(workFolder);
        PostProcess postp = new PostProcess();
    }

    public void doDoc(String basename)
    {
        //TODO: copy basename.pdf?? from source folder into work folder
        //copy (sourceFolder + "/" + basename + ".pdf") -> (workFolder + "/" + basename + ".pdf")
        //sends the doc, basename, and workfolder. returns an array(???) of words [Type?]
        wordList = prep.preProcessDoc(baseName);
        //sends the words to structure the page. 
        procstru.processStructureDoc(wordList);
        
        //sends the finished doc to be cleaned up. return value???
        
    }
}
