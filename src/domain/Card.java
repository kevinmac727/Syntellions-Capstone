package domain;

import java.sql.Date;

public class Card {
	
	String cardId;
	String userId;
	String cardNumber;
	Date expiryDate;
	String securityCode;
	
        //Calls standard 'Object' class constructor via 'super'
	public Card() {
		super();
	} 
	
        //Parameterized constructor, also uses Object constructor
	public Card(String cardId, String userId, String cardNumber, Date expiryDate, String securityCode) {
		super();
		this.cardId = cardId;
		this.userId = userId;
		this.cardNumber = cardNumber;
		this.expiryDate = expiryDate;
		this.securityCode = securityCode;
	}
	
        //Standard getters and setters
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
        
        //Overriden toString method
	@Override
	public String toString() {
		return "Card [cardId=" + cardId + ", userId=" + userId + ", cardNumber=" + cardNumber + ", expiryDate="
				+ expiryDate + ", securityCode=" + securityCode + "]";
	}

    public void setNameOnCard(String cardName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	
	
	

}
