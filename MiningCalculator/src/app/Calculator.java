package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Calculator {
	public static final double moneroPriceUSD = 320.28;
	public static final double XMRDay = 0.008;
	public static final double Hs = 1300.0;
	public static final double XMR_1Hs_day = XMRDay / Hs;

	public static final int investmentYears = 3;
	public static final int investmentDays = 365 * investmentYears; // 3y = 1095

	static int maxHashRate = Integer.MIN_VALUE;
	static int maxPassedDays = Integer.MAX_VALUE;
//	static InvestmentRecord topInvestmentRecord = null;
	
	
	// OK
	public static void createVarRepListStrings(List<List<InvestmentPlan>> listResults, List<InvestmentPlan> boughtPlanes, InvestmentPlan[] result, int count) throws CloneNotSupportedException {
		
		if (count < result.length) {
			for (int i = 0; i < Utils.allPlanList.size(); i++) {
				result[count] = Utils.allPlanList.get(i);
				createVarRepListStrings(listResults, boughtPlanes, result, count + 1);
			}
		} else {
			List<InvestmentPlan> tempList = new ArrayList<>();
			for (InvestmentPlan bPlan : boughtPlanes) {
				tempList.add((InvestmentPlan) bPlan.clone());
			}
			tempList.addAll(Arrays.asList(result.clone()));
			listResults.add(tempList);
//			tempList.clear();
		}
	}

	/*public static void calculateBestPlan(List<InvestmentRecord> listResults, Mining mining, int passedDays, int indexPlan)
			throws CloneNotSupportedException {
			
		for (int i = 0; i < investmentDays; i++) {
			mining.miningDay();
			
		}
				if (investmentDays >= passedDays) {
					InvestmentPlan addPlan =  Utils.allPlanList.get(indexPlan);
					double pricePlan = addPlan.getPlanPrice();
					if (mining.income > pricePlan) {
						mining.income -= pricePlan;
						mining.addPlan(addPlan);
						mining.passedDays = i;
					}
				} else {
					listResults.add((InvestmentRecord) mining.getRecord().clone());
					int tempHashRate = mining.getRecord().getRecordMaxGainedHash();
					if (tempHashRate > topHashRate) {
						topHashRate = tempHashRate;
						topInvestmentRecord = (InvestmentRecord) mining.getRecord().clone();
					}
				}
			}
		}

				
//				calculateBestPlan(listResults, mining, leftDays);
		

	}
*/
	// OK
	public static void calculateInvestmentWithPlan_01(Mining mining, int investmentDays, boolean print) throws CloneNotSupportedException {
		System.out.println("Start mining with Hash Rate: " + mining.getMiningHash() + "H/s. Income per day is: " + Utils.formatter.format(mining.calculateIncomePerDay()) + "$");
		for (int i = 1; i <= investmentDays; i++) {
			mining.miningDay(print);

			InvestmentPlan addPlan = (InvestmentPlan) Utils.allPlanList.get(0);
			double pricePlan = addPlan.getPlanPrice();
			if (mining.income > pricePlan) {
				mining.income -= pricePlan;
				mining.addPlan(addPlan);
				printText(mining, i, addPlan);
				mining.passedDays = i;
			}
		}
		mining.calculateLastDays(mining.income);
		System.out.println(mining.getRecord());
	}
	
	// OK
	public static InvestmentRecord calculatePredefineInvestmentPlan(List<InvestmentPlan> planList, int investmentDaysLeft, boolean print) throws CloneNotSupportedException {
		List<InvestmentPlan> startPlanList = new ArrayList<>();
		startPlanList.add(planList.get(0));
		int j  = 1;

		Mining mining = new Mining(startPlanList, XMR_1Hs_day, moneroPriceUSD, investmentDays);
		
		if (print) {
			System.out.println("Start mining with Hash Rate: " + mining.getMiningHash() + "H/s. Income per day is: " + Utils.formatter.format(mining.calculateIncomePerDay()) + "$");
		}
			InvestmentPlan addPlan =  (InvestmentPlan) planList.get(j); // plane is clonning in addPlan()
	//		Mining tempMining
			for (int i = 1; i <= investmentDaysLeft; i++) {
				mining.miningDay(print);

				if ((planList.size()) > j) {
					double pricePlan = addPlan.getPlanPrice();
					if (mining.income > pricePlan) {
						mining.income -= pricePlan;
						mining.addPlan(addPlan);
						if (print) {
							printText(mining, i, addPlan);
						}
						if ((planList.size() - 1) > j) {
							addPlan =  (InvestmentPlan) planList.get(j += 1);
						} else {
							j++;
						}
						mining.passedDays = i;
					} 
				}
			}
		mining.calculateLastDays(mining.income);
		return mining.getRecord();
		

	}
	
	// OK
	public static List<InvestmentRecord> calcBestPredInvestPlanLists(List<List<InvestmentPlan>> listResults, int investmentdays, boolean print, int numTop) throws CloneNotSupportedException {
//		InvestmentRecord bestInvRec = new InvestmentRecord();
		List<InvestmentRecord> unOrderedList = new ArrayList<>();
		List<InvestmentRecord> OrderedList = new ArrayList<>();
		
		for (List<InvestmentPlan> planList : listResults) {
			InvestmentRecord invRec = calculatePredefineInvestmentPlan(planList, investmentdays, print);
			/*
			int tempHashRate = invRec.getRecordMaxGainedHash();
			int temppassedDays = invRec.getRecordPassedDays();
			
			if (tempHashRate > maxHashRate) {
				maxHashRate = tempHashRate;
				maxPassedDays = temppassedDays;
				bestInvRec = (InvestmentRecord) invRec.clone();
			} else if (tempHashRate > maxHashRate && temppassedDays < maxPassedDays) {
				maxPassedDays = temppassedDays;
				bestInvRec = (InvestmentRecord) invRec.clone();
			}
			*/
			
			// check if all planes are active
			if (planList.size() == invRec.getRecordPlans().size()) {
				unOrderedList.add(invRec);
			}
		}
		
		PriorityQueue<InvestmentRecord> heap = new PriorityQueue<>(unOrderedList.size());
		heap.addAll(unOrderedList);
		
		for (int i = 0; i < numTop; i++) {
			OrderedList.add(heap.poll());
		}
		
		return OrderedList;
		
	}
	
	// OK?
	public static void calcPredInvestRecord(InvestmentRecord record, int investmentdays, boolean print) throws CloneNotSupportedException {
		List<String> planNameList = record.getRecordPlans();
		List<InvestmentPlan> planList = new ArrayList<>();
		for (String stringName : planNameList) {
			int planIndex = Integer.parseInt(stringName.substring(stringName.length() - 1, stringName.length()));
			planList.add(Utils.allPlanList.get(planIndex - 1));
		}
		System.out.println(calculatePredefineInvestmentPlan(planList, investmentdays, print));
		
	}
	
	// OK
	public static void printText(Mining mining, int i, InvestmentPlan addPlan) {
		System.out.println(addPlan.planName + " " + Utils.formatter.format(addPlan.getPlanPrice()) + "$ plan bought for "
				+ (i -  mining.passedDays) + " days (" + ((i -  mining.passedDays) / 30) + " months). "
				+ "Total " + i + " days passed (" + (i / 30) + " months)."
				+ " Continue mining with Hash Rate: " + mining.getMiningHash() + "H/s"
				+ " New income per day will be: " + Utils.formatter.format(mining.calculateIncomePerDay()) + "$ ");
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		long startTime = System.currentTimeMillis();
		System.out.println("Calculator started ...");
/*
		List<InvestmentPlan> stratPlanList = new ArrayList<>();
		stratPlanList.add(Utils.plan_02);

		Mining mining = new Mining(stratPlanList, XMR_1Hs_day, moneroPriceUSD, investmentDays);
*/
		//		calculateInvestmentWithPlan_01(mining, investmentDays);

		/*
		List<InvestmentRecord> listResults = new ArrayList<>();

		calculateBestPlan(listResults, mining, 1, 0);
		
		for (InvestmentRecord investmentRecord : listResults) {
			System.out.println(investmentRecord);
		}
		
		System.out.println("Top Investment Record: " + topInvestmentRecord);
		*/
		
		List<List<InvestmentPlan>> listResults = new ArrayList<>();
		int resultWidth = 3;
		List<InvestmentPlan> boughtPlanes = new ArrayList<>();
		boughtPlanes.add(Utils.plan_02);
//		boughtPlanes.add(Utils.plan_01);
//		boughtPlanes.add(Utils.plan_01);
//		boughtPlanes.add(Utils.plan_03);
		
		InvestmentPlan[] result = new InvestmentPlan[resultWidth - boughtPlanes.size()];
		createVarRepListStrings(listResults, boughtPlanes, result, 0);
		System.out.println(listResults.size() + " plans created.");
		System.out.println((resultWidth) + " plans in investment record, with " 
				+ investmentYears + " years investing period.");
		System.out.println();
		
		int topListSize = 3;
		List<InvestmentRecord> bestInvRec = calcBestPredInvestPlanLists(listResults, investmentDays, false, topListSize);
//		System.out.println("Best plane: " + bestInvRec);
		
		for (InvestmentRecord investmentRecord : bestInvRec) {
			System.out.println();
			calcPredInvestRecord(investmentRecord, investmentDays, true);
		}
		
		long endTime = System.currentTimeMillis();
		System.err.println(Utils.duration(startTime, endTime));
		
	}

}
