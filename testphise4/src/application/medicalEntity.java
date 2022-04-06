package application;



public class medicalEntity {

	private int ID;
	private String address ;
	private String theName;
	private String phoneNumber;
	private int medicalEntityTypeId;
	public medicalEntity(int iD, String address, String theName, String phoneNumber, int medicalEntityTypeId) {
		super();
		ID = iD;
		this.address = address;
		this.theName = theName;
		this.phoneNumber = phoneNumber;
		this.medicalEntityTypeId = medicalEntityTypeId;
	}
	public medicalEntity(String address, String theName, String phoneNumber, int medicalEntityTypeId) {
		super();
		this.address = address;
		this.theName = theName;
		this.phoneNumber = phoneNumber;
		this.medicalEntityTypeId = medicalEntityTypeId;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	public String getTheName() {
		return theName;
	}
	public void setTheName(String theName) {
		this.theName = theName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getMedicalEntityTypeId() {
		return medicalEntityTypeId;
	}
	public void setMedicalEntityTypeId(int medicalEntityTypeId) {
		this.medicalEntityTypeId = medicalEntityTypeId;
	}
	@Override
	public String toString() {
		return "medicalEntity [ID=" + ID + ", address=" + address + ", theName=" + theName + ", phoneNumber=" + phoneNumber
				+ ", medicalEntityTypeId=" + medicalEntityTypeId + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
}