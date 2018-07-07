package cli;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import domain.Card;
import domain.Menu;
import domain.Order;
import domain.Store;
import domain.User;
import services.CardService;
import services.DeliveryMethod;
import services.DeliveryMethodService;
import services.DeliveryStatus;
import services.DeliveryStatusService;
import services.MenuServices;
import services.OrderService;
import services.StoreService;
import services.UserService;

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
		options.add("Alter Combos");
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
                if(input == 13){
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
                    case 3:
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
                    case 4:
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
                    case 5:
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
                                    case 4:
                                            adminScreen();
                                            break;
                                    case 5:
                                            System.exit(0);
                            }
                            break;
                    }
                    case 6:
                            optionsScreen("Item Type");
                            break;
                    case 7:
                            optionsScreen("Location");
                            break;
                    case 8:
                            optionsScreen("Order");
                            break;
                    case 9:
                            optionsScreen("Order Item");
                            break;
                    case 10:
                    {
                            option = optionsScreen("User");
                            switch(option){
                                    case 1:
                                            System.out.println("not yet supported");
                                    case 2:
                                            addUserScreen();
                                    case 3:
                                            deleteUserScreen();
                            }

                    }
                        break;
                    case 11:
                            optionsScreen("User Status");
                            break;
                    case 12:
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
}
