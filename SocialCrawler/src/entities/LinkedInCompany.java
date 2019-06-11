package entities;

public class LinkedInCompany extends Entity{


	private String business_type;  
	private String overview;
	private String website;
	private String com_size;
	private String com_type;
	private String founded;
	private String spetialities;
	private String funding_total_rounds; 
	private String last_round_date;
	private String last_round_amount;
	
	public String getFunding_total_rounds() {
		return funding_total_rounds;
	}
	public void setFunding_total_rounds(String funding_total_rounds) {
		this.funding_total_rounds = funding_total_rounds;
	}
	public String getlast_round_date() {
		return last_round_date;
	}
	public void setlast_round_date(String last_round_date) {
		this.last_round_date = last_round_date;
	}
	public String getlast_round_amount() {
		return last_round_amount;
	}
	public void setlast_round_amount(String last_round_amount) {
		this.last_round_amount = last_round_amount;
	}
	
	public String getBusiness_type() {
		return business_type;
	}
	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getCom_size() {
		return com_size;
	}
	public void setCom_size(String com_size) {
		this.com_size = com_size;
	}
	public String getCom_type() {
		return com_type;
	}
	public void setCom_type(String com_type) {
		this.com_type = com_type;
	}
	public String getFounded() {
		return founded;
	}
	public void setFounded(String founded) {
		this.founded = founded;
	}
	public String getSpetialities() {
		return spetialities;
	}
	public void setSpetialities(String spetialities) {
		this.spetialities = spetialities;
	}
	
}
