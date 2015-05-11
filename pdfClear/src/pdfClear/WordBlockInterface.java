package pdfClear;

import java.util.ArrayList;
import java.util.LinkedList;


public interface WordBlockInterface{
	//put out int array of wordList indexes included in this block
	public int[] getIncluded();
	//accepts ordered and sorted lines and words. 
	public void setLines(ArrayList<LinkedList<Integer>> lines);
	//outputs type of block. 
	public int getType();
	//outputs block's x1. 
	public int getLeft();
	//outputs block's x2. 
	public int getRight();
	//outputs block's y1. 
	public int getTop();
	//outputs block's y2 
	public int getBottom();
	//outputs block's y2 
	public int getHeight();
	//outputs block's y2 
	public int getWidth();
	//add word to block
	public void addWord(Word currWord, int i);
	//absorb block B into block A
	public void mergeBlock(WordBlock Bblock);
	/*BIKRAM:this is what you need to output with
	 * outputs arraylist of lines, represented by linkedlists of ordered indexes for wordList. 
	 * for(LinkedList<Integer> line: lines){
	 * 	//itterate linkedlist for word index (i)
	 * 	Word word = wordlist.get(i)
	 *  //get bounding box of line, word, etc
	 * }
	 */
	public ArrayList<LinkedList<Integer>> getLines();
}