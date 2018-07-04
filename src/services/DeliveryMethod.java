package services;

public class DeliveryMethod {

	String delivery_method_id;
	String delivery_method;
	
	public DeliveryMethod() {
		super();
	}

	public DeliveryMethod(String delivery_method_id, String delivery_method) {
		super();
		this.delivery_method_id = delivery_method_id;
		this.delivery_method = delivery_method;
	}
	
	@Override
	public String toString() {
		return "DeliveryMethod [delivery_method_id=" + delivery_method_id + ", delivery_method=" + delivery_method
				+ "]";
	}
	public String getDelivery_method_id() {
		return delivery_method_id;
	}
	public void setDelivery_method_id(String delivery_method_id) {
		this.delivery_method_id = delivery_method_id;
	}
	public String getDelivery_method() {
		return delivery_method;
	}
	public void setDelivery_method(String delivery_method) {
		this.delivery_method = delivery_method;
	}
	
	
}
