// Utils
// This Class is used for all common utility functions
// 10-01-2023 
//----------------------

package stc.esport.utils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang.RandomStringUtils;

import com.shaft.tools.io.ReportManager;

public class Utils {

	static ArrayList<String> treeMdsNames;
	static String compNode = "";
	static String nodeValue = "";
	static String mainNode = "";

	public static boolean setProperty(String key, String value) {
		boolean propertySaved;
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
				PropertiesConfiguration.class).configure(
						params.properties().setFileName("./src/test/resources/Properties/testconfig.properties"));
		Configuration config;
		try {
			config = builder.getConfiguration();
			config.setProperty(key, value);
			builder.save();
			propertySaved = true;
			ReportManager.log("property with key = " + key + " changed");
		} catch (ConfigurationException e) {
			ReportManager.log("property with key = " + key + " couldn't be changed");
			propertySaved = false;
		}

		return propertySaved;
	}

	public static void PrintArray(ArrayList<String> Ar) {
		ReportManager.log("--------------- Array -----------------");
		ReportManager.log(Arrays.toString(Ar.toArray()));
		ReportManager.log("--------------- Array End-----------------");
	}

	public static ArrayList<String> sortListAlphabetically(ArrayList<String> unOrderedList, boolean asc) {

		Utils.PrintArray(unOrderedList);
		Collections.sort(unOrderedList);
		if (!asc)// sort descending
		{
			Collections.reverse(unOrderedList);
		}
		Utils.PrintArray(unOrderedList);
		return unOrderedList;
	}

	public static String getCurrentTime() {

		Date date = new Date(System.currentTimeMillis());
		DateFormat formatter = new SimpleDateFormat("ddMMyyHHmmss");
		return formatter.format(date);

	}

	public static String getCurrentDate(String format) {

		Date date = new Date(System.currentTimeMillis());
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);

	}
	// function to generate a random string of length n
	public static String getRandomString(int n, String type) {
		String text = null;
		text = RandomStringUtils.randomNumeric(n);
		return text;
	}

	// function to generate a random string of length n
	public static String getRandomStringORNum(int n, String type) {
		String text = null;

		if (type.equals("num"))
			text = RandomStringUtils.randomNumeric(n);
		else if (type.equals("String"))
			text = RandomStringUtils.randomAlphabetic(n);
		else
			text = RandomStringUtils.randomAlphanumeric(n);

		return text;
	}

	// generate random e-mail addresses
	public static String generateRandomEmailAddress(String domain) {
		String emailAddress = "";
		// Generate random email address
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		while (emailAddress.length() < 5) {
			int character = (int) (Math.random() * 26);
			emailAddress += alphabet.substring(character, character + 1);
		}
		emailAddress += Integer.valueOf((int) (Math.random() * 99)).toString();
		emailAddress += "@" + domain + ".com";
		return emailAddress;
	}

	public static boolean compareLists(ArrayList<String> List1, ArrayList<String> List2) {
		boolean matched;
		Collections.sort(List1);
		Collections.sort(List2);

		matched = List1.equals(List2);
		return matched;
	}

	public static String getMonth(int month) {
		return new DateFormatSymbols().getMonths()[month - 1];
	}

	public static String getRandomArabicString(int n) {
		Random r = new Random(System.currentTimeMillis());
		StringBuilder sb = new StringBuilder();
		int asciirange1 = (int) '\u0621';		
		int asciirange2 = (int) '\u063A';
		for (int i = 0; i < n; i++) {
					sb.append((char) (r.nextInt(asciirange2 - asciirange1) + asciirange1));
				}
		return sb.toString();
		}
	
	public static String getTodayDay() {
	  String dayNum= Integer.toString(java.time.LocalDate.now().getDayOfMonth());
	  return dayNum;
	}
	
	public static String getTodayHijriDay() {
		 LocalDate gregorianDate = LocalDate.now();
		 HijrahDate hijradate = HijrahDate.from(gregorianDate);
		 int tomorrow= hijradate.get(ChronoField.DAY_OF_MONTH)+1;
		 String DayNum=  Integer.toString(tomorrow);
		
        return DayNum;
	}
	
	public static long getTomorrowDateInMilisecond() {
		Calendar cal = Calendar.getInstance(); //current date and time      
		cal.add(Calendar.DAY_OF_MONTH, 1); //add a day
		cal.set(Calendar.HOUR_OF_DAY, 23); //set hour to last hour
		cal.set(Calendar.MINUTE, 59); //set minutes to last minute
		cal.set(Calendar.SECOND, 59); //set seconds to last second
		cal.set(Calendar.MILLISECOND, 999); //set milliseconds to last millisecond
		long millis = cal.getTimeInMillis();
		
		return millis;
	}
	
	 public static String replaceLastCharByNextChar(String input) {
	        if (input == null || input.isEmpty()) {
	            return input;
	        }

	        char lastChar = input.charAt(input.length() - 1);
	        char newChar;

	        if (Character.isLetter(lastChar)) {
	            newChar = (char) (lastChar + 1);
	        } else {
	            newChar = lastChar;
	        }

	        return input.substring(0, input.length() - 1) + newChar;
	    }
	public static String getTomorrowDay() {
		int todayDay=java.time.LocalDate.now().getDayOfMonth();
	    int tomorrowDay= todayDay+1;
	    return Integer.toString(tomorrowDay);
	}
	
	public static ArrayList<Integer> splitDate(String date){
		ArrayList<Integer> dateList = new ArrayList<Integer>();  

	      String dateParts[] = (date).split("/");
			 int year =Integer.parseInt(dateParts[2]); 
			 int month=Integer.parseInt(dateParts[1]);
			 int day=Integer.parseInt(dateParts[0]);
			 String	 monthName= Utils.getMonth(month);
			 
			 dateList.add(day);
			 dateList.add(month);
			 dateList.add(year);

		return dateList;
	}
	
	public static String formatDateDDMMMMYYYY(String date) {
		 SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		    SimpleDateFormat format2 = new SimpleDateFormat("dd MMMM yyyy");
		    Date dateFormat = null;
			try {
				dateFormat = format1.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return format2.format(dateFormat);
	}
	
	public static String getTomorrowDate() {
		// In string format
		LocalDate today = LocalDate.now();
		String tomorrow = (today.plusDays(1)).format(DateTimeFormatter.ISO_DATE);
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(tomorrow, formatter1);
		
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formattedString = date.format(formatter2);

		return formattedString;

	}
	
	public static String calculateAge(String DOB) {
		ArrayList<Integer>birthDate=splitDate(DOB);
		LocalDate date= LocalDate.of(birthDate.get(2), birthDate.get(1), birthDate.get(0));
		LocalDate curDate = LocalDate.now();  
		 
		 Period p = Period.between(date, curDate);
		return Long.toString( p.getYears());
	}

	
	
	 public static String removeLastChar(String input) {
	        if (input == null || input.isEmpty()) {
	            return input;
	        }

	        return input.substring(0, input.length() - 1);
	    }

}
