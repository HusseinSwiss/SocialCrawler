package TwitterCrawler;
   
import java.io.IOException;
import java.util.ArrayList;
import java.util.List; 

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;    
import org.openqa.selenium.WebDriver;  
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
  
import Common.Common; 
import entities.Twitter;

		/**
		 * @author hussein.hazimeh
		 * @HES-SO, Switzerland
		 * 22.03.2019
		 *
		 */
		public class TwitterCrawler{
			 
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
		         
		        driver.get("https://www.twitter.com/");
		        
		        driver.manage().window().maximize();
		        
		        driver.findElement(By.name("session[username_or_email]")).sendKeys(this.username);
		        driver.findElement(By.name("session[password]")).sendKeys(this.password); 
		        driver.findElement(By.cssSelector("input[type='submit']")).click();
		        
		         String about;
		    	 String num_of_tweets;
		    	 String joined_date;
		    	 String website;
		    	 String following_count;
		    	 String num_of_likes;
		    	 String num_photos_videos;
		    	 String name;
		    	 String followers_count;
		    	 String location;
		    	
		        ArrayList<Twitter> tws = new ArrayList<Twitter>();
		        System.out.println(urls.size());
		        
		        Thread.sleep(3000);
		        for(int i=0;i<urls.size();i++)
		        {  
		        	driver.get(urls.get(i));
		        	System.out.println(urls.get(i));
		        	
		        	//use this thread to wait the page until being loaded
		        	Thread.sleep(3000);
		        	
		    		name = driver.findElement(By.className("ProfileHeaderCard-name")).getText().replace("Verified account", "").replace("\n", ""); 
		    		about = driver.findElement(By.xpath(".//*[@class='ProfileHeaderCard-bio u-dir']")).getText(); 
		    		website = driver.findElement(By.xpath(".//*[@class='ProfileHeaderCard-urlText u-dir']")).getText();
		    		num_photos_videos = driver.findElement(By.xpath(".//*[@class='PhotoRail-headingText PhotoRail-headingText--withCount']")).getText().
		    				replace(" Photos and videos", "");
		    		
		    		List<WebElement> l = driver.findElements(By.className("ProfileNav-value"));
		    		
		    		num_of_tweets = l.get(0).getText();
		    		following_count = l.get(1).getText();
		    		followers_count = l.get(2).getText();
		    		num_of_likes = l.get(3).getText();
		    		
		    		location = driver.findElement(By.xpath(".//*[@class='ProfileHeaderCard-locationText u-dir']")).getText().replace("\n", "");
		    		joined_date = driver.findElement(By.xpath(".//*[@class='ProfileHeaderCard-joinDateText js-tooltip u-dir']")).getText().replace("\n", "");
		    		
		    		System.out.println("name " + name + " aobut : " + about + " website : " + website + " photos : " + num_photos_videos
		    				+ " tweets: " + num_of_tweets + " following : " + following_count + " followers: " + followers_count
		    				+ " likes: " + num_of_likes + " location: " + location + " date: " + joined_date);
		    		
		 		    Twitter tw = new Twitter();
		 		      
		 		    tw.setFullname(name);
		 		    tw.setAbout(about);
		 		    tw.setFollowers_count(followers_count);
		 		    tw.setFollowing_count(following_count);
		 		    tw.setJoined_date(joined_date);
		 		    tw.setLocation(location);
		 		    tw.setNum_of_likes(num_of_likes);
		 		    tw.setNum_of_tweets(num_of_tweets);
		 		    tw.setNum_photos_videos(num_photos_videos);
		 		    tw.setWebsite(website);
		 		    
		 		    tws.add(tw);
		        } 
		        driver.close();
		        
		        	System.out.println(tws.size() + " is the size of the twitter");
		        	 
			    	list = new JSONArray();
			    	
		        	for(Twitter t : tws)
		        	{  
		        		o2 = new JSONObject();
		        		o2.put("Fullname: ", t.getFullname());
		        		o2.put("About: ", t.getAbout()); 
		        		o2.put("Followers: ", t.getFollowers_count());
		        		o2.put("Following: ", t.getFollowing_count());
		        		o2.put("Joined_date: ", t.getJoined_date());
		        		o2.put("Location: ", t.getLocation());
		        		o2.put("Likes: ", t.getNum_of_likes());
		        		o2.put("Tweets: ", t.getNum_of_tweets());
		        		o2.put("Num_of_media: ", t.getNum_photos_videos());
		        		o2.put("Website: ", t.getWebsite());
		        		 
		        		list.add(o2);
		        	} 
		        	com.buildDataset("Twitter_profiles", list, this.json_path); 
		        }
		}