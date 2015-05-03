package pdfClear;

public class Feeder
{
    private String[] baseName;
    private String workFolder;
    private String sourceFolder;
    private String doneFolder;
    private String errFolder;
    private int numOfThreads;
    //private int outType;
    
    public Feeder(ArrayList<String> names, String sourcefolder, String workfolder, String donefolder, String errfolder, int threads/*, int outtype*/)
    {
        baseName = new String[names.size()];
        for (String basename: baseName)
            baseName[basename] = names[basename]
        workFolder = workfolder;
        sourceFolder = sourcefolder;
        doneFolder = donefolder
        errFolder = errfolder;
        numOfThreads = threads;
        //outType = outtype;
  
    }
    
    public void processList()//what does this do?
    {
        //create a 'Doc' object.
        Doc docthing = new Doc(workFolder, sourceFolder/*, outType*/);
        // send a basename/file to be done
        for (String basename: baseName)
            docthing.doDoc(basename);
    }
}
