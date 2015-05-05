package pdfClear;

import java.util.ArrayList;

public class PostProcess implements PostProcessInterface {
	private int _outType=0; //type of files to output
	private String _workFolder;
	private String _doneFolder;
	
	public PostProcess(int outType, String workFolder, String doneFolder){
		_outType=outType;
		_workFolder=workFolder;
		_doneFolder=doneFolder;
	}

	@Override
	public void outputFile(ArrayList<ArrayList<Word>> wordLists,ArrayList<ArrayList<WordBlock>> wordBlocks, String baseName){
		// TODO Auto-generated method stub
		
	}
	
}