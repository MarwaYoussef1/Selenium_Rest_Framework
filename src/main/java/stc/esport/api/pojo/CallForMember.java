package stc.esport.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CallForMember {

	@JsonProperty
	private String teamId;
	
	@JsonProperty
	private String role;
	
	@JsonProperty
	private String message;
	
	@JsonProperty
	private String vacancies ;
	
	@JsonProperty
	private long endAt ;

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getVacancies() {
		return vacancies;
	}

	public void setVacancies(String vacancies) {
		this.vacancies = vacancies;
	}

	public long getEndAt() {
		return endAt;
	}

	public void setEndAt(long endAt) {
		this.endAt = endAt;
	}

	
	
	
	
}
