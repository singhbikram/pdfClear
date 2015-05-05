package pdfClear;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessDoc implements ProcessDocInterface {
	@Override
	public ArrayList<WordBlock> processPage(ArrayList<Word> wordList) {
		ArrayList<WordBlock> wordLayout = new ArrayList<WordBlock>();
		LineParse parser = new LineParse();
		//initial clustering
		//logical clustering
		//send off for line analysis and word sorting per block
		for(WordBlock block: wordLayout){
			  block.setLines(parser.sortBlock(wordList,block.getIncluded()));
			}
		//block typing
		//reorder wordLayout into read order
		return wordLayout;
	}

	
}