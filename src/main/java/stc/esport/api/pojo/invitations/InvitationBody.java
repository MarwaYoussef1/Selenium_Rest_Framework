package stc.esport.api.pojo.invitations;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InvitationBody {

	
	@JsonProperty
	private String invType;
	
	@JsonProperty
	private String invitationFrom;
	
	@JsonProperty
	private String fromType;
	
  	@JsonProperty
	private ArrayList<Member> members;

	public String getInvType() {
		return invType;
	}

	public void setInvType(String invType) {
		this.invType = invType;
	}

	public String getInvitationFrom() {
		return invitationFrom;
	}

	public void setInvitationFrom(String invitationFrom) {
		this.invitationFrom = invitationFrom;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public ArrayList<Member> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<Member> members) {
		this.members = members;
	}
	
	

	
	
	

}
