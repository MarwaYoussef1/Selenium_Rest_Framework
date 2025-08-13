package stc.esport.api.pojo.userprofile;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserProfileResponseBody {

	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String nameAr;
	
	@JsonProperty
	private String id;
	
    @JsonProperty
	private String levelId;
	
	@JsonProperty
	private String createdBy;
	
	@JsonProperty
	private String updatedBy;
	
	@JsonProperty
	private double updatedDate;
	
	@JsonProperty
	private double createdDate;
	
	@JsonProperty
	private int usersCount;
	
	@JsonProperty
	private int status;
	
	@JsonProperty
	private String levelName;
	
	@JsonProperty
	private boolean signUp;
	
	@JsonProperty
	private boolean isInvitationsEnabled;
	
	@JsonProperty
	private ArrayList<String> authorities;
	
	@JsonProperty
	private UserProfileType type;

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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public double getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(double updatedDate) {
		this.updatedDate = updatedDate;
	}

	public double getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(double createdDate) {
		this.createdDate = createdDate;
	}

	public int getUsersCount() {
		return usersCount;
	}

	public void setUsersCount(int usersCount) {
		this.usersCount = usersCount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
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

	public UserProfileType getType() {
		return type;
	}

	public void setType(UserProfileType type) {
		this.type = type;
	}

	
	

}
