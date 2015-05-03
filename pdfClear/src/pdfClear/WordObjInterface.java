import java.util.List;


public interface WordObjInterface{
	/**returns pixel coordinate of left side of bbox
	 * @return int: pixel location of x1 */
	public int getLeft();	
	/**returns effective midpoint of bbox. for now "(y1+y2)/2" is sufficient
	 * @return int: simple mean y position */
	public int getMid();	
	/** further public methods may be needed as discovered. You will also need to include methods for use in PostProcess.java*/
}