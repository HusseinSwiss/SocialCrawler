package entities;

public class Twitter extends Entity{

	private String about;
	private String num_of_tweets;
	private String joined_date;
	private String website;
	private String following_count;
	private String num_of_likes;
	private String num_photos_videos;
	
	public String getNum_photos_videos() {
		return num_photos_videos;
	}
	public void setNum_photos_videos(String num_photos_videos) {
		this.num_photos_videos = num_photos_videos;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getNum_of_tweets() {
		return num_of_tweets;
	}
	public void setNum_of_tweets(String num_of_tweets) {
		this.num_of_tweets = num_of_tweets;
	}
	public String getJoined_date() {
		return joined_date;
	}
	public void setJoined_date(String joined_date) {
		this.joined_date = joined_date;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getFollowing_count() {
		return following_count;
	}
	public void setFollowing_count(String following_count) {
		this.following_count = following_count;
	}
	public String getNum_of_likes() {
		return num_of_likes;
	}
	public void setNum_of_likes(String num_of_likes) {
		this.num_of_likes = num_of_likes;
	}
}
