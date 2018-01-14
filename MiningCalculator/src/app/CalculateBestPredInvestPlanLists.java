package app;

import java.util.ArrayList;
import java.util.List;

public class CalculateBestPredInvestPlanLists {
	
	public static void main(String[] args) throws CloneNotSupportedException {
		
		int investmentYears = Calculator.INVESTMENTYEARS;
		int investmentDays = 365 * investmentYears;
		
		List<InvestmentPlan> demoPlanListA = new ArrayList<>();
		demoPlanListA.add((InvestmentPlan) Utils.plan_02.clone());
		demoPlanListA.add((InvestmentPlan) Utils.plan_03.clone());
		demoPlanListA.add((InvestmentPlan) Utils.plan_05.clone());
		
		List<InvestmentPlan> demoPlanListB = new ArrayList<>();
		demoPlanListB.add((InvestmentPlan) Utils.plan_02.clone());
		demoPlanListB.add((InvestmentPlan) Utils.plan_02.clone());
		demoPlanListB.add((InvestmentPlan) Utils.plan_03.clone());
		
		List<InvestmentPlan> demoPlanListC = new ArrayList<>();
		demoPlanListC.add((InvestmentPlan) Utils.plan_02.clone());
		demoPlanListC.add((InvestmentPlan) Utils.plan_02.clone());
		demoPlanListC.add((InvestmentPlan) Utils.plan_06.clone());
		
		List<List<InvestmentPlan>> generatedInvPlanList = new ArrayList<>();
		generatedInvPlanList.add(demoPlanListC);
		generatedInvPlanList.add(demoPlanListA);
		generatedInvPlanList.add(demoPlanListB);
		
		int topListSize = 3;
		List<InvestmentRecord> bestInvRec = Calculator.calcBestPredInvestPlanLists(generatedInvPlanList, investmentDays, topListSize, true);
		
		for (InvestmentRecord investmentRecord : bestInvRec) {
			Calculator.calculatePredInvestRecord(investmentRecord, investmentDays, true);
		}
	}
}
