package pdfClear;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PostProcess implements PostProcessInterface {
	private int _outType=0; //type of files to output
	private String _workFolder;
	
	public PostProcess(int outType, String workFolder){
		_outType=outType;
		_workFolder=workFolder;
	}

	@Override
	public void outputFile(ArrayList<Word> wordList,
			ArrayList<WordBlock> wordLayout, String baseName) {
		// TODO Auto-generated method stub
		
	}
	
}