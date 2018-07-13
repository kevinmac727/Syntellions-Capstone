package cli;

import domain.Card;
import domain.Location;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import domain.Menu;
import domain.Order;
import domain.Store;
import domain.User;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import java.util.logging.Level;
import java.util.logging.Logger;
import services.CardService;
import services.DeliveryMethod;
import services.DeliveryMethodService;

import services.EmailService;

import services.MenuServices;
import services.OrderService;
import services.StoreService;
import services.UserService;
import services.CardService;
import services.DeliveryStatusService;
import services.LocationService;
import services.Receipt;

public class Tiger{

	private static ServiceWrapper sw;
	private static Connection con;
	private static User currentUser;
	private static Order currentOrder;
	private static Store currentStore;
        private static HashMap<Menu,Integer> orderSummary; 
	
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
                           "___________\n"                                        
                         + "___  /___(_)____________\n"                          
                         + "__  / __  /_  __ \\_  __ \\\n"                          
                         + "_  /___  / / /_/ /  / / /\n"                          
                         + "/_____/_/  \\____//_/ /_/\n"                           
                         + "__________\n"                                         
                         + "___  ____/___  ____________________________________\n"
                         + "__  __/  __  |/_/__  __ \\_  ___/  _ \\_  ___/_  ___/\n"
                         + "_  /___  __>  < __  /_/ /  /   /  __/(__  )_(__  )\n"
                         + "/_____/  /_/|_| _  .___//_/    \\___//____/ /____/\n" 
                         + "                /_/");                                
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
            
                //continue asking for the input until the user enters a valid one
                while(input  > 3){
                    System.out.println("Invalid Menu Select. try again ");
                    input = sc.nextInt();
                }//while Ends 
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
                        if("1".equals(currentUser.getUserStatusId())){
                            currentOrder = new Order();
                            OrderService service = new OrderService(con);

                            currentOrder.setOrder_id("" + (service.getMaxOrderID() + 1));
                            currentOrder.setUser_id(currentUser.getUserId());
                            currentOrder.setDelivery_status_id("0");
                            //currentOrder.setCard_id();
                            StoreService ss = new StoreService(con);
                            currentStore = ss.getById("0");
                            return homeScreen();
                        }else if("3".equals(currentUser.getUserStatusId()) || "5".equals(currentUser.getUserStatusId())){
                            AdminAndManager aam = new AdminAndManager(con);
                            aam.adminScreen();
                        }
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
                        OrderService service = new OrderService(con);

                        currentOrder.setOrder_id("" + (service.getMaxOrderID() + 1));
			currentOrder.setUser_id(currentUser.getUserId());
			currentOrder.setDelivery_status_id("0");
                        System.out.println(currentOrder.getUser_id());
                        StoreService ss = new StoreService(con);
                        currentStore = ss.getById("0");
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
                EmailService.sendEmail(currentUser.getEmail(), verifyCode, 1);
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
            while(input != 6)
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
                //continue asking for the input until the user enters a valid one
                while(input  > 6){
                    System.out.println("Invalid Menu Select. try again ");
                    input = sc.nextInt();
                }//while Ends 
                //Switch on user input
                switch(input)
                {
                    case 1:
                        menuScreen();
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

            }
            //System.out.println("Shouldn't be here");
            return -1;
	}
	
	public static void menuScreen(){
            MenuServices ms = new MenuServices(con);
            ArrayList<Menu> menus = ms.getAll();
            int input = 0; 
            
            while(input != -1){
                System.out.println("\n*Menu*");
                ServiceWrapper.printMenuItems(menus);
                input = sc.nextInt();
	    
                 if(input==menus.size()+1){
                    if(currentOrder.getOrderSize()>0) {
                        collectOrderInfoScreen();
                    }else{
                        System.out.println("You have not selected any items yet [Press Enter to Continue..]");
                        new Scanner(System.in).nextLine();
                    }//if Ends 
                 }//if Ends 
                 
                 else if(input==menus.size()+2) return;
                 else if(input <= menus.size()) menuItemScreen(menus.get(input-1));
                //Test, unsure if this is proper
            }//while Ends 
            
            //collect additional information about the data
            
            
	}//menuScreen() Ends 
        
	public static void menuItemScreen(Menu menu){
            System.out.println("\n*" + menu.getName() + "*");
            System.out.println(menu.getDescription());
	    System.out.println("$" + menu.getPrice());
	    System.out.println("1. Enter Quantity");
	    System.out.println("2. Go back");
	    int input = sc.nextInt();
	    if(input==1) itemQuantityScreen(menu);
            //Test, unsure if this is proper
	}
        
	//TODO finish this
	public static void itemQuantityScreen(Menu menu){
		System.out.println("Enter Quantity: ");
	    int input = sc.nextInt();
	    for(int i=0;i<input;i++) currentOrder.addItem_id(menu.getId());
		System.out.println("Item(s) added");
	
	}
        
        
        /***********************************************************************
         * @author: Presley M. 
         * This function is used to collect information needed for successfully 
         * process the order. This information includes:
         * -Delivery info
         * -Location 
         * -Payment info 
         */
        public static void collectOrderInfoScreen(){
            
            Scanner scanInput = new Scanner(System.in);
            String menuSelection = "";
            
            /**************************************************************************************/
            /**************************DISPLAY SUMMARY OF SELECTED ITEMS***************************/
            System.out.println("|--------Items Selected--------|"); 
            viewSummaryOfCurrentOrder();
            
            
            /**************************************************************************************/
            /**************************GET INFORMATION ABOUT THE ORDER************************/
            System.out.println("|------Order Information-------|");
            
            //get the date and time of delivery from the user
            DeliveryStatusService dstatus = new DeliveryStatusService();
            SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY HH:MM");
            
            System.out.println("\nEnter Delivery date [MM-DD-YYYY]: ");
            String deliveryDate = scanInput.nextLine();
            
            System.out.println("\nEnter Delivery time [HH:MM]: "); 
            String deliveryTime = scanInput.nextLine();
            
            String deliveryDateTime = deliveryDate+ " "+deliveryTime;
            while(dstatus.validateDeliveryDateTime(deliveryDateTime) == false){
                //add date to receipt summary
                 System.out.println("\nEnter Delivery date [MM-DD-YYYY]: ");
                deliveryDate = scanInput.nextLine();
                
                System.out.println("\nEnter Delivery time [HH:MM]: "); 
                deliveryTime = scanInput.nextLine();
                
                deliveryDateTime = deliveryDate+ " "+deliveryTime;
            }//if Ends 

            
            
            /**************************************************************************************/
            /**************************SELECT DELIVERY METHOD******************************************/
            /**Display methods of delivery  for the user to choose from****/
            System.out.println("\nSelect Delivery method"); 
            showDeliverMethods();
            menuSelection =scanInput.next();
            menuSelection = menuSelection.trim();
            
            //ensure that user enters the correct input
            while(menuSelectionIsValid(menuSelection,'3') == false ){
                System.out.println("**INVALID MENU SELECTION**\nSelect Delivery method: ");
                 menuSelection = scanInput.next();
                 menuSelection = menuSelection.trim();
            }//while Ends 
             
            /**Enter delivery instruction***/ 
            System.out.println("\nEnter Delivery Instruction: "); 
            currentOrder.setInstuctions(scanInput.nextLine());
            
            /**************************************************************************************/
            /**************************GET USER'S Delivery location LOCATION******************************************/
            Location location  = new Location();
            
            System.out.println("Please enter your address information\n");
            String city, street, zipCode; 
            
            System.out.print("Street: ");
            location.setStreet(scanInput.nextLine());
            
            System.out.print("City: ");
            location.setCity(scanInput.nextLine());
            
            System.out.print("Zip: ");
            location.setZip(scanInput.nextLine());
            location.setState("AZ \n");
            
            
            /**************************************************************************************/
            /**************************GET USER'S PAYMENT******************************************/
            /**Process user's payment**/
            System.out.println("Select Method of Payment");
            System.out.println("1. Cash");
            System.out.println("2. Credit/Debit");
            menuSelection = scanInput.next();
            
            //ensure that user makes a valid menu selection
            while(menuSelectionIsValid(menuSelection, '2') == false){
               System.out.println("**INVALID SELECTION**\n"
                       + "Please enter [1] for Cash or [2] for Credit/Debit:");
               menuSelection = scanInput.next();
            }//while Ends 
            
            //ask for credit card information
            if(menuSelection.equals("2")){
                
                //Ask user if he/she wants to use existing cards on file 
                System.out.println("Do you want to use an existing card ?\n Enter [y] for YES or [N] for NO: ");
                menuSelection = scanInput.next(); 
                
                boolean usingExistingCard = false; 
                if(menuSelection.equalsIgnoreCase("y")){
                    //Check if the user has a card in the data base: 
                    //Print out a list of all the user's card and ask them to select one 
                    CardService cardSvc = new CardService(con); 
                    ArrayList<Card> cardList = cardSvc.getUserCards(currentUser.getUserId());
                    
                    if(cardList.size()==0){
                        System.out.println("INFO: You have no card on file");
                    }//if Ends 
                    else{
                        
                        int index = 1;
                        for(Card card : cardList){
                            System.out.println(index+". "+card.getCardNumber());
                            index++;
                        }//for Ends  
                        System.out.println("Select a Card: " );
                        menuSelection = scanInput.next();
                        
                        //give the user a change to exit (to change his/her mind from using an existing card.
                       /* while(menuSelectionIsValid(menuSelection, ('a'-cardList.size()) ) ){
                            System.out.println("INVALID CARD SELECTION-Try again! " );
                            menuSelection = scanInput.next();
                        }//while Ends */
                        
                        //This boolean is set if the user selects an existing card
                        usingExistingCard = true; 
                        
                    }//else-if Ends 
                    
                }//if(menuSelection.equalsIgnoreCase("y")) Ends 
                
                /**if user is not using existing card, ask for its input**/
                if(usingExistingCard == false){
                    //if user does not have a card request for a new card info
                    System.out.println("\n|-----Enter Payment Information-----|"); 
                    scanInput.nextLine();
                    
                    //create a new card objet
                    Card newCard = new Card(); 
                    CardService cardSv = new CardService(con); 
                    
                    System.out.println("Enter Card Type: \n\n");
                    
                    System.out.println("Enter Card Number: ");
                    //TODO: Validate Card Number (write a function for this) 
                    String cardNumber = scanInput.nextLine();
                   
                    while(cardSv.validateCreditCard(cardNumber) == false ){
                        System.out.println("INVALID CREDIT/DEBIT CARD NUMBER-TRY AGAIN: ");
                        cardNumber = scanInput.nextLine();
                    }//while Ends 
                     newCard.setCardNumber(cardNumber);
                     
                    //TODO: if name of card is different from username; it needs to be stored
                    System.out.println("Enter Name on the card: ");
                    String cardName = scanInput.nextLine();
                    newCard.setNameOnCard(cardName); 
                    
                    System.out.println("Enter card's Expiration date[MM/YY]: ");
                    String cardExpDate = scanInput.nextLine();
                    while(cardSv.validateExpDate(cardExpDate) == false ){
                        System.out.println("INVALID DATE -TRY AGAIN! ");
                        cardExpDate = scanInput.nextLine();
                    }//while Ends 
                    //newCard.setExpiryDate(cardExpDate);  
                    
                    System.out.println("Enter three/four digit security code: ");
                    String securityCode = scanInput.next();//three digit
                    while(cardSv.validateSecurityCode(securityCode) == false ){
                        System.out.println("INVALID SECURITY CODE -TRY AGAIN! ");
                        securityCode = scanInput.nextLine();
                    }//while Ends
                     newCard.setSecurityCode(securityCode);
                     
                    //ask user if he/she would like to save this card 
                    System.out.println("Do you want to save this card?");
                    
                }//if (usingExistingCard == false)
                

                System.out.println("Enter [Y] to confirm payment or [C] to Cancel"); 
                String confirmCode = scanInput.next(); 

                System.out.println("Processing payment information...."); 
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Tiger.class.getName()).log(Level.SEVERE, null, ex);
                }//try-catch
                
            }//if(payOption.equals("2")) Ends 
            
            ServiceWrapper sw = new ServiceWrapper(con);
            sw.submitOrder(currentOrder);
            System.out.println("Order Transaction Completed. Thank you");
            
            System.out.println("|------Summary of you Receipt-----|");
            viewSummaryOfCurrentOrder();
            
            System.out.println("Do you want an electronic copy of your receipt? \n"
                    + "Enter [Y] for Yes or  No for [N]: ");
            String receiptPick = scanInput.next(); // receiptPick used to determine 
                                                   // whether user wants receipt (Y) or not (N)
            if(receiptPick.equals("Y") || receiptPick.equals("y") || receiptPick.equals("yes") 
                                || receiptPick.equals("Yes"))
                             {           
                
                System.out.println("Thank you for submitting your order!");
                System.out.println("We will now send you an email containing your receipt...");

                // String that is sent as Email message
                String receipt = "\nOrder ID: " + currentOrder.getOrder_id() + 
                        "\nUser ID: " + currentUser.getUserId() +
                        "\nName: " + currentUser.getFirstName() + " " + currentUser.getLastName() +
                        "\nPhone Number: " + currentUser.getPhone() +
                        "\nEmail Address: " + currentUser.getEmail() +
                        "\nStore ID: " + currentOrder.getStore_id() + 
                        "\nStore Name: " + currentStore.getStoreName() +
                        "\nOrder Method: " + currentOrder.getDelivery_method_id() + 
                        "\nInstructions: " + currentOrder.getInstuctions() + 
                        "\n" + ServiceWrapper.makeOrderItemsString(orderSummary);
                EmailService.sendEmail(currentUser.getEmail(), receipt, 2);
                
                
            }
            else if (receiptPick.equals("N") || receiptPick.equals("n") || receiptPick.equals
                ("no") || receiptPick.equals("No"))
            {
                System.out.println("Thank you for choosing Lion Express!");
            }
            
            
        }//collectOrderInfoScreen() Ends 
        
        
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
                //continue asking for the input until the user enters a valid one
                while(input  > 5){
                    System.out.println("Invalid Menu Select. try again ");
                    input = sc.nextInt();
                }//while Ends 
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
    
            //System.out.println("Shouldn't be here");
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
                //continue asking for the input until the user enters a valid one
                while(input  > 6){
                    System.out.println("Invalid Menu Select. try again ");
                    input = sc.nextInt();
                }//while Ends 
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
                        String newInstructions = new Scanner( System.in).nextLine();
    			currentOrder.setInstuctions(newInstructions);
    			System.out.println("Instructions Changed to: " + newInstructions);
                        break;
                    case 4:
                                                /**
                         * TODO: print the delivery method 
                         */
                         System.out.println("\n Choose Delivery Method"); 
                         showDeliverMethods();
                         
                         String newDelivery_method = new Scanner(System.in).next();
                         newDelivery_method = newDelivery_method.trim();
                         
                         while(newDelivery_method.length()>1 ||
                                 (newDelivery_method.charAt(0) <'0' || newDelivery_method.charAt(0) > '2') ){
                             newDelivery_method = new Scanner(System.in).next();
                             newDelivery_method = newDelivery_method.trim();
                         }//if Ends 
                         
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
                
                System.out.println("|----------Summary Order-----|");
		ArrayList<Menu> items = viewSummaryOfCurrentOrder();
                   
                int input = sc.nextInt();
                if(input==items.size()) return 0;//homeScreen();
                else if(input==items.size()+1) return -1;//currentOrderScreen();
                else orderItemScreen(items.get(input));
            
            //Test, unsure if proper
            return -1;
	}
        
        /***************************************************************
         * This function displays summary view of current order
         * @param menu
         * @return 
         */
        public static ArrayList<Menu> viewSummaryOfCurrentOrder(){                
		ArrayList<String> itemIds = currentOrder.getItem_ids();
                
		ArrayList<Menu> items = sw.getMenuItems(itemIds);
                
	       if(items.isEmpty()) System.out.println("No items");
		//ServiceWrapper.printMenuItems(items);
                orderSummary = ServiceWrapper.printOrderItems(items);
                 
               return items;
        }//viewCurrentOrderSummary() Ends 
        
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
                try{
                    input = sc.nextInt();
                    //Continue asking for the input until the user enters a valid one
                    while(!((input <= count) && (input >= 1))){
                        //While not [a valid input]...
                        System.out.println("Invalid Menu Input. Try again.");
                        input = sc.nextInt();
                    }//while Ends
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
                            //Return to home screen.
                            return 0;
                        default:
                            input = -1;
                            continue;
                    }
                }catch(Exception e){
                    System.out.println("An error has occured. Reason: " + e.getMessage());
                    System.out.println("An invalid input was likely entered. Returning to Accounts menu.");
                    System.out.println("(If program does not continue, enter any input to continue.)");
                    sc.nextLine();
                    input = -1;
                    continue;
                }
                UserService us = new UserService(con);
                us.update(currentUser);
                input = -1;
            }
            System.out.println("Error encountered. Returning to Menu.");
            return 0;
	}

	private static int editCards() 
        {
            //Fetch cards that the user has
            CardService cs = new CardService(con);
            ArrayList<Card> cards = cs.getUserCards(currentUser.getUserId());
            
            Integer input = -1;
            while(input < 1 || input > cards.size() + 1)
            {
            System.out.println("Please select the card to edit");
            
            for (int i = 0; i < cards.size(); i++) {
                System.out.println( (i + 1) + " " + cards.get(i).getCardNumber());
            }
            System.out.println((cards.size() + 1) + " Go Back");
            
            input = sc.nextInt();
            sc.nextLine();//flush input buffer
            }
            if (input == cards.size() + 1) {
                //"Go back" selected, return out
                return -1;
            }
            Card chosen = cards.get(input - 1);
            String cardID;
            String userID = currentUser.getUserId();
            String cardNumber;
            Integer expMonth;
            Integer expYear;
            String ccv;
            
            while(true)
            {
                
                
                
                System.out.println("Please enter new card number");
                String creditInput = sc.next();
                sc.nextLine(); //Just ensuring input buffer gets flushed
                if (creditInput.length() != 16) {
                    System.out.println("Invalid card input, please try again");
                }
                else
                {
                    cardNumber = creditInput;
                    break;
                }
                
            }

            System.out.println("Please enter expiry date (MM/YYYY)");
            String mmyyyy = sc.next();
            String words[] = mmyyyy.split("/");
            System.out.println(words[0] + words[1]);
            expMonth = Integer.parseInt(words[0]) - 1;

            expYear = Integer.parseInt(words[1]);
            sc.nextLine();
            System.out.println("Please enter security code");
            ccv = sc.next();
            
            
            //Set card values
            chosen.setCardNumber(cardNumber);
            chosen.setSecurityCode(ccv);
            
            Date date = new Date(expYear, expMonth, 3); //Date doesn't represent proper date in database.
            chosen.setExpiryDate(date);
            //Call SQL statement to update card?
            cs.update(chosen);
            System.out.println("Card info updated.");
            return 0;//Don't know what is actually supposed to be returned.
	}
        
        private static int editLocations() 
        {
            //Fetch locations that the user has
            LocationService ls = new LocationService(con);
            ArrayList<Location> userLocations = ls.getUserLocations(currentUser.getUserId());
            
            Integer input = -1;
            while(input < 1 || input > userLocations.size() + 1)
            {
                System.out.println("Please select the location to edit");
                for (int i = 0; i < userLocations.size(); i++) {
                    System.out.println( (i + 1) + " " + userLocations.get(i).getStreet());
                }
                System.out.println((userLocations.size() + 1) + " Go Back");
                input = sc.nextInt();
                sc.nextLine();//flush input buffer
            }
            if (input == userLocations.size() + 1) {
                //"Go back" selected, return out
                return -1;
            }
            Location chosen = userLocations.get(input - 1);
            String locationID;
            String userID = currentUser.getUserId();
            Float taxRate;
            String street;
            String city;
            String state;
            String country;
            String zipCode;
            
            //get values
            System.out.println("Please enter tax rate (0.00 to 100.00)");
            taxRate = sc.nextFloat();
            sc.nextLine(); //Flushing buffer
            System.out.println("Please enter new street address");
            street = sc.nextLine();
            System.out.println("Please enter new city");
            city = sc.nextLine();
            System.out.println("Please enter new state");
            state = sc.nextLine();
            System.out.println("Please enter new country");
            country = sc.nextLine();
            while(true)
            {
                System.out.println("Please enter new zip code");
                String zipInput = sc.nextLine();
                if (zipInput.length() != 5) {
                    System.out.println("Invalid zip code input, please try again");
                }
                else
                {
                    zipCode = zipInput;
                    break;
                }
                
            }
            //Set location values
            chosen.setTaxrate(taxRate);
            chosen.setStreet(street);
            chosen.setCity(city);
            chosen.setState(state);
            chosen.setCountry(country);
            chosen.setZip(zipCode);
            ls.update(chosen);
            System.out.println("Location info updated.");
            return 0;
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
        
        /******************************************************************
         * @author: Presley M.
         * This function retrieves the delivery methods from the database 
         * and display them in a menu 
         */
        public static void showDeliverMethods(){
            DeliveryMethodService deliverySvc = new DeliveryMethodService(con);
            int index = 1;
            for(DeliveryMethod m : deliverySvc.getAll()){
                System.out.println(index + " "+ m.getDelivery_method());
                index++;
            }//for Ends 
               
        }//showDeliverMethods() Ends 
        
	public static boolean confirm(){
		System.out.println("\n1*Confirm*");
		System.out.println("1. Yes");
		System.out.println("2. No");
	    int input = sc.nextInt();
	    if(input==1) return true;
	    return false;
	}
        
        //returns false if the index entered for menu selection is out of range
        //or not a valid input
        public static boolean menuSelectionIsValid(String selection, char maxIndex){
            
            return !(selection.length()>1 ||
                 (selection.charAt(0) < '1' || selection.charAt(0) > maxIndex)); 
        }//menuSelectionIsValid() Ends 
}
