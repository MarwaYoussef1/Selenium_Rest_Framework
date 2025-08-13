package stc.esport.api.pojo.userprofile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserProfileType {

	
	@JsonProperty
	private int id;
	
	@JsonProperty
	private String code;
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String nameAr;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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

	

	
	

}
