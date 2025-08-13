package stc.esport.api.pojo.Teams;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamSocialMedia {

	
	@JsonProperty
	private String socialMediaId;
	
	@JsonProperty
	private String accountLink;

	public String getSocialMediaId() {
		return socialMediaId;
	}

	public void setSocialMediaId(String socialMediaId) {
		this.socialMediaId = socialMediaId;
	}

	public String getAccountLink() {
		return accountLink;
	}

	public void setAccountLink(String accountLink) {
		this.accountLink = accountLink;
	}

	
	

}
