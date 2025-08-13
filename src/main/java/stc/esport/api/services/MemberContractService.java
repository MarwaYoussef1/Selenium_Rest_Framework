package stc.esport.api.services;

import java.util.ArrayList;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import stc.esport.api.pojo.contract.Contract;

public class MemberContractService {

	private static String filterContractsUrl="team-club/v1/member-contract/contracts/";
	private static String uploadContractUrl="team-club/v1/member-contract";
	private static String removeContractUrl="team-club/v1/member-contract/teams";
	private static String filterContractRequestsUrl="team-club/v1/member-contract/team-requests/";
	private static String approveContractRequest="team-club/v1/member-contract/approve/";
    private RestActions apiObj;
	private String tokenHeader;

	public MemberContractService(RestActions apiObj,String token) {
		this.apiObj = apiObj;
		this.tokenHeader="Bearer "+token;

	}

	
	public ArrayList<String> filterMemberContract(String memberSystemId,String teamId) {
		
		ArrayList<String> contractData= new ArrayList<String>();
		String invitationId = null;
		String parentRequestId=null;
		String contractId=null;
		String filterUrl=filterContractsUrl +teamId;
	
		Response response = apiObj.buildNewRequest(filterUrl, RequestType.GET).addHeader("Authorization", tokenHeader)
								.setContentType(ContentType.JSON).useRelaxedHTTPSValidation().perform();
		System.out.println("Response is "+response.asPrettyString());
		if(response.getStatusCode()==200)
		{
		
			invitationId=response.jsonPath().getString("findAll { it.memberSystemId=='"+memberSystemId+"' }.invitationSystemId").replaceAll("[\\[\\]\\(\\)]", "");		
			parentRequestId=response.jsonPath().getString("findAll { it.memberSystemId=='"+memberSystemId+"' }.requestId").replaceAll("[\\[\\]\\(\\)]", "");
			contractId=response.jsonPath().getString("findAll { it.memberSystemId=='"+memberSystemId+"' }.contractId").replaceAll("[\\[\\]\\(\\)]", "");
			contractData.add(invitationId);
			contractData.add(parentRequestId);
			contractData.add(contractId);
		}
		return contractData;
	}
	
	public boolean uploadContract(Contract contractBody) {
		Response response = apiObj.buildNewRequest(uploadContractUrl, RequestType.POST).addHeader("Authorization", tokenHeader).setContentType(ContentType.JSON).useRelaxedHTTPSValidation().setRequestBody(contractBody).perform();
		System.out.println("Response is "+response.asPrettyString());
		if(response.getStatusCode()==200)
		{
			
			return true;
		}
		
		
		return false;
	}
	
	public boolean updateContract(Contract contractBody,String contractId) {
		Response response = apiObj.buildNewRequest(uploadContractUrl+"/"+contractId, RequestType.PUT).addHeader("Authorization", tokenHeader).setContentType(ContentType.JSON).useRelaxedHTTPSValidation().setRequestBody(contractBody).perform();
		System.out.println("Response is "+response.asPrettyString());
		if(response.getStatusCode()==200)
		{
			
			return true;
		}
		
		
		return false;
	}
	
    public String searchOnSpecificContractRequest(String requestSystemId,String contractRequestId) {
		
		String systemContractId=null;
		String filterUrl=filterContractRequestsUrl +requestSystemId;
	
		Response response = apiObj.buildNewRequest(filterUrl, RequestType.GET).addHeader("Authorization", tokenHeader)
								.setContentType(ContentType.JSON).useRelaxedHTTPSValidation().perform();
		System.out.println("Response is "+response.asPrettyString());
		if(response.getStatusCode()==200)
		{
		
			systemContractId=response.jsonPath().getString("memberContracts.find{it.requestId=='"+contractRequestId+"' }.contractId").replaceAll("[\\[\\]\\(\\)]", "");		
			
		}
		return systemContractId;
	}
	
    
 public boolean approveContractRequest(String contractSystemId) {
		
		
		String approveUrl=approveContractRequest +contractSystemId;
	
		Response response = apiObj.buildNewRequest(approveUrl, RequestType.PUT).addHeader("Authorization", tokenHeader)
								.setContentType(ContentType.JSON).useRelaxedHTTPSValidation().perform();
		System.out.println("Response is "+response.asPrettyString());
		return response.getStatusCode()==200;
		
	}
 
 public boolean removeMemberContract(String memberSystemId,String teamId) {
		
		
		String removeUrl=removeContractUrl +teamId+"/member/"+memberSystemId+"/removal";
	
		Response response = apiObj.buildNewRequest(removeUrl, RequestType.GET).addHeader("Authorization", tokenHeader)
								.setContentType(ContentType.JSON).useRelaxedHTTPSValidation().perform();
		System.out.println("Response is "+response.asPrettyString());
		return response.getStatusCode()==200;
	
	}


	
	
	
}
