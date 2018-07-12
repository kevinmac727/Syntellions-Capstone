package cli;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import domain.Card;
import domain.IdException;
import domain.Menu;
import domain.Order;
import domain.Store;
import domain.User;
import domain.UserStatus;
import services.CardService;
import services.DeliveryMethod;
import services.DeliveryMethodService;
import services.DeliveryStatus;
import services.DeliveryStatusService;
import services.ItemType;
import services.ItemTypeService;
import services.MenuServices;
import services.OrderItem;
import services.OrderItemService;
import services.OrderService;
import services.StoreService;
import services.UserService;
import services.UserStatusService;

public class AdminAndManager {
	
	static Connection con;
	
	public AdminAndManager(Connection con)
        {
		AdminAndManager.con = con;
	}
	
	public void adminScreen()
        
        {
		ArrayList<String> options = new ArrayList<String>();
		System.out.println("Admin View");
		options.add("Alter Cards");
		options.add("Alter Delivery Methods");
		options.add("Alter Delivery Statuses");
		options.add("Alter Items");
		options.add("Alter Item Types");
		options.add("Alter Locations");
		options.add("Alter Orders");
		options.add("Alter Order_items"); //Probably don't need this one
		options.add("Alter Users");
		options.add("Alter User Statuses");
                options.add("Exit Application");
                options.add("Go back");
                int input = 0;
                int option;
            while(true){
                if(input == 12){
                    break;
                }
                ServiceWrapper.printOptions(options);
                Scanner sc = new Scanner(System.in);
                input = sc.nextInt();
                switch(input){
                    case 1:
                            {
                                    option = optionsScreen("Card");
                                    switch(option){
                                            case 1:
                                                    alterCardScreen();
                                                    break;
                                            case 2:
                                                    addCardScreen();
                                                    break;
                                            case 3:
                                                    deleteCardScreen();
                                                    break;
                                    }
                                    break;
                            }
                    case 2:
                            option = optionsScreen("Delivery Method");
                            switch(option){
                                case 1:
                                    alterDeliveryMethodScreen();
                                    break;
                                case 2:
                                    addDeliveryMethodScreen();
                                    break;
                                case 3:
                                    deleteDeliveryMethodScreen();
                                    break;
                            }
                            break;
                    case 3:
                            option = optionsScreen("Delivery Status");
                            switch(option){
                                case 1:
                                    alterDeliveryStatusScreen();
                                    break;
                                case 2:
                                    addDeliveryStatusScreen();
                                    break;
                                case 3:
                                    deleteDeliveryStatusScreen();
                                    break;
                            }
                            break;
                    case 4:
                    {
                            option = optionsScreen("Item");
                            switch(option){
                                    case 1:
                                            alterItemScreen();
                                            break;
                                    case 2:
                                            addItemScreen();
                                            break;
                                    case 3:
                                            deleteItemScreen();
                                            break;
                            }
                            break;
                    }
                    case 5://TODO
                        {      
                            option = optionsScreen("Item Type");
                            switch(option){
                                case 1:
                                    alterItemTypeScreen();
                                    break;
                                case 2:
                                    addItemTypeScreen();
                                    break;
                                case 3:
                                    deleteItemTypeScreen();
                                    break;
                            }
                        }
                        break;
                    case 6:
                            option = optionsScreen("Location");
                            switch(option){
                                case 1:
                                    alterLocationScreen();
                                    break;
                                case 2:
                                    addLocationScreen();
                                    break;
                                case 3: 
                                    deleteLocationScreen();
                                    break;
                            }
                            break;
                    case 7: {
                                option = optionsScreen("Order");
                                switch(option){
                                    case 1:
                                        alterOrderScreen();
                                        break;
                                    case 2:
                                        addOrderScreen();
                                        break;
                                    case 3:
                                        deleteOrderScreen();
                                        break;
                                }
                            }
                            break;
                    case 8:
                    {        
                            System.out.println("How would you like to modify Order Items");
                            System.out.println("1. Add");
                            System.out.println("2. Delete");
                            
                            option = sc.nextInt();
                            switch(option){
                                case 1:
                                    addOrderItemScreen();
                                    break;
                                case 2:
                                    deleteOrderItemScreen();
                                    break;                           
                            }
                    }
                            break;
                    case 9:
                    {
                            option = optionsScreen("User");
                            switch(option){
                                    case 1:
                                            alterUserScreen();
                                            break;
                                    case 2:
                                            addUserScreen();
                                            break;
                                    case 3:
                                            deleteUserScreen();
                                            break;
                            }

                    }
                        break;
                    case 10:
                            option = optionsScreen("User Status");
                            switch(option){
                                case 1:
                                    alterUserStatusScreen();
                                    break;
                                case 2:
                                    addUserStatusScreen();
                                    break;
                                case 3:
                                    deleteUserStatusScreen();
                                    break;
                                            
                            }
                            break;
                    case 11:
                            System.exit(0);
                }

            }
	}
	
	
	public static int optionsScreen(String thing){
		System.out.println("How would you like to alter " + thing);
		ArrayList<String> options = new ArrayList<String>();
		options.add("Alter");
		options.add("Add");
		options.add("Delete");
		ServiceWrapper.printOptions(options);
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
		return input;
	}

	//Doesn't work
	public static void addCardScreen(){
		System.out.println("Add a Credit Card");
		Scanner sc = new Scanner(System.in);
		System.out.println("\nEnter Card id: ");
	    String cardId= sc.next();
	    System.out.println("\nEnter id of user this card belongs to: ");
		String userId= sc.next();
		System.out.println("\nEnter Card number: ");
		String cardNumber= sc.next();
		System.out.println("\nEnter expiration year: ");
		int year = sc.nextInt();
		System.out.println("\nEnter expiration month: ");
		int month = sc.nextInt();
		System.out.println("\nEnter expiration date: ");
		int day = sc.nextInt();
		Date expiryDate= new Date(year, month, day);
		System.out.println("Enter Security code: ");
		String securityCode= sc.next();
		Card c = new Card(cardId, userId, cardNumber, expiryDate, securityCode);
		
		CardService cs = new CardService(con);
		cs.add(c);
		AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
	}

	public static void deleteCardScreen(){
		System.out.println("List of cards");
		CardService cs = new CardService(con);
		ArrayList<Card> cl = cs.getAll();
		int count=1;
		for(Card c:cl){
			System.out.println(count + ": " + c.getCardNumber());
			count++;
		}
		System.out.println("Select card you'd like to delete");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    cs.deleteById(cl.get(input-1).getCardId());
	    System.out.println("Deleted Card");
		
	}
	
	public static void alterCardScreen(){
		System.out.println("List of cards");
		CardService cs = new CardService(con);
		ArrayList<Card> cl = cs.getAll();
		int count=1;
		for(Card c:cl){
			System.out.println(count + ": " + c.getCardNumber());
			count++;
		}
		System.out.println("Enter the number of the card you'd like to alter");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    
	    String cardId= cl.get(input-1).getCardId();
	    System.out.println("Enter id of user this card belongs to: ");
		String userId= sc.next();
		System.out.println("Enter Card number: ");
		String cardNumber= sc.next();
		System.out.println("Enter expiration year: ");
		int year = sc.nextInt();
		System.out.println("Enter expiration month: ");
		int month = sc.nextInt();
		System.out.println("Enter expiration date: ");
		int day = sc.nextInt();
		Date expiryDate= new Date(year, month, day);
		System.out.println("Enter Security code: ");
		String securityCode= sc.next();
	    
		Card c = new Card(cardId, userId, cardNumber, expiryDate, securityCode);
		
		cs.update(c);
		AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
	}
	
	public static void addItemScreen(){
		System.out.println("Add an item");
		Scanner sc = new Scanner(System.in);
	    System.out.println("\nEnter item id: ");
	    String id= sc.next();
	    System.out.println("\nEnter item name: ");
	    sc.nextLine();
		String name= sc.nextLine();
		System.out.println("\nEnter vegeterian (y or n): ");
		String vege = sc.next();
		char vegetarian = vege.charAt(0);
		System.out.println("\nEnter a description: ");
		sc.nextLine();
		String description= sc.nextLine();
		System.out.println("\nEnter type number id: ");
		String type= sc.next();
		System.out.println("\nEnter meal time: ");
		String slot_ID= sc.next();
		System.out.println("\nEnter photo link: ");
		String photo= sc.next();
		System.out.println("\nEnter a price: ");
		float price= sc.nextFloat();
		
		Menu men = new Menu(id, name, vegetarian, type, description, slot_ID, photo, price);
		MenuServices menServ = new MenuServices(con);
		menServ.add(men);
		System.out.println("\n" + name + " added to database\n");
		AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
		
	}
	
	public static void deleteItemScreen(){
		System.out.println("Choose an item to delete");
		MenuServices ms = new MenuServices(con);
		ArrayList<Menu> menus = ms.getAll();
		ServiceWrapper.printMenuItems(menus);
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    if(input == menus.size() + 1)
	    	return;
	    if(input == menus.size()+2)
	    	System.exit(0);
	    MenuServices menServ = new MenuServices(con);
	    
	    menServ.deleteById(menus.get(input-1).getId());
	    System.out.println("Deleted " + menus.get(input-1).getName());
	}
	
	public static void alterItemScreen(){
		System.out.println("Choose an item to alter");
		MenuServices ms = new MenuServices(con);
		ArrayList<Menu> menus = ms.getAll();
		ServiceWrapper.printMenuItems(menus);
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    Menu men = menus.get(input-1);
	    MenuServices menServ = new MenuServices(con);
	    System.out.println("Enter item name: ");
	    sc.nextLine();
		String name= sc.nextLine();
		System.out.println("Enter vegeterian (y or n): ");
		String vege = sc.next();
		char vegetarian = vege.charAt(0);
		System.out.println("Enter a description: ");
		sc.nextLine();
		String description= sc.nextLine();
		System.out.println("Enter type number id: ");
		String type= sc.next();
		System.out.println("Enter meal time: ");
		String slot_ID= sc.next();
		System.out.println("Enter photo link: ");
		String photo= sc.next();
		System.out.println("Enter a price: ");
		float price= sc.nextFloat();
		String id = men.getId();
		Menu menUp = new Menu(id, name, vegetarian, type, description, slot_ID, photo, price);
	    menServ.update(menUp);
	    System.out.println("Updated " + name);
	}

	public static void addUserScreen(){
		System.out.println("Add a User");
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter user id: ");
	    String userId = sc.next();
	    System.out.println("Enter first name: ");
		String firstName = sc.next();
		System.out.println("Enter last name: ");
		String lastName = sc.next();
		System.out.println("Enter email: ");
		String email = sc.next();
		System.out.println("Enter password: ");
		String password = sc.next();
		System.out.println("Enter status id: ");
		String userStatusId = sc.next();
		System.out.println("Enter location id: ");
		String locationId = sc.next();
		User u = new User(userId, firstName, lastName, email, password, userStatusId, locationId);
		UserService us = new UserService(con);
		us.add(u);
		
		AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
	}
	
	public static void deleteUserScreen() {
		System.out.println("List of users");
		UserService us = new UserService(con);
		ArrayList<User> uArr = us.getAll();
		int count=1;
		for(User u:uArr){
			System.out.println(count + " " + u.getFirstName() + " " + u.getLastName());
			count++;
		}
		
		System.out.println("Select user you'd like to delete");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    us.deleteById(uArr.get(input-1).getUserId());
	    System.out.println(uArr.get(input-1).getFirstName() + "has been deleted");
		
	}

        public static void alterDeliveryMethodScreen() {
            System.out.println("List of delivery methods");
            DeliveryMethodService dms = new DeliveryMethodService(con);
            ArrayList<DeliveryMethod> dmArr= dms.getAll();
            int count =1;
            for(DeliveryMethod dm:dmArr){
                System.out.println(count + ". Delivery Method ID:" + dm.getDelivery_method_id() + " Delivery Method:" + dm.getDelivery_method());
                count ++;
            }
            System.out.println();
            System.out.println("Choose a delivery method to alter.");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            if (input - 1 < dmArr.size()){
                DeliveryMethod newDM = dmArr.get(input - 1);
                System.out.println("Delivery Method ID: " + newDM.getDelivery_method_id());
                System.out.println("Current delivery method: " + newDM.getDelivery_method());
                
                System.out.println("Type in the new delivery method for this delivery method ID");
                sc.nextLine();
                String newDeliveryMethod = sc.nextLine();
                newDM.setDelivery_method(newDeliveryMethod);
                
                dms.update(newDM);
                System.out.println("Delivery method updated.");
            }
        }

        public static void addDeliveryMethodScreen() {
            System.out.println("Current Delivery methods and their ids");
            DeliveryMethodService dms = new DeliveryMethodService(con);
            ArrayList<DeliveryMethod> dmArr= dms.getAll();
            int count =1;
            for(DeliveryMethod dm:dmArr){
                System.out.println(count + ". Delivery Method ID:" + dm.getDelivery_method_id() + " Delivery Method:" + dm.getDelivery_method());
                count ++;
            }

            System.out.println("\nInsert an ID that is currently not in use.\n");
            Scanner sc = new Scanner(System.in);
            String dmID = sc.next();
            System.out.println("Insert your deliver method");
            sc.nextLine();
            String dm = sc.nextLine();
            DeliveryMethod dmToAdd = new DeliveryMethod(dmID, dm);
            dms.add(dmToAdd);
            System.out.println("Delivery Method added.");
            
            
        }

        public static void deleteDeliveryMethodScreen() {
            System.out.println("Current Delivery methods and their ids");
            DeliveryMethodService dms = new DeliveryMethodService(con);
            ArrayList<DeliveryMethod> dmArr= dms.getAll();
            int count =1;
            for(DeliveryMethod dm:dmArr){
                System.out.println(count + ". Delivery Method ID:" + dm.getDelivery_method_id() + " Delivery Method:" + dm.getDelivery_method());
                count ++;
            }
            System.out.println("Select the delivery method you wish to delete.");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            if(input - 1 < dmArr.size()){
                dms.deleteById(dmArr.get(input-1).getDelivery_method_id());
                System.out.println("Delivery Method ID:" + dmArr.get(input - 1).getDelivery_method_id() + " has been deleted.");
            }
        }

        public static void alterDeliveryStatusScreen() {
            System.out.println("List of delivery statuses");
            
            DeliveryStatusService dss = new DeliveryStatusService(con);
            
            ArrayList<DeliveryStatus> dsArr = dss.getAll();
            int count =1;
            for(DeliveryStatus ds:dsArr){
                System.out.println(count + ". Delivery Status ID:" + ds.getDelivery_status_id()+ " Delivery Status:" + ds.getDelivery_status());
                count ++;
            }
            System.out.println();
            System.out.println("Choose a delivery status to alter.");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();

            if (input - 1 < dsArr.size()){
                DeliveryStatus newDS = dsArr.get(input - 1);
                System.out.println("Delivery Status ID: " + newDS.getDelivery_status_id());
                System.out.println("Current delivery status: " + newDS.getDelivery_status());
                
                System.out.println("Type in the new delivery status for this delivery status ID");
                sc.nextLine();
                String newDeliveryStatus = sc.nextLine();
                newDS.setDelivery_status(newDeliveryStatus);
                
                dss.update(newDS);
                System.out.println("Delivery status updated.");
            }
        }

        public static void addDeliveryStatusScreen() {
            System.out.println("Current Delivery statuses and their ids");
            System.out.println("List of delivery statuses");
            
            DeliveryStatusService dss = new DeliveryStatusService(con);
            
            ArrayList<DeliveryStatus> dsArr = dss.getAll();
            int count =1;
            for(DeliveryStatus ds:dsArr){
                System.out.println(count + ". Delivery Status ID:" + ds.getDelivery_status_id()+ " Delivery Status:" + ds.getDelivery_status());
                count ++;
            }
            System.out.println();
            System.out.println("\nInsert an ID that is currently not in use.\n");
            Scanner sc = new Scanner(System.in);
            String dsID = sc.next();
            System.out.println("Insert your deliver status");
            sc.nextLine();
            String ds = sc.nextLine();
            DeliveryStatus dsToAdd = new DeliveryStatus(dsID, ds);
            dss.add(dsToAdd);
            System.out.println("Delivery Status added.");          
        }

        public static void deleteDeliveryStatusScreen() {

            System.out.println("Current Delivery Statuses and their ids");
            DeliveryStatusService dss = new DeliveryStatusService(con);
            ArrayList<DeliveryStatus> dsArr= dss.getAll();
            int count =1;
            for(DeliveryStatus ds:dsArr){
                System.out.println(count + ". Delivery Status ID:" + ds.getDelivery_status_id() + " Delivery Status:" + ds.getDelivery_status());
                count ++;
            }
            System.out.println("Select the delivery statys you wish to delete.");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            if(input - 1 < dsArr.size()){
                dss.deleteByID(dsArr.get(input-1).getDelivery_status_id());
                System.out.println("Delivery Status ID:" + dsArr.get(input - 1).getDelivery_status_id() + " has been deleted.");
            }            
        }

        public static void alterItemTypeScreen() {
            
            System.out.println("List of item types");
            
            ItemTypeService its = new ItemTypeService(con);
            
            ArrayList<ItemType> itemTypeArr = its.getAll();
            int count = 1;
            for(ItemType it:itemTypeArr){
                System.out.println(count + ". Item Type ID: " + it.getItemTypeId() + " Item Type: " + it.getItemType());
                count ++;
            }
            
            System.out.println();
            System.out.println("Choose an item type to alter.");
            
            Scanner sc = new Scanner(System.in);
            
            int input = sc.nextInt();
            
            if (input - 1 < itemTypeArr.size()){
                ItemType newItemType = itemTypeArr.get(input - 1);
                System.out.println("Item Type ID: " + newItemType.getItemTypeId());
                System.out.println("Item Type: " + newItemType.getItemType());
                
                System.out.println("Type in the new item type for this item type ID");
                sc.nextLine();
                String newItemTypeString = sc.nextLine();
                newItemType.setItemType(newItemTypeString);
                
                its.update(newItemType);
                System.out.println("Item type added.");
            }
        }

        public static void addItemTypeScreen() {
            System.out.println("List of item types");
            
            ItemTypeService its = new ItemTypeService(con);
            
            ArrayList<ItemType> itemTypeArr = its.getAll();
            int count = 1;
            for(ItemType it:itemTypeArr){
                System.out.println(count + ". Item Type ID: " + it.getItemTypeId() + " Item Type: " + it.getItemType());
                count ++;
            }
            System.out.println();
            System.out.println("Insert an item ID that doesn't already exist in the database");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            String itemID = sc.nextLine();
            System.out.println();
            
            //NEEDS TO BE FINISHED 7/12/2018. 
            
        }

        public static void deleteItemTypeScreen() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    private void alterLocationScreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addLocationScreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void deleteLocationScreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

        public static void alterOrderScreen() {
//            OrderService os = new OrderService(con);
//            ArrayList<Order> orderArr = os.getAll();
//            int count = 1;
//            for(Order o:orderArr){
//                System.out.println(count + ":");
//                System.out.println(count + "Order ID: " + o.getOrder_id());
//                System.out.println("User ID" + o.getUser_id());
//                System.out.println("Delivery Method: " + o.getDelivery_method_id());
//                System.out.println("Delivery Status: " + o.getDelivery_status_id());
//                System.out.println("Instructions: " + o.getInstuctions());
//                System.out.println("Store ID: " + o.getStore_id());
//                System.out.println("Time placed: " + o.getPlaced_timestamp());
//                System.out.println("Tip: " + o.getTip());
//                System.out.println();
//                if (o.getCard_id() != null){
//                    System.out.println("Card: " + o.getCard_id());
//                }
//                count ++;
//            }        
        }

    private void addOrderScreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void deleteOrderScreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void alterUserScreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

        private static void alterUserStatusScreen() {
            System.out.println("List of current user statuses");
            UserStatusService uss = new UserStatusService(con);
            ArrayList<UserStatus> usArr = uss.getAll();
            int count = 1;
            for(UserStatus us:usArr){
                System.out.println(count + ". User Status ID: " + us.getUserStatusId() + " User Status: " + us.getUserStatus());
                count ++;
            }

            System.out.println("\nSelect the user status you wish to alter.");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            if (input - 1< usArr.size()){
                UserStatus newUserStatus = usArr.get(input - 1);
                System.out.println("Delivery Status ID Selected: " + newUserStatus.getUserStatusId());
                System.out.println("Current delivery status: " + newUserStatus.getUserStatus());

                System.out.println("Type in the new user status for this user status ID");
                sc.nextLine();
                String newUserStatusString = sc.nextLine();

                newUserStatus.setUserStatus(newUserStatusString);
                uss.update(newUserStatus);
                System.out.println("User status updated.");

            }else {
                System.out.println("Invalid input.");
            }
        }

        public static void addUserStatusScreen(){
            System.out.println("List of current user statuses");
            UserStatusService uss = new UserStatusService(con);
            ArrayList<UserStatus> usArr = uss.getAll();
            int count = 1;
            for(UserStatus us:usArr){
                System.out.println(count + ". User Status ID: " + us.getUserStatusId() + " User Status: " + us.getUserStatus());
                count ++;
            }
            Scanner sc = new Scanner(System.in);
            System.out.println("Add a new user status ID that doesn't currently exist");
            sc.nextLine();
            String userStatusID = sc.nextLine();
            System.out.println("Add a user status description");
            sc.nextLine();
            String userStatus = sc.nextLine();
            try{
            UserStatus us = new UserStatus(userStatusID, userStatus);
            uss.add(us);
            System.out.println("User added");
            }
            catch(IdException e){
                e.getMessage();
            }
        }

        public static void deleteUserStatusScreen() {
            System.out.println("List of current user statuses");
            UserStatusService uss = new UserStatusService(con);
            ArrayList<UserStatus> usArr = uss.getAll();
            int count = 1;
            for(UserStatus us:usArr){
                System.out.println(count + ". User Status ID: " + us.getUserStatusId() + " User Status: " + us.getUserStatus());
                count ++;
            }
            
            System.out.println("\n Select a userStatus to delete");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            if((input - 1) < usArr.size()){
                uss.deleteById(usArr.get(input - 1).getUserStatusId());
                System.out.println("User Status deleted.");    
            }
            else{
                System.out.println("Invalid input");
            }
        }

    private void deleteOrderItemScreen() {
        System.out.println("List of current orders and their items");
        OrderItemService ois = new OrderItemService(con);
        ArrayList<OrderItem> oiArr = ois.getAll();
        int count = 1;
        for(OrderItem oi:oiArr){
            System.out.println(count + ". Order ID: " + oi.getOrderID() + " Item ID: " + oi.getItemID());
            count ++;
        }
        
        System.out.println("\nSelect 1 if you wish to delete all items for an order ID.");
        System.out.println("Select 2 if you wish to delete all of one type of item for all order IDs");
        System.out.println("Select any other number to exit");
        Scanner sc = new Scanner(System.in);
        int input;
        
        input = sc.nextInt();
        while(input != 1 || input != 2){
            if(input == 1){
                System.out.println("Select the order ID you wish to delete all items for");
                String orderIDToDelete = sc.next();
                ois.deleteById(orderIDToDelete);
                System.out.println("This order ID has been deleted");
            }else if(input == 2){
                System.out.println("Select the item ID you wish to delete from all orders");
                String itemIDToDelete = sc.next();
                ois.deleteItemById(itemIDToDelete);
                System.out.println("This item ID has been deleted");
            }
        }
    }

    private void addOrderItemScreen() {
        System.out.println("List of current order ids and their respective item ids");
        OrderItemService ois = new OrderItemService(con);
        ArrayList<OrderItem> oiArr = ois.getAll();
        int count = 1;
        for(OrderItem oi:oiArr){
            System.out.println(count + ". Order ID: " + oi.getOrderID() + " Item ID: " + oi.getItemID());
            count ++;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Select an order ID(should be existing one) to add to the table");
        String orderID = sc.next();
        System.out.println("Select an item ID to add to the table(must be existing item ID)");
        String itemID = sc.next();
        OrderItem newOrderItem = new OrderItem(orderID, itemID);
        ois.add(newOrderItem);
        System.out.println("Item has been added");
    }


}
