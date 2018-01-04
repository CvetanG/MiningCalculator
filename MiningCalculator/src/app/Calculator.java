package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Calculator {
	public static final double moneroPriceUSD = 320.28;
	public static final double XMRDay = 0.008D;
	public static final double Hs = 1300.0;
	public static final double XMR_1Hs_day = XMRDay / Hs;
	
	public static final int investmentYears = 3;
	public static final int investmentDays = 365 * investmentYears;
	
	static double income = 0.0;
	// keep passed days
	static int tempDays = 0;
	
	public static final int planPeriodYears = 2;
	public static final int planPeriodDays = 365 * planPeriodYears;
	
	public static final InvestmentPlane plane_01 = new InvestmentPlane(  830.0,  1000, planPeriodDays, "plane_01");
	public static final InvestmentPlane plane_02 = new InvestmentPlane( 1079.0,  1300, planPeriodDays, "plane_02");
	public static final InvestmentPlane plane_03 = new InvestmentPlane( 1411.0,  1700, planPeriodDays, "plane_03");
	public static final InvestmentPlane plane_04 = new InvestmentPlane( 1909.0,  2300, planPeriodDays, "plane_04");
	public static final InvestmentPlane plane_05 = new InvestmentPlane( 2241.0,  2700, planPeriodDays, "plane_05");
	public static final InvestmentPlane plane_06 = new InvestmentPlane( 2460.0,  3000, planPeriodDays, "plane_06");
	public static final InvestmentPlane plane_07 = new InvestmentPlane( 4100.0,  5000, planPeriodDays, "plane_07");
	public static final InvestmentPlane plane_08 = new InvestmentPlane( 6150.0,  7500, planPeriodDays, "plane_08");
	public static final InvestmentPlane plane_09 = new InvestmentPlane( 7200.0,  9000, planPeriodDays, "plane_09");
	public static final InvestmentPlane plane_10 = new InvestmentPlane( 8000.0, 10000, planPeriodDays, "plane_10");
	public static final InvestmentPlane plane_11 = new InvestmentPlane(12000.0, 15000, planPeriodDays, "plane_11");
	
	public static final List<InvestmentPlane> allPlaneList = Collections.unmodifiableList(
			Arrays.asList(
						plane_01,
						plane_02,
						plane_03,
						plane_04,
						plane_05,
						plane_06,
						plane_07,
						plane_08,
						plane_09,
						plane_10,
						plane_11));
	
	public static void calculateBestPlane(Mining mining, int investmentDays) throws CloneNotSupportedException {
		System.out.println("Start mining with Hash Rate: " + mining.getMiningHash() + "H/s. Income per day is: " + Utils.formatter.format(mining.miningDay()) + "$");
		for (int i = 1; i <= investmentDays; i++) {
			income += mining.miningDay();
			
			InvestmentPlane addPlane = (InvestmentPlane) allPlaneList.get(0);
			double pricePlane = addPlane.getPlanPrice();
			if (income > pricePlane) {
				income -= pricePlane;
				mining.addPlane(addPlane);

				printText(mining, i, addPlane);

				tempDays = i;
			}

		}
		mining.calculateLastDays(income);
		System.out.println(mining.getRecord());
		
	}
	
	public static void calculate(Mining mining, int investmentDays) throws CloneNotSupportedException {
		System.out.println("Start mining with Hash Rate: " + mining.getMiningHash() + "H/s. Income per day is: " + Utils.formatter.format(mining.miningDay()) + "$");
		for (int i = 1; i <= investmentDays; i++) {
			income += mining.miningDay();
			
			InvestmentPlane addPlane = (InvestmentPlane) allPlaneList.get(0);
			double pricePlane = addPlane.getPlanPrice();
			if (income > pricePlane) {
				income -= pricePlane;
				mining.addPlane(addPlane);

				printText(mining, i, addPlane);

				tempDays = i;
			}

		}
		mining.calculateLastDays(income);
		System.out.println(mining.getRecord());
		
	}
	
	private static void printPlaneList(List<InvestmentPlane> planList, int investmentdays) throws CloneNotSupportedException {
		List<InvestmentPlane> stratPlanList = new ArrayList<>();
		stratPlanList.add(planList.get(0));
		int j  = 1;

		Mining mining = new Mining(stratPlanList, XMR_1Hs_day, moneroPriceUSD);
		
		System.out.println("Start mining with Hash Rate: " + mining.getMiningHash() + "H/s. Income per day is: " + Utils.formatter.format(mining.miningDay()) + "$");
		for (int i = 1; i <= investmentDays; i++) {
			income += mining.miningDay();
			
			if ((planList.size() - 1) >= j) {
				InvestmentPlane addPlane =  planList.get(j);
				double pricePlane = addPlane.getPlanPrice();
				if (income > pricePlane) {
					income -= pricePlane;
					mining.addPlane(addPlane);
					j++;
					
					printText(mining, i, addPlane);
						
					tempDays = i;
				} 
			}
		}
		mining.calculateLastDays(income);
		System.out.println(mining.getRecord());
		
	}

	private static void printText(Mining mining, int i, InvestmentPlane addPlane) {
		System.out.println(Utils.formatter.format(addPlane.getPlanPrice()) + "$ plan bought for "
				+ (i -  tempDays) + " days. " + "Total " + i + " days passed. " 
				+ " Continue mining with Hash Rate: " + mining.getMiningHash() + "H/s"
				+ " New income per day will be: " + Utils.formatter.format(mining.miningDay()) + "$ ");
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		
		List<InvestmentPlane> stratPlanList = new ArrayList<>();
		stratPlanList.add(plane_02);

		Mining mining = new Mining(stratPlanList, XMR_1Hs_day, moneroPriceUSD);

		calculateBestPlane(mining, investmentDays);
//		calculate(mining, investmentDays);
		
//		List<InvestmentPlane> demoPlanList = new ArrayList<>();
//		demoPlanList.add(plane_02);
//		demoPlanList.add(plane_01);
//		demoPlanList.add(plane_01);
//		demoPlanList.add(plane_01);
//		demoPlanList.add(plane_01);
//		demoPlanList.add(plane_01);
//		demoPlanList.add(plane_01);
//		demoPlanList.add(plane_01);
//		demoPlanList.add(plane_01);
//		printPlaneList(demoPlanList, investmentDays);
	}

}
