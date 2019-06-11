package FacebookCrawler;
		   
import java.io.IOException;
import java.util.ArrayList;
import java.util.List; 
		 
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By; 
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver; 
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
	  
import Common.Common;
import entities.FacebookPage;
		 
		/**
		 * @author hussein.hazimeh
		 * @HES-SO, Switzerland
		 * 22.03.2019
		 *
		 */
		public class FacebookPageCrawler{
			
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
		         
		        driver.get("https://www.facebook.com/");
		        driver.findElement(By.name("email")).sendKeys(this.username);
		        driver.findElement(By.name("pass")).sendKeys(this.password); 
		        driver.findElement(By.id("loginbutton")).click();
		        
		        
		    	 String rating = null;
		    	 String likeers_count = null;
		    	 String followers_count = null; 
		    	 String phone = null;
		    	 String website = null; 
		    	 String business_birthdate = null;
		    	 String email = null;
		    	 String about = null;
		    	 String name = null;
		        
		        ArrayList<FacebookPage> pages = new ArrayList<FacebookPage>();
		        System.out.println(urls.size());
		        for(int i=0;i<urls.size();i++)
		        {  
		        	driver.get(urls.get(i));
		        	System.out.println(urls.get(i));
		        	
		        	name = driver.findElement(By.className("_64-f")).getText();
		        	try
		        	{
		        		rating = driver.findElement(By.xpath(".//*[@class='_2w0a']")).getText();
		        		
		        	}catch(NoSuchElementException e){ 
		        		rating = "";
		        	}
		        	List<WebElement> l = driver.findElements(By.className("_4bl9"));
		        	
		    		for (WebElement e : l)
		    		{
		    			if(e.getText().contains("like this"))
		    				likeers_count = e.getText().replace("people like this", "");
		    			else if(e.getText().contains("follow this"))
		    				followers_count = e.getText().replace("people follow this", "");
		    		}
		    		driver.get(urls.get(i)+"/about");
		    		List<WebElement> links = driver.findElements(By.className("_50f4"));
		    		
		    		for(WebElement a : links)
		    		{
		    			if(a.getText().contains("http"))
		    				website = a.getText();
		    			else if(a.getText().contains("About"))
		    				about = a.findElement(By.xpath("following-sibling::*")).getText();
		    			else if(a.getText().contains("Started"))
		    				business_birthdate = a.getText();
		    			else if(a.getText().contains("@"))
		    				email = a.getText();
		    			else if(a.getText().contains("Call"))
		    				phone = a.getText().replace("Call", "");
		    		}
		    		
		        	System.out.println("name" + name + " rating : " + rating + " likers : " + likeers_count + " followers " + followers_count + " website: " + website
		        			+ " about " + about + " birth : " + business_birthdate + " email : " + email + " phone : " + phone);
		        	
		        	Thread.sleep(3000);
				       
		 		      FacebookPage fbp = new FacebookPage();
		 		      
		 		      fbp.setFullname(name);
		 		      fbp.setAbout(about);
		 		      fbp.setBusiness_birthdate(business_birthdate);
		 		      fbp.setEmail(email);
		 		      fbp.setFollowers_count(followers_count);
		 		      fbp.setLikeers_count(likeers_count);
		 		      fbp.setPhone(phone);
		 		      fbp.setRating(rating);
		 		      fbp.setWebsite(website);
		 		      
		 		      pages.add(fbp);
		        } 
		        driver.close();
		        
		        	System.out.println(pages.size() + " is the size of the pages");
		        	 
			    	list = new JSONArray();
			    	
		        	for(FacebookPage p : pages)
		        	{
		        		//System.out.println(p.getCompany_name() + " ++ " );
		        		
		        		o2 = new JSONObject();
		        		o2.put("Company_name: ", p.getFullname());
		        		o2.put("About: ", p.getAbout());
		        		o2.put("Business_birthdate: ", p.getBusiness_birthdate());
		        		o2.put("Email: ", p.getEmail());
		        		o2.put("Followers_count: ", p.getFollowers_count());
		        		o2.put("Likers_count: ", p.getLikeers_count());
		        		o2.put("Phone: ", p.getPhone());
		        		o2.put("Rating: ", p.getRating());
		        		o2.put("Website: ", p.getWebsite());
		        		 
		        		list.add(o2);
		        	}
		        	com.buildDataset("Facebook_pages", list, this.json_path);  
		        }
		  } 