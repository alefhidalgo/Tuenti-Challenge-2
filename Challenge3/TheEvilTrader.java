import java.util.Scanner;

/**
 * Tuenti Programming Contest 
 * Challenge 3: The Evil Trader
 * 
 * @author Alejandro Fernández Hidalgo - alefhidalgo [at] gmail [dot] com
 * @date 29-Apr-2012
 * 
 */
public class TheEvilTrader {

	private int maxProfit;
	private int maxProfitBeginTime;
	private int maxProfitEndTime;
	private int newMaxProfitBeginValue;
	private int newMaxProfitBeginTime;

	/**
	 * read tradeValues and time and calculates maximum profit
	 * @param tradeValue
	 * @param t
	 */
	public void read(int tradeValue, int t) {
		if (t == 0) {
			newMaxProfitBeginValue = tradeValue;
			newMaxProfitBeginTime = t;
			return;
		}
		if (tradeValue - newMaxProfitBeginValue > maxProfit) {
			maxProfit = tradeValue - newMaxProfitBeginValue;
			maxProfitBeginTime = newMaxProfitBeginTime;
			maxProfitEndTime = t;
		} else if (tradeValue < newMaxProfitBeginValue) {
			newMaxProfitBeginValue = tradeValue;
			newMaxProfitBeginTime = t;
		}

	}

	public int getMaxProfit() {
		return maxProfit;
	}

	public int getMaxProfitBeginTime() {
		return maxProfitBeginTime;
	}

	public int getMaxProfitEndTime() {
		return maxProfitEndTime;
	}
	
	/**
	 * Main Method
	 * @param args
	 */
	public static void main(String[] args) {
		TheEvilTrader tet = new TheEvilTrader();
		Scanner in = new Scanner(System.in);
		int time = 0;
		while (in.hasNextInt()) {
			tet.read(in.nextInt(), time);
			time += 100;
		}
		System.out.println(tet.getMaxProfitBeginTime() + " "
				+ tet.getMaxProfitEndTime() + " " + tet.getMaxProfit());
	}
}
