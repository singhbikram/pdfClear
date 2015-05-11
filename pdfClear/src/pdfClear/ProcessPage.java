package pdfClear;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ProcessPage implements ProcessPageInterface {

	private String _workFolder;
	private String _baseName="";
	private ArrayList<WordBlock> _wordBlocks;
	private ArrayList<Word> _wordList;
	private int _threshold_y = 0;
	private int _threshold_x = 0;
	private int _avgWordHeight = 0;
	private LineParse _lineParser = new LineParse();

	public ProcessPage(String workFolder) {
		_workFolder = workFolder;
	}

	@Override
	public ArrayList<WordBlock> processPage(ArrayList<Word> wordList) {
		_wordList=wordList;
		_wordBlocks = new ArrayList<WordBlock>();
		// initial clustering
		_avgWordHeight = sortToBlocks(wordList);
		// logical clustering
		
		//FOR TESTING: print out picture of page and bounding boxes
		makePic();
		// send off for line analysis and word sorting per block
		for (WordBlock block : _wordBlocks) {
			block.setLines(_lineParser.sortBlock(wordList, block));
		}
		// block typing
		// reorder wordLayout into read order
		return _wordBlocks;
	}

	public ArrayList<WordBlock> processPage(ArrayList<Word> wordList, String baseName) {
		_baseName=baseName;
		processPage(wordList);
		return _wordBlocks;
	}
	
	private void makePic(){
		//for testing only
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(_workFolder+_baseName+".gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Graphics2D g2 = img.createGraphics();
		g2.setColor( Color.red );
		for(WordBlock block : _wordBlocks) {
//		    g2.fillRect( 300, 300, 300, 300);
			g2.drawRoundRect(block.getLeft(), block.getTop(), block.getRight()-block.getLeft(), block.getBottom()-block.getTop(), 5, 5);
		}
	    g2.dispose( );
		try {
			ImageIO.write(img, "GIF", new File(_workFolder+_baseName+"_vis.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int sortToBlocks(ArrayList<Word> wordList) {
		int wordCount = wordList.size();
		int totalWordHeight = 0;
		wordLoop: for (int i = 0; i < wordCount; i++) {
			Word currWord = wordList.get(i);
			System.out.println("h: "+currWord.getHeight());
			totalWordHeight += currWord.getHeight();
			int w_left = currWord.getLeft();
			int w_right = currWord.getRight();
			int w_top = currWord.getTop();
			int w_bot = currWord.getBottom();
			for (WordBlock block : _wordBlocks) {
				int b_left = block.getLeft();
				int b_right = block.getRight();
				int b_top = block.getTop();
				int b_bot = block.getBottom();
				if (overlaps(b_left, b_right, b_top, b_bot, w_left, w_right,
						w_top, w_bot)) {
					//System.out.println("adding block");
					block.addWord(currWord, i);
					continue wordLoop;
				}
			}
			_wordBlocks.add(new WordBlock(currWord, i));
		}
		System.out.println("blocks: "+_wordBlocks.size());
		// merge blocks
		infLoop: while (true) {
			int blockCount = _wordBlocks.size();
			System.out.println("blocksm: "+_wordBlocks.size());
			for (int i = 0; i < blockCount; i++) {
				WordBlock aBlock = _wordBlocks.get(i);
				int a_left = aBlock.getLeft();
				int a_right = aBlock.getRight();
				int a_top = aBlock.getTop();
				int a_bot = aBlock.getBottom();
				for (int b = 0; b < blockCount; b++) {
					if(i == b){
						continue;
					}
					WordBlock bBlock = _wordBlocks.get(b);
					int b_left = bBlock.getLeft();
					int b_right = bBlock.getRight();
					int b_top = bBlock.getTop();
					int b_bot = bBlock.getBottom();
					if (overlaps(a_left, a_right, a_top, a_bot, b_left,
							b_right, b_top, b_bot)) {
						bBlock.mergeBlock(aBlock);
						_wordBlocks.set(i,
								_wordBlocks.get(blockCount - 1));
						_wordBlocks.remove(blockCount - 1);
						continue infLoop;
					}
				}
			}
			break;
		}
		return totalWordHeight / wordList.size();

	}

	private Boolean overlaps(int ax1, int ax2, int ay1, int ay2, int bx1,
			int bx2, int by1, int by2) {
		ax1 -= _threshold_x;
		ax2 += _threshold_x;
		ay1 -= _threshold_y;
		ay2 += _threshold_y;
		if (((ax1 < bx1 && bx1 < ax2) || (ax1 < bx2 && bx2 < ax2))
				&& ((ay1 < by1 && by1 < ay2) || (ay1 < by2 && by2 < ay2))) {
			// overlap, return true
			return true;
		}
		return false;
	}

}
