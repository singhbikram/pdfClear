package pdfClear;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedList;

public class PostProcess implements PostProcessInterface {
	private int _outType = 0; // type of files to output
	private String _workFolder;
	private String _doneFolder;

	public PostProcess(int outType, String workFolder, String doneFolder) {
		_outType = outType;
		_workFolder = workFolder;
		_doneFolder = doneFolder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pdfClear.PostProcessInterface#outputFile(java.util.ArrayList,
	 * java.util.ArrayList, java.lang.String)
	 */
	@Override
	public void outputFile(ArrayList<ArrayList<Word>> pages_wordList,
			ArrayList<ArrayList<WordBlock>> pages_wordBlocks, String baseName) {
		String footer = "\n\t</div></body>\n</html>";
		int pageNumber = 0;
		for (int page = 0; page < pages_wordBlocks.size(); page++) { // for loop
																		// for
																		// one
																		// page
			String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n\t\""
					+ "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
					+ "\n<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">"
					+ "\n<head>\n\t<title>\n</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />"
					+ "\n\t<meta name='ocr-system' content='tesseract 3.03' />\n\t<meta name='ocr-capabilities' content='ocr_page "
					+ "ocr_carea ocr_par ocr_line ocrx_word'/>\n</head>\n<body>"
					+ "<div class='ocr_page' id='page_"
					+ (page + 1)
					+ "' ; bbox 0 0 2560 3300; ppageno 0'>";

			ArrayList<WordBlock> wordBlocks = pages_wordBlocks.get(page); // for
																			// loop
																			// 1
			ArrayList<Word> wordList = pages_wordList.get(page); // for loop 1
			pageNumber++; // increment the pagenumber to create new file for the
							// next page
			String filePath = String.format(_doneFolder + baseName
					+ "_%03d.hocr", pageNumber);

			StringBuilder builder = new StringBuilder();

			// adds header to the target file
			builder.append(header);
			// variable to number blocks
			int blockNumm = 0;
			int lineNum = 0;
			int wordNum = 0;
			// iterate through every block
			for (WordBlock block : wordBlocks) { // for loop for adding a block
				// increments the block number
				blockNumm++;
				String blockNumber = String.format("%d", blockNumm);
				// adds hocr block descriptor
				builder.append("\n<span class='ocr_para' id='block_"
						+ blockNumber + "' title='bbox " + block.getLeft()
						+ " " + block.getRight() + " " + block.getTop() + " "
						+ block.getBottom() + " " + "; baseline 0 -8'>");
				// adds block descriptor
				ArrayList<LinkedList<Integer>> lines;
				// lines gives the list of all the lines in a block
				lines = block.getLines();
				// for loop for iterating through lines
				for (LinkedList<Integer> line : lines) {
					// adds a line attribute to the span tag
					Word firstWord = wordList.get(line.get(0));
					int lx1 = firstWord.getLeft();
					int lx2 = firstWord.getRight();
					int ly1 = firstWord.getTop();
					int ly2 = firstWord.getBottom();
					for (int b : line) { // for loop for adding a line
						Word word = wordList.get(b);
						if (lx1 > word.getLeft()) {
							lx1 = word.getLeft();
						}
						if (lx2 < word.getRight()) {
							lx2 = word.getRight();
						}
						if (ly1 > word.getTop()) {
							ly1 = word.getTop();
						}
						if (ly2 < word.getBottom()) {
							ly2 = word.getBottom();
						}
					}
					lineNum++;
					String lineNumber = String.format("%d", lineNum);
					builder.append("\n\t<span class='ocr_line' id='line_"
							+ lineNumber + "' title='bbox " + lx1 + " " + lx2
							+ " " + ly1 + " " + ly2 + "; baseline 0 -8'>");
					// iterate through the linked list, line, for the list of
					// all the word in the line
					for (int b : line) { // for loop for adding a line
						Word word = wordList.get(b);
						// appends the string to the file
						wordNum++;
						String wordNumber = String.format("word_%d", wordNum);
						int a1 = word.getLeft();
						int a2 = word.getRight();
						int b1 = word.getTop();
						int b2 = word.getBottom();
						builder.append("\n\t\t<span class='ocr_word' id='"
								+ wordNumber + "' title='bbox " + a1 + " " + a2
								+ " " + b1 + " " + b2 + " "
								+ ";  x_wconf 99' lang='eng' dir='ltr'>"
								+ word.getWord() + "</span>");
						// try and finally to add a word to the file

					}// end forloop for adding words
					builder.append("</span>");
					// addes a closing tag to the line
				}// end for loop for iterating through lines
				builder.append("</span>");
				// addes a closing tag to the Block
			}// end for loop adding a block to the page
			// append the footer to the page
			builder.append(footer);
			// File will create a file per page in the filepath location
			PrintWriter out;
			try {
				out = new PrintWriter(filePath);
				out.println(builder.toString());
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				File sourceFile = new File(_workFolder+baseName+"-"+String.format("%03d", page)+".pbm");
				File destFile = new File(_doneFolder+baseName+"-"+String.format("%03d", page)+".pbm");
				Files.copy(sourceFile.toPath(), destFile.toPath(),
						StandardCopyOption.REPLACE_EXISTING);// ,REPLACE_EXISTING,COPY_ATTRIBUTES
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// // for loop for one page
	}// end outputFile
}// end postPorocess Class

