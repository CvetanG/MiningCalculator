package app;

import java.util.ArrayList;
import java.util.List;

public class InvestmentRecord implements Cloneable, Comparable<InvestmentRecord> {
	
	List<InvestmentPlan> recordPlans;
	double recordCash;
	int recordMaxGainedHash;
	double recordFutureIncome;
	int recordFuturePeriod;
	int recordPassedDays; // to max gained hash
	int recordActivePeriod;
	
	public InvestmentRecord(List<InvestmentPlan> recordPlans, double recordCash, int recordMaxGainedHash,
			double recordFutureIncome, int recordFuturePeriod, int recordPassedDays, int recordActivePeriod) {
		this.recordPlans = recordPlans;
		this.recordCash = recordCash;
		this.recordMaxGainedHash = recordMaxGainedHash;
		this.recordFutureIncome = recordFutureIncome;
		this.recordFuturePeriod = recordFuturePeriod;
		this.recordPassedDays = recordPassedDays;
		this.recordActivePeriod = recordActivePeriod;
	}
	
	public InvestmentRecord() {
		this.recordPlans = new ArrayList<>();
	}

	public void addPlan(InvestmentPlan iPlane) {
		recordPlans.add(iPlane);
	}

	public List<InvestmentPlan> getRecordPlans() {
		return recordPlans;
	}

	public void setRecordPlans(List<InvestmentPlan> recordPlans) {
		this.recordPlans = recordPlans;
	}

	public double getRecordCash() {
		return recordCash;
	}

	public void setRecordCash(double recordCash) {
		this.recordCash = recordCash;
	}

	public int getRecordMaxGainedHash() {
		return recordMaxGainedHash;
	}

	public void setRecordMaxGainedHash(int recordMaxGainedHash) {
		this.recordMaxGainedHash = recordMaxGainedHash;
	}

	public double getRecordFutureIncome() {
		return recordFutureIncome;
	}

	public void setRecordFutureIncome(double recordFutureIncome) {
		this.recordFutureIncome = recordFutureIncome;
	}

	public int getRecordFuturePeriod() {
		return recordFuturePeriod;
	}

	public void setRecordFuturePeriod(int recordFuturePeriod) {
		this.recordFuturePeriod = recordFuturePeriod;
	}

	public int getRecordPassedDays() {
		return recordPassedDays;
	}

	public void setRecordPassedDays(int passedDays) {
		this.recordPassedDays = passedDays;
	}

	public int getRecordActivePeriod() {
		return recordActivePeriod;
	}

	public void setRecordActivePeriod(int activePeriod) {
		this.recordActivePeriod = activePeriod;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < recordPlans.size(); i++) {
			sb.append(recordPlans.get(i).getPlanName());
			if ((i + 1) != recordPlans.size()) {
				sb.append(", ");
			} else {
				sb.append(".");
			}
		}
		return "InvestmentRecord: " + sb + "\nGained cash for " + recordActivePeriod + " days: " + Utils.formatter.format(recordCash)
				+ "$. Max Hash Rate gained: " + recordMaxGainedHash + "H/s" + " for " + recordPassedDays + " days"
				+ "(" + (recordPassedDays / 30) + " months). \nFuture Income: "
				+ Utils.formatter.format(recordFutureIncome) + "$ after " + recordFuturePeriod + " days.\n";
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	@Override
	public int compareTo(InvestmentRecord iR) {
		int diffMaxGainedHash;
		int diffRecordPassedDays;
		int diffRecordFutureIncome;
		
		diffMaxGainedHash = iR.recordMaxGainedHash - this.recordMaxGainedHash;
		if (diffMaxGainedHash != 0) return diffMaxGainedHash;
		
		diffRecordPassedDays = this.recordPassedDays - iR.recordPassedDays;
		if (diffRecordPassedDays != 0) return diffRecordPassedDays;
		
		diffRecordFutureIncome = (int) (this.recordFutureIncome - iR.recordFutureIncome);
		if (diffRecordFutureIncome != 0) return diffRecordFutureIncome;
		
		return 0;
	}
	
}
