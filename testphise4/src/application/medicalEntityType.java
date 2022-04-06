package application;

public class medicalEntityType {

	private int id;
	private String typeName;

	public medicalEntityType(int id, String typeName) {
		super();
		this.id = id;
		this.typeName = typeName;

	}

	public medicalEntityType(String typeName) {
		super();

		this.typeName = typeName;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public String toString() {
		return "medicalEntityType [id=" + id + ", typeName=" + typeName + "]";
	}

}
