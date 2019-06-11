package LinkedInCrawler;
   
import java.io.IOException;
import java.util.ArrayList;
import java.util.List; 

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver; 
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import Common.Common;
import entities.LinkedInPerson; 
/**
 * @author hussein.hazimeh
 * @HES-SO, Switzerland
 * 22.03.2019
 *
 */
public class LinkedInPersonCrawler{
	
	
	 private static WebDriver driver;  
	 private static JSONArray list;
	 private static JSONObject o2; 
	 private  String username;
	 private  String password;
	 private String excel_path;
	 private String driver_path;
	 private String json_path;
	  
	 public void set_json_path(String path)
		{
			this.json_path = path;
		}
	public void login(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	public void set_excel_path(String path)
	{
		this.excel_path = path;
	}
	public void set_driver_path(String path)
	{
		this.driver_path = path;
	}
   public  void getdata(int start, int end) throws IOException, JSONException, InterruptedException
   {
   	 
   	Common com = new Common();
       com.setInputFile(this.excel_path);
         List<String> urls =  com.read(start, end);
        
        System.setProperty("webdriver.chrome.driver", this.driver_path +"\\chromedriver.exe" );
        driver  = new ChromeDriver();
         
        driver.get("https://www.linkedin.com/home");
        driver.findElement(By.name("session_key")).sendKeys(this.username);
        driver.findElement(By.name("session_password")).sendKeys(this.password); 
        driver.findElement(By.id("login-submit")).click();
        
        String fullname = null;
        String position = null;
        String location = null;
        String num_conn = null;
        String recommendations = null;
        String graduation_date = null;
        String education = null;
        int experiences = 0 ;
        
        ArrayList<LinkedInPerson> persons = new ArrayList<LinkedInPerson>();
        
        for(int i=0;i<urls.size();i++)
        {  
        	driver.get(urls.get(i));
        	System.out.println(urls.get(i));
         	try{fullname = driver.findElement(By.xpath(".//*[@class='pv-top-card-section__name inline t-24 t-black t-normal']")).getText();}
         	catch(NoSuchElementException e){}
        	position = driver.findElement(By.xpath(".//*[@class='pv-top-card-section__headline mt1 t-18 t-black t-normal']")).getText();
        	location = driver.findElement(By.xpath(".//*[@class='pv-top-card-section__location t-16 t-black--light t-normal mt1 inline-block']")).getText();
        	num_conn = driver.findElement(By.xpath(".//*[@class='pv-top-card-v2-section__entity-name pv-top-card-v2-section__connections ml2 t-14 t-black t-normal']")).getText();
        	
        	JavascriptExecutor js = ((JavascriptExecutor) driver);
        	for(int j=0;j<30;j++) js.executeScript("window.scrollTo("+j+", document.body.scrollHeight)");
        	
        	try
        	{
        		recommendations = driver.findElement(By.tagName("artdeco-tab")).getText();
        	}catch(NoSuchElementException e){
        		//e.printStackTrace();
        	}
        	
        	try{graduation_date = driver.findElement(By.xpath(".//*[@class='pv-entity__dates t-14 t-black--light t-normal']")).getText();}
        	catch(NoSuchElementException e){}
        	try{education = driver.findElement(By.xpath(".//*[@class='pv-entity__secondary-title pv-entity__fos pv-entity__secondary-title t-14 t-black--light t-normal']")).getText();}
        	catch(NoSuchElementException e){}
        	
 		    String[] params = new String[]{
        			"pv-profile-section__section-info section-info pv-profile-section__section-info--has-no-more",
        			"pv-profile-section__section-info section-info pv-profile-section__section-info--has-more ember-view",
        			"pv-profile-section__section-info section-info pv-profile-section__section-info--has-more"
        			};
 		   experiences = get_ul_count("pv-profile-section__section-info section-info pv-profile-section__section-info--has-no-more ember-view");
        	
 		   for (int j = 0; j < params.length ; j++) {
        		if (experiences != -1) break;
        	    experiences = get_ul_count(params[j]);
        	    
        	}
 		      System.out.println(fullname + " position : " + position + " location : " + location + " conn: "  + num_conn + " exper: " + experiences
      			+ " recomm : " + recommendations + " g_date_ : " + graduation_date + " major : " + education);
 		      
 		      LinkedInPerson person = new LinkedInPerson();
 		      
 		      person.setFullname(fullname);
 		      person.setPosition(position);
 		      person.setLocation(location);
 		      person.setNum_conn(num_conn);
 		      person.setExperiences(experiences);
 		      person.setRecommendations(recommendations);
 		      person.setGraduation_date(graduation_date);
 		      person.setMajor_os(education);
 		      
 		      persons.add(person);
        } 
        driver.close(); 
        	System.out.println(persons.size() + " is the size of the persons");
        	 
	    	list = new JSONArray();
	    	
        	for(LinkedInPerson p : persons)
        	{
        		o2 = new JSONObject();
        		o2.put("Fullname: ", p.getFullname());
        		o2.put("Experiences_count: ", p.getExperiences());
        		o2.put("Graduation_date: ", p.getGraduation_date());
        		o2.put("Location: ", p.getLocation());
        		o2.put("Major_of_study: ", p.getMajor_os());
        		o2.put("Number_of_connections: ", p.getNum_conn());
        		o2.put("Recommendations_count: ", p.getRecommendations());
        		o2.put("Current_position: ", p.getPosition());
        		
        		list.add(o2);
        	}
        	com.buildDataset("LinkedIn_persons", list, this.json_path); 
        	  
        	} 
    
    public static int get_ul_count(String _class)
    {
    	int experiences = 0;
    	try{
    		
    		List<WebElement> expeCount1 = driver.findElements(By.xpath(".//*[@class='"+_class+"']/div")); 
    		List<WebElement> expeCount2 = driver.findElements(By.xpath(".//*[@class='"+_class+"']/li")); 
    		experiences = expeCount1.size() + expeCount2.size();
    		
    	}catch(ElementNotVisibleException e){
    		System.out.print(e.getMessage());
    	}
    	//System.out.println(experiences + " vffunction for class_ : " + _class);
    	return experiences > 0 && experiences > 1 ? experiences : -1;
    }
}
