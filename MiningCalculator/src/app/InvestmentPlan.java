package app;

public class InvestmentPlan implements Cloneable {
	
	double planPrice;
	int planHash;
	int planPeriod;
	String planName;

	public InvestmentPlan(double planPrice, int planHash, int planPeriod, String planName) {
		this.planPrice = planPrice;
		this.planHash = planHash;
		this.planPeriod = planPeriod;
		this.planName = planName;
	}

	public double getPlanPrice() {
		return this.planPrice;
	}

	public void setPlanPrice(double planPrice) {
		this.planPrice = planPrice;
	}

	public int getPlanHash() {
		return this.planHash;
	}

	public void setPlanHash(int planHash) {
		this.planHash = planHash;
	}

	public int getPlanPeriod() {
		return this.planPeriod;
	}

	public void setPlanPeriod(int planPeriod) {
		this.planPeriod = planPeriod;
	}

	public String getPlanName() {
		return this.planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
