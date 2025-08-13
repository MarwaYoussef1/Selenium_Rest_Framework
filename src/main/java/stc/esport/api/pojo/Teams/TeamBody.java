package stc.esport.api.pojo.Teams;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamBody {

	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String nameAr;
	
	@JsonProperty
	private String about;
	
    @JsonProperty
	private String email;
	
	@JsonProperty
	private String logo;
	
	@JsonProperty
	private boolean publicProfile;
	
	@JsonProperty
	private int gameId;
	
	@JsonProperty
	private ArrayList<TeamSocialMedia> socialMedia;
	
	@JsonProperty
	private ArrayList<TeamInvitation> invitations;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public boolean isPublicProfile() {
		return publicProfile;
	}

	public void setPublicProfile(boolean publicProfile) {
		this.publicProfile = publicProfile;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public ArrayList<TeamSocialMedia> getSocialMedia() {
		return socialMedia;
	}

	public void setSocialMedia(ArrayList<TeamSocialMedia> socialMedia) {
		this.socialMedia = socialMedia;
	}

	public ArrayList<TeamInvitation> getInvitations() {
		return invitations;
	}

	public void setInvitations(ArrayList<TeamInvitation> invitations) {
		this.invitations = invitations;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}
	
	
	

}
