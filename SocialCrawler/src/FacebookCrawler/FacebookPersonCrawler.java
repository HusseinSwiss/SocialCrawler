package FacebookCrawler;
   
import java.io.IOException;
import java.util.ArrayList;
import java.util.List; 

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;  
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;  
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
   
import Common.Common;
import entities.FacebookPerson; 

		/**
		 * @author hussein.hazimeh
		 * @HES-SO, Switzerland
		 * 22.03.2019
		 *
		 */
		public class FacebookPersonCrawler{
			
			
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
		        
		        //driver.get("https://www.facebook.com/rami.bahsoun.779/friends");
		        
		    	String name = null;
		    	int num_of_frnds = 0;
		    	int num_of_posts;
		    	String location = null;
		    	String followers_count = null;
		    	String affiliation = null;
		        
		        ArrayList<FacebookPerson> persons = new ArrayList<FacebookPerson>();
		        System.out.println(urls.size());
		        for(int i=0;i<urls.size();i++)
		        {  
		        	driver.get(urls.get(i));
		        	System.out.println(urls.get(i));
		        	 
		    		name = driver.findElement(By.xpath(".//*[@class='_2t_q']")).getText(); 
		    		
		    		List<WebElement> l = driver.findElements(By.className("_50f3"));
		        	
		    		for (WebElement e : l)
		    		{
		    			if(e.getText().contains("Lives in"))
		    				location = e.getText();
		    			else if(e.getText().contains("Followed"))
		    				followers_count = e.getText().replace("people", "");
		    			else if(e.getText().contains("at"))
		    				affiliation = e.getText();
		    		}
		        	
		    		try {
		    			num_of_frnds = Integer.parseInt(driver.findElement(By.xpath(".//*[@class='_50f8 _2iem']")).getText());
		    		}catch(NoSuchElementException e){}
		    		
		    		JavascriptExecutor js = ((JavascriptExecutor) driver);
		        	for(int j=0;j<20000;j++) js.executeScript("window.scrollTo("+j+", document.body.scrollHeight)");
		    		
		        	List<WebElement> posts = driver.findElements(By.xpath(".//*[@class='_5pcr userContentWrapper']"));
		        	num_of_posts = posts.size();
		    		
		    		System.out.println("name " + name + " location: " + location + " followers : " + followers_count + " friends : " + num_of_frnds
		        			 + " affiliation : " + affiliation + " num_of_posts: " + num_of_posts);
		        	
		        	Thread.sleep(3000);
				       
		 		    FacebookPerson fbpe = new FacebookPerson();
		 		      
		 		    fbpe.setFullname(name);   
		 		  	fbpe.setFollowers_count(followers_count); 
		 		  	fbpe.setAffiliation(affiliation);
		 		  	fbpe.setLocation(location);
		 		  	fbpe.setNum_of_posts(num_of_posts);
		 		  	fbpe.setNum_of_frnds(num_of_frnds);
		 		      
		 		    persons.add(fbpe);
		        } 
		        driver.close();
		        
		        	System.out.println(persons.size() + " is the size of the persons");
		        	 
			    	list = new JSONArray();
			    	
		        	for(FacebookPerson p : persons)
		        	{ 
		        		
		        		o2 = new JSONObject();
		        		o2.put("Fullname: ", p.getFullname());
		        		o2.put("Location: ", p.getLocation());
		        		o2.put("Num_of_followers: ", p.getFollowers_count());
		        		o2.put("Num_of_friends: ", p.getNum_of_frnds());
		        		o2.put("Num_of_posts: ", p.getNum_of_posts()); 
		        		o2.put("Affiliation: ", p.getAffiliation());
		        		 
		        		list.add(o2);
		        	}
		        	com.buildDataset("Facebook_persons", list, this.json_path); 
			        
		        }
		}