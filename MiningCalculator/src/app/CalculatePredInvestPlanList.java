package app;

import java.util.ArrayList;
import java.util.List;

public class CalculatePredInvestPlanList {
	
	public static void main(String[] args) throws CloneNotSupportedException {

		int investmentYears = Calculator.INVESTMENTYEARS;
		int investmentDays = 365 * investmentYears;
		
		List<InvestmentPlan> demoPlanList = new ArrayList<>();
		
		//plane 4 ok excel 
		/*demoPlanList.add(Utils.plan_02);
		demoPlanList.add(Utils.plan_01);
		demoPlanList.add(Utils.plan_01);
		demoPlanList.add(Utils.plan_02);
		demoPlanList.add(Utils.plan_02);
		demoPlanList.add(Utils.plan_03);
		demoPlanList.add(Utils.plan_03);
		demoPlanList.add(Utils.plan_04);
		demoPlanList.add(Utils.plan_05);
		demoPlanList.add(Utils.plan_06);
		demoPlanList.add(Utils.plan_06);
		demoPlanList.add(Utils.plan_07);
		demoPlanList.add(Utils.plan_07);
		demoPlanList.add(Utils.plan_08);
		demoPlanList.add(Utils.plan_09);
		demoPlanList.add(Utils.plan_10);
		demoPlanList.add(Utils.plan_10);
		demoPlanList.add(Utils.plan_11);*/
		
		demoPlanList.add((InvestmentPlan) Utils.plan_02.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_02.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_05.clone());

		InvestmentRecord invRec = Calculator.calculatePredInvestPlanList(demoPlanList, investmentDays, true);
		System.out.println(invRec);
	}
}
