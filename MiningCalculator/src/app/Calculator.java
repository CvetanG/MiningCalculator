package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator {
	public static final double moneroPriceUSD = 320.28;
	public static final double XMRDay = 0.008;
	public static final double Hs = 1300.0;
	public static final double XMR_1Hs_day = XMRDay / Hs;

	public static final int investmentYears = 3;
	public static final int investmentDays = 365 * investmentYears;

	static int topHashRate = Integer.MIN_VALUE;
	static int maxpassedDays = Integer.MAX_VALUE;
//	static InvestmentRecord topInvestmentRecord = null;
	
	
	// OK
	public static void createVarRepListStrings(List<List<InvestmentPlan>> listResults, InvestmentPlan[] result, int count) {
		
		if (count < result.length) {
			for (int i = 0; i < Utils.allPlanList.size(); i++) {
				result[count] = Utils.allPlanList.get(i);
				createVarRepListStrings(listResults, result, count + 1);
			}
		} else {
			List<InvestmentPlan> tempList = new ArrayList<>();
			tempList.add(Utils.allPlanList.get(1));
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
		List<InvestmentPlan> stratPlanList = new ArrayList<>();
		stratPlanList.add(planList.get(0));
		int j  = 1;

		Mining mining = new Mining(stratPlanList, XMR_1Hs_day, moneroPriceUSD, investmentDays);
		
		if (print) {
			System.out.println("Start mining with Hash Rate: " + mining.getMiningHash() + "H/s. Income per day is: " + Utils.formatter.format(mining.calculateIncomePerDay()) + "$");
		}
		InvestmentPlan addPlan =  (InvestmentPlan) planList.get(j).clone();
		for (int i = 1; i <= investmentDaysLeft; i++) {
			mining.miningDay(print);

			if ((planList.size() - 1) >= j) {
				double pricePlan = addPlan.getPlanPrice();
				if (mining.income > pricePlan) {
					mining.income -= pricePlan;
					mining.addPlan(addPlan);
					addPlan =  (InvestmentPlan) planList.get(j + 1).clone();
					if (print) {
						printText(mining, i, addPlan);
					}
//					mining.passedDays = i;
				} 
			}
		}
		mining.calculateLastDays(mining.income);
		return mining.getRecord();
		

	}
	
	// OK
	public static InvestmentRecord calcBestPredInvestPlanLists(List<List<InvestmentPlan>> listResults, int investmentdays, boolean print) throws CloneNotSupportedException {
		InvestmentRecord bestInvRec = new InvestmentRecord();
		
		for (List<InvestmentPlan> planList : listResults) {
			InvestmentRecord invRec = calculatePredefineInvestmentPlan(planList, investmentdays, print);
			
			int tempHashRate = invRec.getRecordMaxGainedHash();
			int temppassedDays = invRec.getRecordPassedDays();
			
			if (tempHashRate > topHashRate) {
				topHashRate = tempHashRate;
				maxpassedDays = temppassedDays;
				bestInvRec = (InvestmentRecord) invRec.clone();
			} else if (tempHashRate > topHashRate && temppassedDays < maxpassedDays) {
				maxpassedDays = temppassedDays;
				bestInvRec = (InvestmentRecord) invRec.clone();
			}
		}
		
		return bestInvRec;
		
	}
	
	// ?
	public static void calcPredInvestRecord(InvestmentRecord record, int investmentdays, boolean print) throws CloneNotSupportedException {
		List<String> planNameList = record.getRecordPlans();
		List<InvestmentPlan> planList = new ArrayList<>();
		for (String stringName : planNameList) {
			int planIndex = Integer.parseInt(stringName.substring(stringName.length() - 1, stringName.length()));
			planList.add(Utils.allPlanList.get(planIndex - 1));
		}
		calculatePredefineInvestmentPlan(planList, investmentdays, print);
	}
	
	// OK
	public static void printText(Mining mining, int i, InvestmentPlan addPlan) {
		System.out.println(Utils.formatter.format(addPlan.getPlanPrice()) + "$ plan bought for "
				+ (i -  mining.passedDays) + " days. " + "Total " + i + " days passed. " 
				+ " Continue mining with Hash Rate: " + mining.getMiningHash() + "H/s"
				+ " New income per day will be: " + Utils.formatter.format(mining.calculateIncomePerDay()) + "$ ");
	}

	public static void main(String[] args) throws CloneNotSupportedException {

		List<InvestmentPlan> stratPlanList = new ArrayList<>();
		stratPlanList.add(Utils.plan_02);

		Mining mining = new Mining(stratPlanList, XMR_1Hs_day, moneroPriceUSD, investmentDays);

		//		calculateInvestmentWithPlan_01(mining, investmentDays);

		/*
		List<InvestmentPlan> demoPlanList = new ArrayList<>();
		demoPlanList.add(Utils.plan_02);
		demoPlanList.add(Utils.plan_01);
		demoPlanList.add(Utils.plan_01);
		demoPlanList.add(Utils.plan_01);
		demoPlanList.add(Utils.plan_01);
		demoPlanList.add(Utils.plan_01);
		demoPlanList.add(Utils.plan_01);
		demoPlanList.add(Utils.plan_01);
		demoPlanList.add(Utils.plan_01);
		InvestmentRecord invRec = calculatePredefineInvestmentPlan(demoPlanList, investmentDays, true);
		System.out.println(invRec);
		 */

		/*
		List<InvestmentRecord> listResults = new ArrayList<>();

		calculateBestPlan(listResults, mining, 1, 0);
		
		for (InvestmentRecord investmentRecord : listResults) {
			System.out.println(investmentRecord);
		}
		
		System.out.println("Top Investment Record: " + topInvestmentRecord);
		*/
		
		List<List<InvestmentPlan>> listResults = new ArrayList<>();
		int resultWidth = 2;
		InvestmentPlan[] result = new InvestmentPlan[resultWidth];
		createVarRepListStrings(listResults, result, 0);
		
		InvestmentRecord bestInvRec = calcBestPredInvestPlanLists(listResults, investmentDays, false);
		calcPredInvestRecord(bestInvRec, investmentDays, true);
		
	}


}
