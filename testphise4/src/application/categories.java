package application;

public class categories {
	
	private  int id;
	private  int subscrubePeriod;
	private  int coverageRatio;
	private  int catrgouriyCost;
	
	public categories(int id, int subscrubePeriod, int coverageRatio, int catrgouriyCost) {
		super();
		this.id = id;
		this.subscrubePeriod = subscrubePeriod;
		this.coverageRatio = coverageRatio;
		this.catrgouriyCost = catrgouriyCost;
	}
	public categories(int subscrubePeriod, int coverageRatio, int catrgouriyCost) {
		super();
		this.subscrubePeriod = subscrubePeriod;
		this.coverageRatio = coverageRatio;
		this.catrgouriyCost = catrgouriyCost;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSubscrubePeriod() {
		return subscrubePeriod;
	}
	public void setSubscrubePeriod(int subscrubePeriod) {
		this.subscrubePeriod = subscrubePeriod;
	}
	public int getCoverageRatio() {
		return coverageRatio;
	}
	public void setCoverageRatio(int coverageRatio) {
		this.coverageRatio = coverageRatio;
	}
	public int getCatrgouriyCost() {
		return catrgouriyCost;
	}
	public void setCatrgouriyCost(int catrgouriyCost) {
		this.catrgouriyCost = catrgouriyCost;
	}
	
	@Override
	public String toString() {
		return "categories [id=" + id + ",subscrubePeriod=" + subscrubePeriod + ",coverageRatio=" + coverageRatio + ",catrgouriyCost=" + catrgouriyCost + "]";
	} 
	
}
