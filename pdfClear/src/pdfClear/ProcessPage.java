package pdfClear;

import java.util.ArrayList;

public class ProcessPage implements ProcessPageInterface {

	private String _workFolder;
	
	public ProcessPage(String workFolder) {
		_workFolder = workFolder;
	}
	
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