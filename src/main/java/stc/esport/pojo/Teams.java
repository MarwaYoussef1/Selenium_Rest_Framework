package stc.esport.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import stc.esport.utils.Constants;
import stc.esport.utils.Utils;


public class Teams {
	
	 //variables 
    public Properties properties;
    private String teamType;
    private String teamNameEn;
    private String teamNameAr;
    private String game;
    private String about;
    private String teamLogo;
    private String memberRole;
    private HashMap<String,ArrayList<String>> MemberWithRole= new HashMap<String,ArrayList<String>>();
    private HashMap<String,String> socialMedia= new HashMap<String,String>();
    private String memberImage;

    
    public Teams(Properties properties) {
        this.properties = properties;
    }


	public String getTeamNameEn() {
		if(teamNameEn==null) {
		this.teamNameEn= "Auto Team " +Utils.getRandomStringORNum(5,"String");
    	}
		return teamNameEn;
	}
	public void setTeamNameEn(String teamNameEn) {
		this.teamNameEn = teamNameEn;
	}


	public String getTeamNameAr() {
		if(teamNameAr==null) {	
			this.teamNameAr="اوتو تيم" + " "+Utils.getRandomArabicString(5);
			}
		return teamNameAr;
	}
	public void setTeamNameAr(String teamNameAr) {
		this.teamNameAr = teamNameAr;
	}


	public String getGame() {
		if(game==null) {
			 return properties.getProperty("teamGame");
		}
		return game;
	}
	public void setGame(String game) {
		this.game = game;
	}


	public String getAbout() {
		if(about==null) {
			return properties.getProperty("teamAbout");
		}
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}


	public String getTeamLogo() {
		if(teamLogo==null) {
			return properties.getProperty("teamLogo");
		}
		return teamLogo;
	}
	public void setTeamLogo(String teamLogo) {
		this.teamLogo = teamLogo;
	}

	
	public String getMemberRole() {
		if(memberRole==null) {
			return properties.getProperty("memberRole");
		}
		return memberRole;
	}
	public void setMemberRole(String memberRole) {
		this.memberRole = memberRole;
	}


	public HashMap<String, ArrayList<String>> getMemberWithRole() {
		if(MemberWithRole.isEmpty()) {
			ArrayList<String> userDetails= new ArrayList<String>();
				 userDetails.add(Constants.CLUBPLAYERROLE);
				 userDetails.add(properties.getProperty("playerAccName"));
				 //userDetails.add(Status);
				 this.MemberWithRole.put(properties.getProperty("playerAcc"), userDetails);
		}
		return MemberWithRole;
	}
	public void setMemberWithRole(HashMap<String, ArrayList<String>> memberWithRole) {
		MemberWithRole = memberWithRole;
	}


	public HashMap<String, String> getSocialMedia() {
       if(socialMedia.isEmpty()) {
    	   this.socialMedia.put(Constants.FACEBOOK,properties.getProperty("teamFB"));
    	   this.socialMedia.put(Constants.INSTAGRAM,properties.getProperty("teamInstgram"));
       }
		return this.socialMedia;
	}
	public void setSocialMedia(HashMap<String, String> socialMedia) {
		this.socialMedia = socialMedia;
	}


	public String getTeamType() {
		return teamType;
	}
	public void setTeamType(String teamType) {
		this.teamType = teamType;
	}


	public String getMemberImage() {
		return memberImage;
	}
	public void setMemberImage(String memberImage) {
		this.memberImage = memberImage;
	}
    
    
	

}
