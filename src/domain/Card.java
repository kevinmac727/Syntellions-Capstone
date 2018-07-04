package domain;

import java.sql.Date;

public class Card {
	
	String cardId;
	String userId;
	String cardNumber;
	Date expiryDate;
	String securityCode;
	
	public Card() {
		super();
	} 
	
	public Card(String cardId, String userId, String cardNumber, Date expiryDate, String securityCode) {
		super();
		this.cardId = cardId;
		this.userId = userId;
		this.cardNumber = cardNumber;
		this.expiryDate = expiryDate;
		this.securityCode = securityCode;
	}
	
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
	@Override
	public String toString() {
		return "Card [cardId=" + cardId + ", userId=" + userId + ", cardNumber=" + cardNumber + ", expiryDate="
				+ expiryDate + ", securityCode=" + securityCode + "]";
	}
	
	
	

}
