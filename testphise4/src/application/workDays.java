package application;

public class workDays {
	private int id;
	private String dayName ;
	private  int medicalEntityId;
	
	
	
	
	public workDays(int id, String dayName, int medicalEntityId) {
		super();
		this.id = id;
		this.dayName = dayName;
		this.medicalEntityId = medicalEntityId;
	}

	
	
	public workDays(String dayName, int medicalEntityId) {
		super();
		this.dayName = dayName;
		this.medicalEntityId = medicalEntityId;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMedicalEntityId() {
		return medicalEntityId;
	}

	public void setMedicalEntityId(int medicalEntityId) {
		this.medicalEntityId = medicalEntityId;
	}

	public String getDayName() {
		return dayName;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}



	@Override
	public String toString() {
		return "workDays [id=" + id + ", dayName=" + dayName + ", medicalEntityId=" + medicalEntityId + "]";
	}

	
	
	

}