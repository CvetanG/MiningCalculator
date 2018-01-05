package app;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
	public static final double moneroPriceUSD = 320.28;
	public static final double XMRDay = 0.008;
	public static final double Hs = 1300.0;
	public static final double XMR_1Hs_day = XMRDay / Hs;

	public static final int investmentYears = 3;
	public static final int investmentDays = 365 * investmentYears;

	static int topHashRate = Integer.MIN_VALUE;
	static InvestmentRecord topInvestmentRecord = null;
	
	private static void createVariationsRepetitionList(List<String[]> listResults, String[] result, int count) {
		if (count < result.length) {
			for (int i = 0; i < Utils.allPlanList.size(); i++) {
				result[count] = Utils.allPlanList.get(i).getPlanName();
				createVariationsRepetitionList(listResults, result, count + 1);
			}
		} else {
			listResults.add(result.clone());
		}
	}

	public static void calculateBestPlan(List<InvestmentRecord> listResults, Mining mining, int passedDays)
			throws CloneNotSupportedException {
		
		for (int j = 0; j < Utils.allPlanList.size(); j++) {
			for (int i = 1; i <= investmentDays; i++) {
				if (investmentDays >= passedDays) {
					mining.miningDay();
					InvestmentPlan addPlan =  Utils.allPlanList.get(j);
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

	public static void calculateInvestmentWithPlan_01(Mining mining, int investmentDays) throws CloneNotSupportedException {
		System.out.println("Start mining with Hash Rate: " + mining.getMiningHash() + "H/s. Income per day is: " + Utils.formatter.format(mining.calculateIncomePerDay()) + "$");
		for (int i = 1; i <= investmentDays; i++) {
			mining.miningDay();

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

	private static void calculatePredefineInvestmentPlan(List<InvestmentPlan> planList, int investmentDaysLeft) throws CloneNotSupportedException {
		List<InvestmentPlan> stratPlanList = new ArrayList<>();
		stratPlanList.add(planList.get(0));
		int j  = 1;

		Mining mining = new Mining(stratPlanList, XMR_1Hs_day, moneroPriceUSD);

		System.out.println("Start mining with Hash Rate: " + mining.getMiningHash() + "H/s. Income per day is: " + Utils.formatter.format(mining.calculateIncomePerDay()) + "$");
		for (int i = 1; i <= investmentDaysLeft; i++) {
			mining.miningDay();

			if ((planList.size() - 1) >= j) {
				InvestmentPlan addPlan =  planList.get(j);
				double pricePlan = addPlan.getPlanPrice();
				if (mining.income > pricePlan) {
					mining.income -= pricePlan;
					mining.addPlan(addPlan);
					j++;

					printText(mining, i, addPlan);

					mining.passedDays = i;
				} 
			}
		}
		mining.calculateLastDays(mining.income);
		System.out.println(mining.getRecord());

	}

	private static void printText(Mining mining, int i, InvestmentPlan addPlan) {
		System.out.println(Utils.formatter.format(addPlan.getPlanPrice()) + "$ plan bought for "
				+ (i -  mining.passedDays) + " days. " + "Total " + i + " days passed. " 
				+ " Continue mining with Hash Rate: " + mining.getMiningHash() + "H/s"
				+ " New income per day will be: " + Utils.formatter.format(mining.calculateIncomePerDay()) + "$ ");
	}

	public static void main(String[] args) throws CloneNotSupportedException {

		List<InvestmentPlan> stratPlanList = new ArrayList<>();
		stratPlanList.add(Utils.plan_02);

		Mining mining = new Mining(stratPlanList, XMR_1Hs_day, moneroPriceUSD);

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
		calculatePredefineInvestmentPlan(demoPlanList, investmentDays);
		 */


		List<InvestmentRecord> listResults = new ArrayList<>();

		calculateBestPlan(listResults, mining, 1);
		
		for (InvestmentRecord investmentRecord : listResults) {
			System.out.println(investmentRecord);
		}
		
		System.out.println("Top Investment Record: " + topInvestmentRecord);

	}

}
