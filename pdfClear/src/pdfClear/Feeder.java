package pdfClear;
import java.util.concurrent.Semaphore;

public class Feeder  implements FeederInterface {

    private String[] baseNames;
    private String workFolder;
    private String sourceFolder;
    private String doneFolder;
    private int outType;
    private Semaphore available;
    
    public Feeder(String sourcefolder, String workfolder, String donefolder, int threads, int outtype)
    {
        workFolder = workfolder;
        sourceFolder = sourcefolder;
        doneFolder = donefolder;
        outType = outtype;
        available =  new Semaphore(threads);
  
    }
    
    public void processList(String[] names)
    {
        baseNames = names;
        // send a basename/file to be done
        for (String basename: baseNames)
        {
            try {
                available.acquire();
                Doc doc = new Doc(workFolder, sourceFolder, doneFolder, outType, basename, available);
                doc.start();
            }
            catch (InterruptedException e) {
            }
        }    
    }
}