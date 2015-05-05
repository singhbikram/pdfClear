package pdfClear;
/**
 * 
 */

import java.util.ArrayList;
import java.util.List;
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
public class WordList {
	
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
	}
	/**
	 * The Handler for SAX Events.
	 */
	class SAXHandler extends DefaultHandler {

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

	}

	

	  
	
//}//end class wordList