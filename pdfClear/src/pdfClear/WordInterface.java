package pdfClear;


public interface WordInterface{
	/**returns pixel coordinate of left side of bbox
	 * @return int: pixel location of x1 */
	public int getLeft();	
	/**returns effective midpoint of bbox. for now "(y1+y2)/2" is sufficient
	 * @return int: simple mean y position */
	public int getMid();	
	/*returns right edge of bounding box
	 */
	public int getRight();
	/*returns top edge of bounding box
	 */
	public int getTop();
	/*returns bottom edge of bounding box
	 */
	public int getBottom();
	/*returns bottom edge of bounding box
	 */
	public String getWord();
	/*returns bottom edge of bounding box
	 */
	public int getHeight();
	/** further public methods may be needed as discovered. You will also need to include methods for use in PostProcess.java*/
}