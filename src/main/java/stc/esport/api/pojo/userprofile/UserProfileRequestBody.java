package stc.esport.api.pojo.userprofile;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserProfileRequestBody {

	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String nameAr;
	
	@JsonProperty
	private String id;
	
    @JsonProperty
	private String levelId;
	
	@JsonProperty
	private boolean signUp;
	
	@JsonProperty
	private boolean isInvitationsEnabled;
	
	@JsonProperty
	private ArrayList<String> authorities;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameAr() {
		return nameAr;
	}

	public void setNameAr(String nameAr) {
		this.nameAr = nameAr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public boolean isSignUp() {
		return signUp;
	}

	public void setSignUp(boolean signUp) {
		this.signUp = signUp;
	}

	public boolean isInvitationsEnabled() {
		return isInvitationsEnabled;
	}

	public void setInvitationsEnabled(boolean isInvitationsEnabled) {
		this.isInvitationsEnabled = isInvitationsEnabled;
	}

	public ArrayList<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(ArrayList<String> authorities) {
		this.authorities = authorities;
	}
	

	
	

}
