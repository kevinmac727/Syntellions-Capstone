package domain;

public class UserStatus {
	String userStatusId;
	String userStatus;
	
	public UserStatus() {
		super();
	}
	
	public UserStatus(String userStatusId, String userStatus) {
		super();
		
        
	         
        
		this.userStatusId = userStatusId;
		this.userStatus = userStatus;
		
        }
	
	public String getUserStatusId() {
		return userStatusId;
	}
	public void setUserStatusId(String userStatusId){
        
		this.userStatusId = userStatusId;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	@Override
	public String toString() {
		return "UserStatus [userStatusId=" + userStatusId + ", userStatus=" + userStatus + "]";
	}
}
