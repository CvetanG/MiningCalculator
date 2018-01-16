package app;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Calculator_02 {
	
	public static final double MONEROPRICEUSD = 320.28;
	public static final double XMRDAY = 0.008;
	public static final double HS = 1300.0;
	public static final double XMR_1HS_DAY = XMRDAY / HS;

	public static final int INVESTMENTYEARS = 3;
	public static final int INVESTMENTDAYS = 365 * INVESTMENTYEARS; // 3y = 1095

	// OK ?
	public static void createVariationRepetitionInvestPlanList(Mining mining, List<InvestmentRecord> listResults,
			int investDays, int count) throws CloneNotSupportedException {
		

//		for (int i = 0; i < Utils.allPlanList.size(); i++) {
			for (int j = (mining.passedDays + 1); j <= investDays; j++) {
					
				InvestmentPlan addPlan =  Utils.allPlanList.get(count);
				double pricePlan = addPlan.getPlanPrice();
				//				mining.setPassedDays(j);
				mining.miningDay(false);
				mining.passedDays++;

				if (mining.getIncome() > pricePlan) {
					mining.substractIncome(pricePlan);
					mining.addPlan(addPlan);
					// if (false) {
					// 	miningLog(mining, j, addPlan);
					// }

					//				if (mining.passedDays <= investDaysLeft) {

					mining.daysBetweenPlans = (j - mining.daysBetweenPlans);
					createVariationRepetitionInvestPlanList((Mining) mining.clone(), listResults, investDays, count);
					//				} else {
					//					count++;
					//				}
					//						} 
				}
				if (mining.passedDays == investDays) {
					mining.calculateLastDays(mining.income);
					listResults.add(mining.getRecord());
				}
			}
	}

	
	// OK
	public static void calculatePredInvestRecord(InvestmentRecord record, int investDays,
			boolean print) throws CloneNotSupportedException {
		// reset planPeriodDays
		for (InvestmentPlan invPlan : record.getRecordPlans()) {
			invPlan.setPlanPeriod(Utils.planPeriodDays);
		}
		System.out.println(calculatePredInvestPlanList(record.getRecordPlans(), investDays, print));

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
					mining.daysBetweenPlans = i;
				} 
			}
		}
		mining.calculateLastDays(mining.income);
		return mining.getRecord();
	}


	// OK 
	public static List<InvestmentRecord> calcBestPredInvestRecords(List<InvestmentRecord> ivestRecords,
			int investDays, int numTop, boolean print) throws CloneNotSupportedException {
		
		List<InvestmentRecord> orderedList = new ArrayList<>();
		
		if (ivestRecords.size() > 0) {
			PriorityQueue<InvestmentRecord> heap = new PriorityQueue<>(ivestRecords.size());
			heap.addAll(ivestRecords);
			
			for (int i = 0; i < numTop; i++) {
				orderedList.add(heap.poll());
			}
			
			return orderedList;
		} else {
			String message = "There is no investmentplans with these parameters!!!";
			throw new RuntimeException(message);
		}
		
	}
	
	// OK
	private static void printStart(Mining mining, boolean print) {
		if (print) {
			System.out.println("\nStart mining with " + mining.getMiningPlans().get(0).getPlanName() + " - Hash Rate: " + mining.getMiningHash()
					+ "H/s. Income per day is: " + Utils.formatter.format(mining.calculateIncomePerDay()) + "$");
		}
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

		List<InvestmentPlan> boughtPlanes = new ArrayList<>();
		boughtPlanes.add((InvestmentPlan) Utils.plan_02.clone());
//		boughtPlanes.add((InvestmentPlan) Utils.plan_01.clone());
//		boughtPlanes.add((InvestmentPlan) Utils.plan_01.clone());
//		boughtPlanes.add((InvestmentPlan) Utils.plan_03.clone());
		
		List<InvestmentRecord> generatedInvRecords = new ArrayList<>();
//		InvestmentPlan[] generatedArray = new InvestmentPlan[1000];
//		for (int i = 0; i < boughtPlanes.size(); i++) {
//			generatedArray[i] = boughtPlanes.get(i);
//		}
		int count = 0;
		
		List<InvestmentPlan> startPlanList = new ArrayList<>();
		startPlanList.add(boughtPlanes.get(0));
		int j  = 1;

		Mining mining = new Mining(startPlanList, XMR_1HS_DAY, MONEROPRICEUSD, INVESTMENTDAYS);
		
		
		createVariationRepetitionInvestPlanList(mining, generatedInvRecords, INVESTMENTDAYS, count);
		
		
		// OK
		int topListSize = 5;
		List<InvestmentRecord> bestInvRec = calcBestPredInvestRecords(generatedInvRecords, INVESTMENTDAYS, topListSize, false);
		
		for (InvestmentRecord investmentRecord : bestInvRec) {
			calculatePredInvestRecord(investmentRecord, INVESTMENTDAYS, true);
		}
		
		long endTime = System.currentTimeMillis();
		System.err.println(Utils.duration(startTime, endTime));
		
	}
}
