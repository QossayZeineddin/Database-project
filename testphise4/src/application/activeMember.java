package application;

import java.time.LocalDate;

public class activeMember {
	
	private int id;
	private LocalDate fromDate;
	private LocalDate toDate;
	private int categorieId;
	private int persoinId;
	
	
	
	
	public activeMember(int id, LocalDate fromDate, LocalDate toDate, int categorieId, int persoinId) {
		super();
		this.id = id;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.categorieId = categorieId;
		this.persoinId = persoinId;
	}
	
	
	
	
	public activeMember(LocalDate fromDate, LocalDate toDate, int categorieId, int persoinId) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.categorieId = categorieId;
		this.persoinId = persoinId;
	}




	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public int getCategorieId() {
		return categorieId;
	}
	public void setCategorieId(int categorieId) {
		this.categorieId = categorieId;
	}
	public int getPersoinId() {
		return persoinId;
	}
	public void setPersoinId(int persoinId) {
		this.persoinId = persoinId;
	}
	@Override
	public String toString() {
		return "activeMember [id=" + id + ", fromDate=" + fromDate + ", toDate=" + toDate + ", categorieId="
				+ categorieId + ", persoinId=" + persoinId + "]";
	}
	
	
	

}
