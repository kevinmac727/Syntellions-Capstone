package cli;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import domain.Menu;
import domain.Order;
import domain.Store;
import domain.User;
import services.EmailService;
import services.MenuServices;
import services.OrderService;
import services.StoreService;
import services.UserService;

public class Tiger{

	public static ServiceWrapper sw;
	public static Connection con;
	public static User currentUser;
	public static Order currentOrder;
	public static Store currentStore;
	
	static Scanner sc;

	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
	        con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "db_uSpring", "pass");
		} catch (Exception e) {
			e.printStackTrace();
		}
		sw  = new ServiceWrapper(con);
		sc = new Scanner(System.in);
		firstScreen();
		sc.close();
	}
	//Screens will return either -1, 0, or 1 (-1 = Go back 1 screen and loop, 0 = return to first/home screen, 1 = possible exception)
	public static int firstScreen(){
            
            //Loop for duration of the program
            int input = -1;
            while(input == -1 || input == 0)
            {
		System.out.println(
                          " __  __ _                     _ _        _____       __     \n"
                        + "|  \\/  (_)                   (_| )      / ____|     / _|    \n"
                        + "| \\  / |_ _ __ ___  _ __ ___  _|/ ___  | |     __ _| |_ ___ \n"
                        + "| |\\/| | | '_ ` _ \\| '_ ` _ \\| | / __| | |    / _` |  _/ _ \\\n"
                        + "| |  | | | | | | | | | | | | | | \\__ \\ | |___| (_| | ||  __/\n"
                        + "|_|  |_|_|_| |_| |_|_| |_| |_|_| |___/  \\_____\\__,_|_| \\___|");
		ArrayList<String> options = new ArrayList<String>();
		options.add("Login");
		options.add("Register");
		options.add("Quit");
		int count = 0;
		for(String option : options) {
			count++;
			System.out.println(count + ". " + option);
		}
		
	    
                input = sc.nextInt();
            
                switch(input){
                    case 1:
    			input = loginScreen();
                        break;
                    case 2:
    			input = registerScreen();
                        break;
                    case 3:
    			System.out.println("Goodbye");
    			System.exit(0);
                        break;
                    case 4:
                        //May need to alter admin screens to follow new conventions. Test
    			AdminAndManager aam = new AdminAndManager(con);
    			aam.adminScreen();
                        //TEMPORARY HARD CODE, FIX AS SOON AS IT IS POSSIBLE
                        input = -1;
                        break;
                    default:
                        //Ensure valid input is made, loop until it is
                        input = -1;
                }
            }
            return -1;
	}
		
	public static int loginScreen(){
		System.out.println("\n*Login*");
		System.out.println("Enter email:");
	    String email = sc.next();
		System.out.println("Enter password:");
	    String password = sc.next();
	    
		UserService us = new UserService(con);
		User candidate = us.getByEmail(email);
		if(candidate == null){
			System.out.println("Wrong email");
                        return -1;
			//firstScreen();
		}
		if(password.equals(candidate.getPassword())){
			currentUser = candidate;
			currentOrder = new Order();
			currentOrder.setOrder_id(Double.toString(Math.random()* 10001));
			currentOrder.setUser_id(currentUser.getUserId());
			currentOrder.setDelivery_status_id("0");
			//currentOrder.setCard_id();
			StoreService ss = new StoreService(con);
			currentStore = ss.getById("0");
                        return homeScreen();
	    }
	    else{
	    	System.out.println("Wrong email or password");
	    	try {
				TimeUnit.SECONDS.sleep(1);
                                return -1;
				//firstScreen();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
                System.out.println("Shouldn't be here");
                return 0;
	}
	public static int registerScreen(){
		System.out.println("\n*Register*");
		System.out.println("Enter email:");
	    String email = sc.next();
		System.out.println("Enter password:");
	    String password = sc.next();
		System.out.println("Enter password again:");
	    String passwordConfirm = sc.next();
		System.out.println("Enter first name:");
	    String first = sc.next();
		System.out.println("Enter last name:");
	    String last = sc.next();
		System.out.println("Enter phone:");
	    String phone = sc.next();
		/*System.out.println("Enter street:");
	    String street = sc.next();
		System.out.println("Enter city:");
	    String city = sc.next();
		System.out.println("Enter state:");
	    String state = sc.next();
		System.out.println("Enter country:");
	    String country = sc.next();
		System.out.println("Enter zip:");
	    String zip = sc.next();
		System.out.println("Enter status:");
	    String status = sc.next();*/
	    //, street, city, state, country, zip, status
	    if(password.equals(passwordConfirm)){
	    	System.out.println("Registered");
	    	currentUser = sw.register(first, last, phone, email, password);
			currentOrder = new Order();
			currentOrder.setOrder_id(Double.toString(Math.random()* 10001));
			currentOrder.setUser_id(currentUser.getUserId());
			currentOrder.setDelivery_status_id("0");
	    	return homeScreen();
	    }else{
	    	System.out.println("Mismatching passwords, try again");
                return -1;
	    	//firstScreen();
	    }


	}

	public static int homeScreen(){
            //Outside loop because it should only display on initial entry
            System.out.println("Welcome " + currentUser.getFirstName() + "!");
            
            if(currentUser.getUserStatusId().equals("0"))
             {
                System.out.println("We kindly ask that you verify your email before continuing forward!");
                System.out.println("An email will be sent shortly...\n");
                String verifyInput;
                String verifyCode = (Double.toString(Math.random()*10000)).substring(0, 4);
                EmailService.sendEmail(currentUser.getEmail(), verifyCode);
                while(currentUser.getUserStatusId().equals("0"))
                {
                    System.out.println("Please verify your email address.");
                    System.out.println("Submit the code found in the email below: ");
                    verifyInput = sc.next();
                    if(verifyInput.equals(verifyCode))
                    { 
                        currentUser.setUserStatusId("1");
                        UserService us = new UserService(con);
                        us.update(currentUser);
                        System.out.println("Thank you for verifying your email address!");
                    }
                    else
                    {
                        System.out.println("Something went wrong!");
                        System.out.println("Please try verifying your email address again.\n");
                    }
                }
            }
            
            //Default value to enter loop
	    int input =  -1;
            
            //Loops while input isn't valid
            while(input == -1 || input == 0)
            {
                
		System.out.println("\n*Home*");
		ArrayList<String> options = new ArrayList<String>();
		options.add("Menu");
		options.add("Order");
		options.add("Account");
		options.add("Store Details");
		options.add("Logout");
		options.add("Quit");
		int count = 0;
		for(String option : options) {
			count++;
			System.out.println(count + ". " + option);
		}
              
            
                input = sc.nextInt();
                //Switch on user input
                switch(input)
                {
                    case 1:
                        input = menuScreen();
                        break;
                    case 2:
                        input = currentOrderScreen();
                        break;
                    case 3:
                        input = accountScreen();
                        break;
                    case 4:
                        input = storeDetailsScreen();
                        
                        break;
                    case 5:
                        return -1;
                        //input = firstScreen();
                        //break;
                    case 6:
                        System.out.println("Goodbye");
                        System.exit(0);
                        break;
                    default:
                        //No valid selection was made, loop back until input is valid
                        input = -1;
                }
                //Previous code
                /*
		if(input==1) menuScreen();
		if(input==2) currentOrderScreen();    	
		if(input==3) accountScreen();
		if(input==4) storeDetailsScreen();   	
		if(input==5) firstScreen();
		if(input==6) {
			System.out.println("Goodbye");
    		System.exit(0);
                }
                */
            }
            System.out.println("Shouldn't be here");
            return -1;
	}
	
	public static int menuScreen(){
		System.out.println("\n*Menu*");
		MenuServices ms = new MenuServices(con);
		ArrayList<Menu> menus = ms.getAll();
		ServiceWrapper.printMenuItems(menus);
	    int input = sc.nextInt();
	    if(input==menus.size()+1) return 0;//homeScreen();
	    else menuItemScreen(menus.get(input-1));
            //Test, unsure if this is proper
            return -1;
	}
	public static int menuItemScreen(Menu menu){
		System.out.println("\n*" + menu.getName() + "*");
		System.out.println(menu.getDescription());
		System.out.println("$" + menu.getPrice());
		System.out.println("1. Enter Quantity");
		System.out.println("2. Go Back");
	    int input = sc.nextInt();
	    if(input==1) itemQuantityScreen(menu);
	    else if(input==2) menuScreen();
            
            //Test, unsure if this is proper
            return -1;
	}
	//TODO finish this
	public static int itemQuantityScreen(Menu menu){
		System.out.println("Enter Quantity");
	    int input = sc.nextInt();
	    for(int i=0;i<input;i++) currentOrder.addItem_id(menu.getId());
		System.out.println("Item(s) added");
		//menuScreen();
                return -1;
	}
	public static int currentOrderScreen() {
            int input = -1;
            while(input == -1)
            {
		System.out.println("\n*Current Order*");
		System.out.println("Placed: " +currentOrder.getPlaced_timestamp());
		System.out.println("Delivered: " +currentOrder.getDelivery_timestamp());
		ServiceWrapper sw = new ServiceWrapper(con);
		currentOrder.setTotal_price(sw.calculateTotalPrice(currentOrder.getItem_ids()));
		System.out.println("Total price: $" +currentOrder.getTotal_price());
		System.out.println("Method: " +currentOrder.getDelivery_method_id());
		System.out.println("Status: " +currentOrder.getDelivery_status_id());
		System.out.println("1. Cancel");
		System.out.println("2. View\\Edit Items");
		System.out.println("3. Edit Order");
		System.out.println("4. Submit Order");
		System.out.println("5. Go Back");
	    
                input = sc.nextInt();
                
                switch(input)
                {
                    case 1:
                        //Test functionality, unsure of purpose (supposed to cancel order)
                        currentOrder = new Order();
			currentOrder.setOrder_id(Double.toString(Math.random()* 10001));
			currentOrder.setUser_id(currentUser.getUserId());
			currentOrder.setDelivery_status_id("0");
                        break;
                    case 2:
                        input = viewEditOrderItems(currentOrder);
                        break;
                    case 3:
                        input = editOrder(currentOrder);
                        break;
                    case 4:
                        if (confirm()) {
                            sw.submitOrder(currentOrder);
                        }
                        input = -1;
                        break;
                    case 5:
                        //homeScreen();
                        //break;
                        return 0;
                    default:
                        input = -1;
                        
                }
            }
            //Previous code
            /*
	    if(input==1 && confirm()) {
	    	currentOrder = new Order();
			currentOrder.setOrder_id(Double.toString(Math.random()* 10001));
			currentOrder.setUser_id(currentUser.getUserId());
			currentOrder.setDelivery_status_id("0");
	    }
	    if(input==2) viewEditOrderItems(currentOrder);
	    if(input==3) editOrder(currentOrder);
	    if(input==4 && confirm()) sw.submitOrder(currentOrder);
	    else if(input==5) homeScreen();
            */
            
            System.out.println("Shouldn't be here");
            return -1;
	}
	
	private static int editOrder(Order currentOrder2) {
            int input = -1;
            //This loop is true because it serves the purpose of solely editing info, can only leave menu upon correct input
            while(true)
            {
		System.out.println("\n*Edit Order*");
		ArrayList<String> options = new ArrayList<String>();
		options.add("Edit Tip");
		options.add("Edit delivery time");
		options.add("Edit Instructions");
		options.add("Edit Delivery Method");
		options.add("Edit Store");
		options.add("Go Back");
		int count = 0;
		for(String option : options) {
			count++;
			System.out.println(count + ". " + option);
		}
	    
                input = sc.nextInt();
                switch(input)
                {
                    case 1:
                        //Test, may need to change to double
                        int newTip = Integer.parseInt(editString());
    			currentOrder.setTip(newTip);
    			System.out.println("Tip Changed to: $" + newTip);
                        break;
                    case 2:
                        int newDelivery_timestamp = Integer.parseInt(editString());
    			currentOrder.setDelivery_timestamp(newDelivery_timestamp);
    			System.out.println("Delivery Time Changed to: " + newDelivery_timestamp);
                        break;
                    case 3:
                        String newInstructions = editString();
    			currentOrder.setInstuctions(newInstructions);
    			System.out.println("Instructions Changed to: " + newInstructions);
                        break;
                    case 4:
                        String newDelivery_method = editString();
    			currentOrder.setDelivery_method_id(newDelivery_method);
    			System.out.println("Delivery Method Changed to: " + newDelivery_method);
                        break;
                    case 5:
                        String newStore = editString();
    			currentOrder.setStore_id(newStore);
    			System.out.println("Delivery Method Changed to: " + newStore);
                        break;
                    case 6:
                        //homeScreen();
                        return -1;
                    default:
                        input = -1;
                        
                }
            }
                //Previous code
                /*
    		if(input==1){
    			int newTip = Integer.parseInt(editString());
    			currentOrder.setTip(newTip);
    			System.out.println("Tip Changed to: $" + newTip);
    		}
    		if(input==2){
    			int newDelivery_timestamp = Integer.parseInt(editString());
    			currentOrder.setDelivery_timestamp(newDelivery_timestamp);
    			System.out.println("Delivery Time Changed to: " + newDelivery_timestamp);
    		}
    		if(input==3){
    			String newInstructions = editString();
    			currentOrder.setInstuctions(newInstructions);
    			System.out.println("Instructions Changed to: " + newInstructions);
    		}
    		if(input==4){
    			String newDelivery_method = editString();
    			currentOrder.setDelivery_method_id(newDelivery_method);
    			System.out.println("Delivery Method Changed to: " + newDelivery_method);
    		}
    		if(input==5){
    			String newStore = editString();
    			currentOrder.setStore_id(newStore);
    			System.out.println("Delivery Method Changed to: " + newStore);
    		}

    		if(input==6) homeScreen();
                */
	    //currentOrderScreen();
	           //System.out.println("Shouldn't be here");
                   //return -1;
	}

	//TODO get item from item id here
        
	private static int viewEditOrderItems(Order order) {
		System.out.println("*View Items*");
                
		ArrayList<String> itemIds = currentOrder.getItem_ids();
                
		ArrayList<Menu> items = sw.getMenuItems(itemIds);
		if(items.isEmpty()) System.out.println("No items");
		//ServiceWrapper.printMenuItems(items);
                 ServiceWrapper.printOrderItems(items);
                //System.out.println( " Go Back");
                
	    int input = sc.nextInt();
	    if(input==items.size()) return 0;//homeScreen();
	    else if(input==items.size()+1) return -1;//currentOrderScreen();
	    else orderItemScreen(items.get(input));
            
            //Test, unsure if proper
            return -1;
	}
	public static int orderItemScreen(Menu menu){
		/*System.out.println(menu.getName());
		System.out.println(menu.getDescription());
		System.out.println(menu.getPrice());
		System.out.println("1. Enter Quantity");
		System.out.println("2. Go Back");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    if(input==1) itemQuantityScreen(menu);
	    else if(input==2) System.exit(0);*/
                
                
                //Test after implementing
                return -1;
	}

	//TODO
	public static int submitOrder(){
		System.out.println("\n*Submit*");

	    //OrderService os = new OrderService(con);
	    //input should be equal to number of items in order
	    //Menu menu = null;
	   // int input = 0;
	    //for(int i=0;i<input;i++){
	    	//create order item and add to item
	    	//os.addItem_id(menu.getId(), currentOrder.getOrder_id());
	   // }
	    //OrderService os = new OrderService(con);
	    //os.update(currentOrder);
            
            //Test after implementing
            return -1;
	}
	
	public static int accountScreen(){
            int input = -1;
            while(input == -1)
            {
		System.out.println("\n*Account*");
		ArrayList<String> options = new ArrayList<String>();
		options.add("Edit First Name");
		options.add("Edit Last Name");
		options.add("Edit Email");
		options.add("Edit Password");
		options.add("Edit Phone Number");
		options.add("Edit Payment Options");
		options.add("Edit Saved Locations");
		options.add("View Orders");
		options.add("Go Back");
		int count = 0;
		for(String option : options) {
			count++;
			System.out.println(count + ". " + option);
		}
	    
                input = sc.nextInt();
                
                switch(input)
                {
                    case 1:
                        String newFirstName = editString();
    			currentUser.setFirstName(newFirstName);
    			System.out.println("First Name Changed to: " + newFirstName);
                        break;
                    case 2:
                        String newLastName = editString();
    			currentUser.setLastName(newLastName);
    			System.out.println("Last Name Changed to: " + newLastName);
                        break;
                    case 3:
                        String newEmail = editString();
    			currentUser.setEmail(newEmail);
    			System.out.println("Email Changed to: " + newEmail);
                        break;
                    case 4:
                        String newPassword = editString();
    			currentUser.setPassword(newPassword);
    			System.out.println("Password Changed to: " + newPassword);
                    case 5:
                        String newPhoneNumber = editString();
    			currentUser.setPhone(newPhoneNumber);
    			System.out.println("Phone Number Changed to: " + newPhoneNumber);
                        break;
                    case 6:
                        editCards();
                        break;
                    case 7:
                        editLocations();
                        break;
                    case 8:
                        input = allOrdersScreen();
                        break;
                    case 9:
                        //homeScreen();
                        return 0;
                    default:
                        input = -1;
                        continue;
                }
                UserService us = new UserService(con);
                us.update(currentUser);
                //accountScreen();
            }
            //Previous code
            /*
    		if(input==1){
    			String newFirstName = editString();
    			currentUser.setFirstName(newFirstName);
    			System.out.println("First Name Changed to: " + newFirstName);
    		}
    		if(input==2){
    			String newLastName = editString();
    			currentUser.setLastName(newLastName);
    			System.out.println("Last Name Changed to: " + newLastName);
    		}
    		if(input==3){
    			String newEmail = editString();
    			currentUser.setEmail(newEmail);
    			System.out.println("Email Changed to: " + newEmail);
    		}
    		if(input==4){
    			String newPassword = editString();
    			currentUser.setPassword(newPassword);
    			System.out.println("Password Changed to: " + newPassword);
    		}
    		if(input==5){

    			String newPhoneNumber = editString();
    			currentUser.setPhone(newPhoneNumber);
    			System.out.println("Phone Number Changed to: " + newPhoneNumber);
    		}
    		if(input==6) editCards();
    		if(input==7) editLocations();
    		if(input==8) allOrdersScreen();
    		if(input==9) homeScreen();
	    */
	    
            System.out.println("Shouldn't be here");
            return -1;
	}
	private static void editLocations()
        {
		// TODO Auto-generated method stub
                System.out.println("Please enter new street address");
                String address = sc.next();
                System.out.println("Please enter new city");
                String city = sc.next();
                System.out.println("Please enter new country");
                String country = sc.next();
                System.out.println("Please enter new state");
                String state = sc.next();
                System.out.println("Please enter new zip code");
                String zipCode = sc.next();
                
	}

	private static void editCards() 
        {
		
		
	}

	private static String editString() 
        {
		System.out.println("Enter new value");
	    String inp = sc.next();
		return inp;
	}

	public static int allOrdersScreen()
        {
		System.out.println("\n*All orders*");
		OrderService os = new OrderService(con);
		ArrayList<Order> orders = os.getUserOrders(currentUser.getUserId());
		ServiceWrapper.printOrders(orders);
	    int input = sc.nextInt();
	    if(input==orders.size()) return -1;//homeScreen();
	    else oldOrderScreen(orders.get(input));
            
            //Test, unsure if proper
            return -1;
	}
	public static int oldOrderScreen(Order order) 
        {
		System.out.println("Placed: " +order.getPlaced_timestamp());
		System.out.println("Delivered: " +order.getDelivery_timestamp());
		System.out.println("Total price: " +order.getTotal_price());
		System.out.println("Method: " +order.getDelivery_method_id());
		System.out.println("Status: " +order.getDelivery_status_id());
		System.out.println("1. Reorder");
		System.out.println("2. Go Back");
	    int input = sc.nextInt();
	    if(input==1 && confirm()) {
	    	currentOrder=order;
	    	//TODO find out what the status id this thing needs is
	    	currentOrder.setDelivery_status_id("1");
	    }
	    else if(input==2) return -1;//accountScreen();
            
            
            System.out.println("Shouldn't be here");
            return -1;
	}
	public static int storeDetailsScreen(){
		System.out.println("\n*Store*");
		System.out.println("Name: " + currentStore.getStoreName());
		System.out.println("Phone Number: " + currentStore.getPhoneNumber());
		System.out.println("Location: " + currentStore.getLocationId());
		System.out.println("Open: " + currentStore.getOpenTime());
		System.out.println("Close: " + currentStore.getCloseTime());
                
                System.out.println("1. Go Back");
                //Test, no loop or input required at this screen?
		//homeScreen();
                //Hard code-ish way to just make user confirm to leave store details display
                int temp = -1;
                while(temp != 1)
                {
                    temp = sc.nextInt();
                }
                return -1;
	}

	public static boolean confirm(){
		System.out.println("\n1*Confirm*");
		System.out.println("1. Yes");
		System.out.println("2. No");
	    int input = sc.nextInt();
	    if(input==1) return true;
	    return false;
	}
}
