package domain;

public class DeliveryMethod {

	int delivery_method_id;
	String delivery_method;
	
	
	//Calls Object constructor
	public DeliveryMethod() {
		super();
	}

        //Parameterized constructor with call to Object constructor
	public DeliveryMethod(int delivery_method_id, String delivery_method) {
		super();
		this.delivery_method_id = delivery_method_id;
		this.delivery_method = delivery_method;
	}
	
        //Overriden toString method
	@Override
	public String toString() {
		return "DeliveryMethod [delivery_method_id=" + delivery_method_id + ", delivery_method=" + delivery_method
				+ "]";
	}
        
        //Standard getters and setters
	public int getDelivery_method_id() {
		return delivery_method_id;
	}
	public void setDelivery_method_id(int delivery_method_id) {
		this.delivery_method_id = delivery_method_id;
	}
	public String getDelivery_method() {
		return delivery_method;
	}
	public void setDelivery_method(String delivery_method) {
		this.delivery_method = delivery_method;
	}
	
	
}
