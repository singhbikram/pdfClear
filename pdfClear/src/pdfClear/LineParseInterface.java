package pdfClear;

import java.util.ArrayList;
import java.util.LinkedList;


public interface LineParseInterface{
	/*
	 * sorts words into an array of lines of text, each represented by a linkedlist of in-order words 
	 */
	public ArrayList<LinkedList<Integer>> sortBlock(ArrayList<Word> wordList,int[] included);
}