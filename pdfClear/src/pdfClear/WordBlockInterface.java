package pdfClear;

import java.util.ArrayList;
import java.util.LinkedList;


public interface WordBlockInterface{
	//put out int array of wordList indexes included in this block
	public int[] getIncluded();
	//accepts ordered and sorted lines and words. 
	public Void setLines(ArrayList<LinkedList<Integer>> lines);
	//outputs arraylist of lines represented by linkedlists of ordered indexes for wordList. 
	public ArrayList<LinkedList<Integer>> getLines();
	//outputs type of block. 
	public int getType();
	//outputs block's x1. 
	public int getx1();
	//outputs block's x2. 
	public int getx2();
	//outputs block's y1. 
	public int gety1();
	//outputs block's y2. 
	public int gety2();
}