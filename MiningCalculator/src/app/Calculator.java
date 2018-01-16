package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Calculator {
	public static final double MONEROPRICEUSD = 320.28;
	public static final double XMRDAY = 0.008;
	public static final double HS = 1300.0;
	public static final double XMR_1HS_DAY = XMRDAY / HS;

	public static final int INVESTMENTYEARS = 4;
	public static final int INVESTMENTDAYS = 365 * INVESTMENTYEARS; // 3y = 1095

	// OK
	public static void createVariationRepetitionInvestPlanList(List<List<InvestmentPlan>> listResults,
			List<InvestmentPlan> boughtPlanes, InvestmentPlan[] result, int count) throws CloneNotSupportedException {
		
		if (count < result.length) {
			for (int i = 0; i < Utils.allPlanList.size(); i++) {
				result[count] = Utils.allPlanList.get(i);
				createVariationRepetitionInvestPlanList(listResults, boughtPlanes, result, count + 1);
			}
		} else {
			List<InvestmentPlan> tempList = new ArrayList<>();
			for (InvestmentPlan bPlan : boughtPlanes) {
				tempList.add((InvestmentPlan) bPlan.clone());
			}
			tempList.addAll(Arrays.asList(result.clone()));
			listResults.add(tempList);
		}
	}

	// OK
	public static void calculateInvestmentWithPlan_01(Mining mining, int investDays,
			boolean print) throws CloneNotSupportedException {
		
		printStart(mining, print);
		for (int i = 1; i <= investDays; i++) {
			mining.miningDay(print);

			InvestmentPlan addPlan = (InvestmentPlan) Utils.allPlanList.get(0);
			double pricePlan = addPlan.getPlanPrice();
			if (mining.income > pricePlan) {
				mining.income -= pricePlan;
				mining.addPlan(addPlan); // clone the object
				miningLog(mining, i, addPlan);
				mining.passedDays = i;
			}
		}
		mining.calculateLastDays(mining.income);
		System.out.println(mining.getRecord());
	}
	
	// OK
	public static InvestmentRecord calculatePredInvestPlanList(List<InvestmentPlan> planList,
			int investDaysLeft, boolean print) throws CloneNotSupportedException {
		
		List<InvestmentPlan> startPlanList = new ArrayList<>();
		startPlanList.add(planList.get(0));
		int j  = 1;

		Mining mining = new Mining(startPlanList, XMR_1HS_DAY, MONEROPRICEUSD, INVESTMENTDAYS);
		
		printStart(mining, print);
		InvestmentPlan addPlan =  (InvestmentPlan) planList.get(j); // plane is clonning in addPlan()
		double pricePlan = addPlan.getPlanPrice();
		for (int i = 1; i <= investDaysLeft; i++) {
			mining.setPassedDays(i);
			mining.miningDay(print);

			if ((planList.size()) > j) {
				if (mining.getIncome() > pricePlan) {
					mining.substractIncome(pricePlan);
					mining.addPlan(addPlan);
					if (print) {
						miningLog(mining, i, addPlan);
					}
					// get next plan
					if ((planList.size() - 1) > j) {
						addPlan = (InvestmentPlan) planList.get(j += 1);
						pricePlan = addPlan.getPlanPrice();
					} else {
						j++;
					}
//					mining.setPassedDays(i);
					mining.daysBetweenPlans = i;
				} 
			}
		}
		mining.calculateLastDays(mining.income);
		return mining.getRecord();
	}

	private static void printStart(Mining mining, boolean print) {
		if (print) {
			System.out.println("\nStart mining with " + mining.getMiningPlans().get(0).getPlanName() + " - Hash Rate: " + mining.getMiningHash()
					+ "H/s. Income per day is: " + Utils.formatter.format(mining.calculateIncomePerDay()) + "$");
		}
	}
	
	// OK
	public static List<InvestmentRecord> calcBestPredInvestPlanLists(List<List<InvestmentPlan>> listListIvestPlans,
			int investDays, int numTop, boolean print) throws CloneNotSupportedException {
		
		List<InvestmentRecord> unOrderedList = new ArrayList<>();
		List<InvestmentRecord> OrderedList = new ArrayList<>();
		
		for (List<InvestmentPlan> planList : listListIvestPlans) {
			InvestmentRecord invRec = calculatePredInvestPlanList(planList, investDays, print);
			if (planList.size() == invRec.getRecordPlans().size()) {
				unOrderedList.add(invRec);
			}
		}
		
		if (unOrderedList.size() > 0) {
			PriorityQueue<InvestmentRecord> heap = new PriorityQueue<>(unOrderedList.size());
			heap.addAll(unOrderedList);
			
			for (int i = 0; i < numTop; i++) {
				OrderedList.add(heap.poll());
			}
			
			return OrderedList;
		} else {
			String message = "There is no investmentplans with these parameters!!!";
			throw new RuntimeException(message);
		}
		
	}
	
	// OK
	public static void calculatePredInvestRecord(InvestmentRecord record, int investDays, boolean print) throws CloneNotSupportedException {
		// reset planPeriodDays
		for (InvestmentPlan invPlan : record.getRecordPlans()) {
			invPlan.setPlanPeriod(Utils.planPeriodDays);
		}
		System.out.println(calculatePredInvestPlanList(record.getRecordPlans(), investDays, print));
		
	}
	
	// OK
	public static void miningLog(Mining mining, int i, InvestmentPlan addPlan) {
		
		System.out.println("+" + addPlan.planName + " with " + addPlan.getPlanHash() + "H/s"+ " bought for " + Utils.formatter.format(addPlan.getPlanPrice()) + "$ within "
				+ (i -  mining.daysBetweenPlans) + " days (" + ((i -  mining.daysBetweenPlans) / 30) + " months). "
				+ "Total " + i + " days passed (" + (i / 30) + " months)."
				+ "\nContinue mining with Hash Rate: " + mining.getMiningHash() + "H/s"
				+ " New income per day will be: " + Utils.formatter.format(mining.calculateIncomePerDay()) + "$ ");
		
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		
		long startTime = System.currentTimeMillis();
		System.out.println("Calculator started ...");
		/*
		List<InvestmentPlan> stratPlanList = new ArrayList<>();
		stratPlanList.add((InvestmentPlan) Utils.plan_02.clone());

		Mining mining = new Mining(stratPlanList, XMR_1Hs_day, moneroPriceUSD, investmentDays);
		//		calculateInvestmentWithPlan_01(mining, investmentDays);
 		*/

		int resultWidth = 4;
		List<InvestmentPlan> boughtPlanes = new ArrayList<>();
		boughtPlanes.add((InvestmentPlan) Utils.plan_02.clone());
//		boughtPlanes.add((InvestmentPlan) Utils.plan_01.clone());
//		boughtPlanes.add((InvestmentPlan) Utils.plan_01.clone());
//		boughtPlanes.add((InvestmentPlan) Utils.plan_03.clone());
		
		List<List<InvestmentPlan>> generatedInvPlanList = new ArrayList<>();
		InvestmentPlan[] generatedArray = new InvestmentPlan[resultWidth - boughtPlanes.size()];
		int count = 0;
		createVariationRepetitionInvestPlanList(generatedInvPlanList, boughtPlanes, generatedArray, count);
		
		System.out.println(generatedInvPlanList.size() + " plans created.");
		System.out.println((resultWidth) + " plans in investment record, with " 
				+ INVESTMENTYEARS + " years investing period.");
//		System.out.println();
		
		int topListSize = 5;
		List<InvestmentRecord> bestInvRec = calcBestPredInvestPlanLists(generatedInvPlanList, INVESTMENTDAYS, topListSize, false);
		
		for (InvestmentRecord investmentRecord : bestInvRec) {
			calculatePredInvestRecord(investmentRecord, INVESTMENTDAYS, true);
		}
		
		long endTime = System.currentTimeMillis();
		System.err.println(Utils.duration(startTime, endTime));
		
	}

}
