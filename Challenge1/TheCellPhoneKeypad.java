import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Tuenti Programming Contest Challenge 1: The cell phone keypad *
 * 
 * @author Alejandro Fernández Hidalgo - alefhidalgo [at] gmail [dot] com
 * @date 28-Apr-2012
 * 
 */
public class TheCellPhoneKeypad {

	private Map<String, Integer> keysMap = new LinkedHashMap<String, Integer>();
	private int[][] costMatrix;
	private static final int COST_UP_DOWN = 300;
	private static final int COST_DIAGONAL = 350;
	private static final int COST_LEFT_RIGHT = 200;
	private static final int COST_PUSH = 100;
	private static final int COST_WAIT_SAME_KEY = 500;

	public TheCellPhoneKeypad() {
		loadKeyMap();
		loadMatrix();
	}

	/**
	 * loadKeyMap - relation between keypads and the costMatrix index
	 */
	void loadKeyMap() {
		keysMap.put(" 1", 0);
		keysMap.put("ABC2", 1);
		keysMap.put("DEF3", 2);
		keysMap.put("GHI4", 3);
		keysMap.put("JKL5", 4);
		keysMap.put("MNO6", 5);
		keysMap.put("PQRS7", 6);
		keysMap.put("TUV8", 7);
		keysMap.put("WXYZ9", 8);
		keysMap.put("0", 9);
		keysMap.put("^", 10);
	}

	/**
	 * Load cost Matrix using Floyd Alghoritm
	 */
	public void loadMatrix() {
		int n = 11;
		costMatrix = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j)
					costMatrix[i][j] = 0;
				else
					costMatrix[i][j] = -1;

			}
		}
		// cost filling
		setCost(0, 1, COST_LEFT_RIGHT);
		setCost(0, 3, COST_UP_DOWN);
		setCost(0, 4, COST_DIAGONAL);
		setCost(1, 2, COST_LEFT_RIGHT);
		setCost(1, 3, COST_DIAGONAL);
		setCost(1, 4, COST_UP_DOWN);
		setCost(1, 5, COST_DIAGONAL);
		setCost(2, 4, COST_DIAGONAL);
		setCost(2, 5, COST_UP_DOWN);
		setCost(3, 4, COST_LEFT_RIGHT);
		setCost(3, 6, COST_UP_DOWN);
		setCost(3, 7, COST_DIAGONAL);
		setCost(4, 5, COST_LEFT_RIGHT);
		setCost(4, 6, COST_DIAGONAL);
		setCost(4, 7, COST_UP_DOWN);
		setCost(4, 8, COST_DIAGONAL);
		setCost(5, 7, COST_DIAGONAL);
		setCost(5, 8, COST_UP_DOWN);
		setCost(6, 7, COST_LEFT_RIGHT);
		setCost(6, 9, COST_DIAGONAL);
		setCost(7, 8, COST_LEFT_RIGHT);
		setCost(7, 9, COST_UP_DOWN);
		setCost(7, 10, COST_DIAGONAL);
		setCost(8, 9, COST_DIAGONAL);
		setCost(8, 10, COST_UP_DOWN);
		setCost(9, 10, COST_LEFT_RIGHT);

		for (int k = 0; k <= n - 1; k++) {
			for (int i = 0; i <= n - 1; i++) {
				for (int j = 0; j <= n - 1; j++) {
					if ((costMatrix[i][k] != -1) && (costMatrix[k][j] != -1)) {
						costMatrix[i][j] = doFloyd(costMatrix[i][j],
								costMatrix[i][k] + costMatrix[k][j]);
					}
				}
			}

		}
	}

	public void setCost(int i, int j, int cost) {
		costMatrix[i][j] = cost;
		costMatrix[j][i] = cost;
	}

	public int doFloyd(int a, int b) {
		if ((a == -1) && (b == -1))
			return -1;
		else if (a == -1)
			return b;
		else if (b == -1)
			return a;
		else if (a > b)
			return b;
		else
			return a;

	}

	/**
	 * getKeyboadMapKey - Finds the relacted mapKey
	 * 
	 * @param character
	 * @return
	 */
	public String getKeyboadMapKey(char character) {
		String mapKey = null;
		for (String key : keysMap.keySet()) {
			if (key.contains(String.valueOf(character).toUpperCase()))
				mapKey = key;
		}
		return mapKey;
	}

	/**
	 * calcTotalCost - Test-Case total cost
	 * 
	 * @param typedText
	 * @return
	 */
	public long calcTotalCost(char[] typedText) {
		int padPosFrom = keysMap.get("0"); // 0 pad key
		int lastPadPos = keysMap.get("0"); // 0 pad key
		long cost = 0;
		boolean isLowerCase = true;
		for (char c : typedText) {
			// Push ^ button if it's necessary
			if (Character.isUpperCase(c) && isLowerCase
					|| Character.isLowerCase(c) && !isLowerCase) {
				// cost = move cost + push cost (COST_PUSH)
				cost += costMatrix[padPosFrom][keysMap.get("^")] + COST_PUSH;
				lastPadPos = keysMap.get("^"); // ^ pad key
				padPosFrom = keysMap.get("^"); // ^ pad key
				isLowerCase = !isLowerCase;
			}
			// Get Key to push
			String key = getKeyboadMapKey(c);
			// key index into keysMap
			int padPosTo = keysMap.get(key);
			// character index into the key pad
			int padCharIndex = key.indexOf(Character.toUpperCase(c)) + 1;
			// add the cost
			cost += costMatrix[padPosFrom][padPosTo]; // move cost
			cost += padCharIndex * COST_PUSH; // key press cost
			cost += lastPadPos == padPosTo ? COST_WAIT_SAME_KEY : 0; // same key cost
			padPosFrom = padPosTo;
			lastPadPos = padPosTo;

		}
		return cost;
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TheCellPhoneKeypad tcpk = new TheCellPhoneKeypad();
		Scanner in = new Scanner(System.in);
		int testCases = in.nextInt();
		in.nextLine();
		for (int i = 0; i < testCases; i++) {
			System.out.println(tcpk.calcTotalCost(in.nextLine().toCharArray()));
		}
	}
}
