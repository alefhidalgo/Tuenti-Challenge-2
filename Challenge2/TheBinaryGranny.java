import java.math.BigInteger;
import java.util.Scanner;

/**
 * Tuenti Programming Contest 
 * Challenge 2: The binary granny
 * 
 * @author Alejandro Fernández Hidalgo - alefhidalgo [at] gmail [dot] com
 * @date 29-Apr-2012
 * 
 */
public class TheBinaryGranny {

	/**
	 * getMaxOnes
	 * @param n - The number
	 * @return Max num bits: n = a + b; bits(a) + bits(b)
	 */
	public long getMaxOnes(BigInteger n) {		
		BigInteger maxNumber = new BigInteger("2").pow(n.bitLength() - 1).subtract(
				BigInteger.ONE);
		BigInteger complementary = n.subtract(maxNumber);
		long bitCount = complementary.bitCount() + maxNumber.bitCount();
		return bitCount;
	}
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		TheBinaryGranny tbg = new TheBinaryGranny();
		Scanner in = new Scanner(System.in);
		int testCases = in.nextInt();
		in.nextLine();
		for (int i = 0; i < testCases; i++) {
			System.out.println("Case #"+(i+1)+": " + tbg.getMaxOnes(in.nextBigInteger()));
		}

	}
}
