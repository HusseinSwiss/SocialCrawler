package entities;

public class LinkedInPerson extends Entity{
 
	private String position;
	private String num_conn; 
	private String recommendations;
	private String graduation_date;
	private String major_os;
	
	public String getMajor_os() {
		return major_os;
	}
	public void setMajor_os(String major_os) {
		this.major_os = major_os;
	}
	private int experiences;
	
	 
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getNum_conn() {
		return num_conn;
	}
	public void setNum_conn(String num_conn) {
		this.num_conn = num_conn;
	}
	 
	public String getRecommendations() {
		return recommendations;
	}
	public void setRecommendations(String recommendations2) {
		this.recommendations = recommendations2;
	}
	public String getGraduation_date() {
		return graduation_date;
	}
	public void setGraduation_date(String graduation_date2) {
		this.graduation_date = graduation_date2;
	}
	public int getExperiences() {
		return experiences;
	}
	public void setExperiences(int experiences) {
		this.experiences = experiences;
	}
}
