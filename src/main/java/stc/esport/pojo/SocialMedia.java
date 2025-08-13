package stc.esport.pojo;

import java.util.Map;
import java.util.Properties;

public class SocialMedia {

	
	
	 //variables 
    public Properties properties;
    private  Map<String, String> socialMediaLinks;
  
    
    public SocialMedia(Properties properties) {
        this.properties = properties;
    }

    
    
	public Map<String, String> getSocialMediaLinks() {
		return socialMediaLinks;
	}



	public void setSocialMediaLinks(Map<String, String> socialMediaLinks) {
		this.socialMediaLinks = socialMediaLinks;
	}




    
}
