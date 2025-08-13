package stc.esport.api.pojo.Teams;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamInvitation {

	
	@JsonProperty
	private String email;
	
	@JsonProperty
	private String role;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
	

}
