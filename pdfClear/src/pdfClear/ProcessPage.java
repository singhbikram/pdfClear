package pdfClear;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

public class ProcessPage implements ProcessPageInterface {

	private String _workFolder;
	private String _doneFolder = "/home/chrisc/Documents/School/java/labs/pdfClear/pdfClear/src/pdfClear/done/";
	private String _baseName = "";
	private ArrayList<WordBlock> _wordBlocks;
	private ArrayList<Word> _wordList;
	private int _threshold_y = 38;
	private int _threshold_x = 43;
	private int _avgWordHeight = 0;
	private int _pic = 0;
	private LineParse _lineParser = new LineParse();

	public ProcessPage(String workFolder) {
		_workFolder = workFolder;
	}

	@Override
	public ArrayList<WordBlock> processPage(ArrayList<Word> wordList) {
		_wordList = wordList;
		_wordBlocks = new ArrayList<WordBlock>();
		// initial clustering
		_avgWordHeight = sortToBlocks(wordList);

		// logical clustering

		// FOR TESTING: print out picture of page and bounding boxes
		// send off for line analysis and word sorting per block
		for (WordBlock block : _wordBlocks) {
			ArrayList<LinkedList<Integer>> lineList = _lineParser.sortBlock(
					wordList, block);
			block.setLines(lineList);
			int[] included = block.getIncluded();
			for (LinkedList<Integer> line : lineList) {
				for (int i = 0; i < line.size(); i++) {
				}
			}
		}

		// block typing
//		makePic();
		// reorder wordLayout into read order
		return _wordBlocks;
	}
/*
	private void makePic() {
		// for testing only
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(_workFolder + _baseName + ".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Graphics2D g2 = img.createGraphics();
		for (WordBlock block : _wordBlocks) {
			g2.setStroke(new BasicStroke(5));
			g2.setColor(Color.red);
			g2.drawRoundRect(block.getLeft(), block.getTop(), block.getRight()
					- block.getLeft(), block.getBottom() - block.getTop(), 5, 5);
			ArrayList<LinkedList<Integer>> lines = block.getLines();
			// for(LinkedList<Integer> line:lines){
			for (LinkedList<Integer> line : lines) {
				int x1 = _wordList.get(line.get(0)).getLeft();
				int x2 = _wordList.get(line.get(0)).getRight();
				int y1 = _wordList.get(line.get(0)).getTop();
				int y2 = _wordList.get(line.get(0)).getBottom();
				for (int i = 0; i < line.size(); i++) {
					if (x1 > _wordList.get(line.get(i)).getLeft()) {
						x1 = _wordList.get(line.get(i)).getLeft();
					}
					if (x2 < _wordList.get(line.get(i)).getRight()) {
						x2 = _wordList.get(line.get(i)).getRight();
					}
					if (y1 > _wordList.get(line.get(i)).getTop()) {
						y1 = _wordList.get(line.get(i)).getTop();
					}
					if (y2 < _wordList.get(line.get(i)).getBottom()) {
						y2 = _wordList.get(line.get(i)).getBottom();
					}
				}
				g2.setStroke(new BasicStroke(3));
				g2.setColor(Color.blue);
				g2.drawRoundRect(x1, y1, x2 - x1, y2 - y1, 5, 5);
			}
		}
		g2.dispose();
		try {
			ImageIO.write(img, "JPG", new File(_doneFolder + _baseName
					+ "_vis.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	public ArrayList<WordBlock> processPage(ArrayList<Word> wordList,
			String baseName) {
		_baseName = baseName;
		processPage(wordList);
		return _wordBlocks;
	}

	private int sortToBlocks(ArrayList<Word> wordList) {
		int wordCount = wordList.size();
		if (wordCount < 1) {
			return 1;
		}
		int[] wordOrder = new int[wordList.size()];
		LinkedList<Integer> rng = new LinkedList<Integer>();
		for (int i = 0; i < wordList.size(); i++) {
			rng.add(i);
		}
		Random seed = new Random();
		int c = 0;
		while (rng.size() > 0) {
			int nInt = seed.nextInt(rng.size());
			wordOrder[c] = rng.remove(nInt);
			c++;
		}

		int totalWordHeight = 0;
		wordLoop: for (int i = 0; i < wordCount; i++) {
			Word currWord = wordList.get(wordOrder[i]);
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
					block.addWord(currWord, i);
					continue wordLoop;
				}
			}
			_wordBlocks.add(new WordBlock(currWord, i));
		}
		// merge blocks
		infLoop: while (true) {
			int blockCount = _wordBlocks.size();
			for (int i = 0; i < blockCount; i++) {
				WordBlock aBlock = _wordBlocks.get(i);
				int a_left = aBlock.getLeft();
				int a_right = aBlock.getRight();
				int a_top = aBlock.getTop();
				int a_bot = aBlock.getBottom();
				for (int b = 0; b < blockCount; b++) {
					if (i == b) {
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
						_wordBlocks.set(i, _wordBlocks.get(blockCount - 1));
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
