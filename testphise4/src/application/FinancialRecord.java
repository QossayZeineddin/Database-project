package application;

public class FinancialRecord {
private int year;
private double totalCompanyPayment;
private double totalMemberPayment;
private double totalCatrgouriyCost;
private double totalMoney;

public FinancialRecord(int year, double totalCompanyPayment, double totalMemberPayment, double totalCatrgouriyCost,
		double totalMoney) {
	super();
	this.year = year;
	this.totalCompanyPayment = totalCompanyPayment;
	this.totalMemberPayment = totalMemberPayment;
	this.totalCatrgouriyCost = totalCatrgouriyCost;
	this.totalMoney = totalMoney;
}

public int getYear() {
	return year;
}

public void setYear(int year) {
	this.year = year;
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

public double getTotalCatrgouriyCost() {
	return totalCatrgouriyCost;
}

public void setTotalCatrgouriyCost(double totalCatrgouriyCost) {
	this.totalCatrgouriyCost = totalCatrgouriyCost;
}

public double getTotalMoney() {
	return totalMoney;
}

public void setTotalMoney(double totalMoney) {
	this.totalMoney = totalMoney;
}



}
