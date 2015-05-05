package pdfClear;

public class Feeder  implements FeederInterface {

    private String[] baseNames;
    private String workFolder;
    private String sourceFolder;
    private String doneFolder;
    private int numOfThreads;
    private int outType;
    
    public Feeder(String sourcefolder, String workfolder, String donefolder, int threads, int outtype)
    {
        workFolder = workfolder;
        sourceFolder = sourcefolder;
        doneFolder = donefolder;
        numOfThreads = threads;
        outType = outtype;
  
    }
    
    public void processList(String[] names)
    {
        baseNames = names;
        //create a 'Doc' object.
        Doc docthing = new Doc(workFolder, sourceFolder, doneFolder, outType);
        // send a basename/file to be done
        for (String basename: baseNames)
            docthing.doDoc(basename);
    }
}
