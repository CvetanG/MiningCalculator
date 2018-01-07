package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutation {
/*
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
	*/
	/*
	private static void createVarRepListStrings(List<List<String>> listResults, List<String> dataToCombine,
			String[] result, int count) {
		
		if (count < result.length) {
			for (int i = 0; i < dataToCombine.size(); i++) {
				result[count] = dataToCombine.get(i);
				createVarRepListStrings(listResults, dataToCombine, result, count + 1);
			}
		} else {
			listResults.add(Arrays.asList(result.clone()));
		}
		
	}
	*/
	private static void createVarRepListStrings(List<List<String>> listResults, String[] result, int count) {
		
		if (count < result.length) {
			for (int i = 0; i < Utils.allPlanList.size(); i++) {
				result[count] = Utils.allPlanList.get(i).getPlanName();
				createVarRepListStrings(listResults, result, count + 1);
			}
		} else {
			listResults.add(Arrays.asList(result.clone()));
		}
		
	}

	/*private static List<List<String>> listPermutationsString(List<InvestmentPlan> permStrList) {

	    if (permStrList.size() == 0) {
	        List<List<String>> result = new ArrayList<List<String>>();
	        result.add(new ArrayList<String>());
	        return result;
	    }

	    List<List<String>> returnList = new ArrayList<List<String>>();

	    String firstElement = permStrList.get(0).getPlanName();
	    permStrList.remove(0);

	    List<List<String>> recursiveReturn = listPermutationsString(permStrList);
	    for (List<String> intList : recursiveReturn) {

	        for (int index = 0; index <= intList.size(); index++) {
	            List<String> temp = new ArrayList<String>(intList);
	            temp.add(index, firstElement);
	            returnList.add(temp);
	        }

	    }
	    return returnList;
	}*/
	/*
	public static List<List<Integer>> listPermutations(List<Integer> permIntList, int count) {

	    if (count == permIntList.size()) {
	        List<List<Integer>> result = new ArrayList<List<Integer>>();
	        result.add(new ArrayList<Integer>());
	        return result;
	    }

	    List<List<Integer>> returnList = new ArrayList<List<Integer>>();

	    Integer firstElement = permIntList.get(count);

	    List<List<Integer>> recursiveReturn = listPermutations(permIntList, count + 1);
	    for (List<Integer> intList : recursiveReturn) {

	        for (int index = 0; index <= intList.size(); index++) {
	            List<Integer> temp = new ArrayList<Integer>(intList);
	            temp.add(index, firstElement);
	            returnList.add(temp);
	        }

	    }
	    return returnList;
	}
	*/
	
	/*
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
*/
	public static void main(String[] args) throws IOException {
		/*
		List<int[]> listResults = new ArrayList<>();
		int[] dataToCombine = { 1, 2, 3, 4 };
		int[] result = new int[3];
		createVariationsRepetition(listResults, dataToCombine, result, 0);
		for (int[] rep : listResults) {
			System.out.println(Arrays.toString(rep));
		}
		*/
		
		List<String> dataToCombine = new ArrayList<>();
		dataToCombine.add("plane_01");
		dataToCombine.add("plane_02");
		dataToCombine.add("plane_03");
		dataToCombine.add("plane_04");
		
		List<List<String>> listResults = new ArrayList<>();
		int resultWidth = 3;
		String[] result = new String[resultWidth];
		createVarRepListStrings(listResults, result, 0);
		
		int num = 1;
		for (List<String> strList : listResults) {
	        String appender = "";
	        if (10 > num) {
	        	System.out.print("0" + num + ". ");
			} else {
				System.out.print(num + ". ");
			}
	        for (String str : strList) {
	            System.out.print(appender + str);
	            appender = " ";
	        }
	        num++;
	        System.out.println();
	    }

		
		/*
		List<String[]> listResults = new ArrayList<>();
		String[] result = new String[2];
		createVariationsRepetitionListStrings(listResults, result, 0);
		for (String[] rep : listResults) {
			System.out.println(Arrays.toString(rep));
		}
		*/
		/*
		List<InvestmentPlan> tempPlanList = new ArrayList<>(Utils.allPlanList);
		
		List<List<String>> listResults = listPermutationsString(tempPlanList);
		for (List<String> strList : listResults) {
	        String appender = "";
	        for (String str : strList) {
	            System.out.print(appender + str);
	            appender = " ";
	        }
	        System.out.println();
	    }
		*/

		/*
		List<Integer> permIntList = new ArrayList<Integer>();
		permIntList.add(1);
		permIntList.add(2);
		permIntList.add(3);
	    List<List<Integer>> resultPermLists = listPermutations(permIntList, 0);

	    for (List<Integer> intList : resultPermLists) {
	        String appender = "";
	        for (Integer i : intList) {
	            System.out.print(appender + i);
	            appender = " ";
	        }
	        System.out.println();
	    }
	    */
	}

}
