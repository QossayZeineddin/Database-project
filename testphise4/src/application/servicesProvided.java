package application;

public class servicesProvided {

	private int id;
	private int medicalEntityId;
	private int typeServiesId;
	
	public servicesProvided(int id, int medicalEntityId, int typeServiesId) {
		super();
		this.id = id;
		this.medicalEntityId = medicalEntityId;
		this.typeServiesId = typeServiesId;
	}

	public servicesProvided(int medicalEntityId, int typeServiesId) {
		super();
		this.medicalEntityId = medicalEntityId;
		this.typeServiesId = typeServiesId;
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

	public int getTypeServiesId() {
		return typeServiesId;
	}

	public void setTypeServiesId(int typeServiesId) {
		this.typeServiesId = typeServiesId;
	}

	@Override
	public String toString() {
		return "servicesProvided [id=" + id + ", medicalEntityId=" + medicalEntityId + ", typeServiesId="
				+ typeServiesId + "]";
	}
	
	

}
