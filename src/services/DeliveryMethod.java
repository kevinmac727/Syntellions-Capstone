package services;

public class DeliveryMethod {

	String delivery_method_id;
	String delivery_method;
	
	public DeliveryMethod() {
		super();
	}
        //Constructor takes in the delivery method id and
        //the deliver method.
	public DeliveryMethod(String delivery_method_id, String delivery_method) {
		super();
		this.delivery_method_id = delivery_method_id;
		this.delivery_method = delivery_method;
	}
	//Returns a to string printing out the delivery method id and the delivery method.
	@Override
	public String toString() {
		return "DeliveryMethod [delivery_method_id=" + delivery_method_id + ", delivery_method=" + delivery_method
				+ "]";
	}
        //Returns deliveryMethod id.
	public String getDelivery_method_id() {
		return delivery_method_id;
	}
        //Sets delivery method id.
	public void setDelivery_method_id(String delivery_method_id) {
		this.delivery_method_id = delivery_method_id;
	}
        //Returns delivery method
	public String getDelivery_method() {
		return delivery_method;
	}
        //Sets delivery method.
	public void setDelivery_method(String delivery_method) {
		this.delivery_method = delivery_method;
	}
	
	
}
