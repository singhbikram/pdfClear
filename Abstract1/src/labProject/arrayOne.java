/**
 * 
 */
package labProject;

import java.util.Random;

/**
 * @author Bikram
 *
 */
public class arrayOne {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] arrayRand = new int[10];
		
		Random rand = new Random();
		
		 
		for (int a=0 ; a<arrayRand.length ; a++) {
			
			int num = rand.nextInt(50);
			arrayRand[a] = num;
			
		}// initialing array
		
		System.out.println("Array with 10 elements:");
		
		for(int b : arrayRand){
			
			System.out.printf("|%2d|\n", b);
			System.out.println(" -- ");
			
		}// print array
		

	}

}
