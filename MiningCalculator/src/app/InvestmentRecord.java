package app;

import java.util.ArrayList;
import java.util.List;

public class InvestmentRecord implements Cloneable {
	
	List<String> recordPlans;
	double recordCash;
	int recordMaxGainedHash;
	double recordFutureIncome;
	int recordFuturePeriod;
	
	public InvestmentRecord(List<String> recordPlans, double recordCash, int recordMaxGainedHash,
			double recordFutureIncome, int recordFuturePeriod) {
		this.recordPlans = recordPlans;
		this.recordCash = recordCash;
		this.recordMaxGainedHash = recordMaxGainedHash;
		this.recordFutureIncome = recordFutureIncome;
		this.recordFuturePeriod = recordFuturePeriod;
	}
	
	public InvestmentRecord() {
		this.recordPlans = new ArrayList<>();
	}

	public void addPlanName(String planName) {
		recordPlans.add(planName);
	}

	public List<String> getRecordPlans() {
		return recordPlans;
	}

	public void setRecordPlans(List<String> recordPlans) {
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String plan : recordPlans) {
			sb.append(plan);
			sb.append(", ");
		}
		return "InvestmentRecord: " + sb + "\nGained cash end of the preriod: " + Utils.formatter.format(recordCash)
				+ "$. Max Hash Rate gained: " + recordMaxGainedHash + "H/s" + ". Future Income: "
				+ Utils.formatter.format(recordFutureIncome) + "$ after " + recordFuturePeriod + " days.";

	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	
}
