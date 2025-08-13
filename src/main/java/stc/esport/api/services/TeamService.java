package stc.esport.api.services;

import java.util.ArrayList;
import java.util.Properties;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import stc.esport.api.pojo.Teams.TeamBody;
import stc.esport.api.pojo.Teams.TeamInvitation;
import stc.esport.api.pojo.filter.Filter;
import stc.esport.api.pojo.filter.FilterBody;
import stc.esport.api.pojo.filter.FilterSorting;
import stc.esport.utils.Constants;
import stc.esport.utils.Utils;

public class TeamService {

	private static String addTeamUrl = "team-club/v1/teams";
	private static String filterTeamsUrl="team-club/v1/teams/my-team";
	private static String filterArchiveTeamsUrl="team-club/v1/teams/my-team?archived=true";
	private static String archiveTeamUrl="/archive";
	private RestActions apiObj;
	private Properties properties;
	private String tokenHeader;

	public TeamService(RestActions apiObj, Properties properties) {
		this.apiObj = apiObj; 
		this.properties = properties;

	}

	private TeamBody fillClanTeamBody(boolean team, ArrayList<TeamInvitation> invitations) {

		TeamBody body = new TeamBody();
		UploadService uploadservice = new UploadService(apiObj);
		String teamLogo = null;
		if (team) {
			teamLogo = uploadservice.uploadFile(properties.getProperty("clubLogo"), Constants.SIGNUPCLUBLOGO);
		}
 
		body.setNameAr("نادى" + " " + Utils.getRandomArabicString(10));
		body.setName("Autooo" + Utils.getRandomStringORNum(10, "String"));
		body.setAbout("about " + Utils.getRandomStringORNum(10, "String"));  
		body.setPublicProfile(true); 
		body.setLogo(teamLogo);
		body.setGameId(5); 
		body.setSocialMedia(null);
		body.setInvitations(invitations);
		return body;

	}

	public TeamBody createTeamWithInvitation(String token, boolean team, ArrayList<TeamInvitation> invitations) {
		TeamBody teamBody = null;
		if (team)
			teamBody = fillClanTeamBody(true, invitations);
		else
			teamBody = fillClanTeamBody(false, invitations);
		if(sendTeamClanRequest(token, teamBody))
		return teamBody;
		return null;
	}

	private boolean sendTeamClanRequest(String token, TeamBody body) {
		tokenHeader = "Bearer " + token;
		Response response = apiObj.buildNewRequest(addTeamUrl, RequestType.POST).addHeader("Authorization", tokenHeader)
				.setContentType(ContentType.JSON).useRelaxedHTTPSValidation().setRequestBody(body)
				.setTargetStatusCode(201).perform();
		System.out.println("Response status is " + response.statusCode());
		return response.statusCode()==201;
		

	}
	
	public String filterTeamByTeamEnName(String teamNameEn, String listType, String token) {
		tokenHeader="Bearer "+token;
		String filterRole;
		Filter filter= new Filter();
		filter.setColumnName("name");
		filter.setColumnValue(teamNameEn);
		filter.setOperator("equals");
		ArrayList<Filter> filters= new ArrayList<Filter>();
		filters.add(filter);
		
		FilterSorting sorting = new FilterSorting();
		sorting.setSortName("createdAt");
		sorting.setSortType("DESC");
		sorting.setSortColumnType("date");
		ArrayList<FilterSorting> sortingList= new ArrayList<FilterSorting>();
		sortingList.add(sorting);
		
		FilterBody teamFilterBody= new FilterBody();
		teamFilterBody.setFilters(filters);
		teamFilterBody.setSorting(sortingList);
		teamFilterBody.setPageLength(16);
		teamFilterBody.setPageNo(0);
		
		if(listType.equals(Constants.MYTEAMLIST)) {
			filterRole= filterTeamsUrl;
		}else {
			filterRole= filterArchiveTeamsUrl;
		}
		
		Response response = apiObj.buildNewRequest(filterRole, RequestType.POST).addHeader("Authorization", tokenHeader)
								.setContentType(ContentType.JSON).setRequestBody(teamFilterBody).useRelaxedHTTPSValidation().perform();
		System.out.println("Response is "+response.asPrettyString());
		
		JsonPath jsonPathObj = new JsonPath(response.asString());
		String teamId = jsonPathObj.get("content[0].systemId");
		
		System.out.println(teamId);
		return teamId;
	}
	
	public boolean archiveTeam (String token, String teamId) {
		
		tokenHeader="Bearer "+token;
		String url= addTeamUrl+"/"+teamId+archiveTeamUrl;
		Response response = apiObj.buildNewRequest(url, RequestType.PUT).addHeader("Authorization", tokenHeader)
				.setContentType(ContentType.JSON).useRelaxedHTTPSValidation().perform();
		
            System.out.println("Response is "+response.asPrettyString());
            
            return response.getStatusCode()==200;
	}

}
