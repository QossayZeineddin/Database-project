package application;

public class MonthlyFinancialRecord {

	private String monthly;
	private double totalCompanyPayment;
	private double totalMemberPayment;
	
	
	
	public MonthlyFinancialRecord(String monthly, double totalCompanyPayment, double totalMemberPayment) {
		super();
		this.monthly = monthly;
		this.totalCompanyPayment = totalCompanyPayment;
		this.totalMemberPayment = totalMemberPayment;
	}
	
	public String getMonthly() {
		return monthly;
	}
	public void setMonthly(String monthly) {
		this.monthly = monthly;
	}
	public double getTotalCompanyPayment() {
		return totalCompanyPayment;
	}
	public void setTotalCompanyPayment(double totalCompanyPayment) {
		this.totalCompanyPayment = totalCompanyPayment;
	}
	public double getTotalMemberPayment() {
		return totalMemberPayment;
	}
	public void setTotalMemberPayment(double totalMemberPayment) {
		this.totalMemberPayment = totalMemberPayment;
	}

	@Override
	public String toString() {
		return "MonthlyFinancialRecord [monthly=" + monthly + ", totalCompanyPayment=" + totalCompanyPayment
				+ ", totalMemberPayment=" + totalMemberPayment + "]";
	}
	



}
