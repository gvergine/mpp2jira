package gvergine.mpp2jira.utils;

public class DateUtils
{
//	public static org.joda.time.LocalDate toJoda(java.time.LocalDate input) {
//		return new org.joda.time.LocalDate(input.getYear(),
//				input.getMonthValue(),
//				input.getDayOfMonth());
//	}
	
	public static org.joda.time.DateTime toJoda(java.time.LocalDate input) {
		return new org.joda.time.DateTime(input.getYear(),
				input.getMonthValue(),
				input.getDayOfMonth(),0,0,0);
	}

	public static java.time.LocalDate fromJoda(org.joda.time.LocalDate input) {
		return java.time.LocalDate.of(input.getYear(),
				input.getMonthOfYear(),
				input.getDayOfMonth());
	}

	public static java.time.LocalDate fromJoda(org.joda.time.DateTime input) {
		return java.time.LocalDate.of(input.getYear(),
				input.getMonthOfYear(),
				input.getDayOfMonth());
	}

}
