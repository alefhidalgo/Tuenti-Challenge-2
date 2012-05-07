import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Tuenti Programming Contest 
 * Challenge 4: 20 fast 20 furious
 * 
 * @author Alejandro Fernández Hidalgo - alefhidalgo [at] gmail [dot] com
 * @date 01-May-2012
 * 
 */
public class TuentiFastTuentiFurious {
	List<Integer> allValues = null;
	List<Integer> cycleValues = null;
	List<Integer> outCycleValues = null;
	private int sumOutCycle = 0;
	private int sumCycle = 0;

	/**
	 * calcCycles - 
	 * Creates a map with total cycles: (|group1|group2 ... |groupN -> total karts used
	 * Identifies groups out and inner cycles
	 * @param tc
	 * @return
	 */
	private void calcCycles(TestCase tc) {
		allValues = null;
		cycleValues = null;
		sumOutCycle = 0;
		outCycleValues = null;
		sumCycle = 0;
		int groups[] = tc.getGroups();
		int i = 0;
		String firstInCycle = null;
		boolean end = false;
		Map<String, Integer> cycles = new LinkedHashMap<String, Integer>();
		// calculates all cycles (groups per race)
		do {
			int add = 0;
			String cycle = "";
			int cycleSize = 0;
			while (add + groups[i] <= tc.getKarts()
					&& cycleSize < groups.length) {
				add += groups[i];
				cycle += "|" + i;
				cycleSize++;
				i++;
				i = (i == groups.length) ? 0 : i;

			}
			if (cycles.containsKey(cycle)) {
				firstInCycle = cycle;
				end = true;
			} else {
				cycles.put(cycle, add);
			}
		} while (!end);

		List<String> keys = new ArrayList<String>(cycles.keySet());
		int firstIndex = 0;
		for (int j = 0; j < keys.size(); j++) {
			if (keys.get(j).equals(firstInCycle)) {
				firstIndex = j;
				break;
			}
			sumOutCycle += cycles.get(keys.get(j));
		}
		//all values
		allValues = new ArrayList<Integer>(cycles.values());
		//cycle values
		cycleValues = new ArrayList<Integer>(cycles.values()).subList(
				firstIndex, cycles.size());
		//cycle values
		outCycleValues = new ArrayList<Integer>(cycles.values()).subList(0,
				firstIndex);
		
		for (Integer lit : cycleValues) {
			sumCycle += lit;
		}
	}

	/**
	 * calcGasoline
	 * @param tc
	 * @return
	 */
	private long calcGasoline(TestCase tc) {
		calcCycles(tc);
		long totalLitres = 0;
		//races < all cycle values
		if (tc.getRaces() < allValues.size()) {
			for (int i = 0; i < tc.getRaces(); i++) {
				totalLitres += allValues.get(i);
			}
		} else {
			int division = (tc.getRaces() - outCycleValues.size())
					/ (cycleValues.size());
			int module = (tc.getRaces() - outCycleValues.size())
					% (cycleValues.size());
			//raw liters = sumOutCycle + (sumCycle * numRepeatedGroups)
			totalLitres = (sumCycle * division) + sumOutCycle;
			//module liters
			for (int i = 0; i < (module); i++) {
				totalLitres += cycleValues.get(i);
			}

		}
		return totalLitres;
	}

	/**
	 * Inner Class that representing a test case.
	 */
	class TestCase {
		private int races;
		private int karts;
		private int[] groups;

		public int getRaces() {
			return races;
		}

		public void setRaces(int races) {
			this.races = races;
		}

		public int getKarts() {
			return karts;
		}

		public void setKarts(int karts) {
			this.karts = karts;
		}

		public int[] getGroups() {
			return groups;
		}

		public void setGroups(int[] groups) {
			this.groups = groups;
		}

	}

	/**
	 * Main class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TuentiFastTuentiFurious tftf = new TuentiFastTuentiFurious();

		Scanner in = new Scanner(System.in);
		TestCase tc = null;
		int testCases = in.nextInt();
		for (int i = 0; i < testCases; i++) {
			in.nextLine();
			tc = tftf.new TestCase();
			tc.setRaces(in.nextInt());
			tc.setKarts(in.nextInt());
			int nGroups = in.nextInt();
			in.nextLine();
			int groups[] = new int[nGroups];
			for (int j = 0; j < nGroups; j++) {
				groups[j] = in.nextInt();
			}
			tc.setGroups(groups);
			System.out.println(tftf.calcGasoline(tc));

		}

	}

}
