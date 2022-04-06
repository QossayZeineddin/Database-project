package application;

import java.time.LocalDate;

public class Visit {
	
	private  int id;	
	private double companyPayment ; 
	private LocalDate dateVisit ;
	private  int memberPayment;
	private  int medicalEntityId;
	private  int memberId;
	


	public Visit(int id, double companyPayment, LocalDate dateVisit, int memberPayment, int medicalEntityId,
			int memberId) {
		super();
		this.id = id;
		this.companyPayment = companyPayment;
		this.dateVisit = dateVisit;
		this.memberPayment = memberPayment;
		this.medicalEntityId = medicalEntityId;
		this.memberId = memberId;
	}
	
	

	public Visit(double companyPayment, LocalDate dateVisit, int memberPayment, int medicalEntityId, int memberId) {
		super();
		this.companyPayment = companyPayment;
		this.dateVisit = dateVisit;
		this.memberPayment = memberPayment;
		this.medicalEntityId = medicalEntityId;
		this.memberId = memberId;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMemberPayment() {
		return memberPayment;
	}

	public void setMemberPayment(int memberPayment) {
		this.memberPayment = memberPayment;
	}

	public int getMedicalEntityId() {
		return medicalEntityId;
	}

	public void setMedicalEntityId(int medicalEntityId) {
		this.medicalEntityId = medicalEntityId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public double getCompanyPayment() {
		return companyPayment;
	}

	public void setCompanyPayment(double companyPayment) {
		this.companyPayment = companyPayment;
	}

	public LocalDate getDateVisit() {
		return dateVisit;
	}

	public void setDateVisit(LocalDate dateVisit) {
		this.dateVisit = dateVisit;
	}

	@Override
	public String toString() {
		return "Visit [id=" + id + ", memberPayment=" + memberPayment + ", medicalEntityId=" + medicalEntityId
				+ ", memberId=" + memberId + ", companyPayment=" + companyPayment + ", dateVisit=" + dateVisit + "]";
	}
	
	

}