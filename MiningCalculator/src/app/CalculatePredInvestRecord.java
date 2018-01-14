package app;

import java.util.ArrayList;
import java.util.List;

public class CalculatePredInvestRecord {
	
	
	public static void main(String[] args) throws CloneNotSupportedException {
		
		int investmentYears = Calculator.INVESTMENTYEARS;
		int investmentDays = 365 * investmentYears;
		
		InvestmentRecord investmentRecord = new InvestmentRecord();
		
		List<InvestmentPlan> demoPlanList = new ArrayList<>();
		demoPlanList.add((InvestmentPlan) Utils.plan_02.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_01.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_01.clone());
		demoPlanList.add((InvestmentPlan) Utils.plan_03.clone());
		
		investmentRecord.setRecordPlans(demoPlanList);
		
		Calculator.calculatePredInvestRecord(investmentRecord, investmentDays, true);
	}
}
