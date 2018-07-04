package domain;

public class DeliveryStatus {
	
	int delivery_status_id;
	String delivery_status;
	
	
	
	public DeliveryStatus() {
		super();
	}

	public DeliveryStatus(int delivery_status_id, String delivery_status) {
		super();
		this.delivery_status_id = delivery_status_id;
		this.delivery_status = delivery_status;
	}

	@Override
	public String toString() {
		return "DeliveryStatus [delivery_status_id=" + delivery_status_id + ", delivery_status=" + delivery_status
				+ "]";
	}

	public int getDelivery_status_id() {
		return delivery_status_id;
	}

	public void setDelivery_status_id(int delivery_status_id) {
		this.delivery_status_id = delivery_status_id;
	}

	public String getDelivery_status() {
		return delivery_status;
	}

	public void setDelivery_status(String delivery_status) {
		this.delivery_status = delivery_status;
	}
	
	
	
}
