package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Card;

public class CardService implements Service<Card>{
	
	Connection connection;
	
	public CardService() {
		super();
	}
        //Takes in existing connection to use for sql operations.
	public CardService(Connection connection) {
		super();
		this.connection = connection;
	}
	//Adds a card to the database by taking information from the card object and passing
        //it into the parameters of a CallableStatement.
        @Override
	public boolean add(Card card){
		try{
			String cardId = card.getCardId();
			String userId = card.getUserId();
			String cardNumber = card.getCardNumber();
			Date expiryDate = card.getExpiryDate();
			String securityCode = card.getSecurityCode();
			
			CallableStatement oCSF = connection.prepareCall("{call sp_insert_card(?,?,?,?,?)}");
			oCSF.setString(1, cardId);
			oCSF.setString(2, userId);
			oCSF.setString(3, cardNumber);
			oCSF.setDate(4, expiryDate);
			oCSF.setString(5, securityCode);
			oCSF.execute();
			oCSF.close();
			return true;
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}	
	}

    /**
     * Deletes cards from the database by using the card's id.
     * This should probably be a prepared statement
     * @param id
     */
    @Override
	public void deleteById(String id){
		try{
			Statement cardsSt = connection.createStatement();
			cardsSt.executeQuery("Delete from cards where card_id = "+id);
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}

    /**
     *
     * @return Returns all cards in the database by storing them into an ArrayList of card objects.
     * Not sure why this method is needed.
     */
    @Override
	public ArrayList<Card> getAll(){

		ArrayList<Card> cards = new ArrayList<Card>();
		
		try{
			Statement cardsSt = connection.createStatement();
			ResultSet cardsRs = cardsSt.executeQuery("Select * from Cards");
			
			while(cardsRs.next()){
				Card card = new Card(
						cardsRs.getString(1),
						cardsRs.getString(2),
						cardsRs.getString(3),
						cardsRs.getDate(4),
						cardsRs.getString(5)
						); 
				cards.add(card);
			}
                        cardsRs.close();
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return cards;
	}

    /**
     * Returns a card using sql statement to select a card matching said parameter.
     * @param id
     * @return Card with id matching the parameter.
     */
    @Override
	public Card getById(String id){
		Card card = null;
		
		try{
			Statement cardsSt = connection.createStatement();
			ResultSet cardsRs = cardsSt.executeQuery("Select * from Cards where card_id = " + id);
			
			cardsRs.next();
			card = new Card(
					cardsRs.getString(1),
					cardsRs.getString(2),
					cardsRs.getString(3),
					cardsRs.getDate(4),
					cardsRs.getString(5)
					); 

                }catch(SQLException e){
			System.out.println(e.getMessage());
		}	
		
		return card;
	}
        
        //Updates existing card's information. Uses existing card id to update the card in the database.
        //Potential problem is if user wished to change the card id entirely.
        //Better method for this is to list the cards that the user currently has in the database and 
        //allow the user to modify one of those cards.
        @Override
	public void update(Card card){
		try{
			String cardId = card.getCardId();
			String userId = card.getUserId();
			String cardNumber = card.getCardNumber();
			Date expiryDate = card.getExpiryDate();
			String securityCode = card.getSecurityCode();
			
                    try (CallableStatement oCSF = connection.prepareCall("{call sp_update_card(?,?,?,?,?)}")) {
                        oCSF.setString(1, cardId);
                        oCSF.setString(2, userId);
                        oCSF.setString(3, cardNumber);
                        oCSF.setDate(4, expiryDate);
                        oCSF.setString(5, securityCode);
                        oCSF.execute();
                    }
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}	
	}
	//Lists all of the users cards using an arraylist to store the cards
        //and an sql query to produce the user cards.
	public ArrayList<Card> getUserCards(String userId){

		ArrayList<Card> cards = new ArrayList<Card>();
		
		try{
			Statement cardsSt = connection.createStatement();
			ResultSet cardsRs = cardsSt.executeQuery("Select * from Cards where user_id = '" + userId + "'");
			
			while(cardsRs.next()){
				Card card = new Card(
						cardsRs.getString(1),
						cardsRs.getString(2),
						cardsRs.getString(3),
						cardsRs.getDate(4),
						cardsRs.getString(5)
						); 
				cards.add(card);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return cards;
	}

        /**
         * @Author: Presley M.
         * @param creditCardNumber
         * @return 
         * This function validates the credit card number, passed as a string parameter
         * and returns true if the card number is valid or false otherwise. 
         */
        public boolean validateCreditCard(String creditCardNumber){
            
            if(creditCardNumber.length() < 15 && creditCardNumber.length() > 16){
                return false; 
            }//if Ends 
            for(char c : creditCardNumber.toCharArray()){
                if(c > '9' || c < '0')return false; 
            }//for Ends 
            return true;
        }//validateCreditCard Ends 
        
        /**
         * @author: Presley M.
         * @param date
         * @return 
         * This function validates the credit card's expiration date. It receives 
         * the date as a String parameter 
         */
        public boolean validateExpDate(String date){
            //make sure date has the right format
             date = date.trim();
             int currentMonth = 7;
             int currentYear = 18; 

             try{
                  //extract month and make sure it is a numeric value 
                  //between 1 and 12
                 int month = Integer.parseInt(date.substring(0, 2));
                 if(month > 12) return false; 
                 
                 //ivalid if year has passed 
                 int year = Integer.parseInt(date.substring(3, 5));
                 if(year < 18) return false; 
                 
                 //invalid if it expires this year and the month has passed 
                 if(month < currentMonth && year==currentYear) return false; 
             }catch(Exception ex){
                 //return false; 
             }//try-catch ends 
             
            return true; 
        }//validateExpDate() Ends 
        
        /***
         * @author: Presley M. 
         * @param securityCodes
         * @return 
         * This function validate the 3-4 digit security code of the card  
         */
        public boolean validateSecurityCode(String securityCodes){
            securityCodes = securityCodes.trim(); // remove white spaces 
            if(securityCodes.length() > 4 || securityCodes.length() < 3){
                return false;
            }//if Ends 
            
            //if security code does not contain digits only 
            if(securityCodes.matches("\\d\\d\\d") == false &&
                    securityCodes.matches("\\d\\d\\d\\d")==false){
                return false; 
            }//if Ends 
            
            /*Another way: try Integer.parsInt(securityCodes) and catch exception*/
            return true; 
        }//validateSecurityCode
}
