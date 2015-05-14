package pdfClear;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class LineParse implements LineParseInterface {
	private int curLine;// index of current last line
	private int _pic = 60;
	private String _workFolder = "/home/chrisc/Documents/School/java/labs/pdfClear/pdfClear/src/pdfClear/jnk/";
	private String _baseName = "6953718-010";
	private ArrayList<Word> _wordList;
	private WordBlock _block;

	@Override
	public ArrayList<LinkedList<Integer>> sortBlock(ArrayList<Word> wordList,
			WordBlock block) {
		_wordList = wordList;
		_block = block;
		int[] included = block.getIncluded();
		curLine = 1;
		int topLine = block.getTop();
		int height = block.getHeight();
		ArrayList<LinkedList<Integer>> lines = new ArrayList<LinkedList<Integer>>();
		lines.add(new LinkedList<Integer>());
		QuickSort quick = new QuickSort();
		quick.sort(included, wordList);// sort included by x1
		int[] midY = new int[height];// use in getLine() method to find which
										// line a word belongs to
		int[] mask = new int[height];
		for (int i : included) {
			int y2 = wordList.get(i).getBottom() - topLine;
			int y1 = wordList.get(i).getTop() - topLine;
			int lineIndex = getLine(y1, y2, midY, 1);// get index of the line
														// the word belongs to,
														// return (last line's
														// index) +1 if this
														// word belongs to a new
														// line
			if (midY[lineIndex] == 0) {
				midY[lineIndex] = (y1 + y2) / 2;// if this word belongs to a new
												// line, save midY for future
												// comparison
			}
			LinkedList<Integer> link = new LinkedList<Integer>();
			if (lines.size() <= lineIndex) {
				link.add(i);
				int indexInArray = 1;
				// find index of this line in lines array
				while (true) {
					// if this line has highest y1, add it to lines array at the
					// bottom
					if (indexInArray >= lines.size()) {
						lines.add(link);
						mask[lineIndex] = lineIndex;
						break;
					}
					int indexOfFirstElement = lines.get(indexInArray)
							.getFirst();
					if (wordList.get(indexOfFirstElement).getTop() >= wordList
							.get(link.getFirst()).getTop()) {
						lines.add(indexInArray, link);
						// since indexes of lines in lines array will change if
						// a new line is inserted in the middle,
						// we need an array called mask to keep track of the
						// real indexes.
						for (int cur = 0; cur < lines.size(); cur++)
							if (mask[cur] >= indexInArray) {
								// indexes of lines that are >= the newly
								// inserted line's index
								// will be increased by 1
								mask[cur] = mask[cur] + 1;
							}
						mask[lineIndex] = indexInArray;
						break;
					}
					indexInArray++;
				}
			} else {
				lines.get(mask[lineIndex]).add(i);// add the word to the line it
													// belongs to
				makePic(lines);
			}

		}
		lines.remove(0);
		return lines;

	}

	private int getLine(int y1, int y2, int[] midY, int id) {
		if (midY[1] == 0 || id == midY.length)
			return curLine++;
		if (midY[id] > y1 && midY[id] < y2)
			return id;
		return getLine(y1, y2, midY, ++id);
	}

	private void makePic(ArrayList<LinkedList<Integer>> lines) {
	/*	// for testing only
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(_workFolder + _baseName + ".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Graphics2D g2 = img.createGraphics();
		g2.setStroke(new BasicStroke(5));
		g2.setColor(Color.red);
		g2.drawRoundRect(_block.getLeft(), _block.getTop(), _block.getRight()
				- _block.getLeft(), _block.getBottom() - _block.getTop(), 5, 5);
		// ArrayList<LinkedList<Integer>> lines = _block.getLines();
		// for(LinkedList<Integer> line:lines){
		// System.out.println(lines.size());)
		for (LinkedList<Integer> line : lines) {
			if (!line.isEmpty()) {
				int x1 = _wordList.get(line.get(0)).getLeft();
				int x2 = _wordList.get(line.get(0)).getRight();
				int y1 = _wordList.get(line.get(0)).getTop();
				int y2 = _wordList.get(line.get(0)).getBottom();

				g2.setStroke(new BasicStroke(2));
				g2.setColor(Color.green);
				for (int i = 0; i < line.size(); i++) {
					Word word = _wordList.get(line.get(i));
					if (x1 > word.getLeft()) {
						x1 = word.getLeft();
					}
					if (x2 < word.getRight()) {
						x2 = word.getRight();
					}
					if (y1 > word.getTop()) {
						y1 = word.getTop();
					}
					if (y2 < word.getBottom()) {
						y2 = word.getBottom();
					}

					g2.drawRoundRect(word.getLeft(), word.getTop(),
							word.getRight() - word.getLeft(), word.getBottom()
									- word.getTop(), 5, 5);
				}
				g2.setStroke(new BasicStroke(2));
				g2.setColor(Color.blue);
				g2.drawRoundRect(x1, y1, x2 - x1, y2 - y1, 5, 5);
			}
		}

		g2.dispose();
		try {
			_pic++;
			ImageIO.write(img, "JPG", new File(_workFolder + _baseName
					+ "_vis_" + String.format("%05d", _pic) + ".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	private class QuickSort {
		public void sort(int[] included, ArrayList<Word> wordList) {
			sort(included, 0, included.length - 1, wordList);
		}

		private void sort(int[] included, int lo, int hi,
				ArrayList<Word> wordList) {
			if (hi <= lo)
				return;
			int mid = partition(included, lo, hi, wordList);
			sort(included, lo, mid - 1, wordList);
			sort(included, mid + 1, hi, wordList);
		}

		private int partition(int[] included, int first, int last,
				ArrayList<Word> wordList) {
			int i = first + 1;
			int j = last - 1;
			int mid = (first + last) / 2;
			sortFirstMiddleLast(included, first, mid, last, wordList);
			if (last - first == 2)
				return first + 1;
			if (last - first <= 1)
				return first;
			swap(included, mid, last);
			Word pivot = wordList.get(included[last]);
			while (i <= j) {
				while (i < last
						&& wordList.get(included[i]).getLeft() < pivot
								.getLeft()) {
					i++;
				}
				while (j > first
						&& wordList.get(included[j]).getLeft() >= pivot
								.getLeft()) {
					j--;
				}
				if (i < j) {
					swap(included, i++, j--);
				} else
					break;
			}
			swap(included, last, i);
			return i;
		}

		private void swap(int[] included, int i, int j) {
			included[i] = included[i] ^ included[j];
			included[j] = included[i] ^ included[j];
			included[i] = included[i] ^ included[j];
		}

		private void sortFirstMiddleLast(int[] included, int first, int mid,
				int last, ArrayList<Word> wordList) {
			if (wordList.get(included[first]).getLeft() > wordList.get(
					included[mid]).getLeft())
				swap(included, first, mid);

			if (wordList.get(included[last]).getLeft() < wordList.get(
					included[mid]).getLeft())
				swap(included, last, mid);

			if (wordList.get(included[first]).getLeft() > wordList.get(
					included[mid]).getLeft())
				swap(included, first, mid);

		}
	}
}