package pdfClear;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;

public class PostProcess implements PostProcessInterface {
	private int _outType=0; //type of files to output
	private String _workFolder;
	private String _doneFolder;
	
	public PostProcess(int outType, String workFolder, String doneFolder){
		_outType=outType;
		_workFolder=workFolder;
		_doneFolder=doneFolder;
	}

	/* (non-Javadoc)
	 * @see pdfClear.PostProcessInterface#outputFile(java.util.ArrayList, java.util.ArrayList, java.lang.String)
	 */
	@Override
	public void outputFile(ArrayList<ArrayList<Word>> pages_wordList, ArrayList<ArrayList<WordBlock>> pages_wordBlocks, String baseName) throws UnsupportedEncodingException, IOException{
		// TODO Auto-generated method stub
		String header ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n\t\""
				+ "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
				+ "\n<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">"
				+ "\n<head>\n\t<title>\n</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />"
				+ "\n\t<meta name='ocr-system' content='tesseract 3.03' />\n\t<meta name='ocr-capabilities' content='ocr_page "
				+ "ocr_carea ocr_par ocr_line ocrx_word'/>\n</head>\n<body>"; 
		String footer = "\n\t</body>\n</html>";
		int pageNumber = 0;
		for(int page = 0;  page < pages_wordBlocks.size(); page++){  // for loop for one page
			 
			ArrayList<WordBlock> wordBlocks =  pages_wordBlocks.get(page); //for loop 1
			ArrayList<Word> wordList =  pages_wordList.get(page); //for loop 1
			pageNumber++;  // increment the pagenumber to create new file for the next page
			String filePath = String.format("D:/eclipse/Javaworkspace/test/src/test/result%03d.hocr", pageNumber);
			// File will create a file per page in the filepath location
			File file = new File(filePath);
			FileOutputStream fos = new FileOutputStream(file);
			PrintStream ps = new PrintStream(fos);
			System.setOut(ps);
			// adds header to the target file
			System.out.println(header);
			//variable to number blocks
			int blockNumm = 0;
			//iterate through every block
			for(WordBlock block: wordBlocks){   //for loop for adding a block
	 				//increments the block number 
					blockNumm++;
					String blockNumber = String.format("block_%d", blockNumm);
					//adds hocr block descriptor
					String stringB = "<span class='hocr_line' id='"+blockNumber+"' title=\"bbox "+block.getLeft()+ " "+block.getRight()+ " "+block.getTop()+ " "+block.getBottom()+ " "+"; baseline 0 -8\">";
					FileOutputStream stream2 = new FileOutputStream(filePath, true);
					try {
					    stream2.write(stringB.getBytes("UTF-8")
					    );
					} finally {
					    stream2.close();
					}// adds block descriptor 
					ArrayList<LinkedList<Integer>> lines;
					//lines gives the list of all the lines in a block
					lines = block.getLines();
					int lineNum = 0;
					int wordNum = 0;
					//for loop for iterating through lines
					for(LinkedList<Integer> line : lines){   
						// adds a line attribute to the span tag
						Word firstWord = wordList.get(line.get(0));
						lineNum++;
						String lineNumber = String.format("line_%d", lineNum);
						String string = "<span class='hocr_line' id='"+lineNumber+"' title=\"bbox "+firstWord.getLeft()+ " "+firstWord.getRight()+ " "+firstWord.getTop()+ " "+firstWord.getBottom()+ " "+"; baseline 0 -8\">";
						FileOutputStream stream = new FileOutputStream(filePath, true);
						try {
						    stream.write(string.getBytes("UTF-8")
						    );
						} finally {
						    stream.close();
						}
						//iterate through the linked list, line, for the list of all the word in the line
						for(int b : line){    //for loop for adding a line
							Word word = wordList.get(line.get(b));
							//int x = word.getBottom();
							//appends the string to the file
							wordNum++;
							String wordNumber = String.format("word_%d", wordNum);
							int a1 = word.getLeft(); 
							int a2 = word.getRight(); 
							int b1 = word.getTop(); 
							int b2 = word.getBottom();							
							String newWord = "<span class='hocr_line' id='"+wordNumber+"' title=\"bbox "+a1+ " "+a2+ " "+b1+ " "+b2+ " " + ";  x_wconf 99' lang='eng' dir='ltr'>"+word.getWord()+"</span>";
							FileOutputStream stream1 = new FileOutputStream(filePath, true);
							try {
							    stream1.write(newWord.getBytes("UTF-8")
							    );
							} finally {
							    stream1.close();
							}// try and finally to add a word to the file
							
						}//end forloop for adding words
						FileOutputStream stream4 = new FileOutputStream(filePath, true);
						try {
						    stream4.write("</span>".getBytes("UTF-8")
						    );
					    } finally {
					    stream4.close();
					    }// addes a closing tag to the line
					}//end for loop for iterating through lines
					FileOutputStream stream3 = new FileOutputStream(filePath, true);
					try {
					    stream3.write("</span>".getBytes("UTF-8")
					    );
				    } finally {
				    stream3.close();
				    }// addes a closing tag to the Block
				}//end for loop adding a block to the page
			//append the footer to the page
			FileOutputStream stream = new FileOutputStream(filePath, true);
			try {
			    stream.write(footer.getBytes("UTF-8")
			    );
		    } finally {
		    stream.close();
		    }// addes the footer to the page
		}//// for loop for one page
	}// end outputFile
}// end postPorocess Class
}
