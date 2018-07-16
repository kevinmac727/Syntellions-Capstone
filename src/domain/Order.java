package domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Order {
	
	private String order_id; //varchar
	private String user_id; //varchar
	private float tip; //number(5,2)
	private float total_price;//number(7,2)
	private Long placed_timestamp; //int
	private Long delivery_timestamp; //int
	private String card_id; //varchar
	private String instuctions; //carchar
	private String delivery_method_id; //varchar
	private String store_id; //varchar
	private String delivery_status_id; //varchar
	private static int orderSize = 0; 
        private SimpleDateFormat sDateFormater = new SimpleDateFormat("MM-dd-yyyy HH:mm");
	//Array to hold order items rather than the order_items table
	private ArrayList<String> item_ids = new ArrayList<String>();

	
	
	public Order(String order_id, String user_id, float tip, float total_price, Long placed_timestamp,
			Long delivery_timestamp, String card_id, String instuctions, String delivery_method_id, String store_id,
			String delivery_status_id, ArrayList<String> item_ids) {
		super();
		this.order_id = order_id;
		this.user_id = user_id;
		this.tip = tip;
		this.total_price = total_price;
		this.placed_timestamp = placed_timestamp;
		this.delivery_timestamp = delivery_timestamp;
		this.card_id = card_id;
		this.instuctions = instuctions;
		this.delivery_method_id = delivery_method_id;
		this.store_id = store_id;
		this.delivery_status_id = delivery_status_id;
		this.item_ids = item_ids;
	}

	public Order() {
		super();
		this.tip = 0;
		this.total_price = 0;
		this.placed_timestamp = new Date().getTime();
		this.delivery_timestamp = null;
		this.instuctions = "";
		this.delivery_method_id = "0";
		this.store_id = "0";
               // this.card_id = null;
		this.delivery_status_id = "0";
	}
	
	public ArrayList<String> getItem_ids() {
		return item_ids;
	}

	public void setItem_ids(ArrayList<String> item_ids) { 
		this.item_ids = (ArrayList<String>) item_ids.clone();
	}
	public void addItem_id(String item_id){
                orderSize++;
		this.item_ids.add(item_id);
	}
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public float getTip() {
		return tip;
	}

	public void setTip(float tip) {
		this.tip = tip;
	}

	public float getTotal_price() {
		return total_price;
	}

	public void setTotal_price(float total_price) {
		this.total_price = total_price;
	}

	public Long getPlaced_timestamp() {
		return placed_timestamp;
	}

	public void setPlaced_timestamp(Long placed_timestamp) {
            
           this.placed_timestamp = placed_timestamp;
	}

	public Long getDelivery_timestamp() {
		return delivery_timestamp;
	}

	public void setDelivery_timestamp(Long delivery_timestamp) {
                
            this.delivery_timestamp = delivery_timestamp;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public String getInstuctions() {
		return instuctions;
	}

	public void setInstuctions(String instuctions) {
		this.instuctions = instuctions;
	}

	public String getDelivery_method_id() {
		return delivery_method_id;
	}

	public void setDelivery_method_id(String delivery_method_id) {
		this.delivery_method_id = delivery_method_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getDelivery_status_id() {
		return delivery_status_id;
	}

	public void setDelivery_status_id(String delivery_status_id) {
		this.delivery_status_id = delivery_status_id;
	}

	@Override
	public String toString() {
            return "Order [order_id=" + order_id + ", user_id=" + user_id + ", tip=" + tip + ", total_price=" + total_price
                + ", placed_timestamp=" + placed_timestamp + ", delivery_timestamp=" + delivery_timestamp + ", card_id="
                + card_id + ", instuctions=" + instuctions + ", delivery_method_id=" + delivery_method_id
                + ", store_id=" + store_id + ", delivery_status_id=" + delivery_status_id + ", item_ids=" + item_ids
                + "]";
	}
	
        public int getOrderSize(){
            return orderSize;
        }
}
