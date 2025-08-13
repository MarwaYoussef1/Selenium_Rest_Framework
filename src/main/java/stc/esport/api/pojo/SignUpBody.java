package stc.esport.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignUpBody {

	
	@JsonProperty
	private String otpCode;
	
	@JsonProperty
	private SignUpData signupData;

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

	public SignUpData getSignupData() {
		return signupData;
	}

	public void setSignupData(SignUpData signupData) {
		this.signupData = signupData;
	}
	
	

}
