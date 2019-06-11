package entities;

public class FacebookPerson extends Entity{
	
	private int num_of_posts;
	private int num_of_frnds;
	private String affiliation;
	 
	public String getAffiliation() {
		return affiliation;
	}
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}
	public int getNum_of_posts() {
		return num_of_posts;
	}
	public void setNum_of_posts(int num_of_posts) {
		this.num_of_posts = num_of_posts;
	}
	public int getNum_of_frnds() {
		return num_of_frnds;
	}
	public void setNum_of_frnds(int num_of_frnds) {
		this.num_of_frnds = num_of_frnds;
	}
	 
	 
}
