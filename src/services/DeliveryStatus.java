package services;

public class DeliveryStatus {
	
	String delivery_status_id;
	String delivery_status;

	public DeliveryStatus() {
		super();
	}

	public DeliveryStatus(String delivery_status_id, String delivery_status) {
		super();
		this.delivery_status_id = delivery_status_id;
		this.delivery_status = delivery_status;
	}

	@Override
	public String toString() {
		return "DeliveryStatus [delivery_status_id=" + delivery_status_id + ", delivery_status=" + delivery_status
				+ "]";
	}

	public String getDelivery_status_id() {
		return delivery_status_id;
	}

	public void setDelivery_status_id(String delivery_status_id) {
		this.delivery_status_id = delivery_status_id;
	}

	public String getDelivery_status() {
		return delivery_status;
	}

	public void setDelivery_status(String delivery_status) {
		this.delivery_status = delivery_status;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeliveryStatus other = (DeliveryStatus) obj;
		if (delivery_status == null) {
			if (other.delivery_status != null)
				return false;
		} else if (!delivery_status.equals(other.delivery_status))
			return false;
		if (delivery_status_id == null) {
			if (other.delivery_status_id != null)
				return false;
		} else if (!delivery_status_id.equals(other.delivery_status_id))
			return false;
		return true;
	}
}
