package entities;

public class FacebookPage extends Entity{
	
	private String rating;
	private String likeers_count;  
	private String phone;
	private String website; 
	private String business_birthdate;
	private String email;
	private String about; 
	
	 
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getLikeers_count() {
		return likeers_count;
	}
	public void setLikeers_count(String likeers_count) {
		this.likeers_count = likeers_count;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
 
	public String getBusiness_birthdate() {
		return business_birthdate;
	}
	public void setBusiness_birthdate(String business_birthdate) {
		this.business_birthdate = business_birthdate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
}
