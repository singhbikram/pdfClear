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
	
	
	/*
		public static void main(String[] args) throws Exception {
	    SAXParserFactory parserFactor = SAXParserFactory.newInstance();
	    SAXParser parser = parserFactor.newSAXParser();
	    SAXHandler handler = new SAXHandler();
	    parser.parse(ClassLoader.getSystemResourceAsStream("ocrProject/sampleHOCR.xml"), 
	                 handler);
	    //Printing the list of words obtained from XML
	    for ( Word word : handler.wordList){
	      System.out.println(word);
	    }
	  }

	}*/
	/**
	 * The Handler for SAX Events.
	 */
	/*class SAXHandler extends DefaultHandler {

	  List<Word> wordList = new ArrayList<>();
	  Word word = null;
	  String content = null;
	  @Override
	  //Triggered when the start of tag is found.
	  public void startElement(String uri, String localName, 
	                           String qName, Attributes attributes) 
	                           throws SAXException {

	    switch(qName){
	      //Create a new Employee object when the start tag is found
	      case "span":
	        word = new Word();
	        word.title = attributes.getValue("title");
	        word.data = attributes.getValue("character");
	        break;
	    }
	  }

	  @Override
	  public void endElement(String uri, String localName, 
	                         String qName) throws SAXException {
	   switch(qName){
	     //Add the employee to list once end tag is found
	     case "span":
	       wordList.add(word);       
	       word.data = content;
	       break;
	     
	   }
	  }

	  @Override
	  public void characters(char[] ch, int start, int length) 
	          throws SAXException {
	    content = String.copyValueOf(ch, start, length).trim();
	  }

	}*/

	

	  
	
}//end class wordList