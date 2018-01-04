package app;

import java.util.ArrayList;
import java.util.List;

public class Mining {
	InvestmentRecord record;
	List<InvestmentPlane> miningPlanes;
	double moneroPriceUSD;
	double XMR_1Hs_Day;
	int miningHash;

	public Mining(List<InvestmentPlane> miningPlanes, double XMR_1Hs_Day, double moneroPriceUSD) throws CloneNotSupportedException {
		this.record = new InvestmentRecord();
		this.miningPlanes = miningPlanes;
		this.moneroPriceUSD = moneroPriceUSD;
		this.XMR_1Hs_Day = XMR_1Hs_Day;
		int tempHash = 0;
		for (InvestmentPlane curPlane : miningPlanes) {
			tempHash += curPlane.getPlanHash();
			record.addPlaneName(curPlane.getPlanName());
		}
		this.miningHash = tempHash;
	}

	public double calculateIncomeDay() {
		double result = XMR_1Hs_Day * miningHash * moneroPriceUSD;
		return result;
	}
	
	public void calculateLastDays(double cash) {
		double result = 0.0;
		for (InvestmentPlane plane : miningPlanes) {
			result += (plane.getPlanPeriod() * XMR_1Hs_Day * plane.getPlanHash() * moneroPriceUSD);
		}
		
		record.setRecordCash(cash);
		record.setRecordMaxGainedHash(getMiningHash());
		record.setRecordFutureIncome(result);
		record.setRecordFuturePeriod(getMiningPlanes().get(getMiningPlanes().size() - 1).getPlanPeriod());
	}

	public void addPlane(InvestmentPlane newPlane) throws CloneNotSupportedException {
		this.miningPlanes.add((InvestmentPlane) newPlane.clone());
		this.miningHash += newPlane.getPlanHash();
		this.record.addPlaneName(newPlane.getPlanName());
	}

	public void removePlane(List<InvestmentPlane> removeList) {
		this.miningPlanes.removeAll(removeList);
		for (InvestmentPlane plane : removeList) {
			this.miningHash -= plane.getPlanHash();
			System.out.println("Plane expired so " + plane.getPlanHash() + " H/s are removed. Left Hash Rate is: "
					+ this.miningHash + "H/s");
		}
	}

	public double miningDay() {
		double result = calculateIncomeDay();
		List<InvestmentPlane> removeList = new ArrayList<>();
		for (InvestmentPlane curPlane : this.miningPlanes) {
			curPlane.setPlanPeriod(curPlane.getPlanPeriod() - 1);
			if (curPlane.getPlanPeriod() == 0) {
				removeList.add(curPlane);
			}
		}
		if (!removeList.isEmpty()) {
			removePlane(removeList);
		}
		return result;
	}

	public List<InvestmentPlane> getMiningPlanes() {
		return this.miningPlanes;
	}

	public void setMiningPlanes(List<InvestmentPlane> miningPlanes) {
		this.miningPlanes = miningPlanes;
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
