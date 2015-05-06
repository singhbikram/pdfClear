package pdfClear;
/**
 * 
 */


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//import java.util.Vector;

/**
 * @author Laptop
 *
 */
public class ImportFile  implements ImportFileInterface {

	
	public Void ImportFile() {
		return null;
	}

	@Override
	public ArrayList<Word> readHOCR(String pagePath) {
		ArrayList<Word> wordList = new ArrayList<Word>();
		String raw;
		try {
			raw = loadFile(pagePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		Pattern p = Pattern.compile("<span class=\'ocrx_word\' id=\'word_\\d++_\\d++\' title=\'bbox (\\d++) (\\d++) (\\d++) (\\d++); x_wconf \\d++\' lang=\'eng\' dir=\'\\w++\'>([^<]++)</span>");
		 Matcher m = p.matcher(raw);
		 while(m.find()){
			    int count = m.groupCount();
			    
			    for(int i=1;i<=5;i++){
			        wordList.add(new Word(Integer.parseInt(m.group(1)),Integer.parseInt(m.group(2)),Integer.parseInt(m.group(3)),Integer.parseInt(m.group(4)),m.group(5)));
			    }
			}
		return wordList;
	}
	
	//get text from file
	private String loadFile(String pagePath) throws IOException{
		  byte[] encoded = Files.readAllBytes(Paths.get(pagePath));
		  return new String(encoded, StandardCharsets.UTF_8);
		}
	
	
	/*List<Word> wordlist = new ArrayList<Word>();
	    	String fileName = "D:/eclipse/Javaworkspace/pdfClear/src/pdfClear/sampleHOCR.xml";
	    	FileInputStream fis = new FileInputStream(fileName);
	    	
	    	@SuppressWarnings("resource")
			String StringFis = new Scanner(fis, "UTF-8").useDelimiter("\\A").next();
	    	
	    	Pattern p = Pattern.compile("<span class=\'ocrx_word\' id=\'word_\\d++_\\d++\' title=\'bbox (\\d++) (\\d++) (\\d++) (\\d++); x_wconf \\d++\' lang=\'eng\' dir=\'\\w++\'>([^<]++)</span>");
			Matcher m = p.matcher(StringFis);
			
			while(m.find()){
				wordlist.add(new Word(m.group(1),m.group(2),m.group(3),m.group(4),m.group(5)));
			}//while
		*/

	

	  
	
}//end class wordList