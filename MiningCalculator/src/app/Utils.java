package app;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Utils {
	
	public static NumberFormat formatter = new DecimalFormat("### ##0.00");
	
	public static final int planPeriodYears = 2;
	public static final int planPeriodDays = 365 * planPeriodYears;
	
	public static final InvestmentPlan plan_01 = new InvestmentPlan(  830.0,  1000, planPeriodDays, "plan_01");
	public static final InvestmentPlan plan_02 = new InvestmentPlan( 1079.0,  1300, planPeriodDays, "plan_02");
	public static final InvestmentPlan plan_03 = new InvestmentPlan( 1411.0,  1700, planPeriodDays, "plan_03");
	public static final InvestmentPlan plan_04 = new InvestmentPlan( 1909.0,  2300, planPeriodDays, "plan_04");
	public static final InvestmentPlan plan_05 = new InvestmentPlan( 2241.0,  2700, planPeriodDays, "plan_05");
	public static final InvestmentPlan plan_06 = new InvestmentPlan( 2460.0,  3000, planPeriodDays, "plan_06");
	public static final InvestmentPlan plan_07 = new InvestmentPlan( 4100.0,  5000, planPeriodDays, "plan_07");
	public static final InvestmentPlan plan_08 = new InvestmentPlan( 6150.0,  7500, planPeriodDays, "plan_08");
	public static final InvestmentPlan plan_09 = new InvestmentPlan( 7200.0,  9000, planPeriodDays, "plan_09");
	public static final InvestmentPlan plan_10 = new InvestmentPlan( 8000.0, 10000, planPeriodDays, "plan_10");
	public static final InvestmentPlan plan_11 = new InvestmentPlan(12000.0, 15000, planPeriodDays, "plan_11");
	
	public static final List<InvestmentPlan> allPlanList = Collections.unmodifiableList(
			Arrays.asList(
						plan_01,
						plan_02,
						plan_03,
						plan_04,
						plan_05,
						plan_06,
						plan_07,
						plan_08,
						plan_09,
						plan_10,
						plan_11));
	
	
	public static String duration(long startTime, long endTime) {
		long totalTime = endTime - startTime;
		
		int seconds = (int) (totalTime / 1000) % 60 ;
		int minutes = (int) ((totalTime / (1000*60)) % 60);
		int milisec = (int) (totalTime - ((seconds * 1000) + (minutes * 60 * 1000)));
		
		StringBuilder sb = new StringBuilder(64);
		sb.append("Elapsed time: ");
        sb.append(minutes);
        sb.append(" min, ");
        sb.append(seconds);
        sb.append(" sec. ");
        sb.append(milisec);
        sb.append(" milsec.");
        
		return sb.toString();
	}
}
