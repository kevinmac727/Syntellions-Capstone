package domain;

public class Special {
	String item_ID;
	int discoutPercentage;
	
	public Special(String item_ID, int discoutPercentage) {
		super();
		this.item_ID = item_ID;
		this.discoutPercentage = discoutPercentage;
	}
	
	public String getItem_ID() {
		return item_ID;
	}
	public void setItem_ID(String item_ID) {
		this.item_ID = item_ID;
	}
	public int getDiscoutPercentage() {
		return discoutPercentage;
	}
	public void setDiscoutPercentage(int discoutPercentage) {
		this.discoutPercentage = discoutPercentage;
	}
	
	
}
