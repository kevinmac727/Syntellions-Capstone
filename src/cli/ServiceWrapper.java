package cli;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import domain.*;
import static java.lang.System.in;
import static java.lang.reflect.Array.get;
import java.util.HashMap;


import services.MenuServices;
import services.OrderService;
import services.UserService;

public class ServiceWrapper {
	
	Connection con;
    
	public ServiceWrapper(Connection con) {
		super();
		this.con = con;

	}

	public User login(String email, String password){
		
		UserService us = new UserService(con);
		User candidate = us.getByEmail(email);
		System.out.println(candidate.getFirstName());
		if(password.equals(candidate.getPassword())) return candidate;
		else return null;
	}
	
	public User register(String firstName, String lastName, String phone, String email, String password){
		//, String street, String city, String state, String country, String zip, String userStatus
		boolean result = false;
		String userId = Double.toString(Math.random()* 10001);
		String userStatusId = "0";

		User user = new User(userId,firstName,lastName,phone, email,password,userStatusId);
		UserService us = new UserService(con);
		result =  us.add(user);
		return user;
	}

	public static void printOptions(ArrayList<String> options){
		options.add("Go back");
		int count = 0;
		for(String option : options) {
			count++;
			System.out.println(count + ". " + option);
		}
		
	}
	
	public static void printMenuItems(ArrayList<Menu> menus){
         
		int count = 0;
		for(Menu menu: menus){
			count++;
			System.out.println(count + "  . $" + menu.getPrice() + " " + menu.getName());
		}

		System.out.println(++count + ". [Done]");

                // we dont need count
		System.out.println( ++count + ". Go Back");

	}
        
        
        public static  HashMap<Menu, Integer> printOrderItems(ArrayList<Menu> menus){
          // how can i get the id  Menu.getId()?  
          HashMap<Menu, Integer> mapCount = new HashMap<> ();
          double totalPrice = 0;
          for(Menu menu: menus){

              int count;
              if ( mapCount.containsKey(menu)){
                  count = mapCount.get(menu) ;
                  mapCount.put(menu, count+1);

              } else {

              count = 1;
              mapCount.put(menu, count);

                }
          }
          int x = 1;

            //for (int i = 1; i<= mapCount.size(); i++ ){
          System.out.println("\tCost\tCount\tUnit Price\tItem");
          for ( Menu key :mapCount.keySet() ){

               int value = mapCount.get(key);

               System.out.println( (x++) +".\t"+(value*key.getPrice())+"\t"+ value + "  \t$" + key.getPrice() + "\t\t" + key.getName());
               totalPrice = (totalPrice + (value*key.getPrice()));
          }//for Ends 

          System.out.println("Total: "+ totalPrice);

         // we dont need count
          System.out.println(x++ + " Go Back");
          
          return mapCount;
          
    }//printOrderItems() Ends 
        

    public static void printOrders(ArrayList<Order> orders){
            int count = 0;
            for(Order order: orders){
                    count++;
                    System.out.println(count + ". " + order.getPlaced_timestamp());
            }
            System.out.println(count++ + ". Go Back");
    }

    public void cancelOrder(Order order) {
            order.setDelivery_status_id("3");
            OrderService os = new OrderService(con);
            os.update(order);
    }

    public void submitOrder(Order currentOrder) {
            // TODO Auto-generated method stub

            currentOrder.setDelivery_status_id("0");
            OrderService os = new OrderService(con);
            os.add(currentOrder);

    }

    public ArrayList<Menu> getMenuItems(ArrayList<String> itemIds) {

            MenuServices ms = new MenuServices(con);
            ArrayList<Menu> items = new ArrayList<Menu>();


            for (String itemId:itemIds){
                    items.add(ms.getById(itemId));
            }

            return items;
    }

    public float calculateTotalPrice(ArrayList<String> item_ids) {
            int total = 0;
            ServiceWrapper sw = new ServiceWrapper(con);
            ArrayList<Menu> items = sw.getMenuItems(item_ids);
            for(Menu item: items){
                    total += item.getPrice();
            }
            return total;
    }


}
