package application;

public class receivedServies {

	private int id;
	private int visitId;
	private int typeServiesId;
	private String detels;
	
	public receivedServies(int id, int visitId, int typeServiesId, String detels) {
		super();
		this.id = id;
		this.visitId = visitId;
		this.typeServiesId = typeServiesId;
		this.detels = detels;
	}

	public receivedServies(int visitId, int typeServiesId, String detels) {
		super();
		this.visitId = visitId;
		this.typeServiesId = typeServiesId;
		this.detels = detels;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVisitId() {
		return visitId;
	}

	public void setVisitId(int visitId) {
		this.visitId = visitId;
	}

	public int getTypeServiesId() {
		return typeServiesId;
	}

	public void setTypeServiesId(int typeServiesId) {
		this.typeServiesId = typeServiesId;
	}

	public String getDetels() {
		return detels;
	}

	public void setDetels(String detels) {
		this.detels = detels;
	}

	@Override
	public String toString() {
		return "receivedServies [id=" + id + ", visitId=" + visitId + ", typeServiesId=" + typeServiesId + ", detels="
				+ detels + "]";
	}
	
	
	

}
