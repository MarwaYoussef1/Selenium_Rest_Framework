package stc.esport.api.services;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.ParametersType;
import com.shaft.api.RestActions.RequestType;
import com.shaft.cli.FileActions;

import io.restassured.response.Response;



public class UploadService {

	private static String uploadServiceUrl="team-club/v1/public/file/upload/";
	private RestActions apiObj;
	
	
	public UploadService(RestActions apiObj) {
		this.apiObj = apiObj;
		
	}
	
	public String uploadFile(String fileName,String fileType)
	{
		String FilePath = System.getProperty("testUploadFilesPath");
		File uploadFile = new File(FileActions.getInstance().getAbsolutePath(FilePath, fileName));
		List<List<Object>> parameters = Arrays.asList(
				Arrays.asList("file", uploadFile),
				Arrays.asList("file-usage", fileType)
				);
		Response resp=apiObj
				.buildNewRequest(uploadServiceUrl, RequestType.POST).useRelaxedHTTPSValidation()
				.setParameters(parameters, ParametersType.FORM)
				.performRequest();
		/* RequestSpecBuilder builder = new RequestSpecBuilder();     
		 
		 builder.setConfig(
	                (new RestAssuredConfig()).encoderConfig((new EncoderConfig()).defaultContentCharset("UTF-8").appendDefaultContentCharsetToContentTypeIfUndefined(false)).and()
	                        .httpClient(HttpClientConfig.httpClientConfig()
	                                .setParam("http.connection.timeout", 30 * 1000)
	                                .setParam("http.socket.timeout", 30 * 1000)
	                                .setParam("http.connection-manager.timeout", 30 * 1000)));
		
		 builder.setConfig(RestAssured.config().sslConfig( new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()));
		 builder.setContentType("multipart/form-data");
		
       	RequestSpecification spec = builder.build();
		Response resp1=RestAssured.given().spec(spec)//.config(config().sslConfig(SSLConfig.sslConfig().allowAllHostnames().relaxedHTTPSValidation()))
	            
	            .multiPart("file", uploadFile)
	            .multiPart("file-usage", fileType).when().post("https://stage.saudiesports.sa/api/team-club/v1/public/file/upload/");*/
		
		String fileId= resp.jsonPath().getString("fileId");
		System.out.println("fileId is =" +fileId);
				
				return fileId ;
		
	}
	
	
}
