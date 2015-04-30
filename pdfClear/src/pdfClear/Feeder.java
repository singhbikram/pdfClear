package pdfClear;

public class Feeder
{
    private String[] baseName;
    private String workFolder;
    private String sourceFolder;
    //private int outType;
    //variables: array of base names, pathnames/folders, etc
    
    public Feeder(String workfolder, String sourcefolder/*, int outtype*/)
    {
        workFolder = workfolder;
        sourceFolder = sourcefolder;
        //outType = outtype;
        //TODO: go through folder and collect basenames, does other things idk.
        
        //INITIALIZES baseName array and puts in basenames
        int count = 0;
        for (File file: sourceFolder) //gets size of array
        {
            if (isFile())
                count++;
        }
        //sets array size
        @SuppressWarnings("unchecked")
        baseName = new String[count];
        
        //puts names into array
        for (File file: sourceFolder)
        {
            if (isFile())//makes sure the thing is a file
            {
                String x = file.getName();//CHECK to see if 'getName' returns the .pdf
                baseName[name] = x.subString(0, x.length - 4);
            }    
        }    
        
        //create a 'Doc' object.
        Doc docthing = new Doc(workFolder, sourceFolder/*, outType*/);
        // send a basename/file to be done
        for (String basename: baseName)
            docthing.doDoc(basename);
        
    }
}
