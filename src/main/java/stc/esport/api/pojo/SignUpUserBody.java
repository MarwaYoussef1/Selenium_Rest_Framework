package stc.esport.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignUpUserBody {

	
	@JsonProperty
	private String otpCode;
	
	@JsonProperty
	private SignUpUserData userSignUpDto;

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

	public SignUpUserData getUserSignUpDto() {
		return userSignUpDto;
	}

	public void setUserSignUpDto(SignUpUserData userSignUpDto) {
		this.userSignUpDto = userSignUpDto;
	}

	
	

}
