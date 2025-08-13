package stc.esport.pages.contracts;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;
import com.shaft.tools.io.ReportManager;


import stc.esport.pojo.ContractRequest;
import stc.esport.base.PageBase;
import stc.esport.pojo.Teams;
import stc.esport.utils.Utils;


public class ViewContractDetailsPage  extends PageBase{
	public WebDriver driver;
	public Properties properties;
	
	
	// identifier
	By memberName= By.xpath("//h4[contains(@class,'heder-name')]");
	By teamName= By.xpath("(//i[@class='icon-users']//following::span)[1]");
	By creationDate= By.xpath("(//i[@class='icon-calendar']//following::span)[1]");
	By contractStatus= By.xpath("//span[contains(@class,'status')]");
	By contractENName= By.xpath("(//h5[text()='Full Name in English']//following::p)[1]");
	By contactARName= By.xpath("(//h5[text()='Full Name in Arabic']//following::p)[1]");
	By contractGame= By.xpath("(//h5[text()='In-Game Name']//following::p)[1]");
	By gender= By.xpath("(//h5[text()='Gender']//following::p)[1]");
	By email= By.xpath("(//h5[text()='Email']//following::p)[1]");
	By mobileNum= By.xpath("(//h5[text()='Mobile Number']//following::p)[1]");
	By memberStatus= By.xpath("(//h5[text()='Status']//following::p)[1]");
	By nationality= By.xpath("(//h5[text()='Nationality']//following::p)[1]");
	By city= By.xpath("(//h5[text()='City']//following::p)[1]");
	By DOB= By.xpath("(//h5[text()='Date of birth']//following::p)[1]");
	By age= By.xpath("(//h5[text()='Age']//following::p)[1]");
	By idType= By.xpath("(//h5[contains(text(),'ID Type')]//following::p)[1]");
	By parentIdType= By.xpath("(//h5[contains(text(),'Parent ID Type')]//following::p)[1]");
	By nationalID= By.xpath("(//h5[contains(text(),'National ID')]//following::p)[1]");
	By clubName= By.xpath("(//h5[text()='Club name']//following::p)[1]");
	By team= By.xpath("(//h5[text()='Team']//following::p)[1]");
	By contractStartDate= By.xpath("(//h5[text()='Contract start date']//following::p)[1]");
	By contractEndDate= By.xpath("(//h5[text()='Contract end date']//following::p)[1]");
	By contractPeriod= By.xpath("(//h5[text()=' Contract period in Days ']//following::p)[1]");
	By salary= By.xpath("(//h5[text()='Salary per month']//following::p)[1]");
	By eleagueContract= By.xpath("(//h5[text()='Saudi eleague contract']//following::p)[1]");
	By contractFile= By.xpath("(//h5[text()='Contract file']//following::span)[1]");
	By parentApproval= By.xpath("(//h5[text()='Parent Approval']//following::span)[1]");
	By parentAttachment= By.xpath("(//h5[text()='Parent ID Attachment']//following::span)[1]");
	By idAttachment= By.xpath("(//h5[text()='ID Attachment']//following::span)[1]");
	By terms= By.xpath("(//h4[text()='Terms']//following::p)[1]");
	
	
	
	
	// Constractor
			public ViewContractDetailsPage(WebDriver driver, Properties properties) {
				this.driver = driver;
				this.properties = properties;
			}

			private String calculateContractPeriod(String startDate, String endDate) {
				ArrayList<Integer>s=Utils.splitDate(startDate);
				ArrayList<Integer> e=Utils.splitDate(endDate);
				
				LocalDate start= LocalDate.of(s.get(2), s.get(1), s.get(0));
				LocalDate end= LocalDate.of(e.get(2), e.get(1), e.get(0));
			    long months = ChronoUnit.MONTHS.between(YearMonth.from(start), YearMonth.from(end));
			    long days = ChronoUnit.DAYS.between( start , end );

               if (months==0) {
			    return Long.toString(days+1);
               }else {
            	   return Long.toString(months);  
               }
			}
			
			private String getContractCreationDate() {
				return Utils.getCurrentDate("dd/MM/yyyy");
			}
			
			private String formatDisplayedDate(String date) {
				 return Utils.formatDateDDMMMMYYYY(date);
			}
			
			private String getContractStatus(String status) {
				String fword=status.substring(0, status.indexOf(' '));
				String Swords=status.substring(status.indexOf(' ') + 1);
		        String lowercaseStr = Swords.toLowerCase(); 
		        
		        return fword+" "+lowercaseStr;
			}
			
			public boolean checkMemberInformation(ContractRequest contract, String actualTeamGame,String actualTeamName, String actualMemberName, String actualMemberMail, String actualMemberMobile,
					String actualContractStatus, boolean MemberAge16) {
				ReportManager.log("Check member contract information");
				
				String contractStatusFormat= getContractStatus(actualContractStatus);
				
				  ArrayList<Boolean> status=new ArrayList<Boolean>(); 
				  status.add(driver.element().getText(memberName).trim().equals(actualMemberName.trim()));
			      status.add(driver.element().getText(teamName).equals(actualTeamName));
			      status.add(driver.element().getText(creationDate).equals(formatDisplayedDate(getContractCreationDate())));
			      status.add(driver.element().getText(contractStatus).equals(contractStatusFormat));

			      status.add(driver.element().getText(contractENName).equals(contract.getFullNameInEN()));
			      status.add(driver.element().getText(contactARName).equals(contract.getFullNameInAR()));
			     // status.add(driver.element().getText(contractGame).equals(actualTeamGame));
			      status.add(driver.element().getText(gender).equals(contract.getGender()));
			      status.add(driver.element().getText(email).equalsIgnoreCase(actualMemberMail));
			      status.add(driver.element().getText(mobileNum).equals(actualMemberMobile));
			      status.add(driver.element().getText(memberStatus).equals(contract.getStatus().toLowerCase()));
			      status.add(driver.element().getText(nationality).equals(contract.getNationality()));
			      status.add(driver.element().getText(city).equals(contract.getCity()));
			      status.add(driver.element().getText(DOB).equals(formatDisplayedDate(contract.getDateOfBirth())));
			      status.add(driver.element().getText(age).equals(Utils.calculateAge(contract.getDateOfBirth())));
			      status.add(driver.element().getText(nationalID).equals(contract.getIdNumber()));

			      if(MemberAge16) {
			      status.add(driver.element().getText(idType).equals(contract.getIdType()));
			      }else {
				  status.add(driver.element().getText(parentIdType).equals(contract.getIdType()));
			      }
			      
			      boolean isAllTrue= status.stream().allMatch(val -> val == true);
				  ReportManager.log("Member contract information list "+ isAllTrue);

	              return isAllTrue;
			}
			
			public boolean checkContractInformation(ContractRequest contract, String actualClubName, String actualTeamName ) {
				ReportManager.log("Check contract information");

				  ArrayList<Boolean> status=new ArrayList<Boolean>(); 

				 status.add(driver.element().getText(clubName).equals(actualClubName));
			      status.add(driver.element().getText(team).equals(actualTeamName));
			      status.add(driver.element().getText(contractStartDate).equals(formatDisplayedDate(contract.getContractStartDate())));
			      status.add(driver.element().getText(contractEndDate).equals(formatDisplayedDate(contract.getContractEndDate())));
			      status.add(driver.element().getText(contractPeriod).equals(calculateContractPeriod(contract.getContractStartDate(),contract.getContractEndDate())));
			      status.add(driver.element().getText(salary).equals("SAR "+contract.getSalary()));
			      status.add(driver.element().getText(eleagueContract).equals(contract.getSaudiEleague()));
			      
			      boolean isAllTrue= status.stream().allMatch(val -> val == true);
				  ReportManager.log("Contract information list "+ isAllTrue);

	              return isAllTrue;
			}
			
			public boolean checkAttachmentsAndTerms(ContractRequest contract, boolean MemberAge16) {
				ReportManager.log("Check contract attachments and terms");

				 String termsContractMsg = properties.getProperty("contractTerms");

				  ArrayList<Boolean> status=new ArrayList<Boolean>(); 

				  status.add(driver.element().getText(contractFile).equals(contract.getUploadContract()));

			      status.add(driver.element().getText(terms).equals(termsContractMsg));

			      if(!MemberAge16) {
			      status.add(driver.element().getText(parentApproval).equals(contract.getParentApproval()));
			      status.add(driver.element().getText(parentAttachment).equals(contract.getIdAttachement()));
			      }else {
				  status.add(driver.element().getText(idAttachment).equals(contract.getIdAttachement()));
			      }
			      
			      boolean isAllTrue= status.stream().allMatch(val -> val == true);
				  ReportManager.log("Attachment and terms list "+ isAllTrue);
				  
                  return isAllTrue;
			}
			
			 public boolean checkContractDetails(ContractRequest contractObj,Teams team, boolean MemberAge16, String actualContractStatus, String actualClubName, String actualMemberMail, String actualMemberMobile, String actualMemberName){
				  ArrayList<Boolean> status=new ArrayList<Boolean>(); 
				  waitForPageLoadWithoutSpinner(driver);

				  
				 status.add(checkMemberInformation(contractObj,team.getGame(), team.getTeamNameEn(),actualMemberName,actualMemberMail, actualMemberMobile,actualContractStatus,MemberAge16));
				 status.add(checkContractInformation(contractObj,actualClubName,team.getTeamNameEn()));
				 status.add(checkAttachmentsAndTerms(contractObj, MemberAge16));
				 
				 boolean isAllTrue= status.stream().allMatch(val -> val == true);
                 return isAllTrue;				
			        
			 }

}
