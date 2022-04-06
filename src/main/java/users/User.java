package users;

public class User {
	private String username;
	private String password;
	private String sessionID;
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setUsername(String newUsername) {
		this.username = newUsername;
	}
	
	public void setPassword(String newPassword) {
		this.password = newPassword;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	
	public User() {
	}
	
	public User(String newUsername, String newPassword) {
		this.setUsername(newUsername);
		this.setPassword(newPassword);
	}
	
	public User(String newUsername, String newPassword, String newSessionID) {
		this.setUsername(newUsername);
		this.setPassword(newPassword);
		this.setSessionID(newSessionID);
	}
}
