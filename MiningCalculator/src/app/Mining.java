package app;

import java.util.ArrayList;
import java.util.List;

public class Mining {
	
	InvestmentRecord record;
	List<InvestmentPlan> miningPlans;
	double moneroPriceUSD;
	double XMR_1Hs_Day;
	int miningHash;
	public double income;
	public int passedDays;

	public Mining(List<InvestmentPlan> miningPlans, double XMR_1Hs_Day, double moneroPriceUSD) throws CloneNotSupportedException {
		this.record = new InvestmentRecord();
		this.miningPlans = miningPlans;
		this.moneroPriceUSD = moneroPriceUSD;
		this.XMR_1Hs_Day = XMR_1Hs_Day;
		int tempHash = 0;
		for (InvestmentPlan curPlan : miningPlans) {
			tempHash += curPlan.getPlanHash();
			record.addPlanName(curPlan.getPlanName());
		}
		this.miningHash = tempHash;
	}

	public double calculateIncomePerDay() {
		double result = XMR_1Hs_Day * miningHash * moneroPriceUSD;
		return result;
	}
	
	public void calculateLastDays(double cash) {
		double result = 0.0;
		for (InvestmentPlan plan : miningPlans) {
			result += (plan.getPlanPeriod() * XMR_1Hs_Day * plan.getPlanHash() * moneroPriceUSD);
		}
		
		record.setRecordCash(cash);
		record.setRecordMaxGainedHash(getMiningHash());
		record.setRecordFutureIncome(result);
		record.setRecordFuturePeriod(getMiningPlans().get(getMiningPlans().size() - 1).getPlanPeriod());
	}

	public void addPlan(InvestmentPlan newPlan) throws CloneNotSupportedException {
		this.miningPlans.add((InvestmentPlan) newPlan.clone());
		this.miningHash += newPlan.getPlanHash();
		this.record.addPlanName(newPlan.getPlanName());
	}

	public void removePlan(List<InvestmentPlan> removeList) {
		this.miningPlans.removeAll(removeList);
		for (InvestmentPlan plan : removeList) {
			this.miningHash -= plan.getPlanHash();
			System.out.println("Plan expired so " + plan.getPlanHash() + " H/s are removed. Left Hash Rate is: "
					+ this.miningHash + "H/s");
		}
	}

	public void miningDay() {
		double result = calculateIncomePerDay();
		List<InvestmentPlan> removeList = new ArrayList<>();
		for (InvestmentPlan curPlan : this.miningPlans) {
			curPlan.setPlanPeriod(curPlan.getPlanPeriod() - 1);
			if (curPlan.getPlanPeriod() == 0) {
				removeList.add(curPlan);
			}
		}
		if (!removeList.isEmpty()) {
			removePlan(removeList);
		}
		income += result;
	}

	public List<InvestmentPlan> getMiningPlans() {
		return this.miningPlans;
	}

	public void setMiningPlans(List<InvestmentPlan> miningPlans) {
		this.miningPlans = miningPlans;
	}

	public double getMoneroPriceUSD() {
		return this.moneroPriceUSD;
	}

	public void setMoneroPriceUSD(double moneroPriceUSD) {
		this.moneroPriceUSD = moneroPriceUSD;
	}

	public int getMiningHash() {
		return this.miningHash;
	}

	public void setMiningHash(int miningHash) {
		this.miningHash = miningHash;
	}

	public double getXMR_1Hs_Day() {
		return this.XMR_1Hs_Day;
	}

	public void setXMR_1Hs_Day(double xMR_1Hs_Day) {
		this.XMR_1Hs_Day = xMR_1Hs_Day;
	}

	public InvestmentRecord getRecord() {
		return record;
	}

	public void setRecord(InvestmentRecord record) {
		this.record = record;
	}

}
