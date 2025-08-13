package stc.esport.api.pojo.incident;


import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import stc.esport.api.pojo.invitations.Member;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Incident {

	
	@JsonProperty
	private String haveRepresentative;	
	@JsonProperty
	private boolean haveDefendants;	
	@JsonProperty
	private String type;	
	@JsonProperty
	private String onBehalf;	
	@JsonProperty
	private boolean acceptTerms;
	@JsonProperty
	private Issuer issuer;	
	@JsonProperty
	private ArrayList<String> generalAttachments;	
	@JsonProperty
	private ArrayList<Defendant> defendants;	
	@JsonProperty
	private String summary;	
	@JsonProperty
	private ArrayList<EvidenceAttachment> evidenceAttachments;
	@JsonProperty
	private ArrayList<String> requests;
	
	public String getHaveRepresentative() {
		return haveRepresentative;
	}
	public void setHaveRepresentative(String haveRepresentative) {
		this.haveRepresentative = haveRepresentative;
	}
	public boolean isHaveDefendants() {
		return haveDefendants;
	}
	public void setHaveDefendants(boolean haveDefendants) {
		this.haveDefendants = haveDefendants;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOnBehalf() {
		return onBehalf;
	}
	public void setOnBehalf(String onBehalf) {
		this.onBehalf = onBehalf;
	}
	public boolean isAcceptTerms() {
		return acceptTerms;
	}
	public void setAcceptTerms(boolean acceptTerms) {
		this.acceptTerms = acceptTerms;
	}
	public Issuer getIssuer() {
		return issuer;
	}
	public void setIssuer(Issuer issuer) {
		this.issuer = issuer;
	}
	public ArrayList<String> getGeneralAttachments() {
		return generalAttachments;
	}
	public void setGeneralAttachments(ArrayList<String> generalAttachments) {
		this.generalAttachments = generalAttachments;
	}
	public ArrayList<Defendant> getDefendants() {
		return defendants;
	}
	public void setDefendants(ArrayList<Defendant> defendants) {
		this.defendants = defendants;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public ArrayList<EvidenceAttachment> getEvidenceAttachments() {
		return evidenceAttachments;
	}
	public void setEvidenceAttachments(ArrayList<EvidenceAttachment> evidenceAttachments) {
		this.evidenceAttachments = evidenceAttachments;
	}
	public ArrayList<String> getRequests() {
		return requests;
	}
	public void setRequests(ArrayList<String> requests) {
		this.requests = requests;
	}	
	
	
	
	
}
