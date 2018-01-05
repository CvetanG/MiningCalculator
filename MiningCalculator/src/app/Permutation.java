package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Permutation {

	private static void createVariationsRepetition(List<int[]> listResults, int[] dataToCombine, int[] result, int count) {
		if (count < result.length) {
			for (int i = 0; i < dataToCombine.length; i++) {
				result[count] = dataToCombine[i];
				createVariationsRepetition(listResults, dataToCombine, result, count + 1);
			}
		} else {
			listResults.add(result.clone());
		}
	}
	
	private static void createVariationsRepetitionListStrings(List<String[]> listResults, String[] result, int count) {
		if (count < result.length) {
			for (int i = 0; i < Utils.allPlanList.size(); i++) {
				result[count] = Utils.allPlanList.get(i).getPlanName();
				createVariationsRepetitionListStrings(listResults, result, count + 1);
			}
		} else {
			listResults.add(result.clone());
		}
	}
	
	/*
	 * https://stackoverflow.com/questions/24460480/permutation-of-an-arraylist-of-numbers-using-recursion
	public static List<List<Integer>> listPermutations(List<Integer> list) {

	    if (list.size() == 0) {
	        List<List<Integer>> result = new ArrayList<List<Integer>>();
	        result.add(new ArrayList<Integer>());
	        return result;
	    }

	    List<List<Integer>> returnMe = new ArrayList<List<Integer>>();

	    Integer firstElement = list.remove(0);

	    List<List<Integer>> recursiveReturn = listPermutations(list);
	    for (List<Integer> li : recursiveReturn) {

	        for (int index = 0; index <= li.size(); index++) {
	            List<Integer> temp = new ArrayList<Integer>(li);
	            temp.add(index, firstElement);
	            returnMe.add(temp);
	        }

	    }
	    return returnMe;
	}
	*/
	
	public static List<List<String>> listPermutations(List<InvestmentPlan> list) {

	    if (list.size() == 0) {
	        List<List<String>> result = new ArrayList<List<String>>();
	        result.add(new ArrayList<String>());
	        return result;
	    }

	    List<List<String>> returnMe = new ArrayList<List<String>>();

	    String firstElement = list.get(0).getPlanName();
//	    list.remove(0);

	    List<List<String>> recursiveReturn = listPermutations(list);
	    for (List<String> li : recursiveReturn) {

	        for (int index = 0; index <= li.size(); index++) {
	            List<String> temp = new ArrayList<String>(li);
	            temp.add(index, firstElement);
	            returnMe.add(temp);
	        }

	    }
	    return returnMe;
	}
	
	
	static int topSum = Integer.MIN_VALUE;
	static int[] topArray = null;
	private static void printVarTopSum(List<int[]> listResults, int[] dataToCombine, int[] result, int count) {
		
				
		topArray = new int[result.length];
		
		if (count < result.length) {
			for (int i = 0; i < dataToCombine.length; i++) {
				result[count] = dataToCombine[i];
				printVarTopSum(listResults, dataToCombine, result, count + 1);
			}
		} else {
			listResults.add(result.clone());
			int tempSum = IntStream.of(result).sum();
			if (tempSum > topSum) {
				topSum = tempSum;
				topArray = result;
			}
		}
		
	}

	public static void main(String[] args) throws IOException {
		/*List<int[]> listResults = new ArrayList<>();
		int[] dataToCombine = { 1, 2, 3, 4 };
		String[] result = new String[3];*/
//		createVariationsRepetition(listResults, dataToCombine, result, 0);
//		for (int[] rep : listResults) {
//			System.out.println(Arrays.toString(rep));
//		}
		
		/*
		List<int[]> listResults = new ArrayList<>();
		int[] dataToCombine = { 1, 2, 3, 4 };
		String[] result = new String[3]
		printVarTopSum(listResults, dataToCombine, result, 0);
		System.out.println(Arrays.toString(topArray) + "\nSum: " + topSum);
		*/
		/*
		List<String[]> listResults = new ArrayList<>();
		String[] result = new String[2];
		createVariationsRepetitionListStrings(listResults, result, 0);
		for (String[] rep : listResults) {
			System.out.println(Arrays.toString(rep));
		}
		*/
		
		List<List<String>> listResults = listPermutations(Utils.allPlanList);
		for (List<String> rep : listResults) {
			for (String string : rep) {
				System.out.printf(string + " ");
			}
			System.out.println();
		}
	}

}
