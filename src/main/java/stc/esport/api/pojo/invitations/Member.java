package stc.esport.api.pojo.invitations;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Member {

	
	@JsonProperty
	private String email;
	
	@JsonProperty
	private String role;
	
	@JsonProperty
	private String memberId;

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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	

	
	

}
