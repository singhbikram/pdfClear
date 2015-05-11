package pdfClear;

import java.util.ArrayList;
import java.util.LinkedList;

public class WordBlock implements WordBlockInterface {
	private int _x1;
	private int _x2;
	private int _y1;
	private int _y2;
	private int _blockType=0;
	private ArrayList<Integer> _includedWords;
	private ArrayList<LinkedList<Integer>> _lines;

	public WordBlock(Word firstWord,int i){
		_x1=firstWord.getLeft();
		_x2=firstWord.getRight();
		_y1=firstWord.getTop();
		_y2=firstWord.getBottom();
		_includedWords = new ArrayList<Integer>();
		_includedWords.add(i);
	}
	@Override
	public int[] getIncluded() {
		// TODO Auto-generated method stub
		int[] inc = new int[_includedWords.size()];
		for(int i=0;i<_includedWords.size();i++){
			inc[i]=_includedWords.get(i);
		}
		return inc;
	}

	@Override
	public void setLines(ArrayList<LinkedList<Integer>> lines) {
		_lines = lines;
	}

	@Override
	public ArrayList<LinkedList<Integer>> getLines() {
		return _lines;
	}

	@Override
	public int getType() {
		return _blockType;
	}
	public int getLeft() {
		return _x1;
	}
	
	@Override
	public int getRight() {
		return _x2;
	}
	
	@Override
	public int getTop() {
		return _y1;
	}
	
	@Override
	public int getBottom() {
		return _y2;
	}

	@Override
	public void addWord(Word newWord, int i) {
		int x1=newWord.getLeft();
		int x2=newWord.getRight();
		int y1=newWord.getTop();
		int y2=newWord.getBottom();
		//expand bounding box
		joinBBox(x1,x2,y1,y2);
		_includedWords.add(i);
	}
	private void joinBBox(int x1,int x2,int y1,int y2){
		if(x1<_x1){
			_x1=x1;
		}
		if(x2>_x2){
			_x2=x2;
		}
		if(y1<_y1){
			_y1=y1;
		}
		if(y2>_y2){
			_y2=y2;
		}
	}
	@Override
	public void mergeBlock(WordBlock Bblock) {
		int x1=Bblock.getLeft();
		int x2=Bblock.getRight();
		int y1=Bblock.getTop();
		int y2=Bblock.getBottom();
		//expand bounding box
		joinBBox(x1,x2,y1,y2);
		int[] newIncludes = Bblock.getIncluded();
		for(int i: newIncludes){
			_includedWords.add(i);
		}
	}
	@Override
	public int getHeight() {
		return _x2-_x1;
	}
	@Override
	public int getWidth() {
		return _y2-_y1;
	}
	
}
