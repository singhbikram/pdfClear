package pdfClear;
import java.util.concurrent.Semaphore;

public class Feeder  implements FeederInterface {

    private String[] baseNames;
    private String workFolder;
    private String sourceFolder;
    private String doneFolder;
    private int outType;
    private Semaphore available;
    
    //constructor
    public Feeder(String sourcefolder, String workfolder, String donefolder, int threads, int outtype)
    {
        workFolder = workfolder;
        sourceFolder = sourcefolder;
        doneFolder = donefolder;
        outType = outtype;
        //creates a thread counter with specified number of threads
        available =  new Semaphore(threads);
  
    }
    
    public void processList(String[] names)
    {
        baseNames = names;
        // send a basename/file to be processed
        for (String basename: baseNames)
        {
            try {
                //checks to see if there is a thread available
                available.acquire();
                //creates a new Doc object and starts the thread
                Doc doc = new Doc(workFolder, sourceFolder, doneFolder, outType, basename, available);
                doc.start();
            }
            catch (InterruptedException e) {
            }
        }    
    }
}
