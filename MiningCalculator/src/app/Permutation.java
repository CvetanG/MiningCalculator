package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutation {

	private static void variationsWithRepetition(List<int[]> listResults, int[] dataToCombine, int[] result, int count) {
		if (count < result.length) {
			for (int i = 0; i < dataToCombine.length; i++) {
				result[count] = dataToCombine[i];
				variationsWithRepetition(listResults, dataToCombine, result, count + 1);
			}
		} else {
			listResults.add(result.clone());
		}
	}

	public static void main(String[] args) throws IOException {
		List<int[]> listResults = new ArrayList<>();
		int[] dataToCombine = { 1, 2, 3, 4 };
		int[] result = new int[4];
		variationsWithRepetition(listResults, dataToCombine, result, 0);

		for (int[] rep : listResults) {
			System.out.println(Arrays.toString(rep));
		}
	}

}
