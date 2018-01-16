package app;

import java.util.ArrayList;
import java.util.List;

public class CalculatePredInvestPlanList {
	
	public static void main(String[] args) throws CloneNotSupportedException {

//		int investmentYears = Calculator_02.INVESTMENTYEARS;
		int investmentYears = 7;
		int investmentDays = 365 * investmentYears;
		
		List<InvestmentPlan> demoPlanList = new ArrayList<>();
		
		//plane 4.1 excel 
		demoPlanList.add((InvestmentPlan) Utils.plan_02.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_01.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_01.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_02.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_02.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_03.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_03.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_04.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_05.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_06.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_06.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_07.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_07.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_08.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_09.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_10.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_10.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_11.clone());

//		demoPlanList.add((InvestmentPlan) Utils.plan_02.clone());
//		demoPlanList.add((InvestmentPlan) Utils.plan_02.clone());
//		demoPlanList.add((InvestmentPlan) Utils.plan_03.clone());
//		demoPlanList.add((InvestmentPlan) Utils.plan_03.clone());
//		demoPlanList.add((InvestmentPlan) Utils.plan_04.clone());

		InvestmentRecord invRec = Calculator.calculatePredInvestPlanList(demoPlanList, investmentDays, true);
		System.out.println(invRec);
	}
}
