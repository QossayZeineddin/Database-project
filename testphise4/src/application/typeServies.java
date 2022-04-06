package application;

public class typeServies {

	private int id;
	private double typeServiesCost;
	private String tsName;

	public typeServies(double typeServiesCost, String tsName) {
		super();

		this.typeServiesCost = typeServiesCost;
		this.tsName = tsName;

	}

	public typeServies(int id, double typeServiesCost, String tsName) {
		super();
		this.id = id;
		this.typeServiesCost = typeServiesCost;
		this.tsName = tsName;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTypeServiesCost() {
		return typeServiesCost;
	}

	public void setTypeServiesCost(double typeServiesCost) {
		this.typeServiesCost = typeServiesCost;
	}

	public String getTsName() {
		return tsName;
	}

	public void setTsName(String tsName) {
		this.tsName = tsName;
	}

	@Override
	public String toString() {
		return "typeServies [id=" + id + ", typeServiesCost=" + typeServiesCost + ", tsName=" + tsName + "]";
	}

}
