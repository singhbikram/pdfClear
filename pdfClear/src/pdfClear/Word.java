package pdfClear;

/**
 * @author Bikram Singh
 *
 */
public class Word implements WordInterface {
	private int _y1=0;
	private int _y2=0;
	private int _x1 =0;
	private int _x2 =0;
	private int _h =0;
	private int _ymid =0;
	private String _data = null;
	private String _title = null;
	//private List<Character> high =Arrays.asList('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','b','d','f','h','I','j','k','l','t');
	//private List<Character> low =Arrays.asList('g','j','p','q','y');
	
	/**
	 * @param x1 is the x coordinate of the upper left corner of the word
	 * @param y1 is the y coordinate of the upper left corner of the word
	 * @param x2 is the x coordinate of the lower right corner of the word
	 * @param y2 is the y coordinate of the lower right corner of the word
	 * @param title is the 
	 */
	public Word(int x1,int y1,int x2,int y2,String title)// int i, int j, int k, int l, String s)
	{
		_x1=x1;
		_x2=x2;
		_y1=y1;
		_y2=y2;
		_title=title;
		for (int i = 0; i < title.length(); i++){
		    char c = title.charAt(i);        
		    //Process char
		}
		_h=_y2-_y1;
		_ymid = (y1+y2)/2;
	}

	@Override
	public int getLeft() {
		return _y1;
	}
	
	@Override
	public int getRight() {
		return _y2;
	}
	
	@Override
	public int getTop() {
		return _x1;
	}
	
	@Override
	public int getBottom() {
		return _x2;
	}

	@Override
	public int getMid() {
		return _ymid;
	}

	@Override
	public int getHeight() {
		return _h;
	}

	public String toString() {
	    return "Word: " + _y1 + " " + _y2 + " " + _x1 + " " + _x2 + " " + _data +" "+ _title;
	  }

}
