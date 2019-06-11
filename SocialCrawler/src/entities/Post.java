package entities;

public class Post {
	
	private String n_of_comments;
	private int n_of_shares;
	private String post_content;
	private int n_of_reactions;
	private String post_date;
	private String post_url;
	
	public void set_n_of_comments(String noc)
	{
		this.n_of_comments = noc;
	}
	
	public String get_n_of_comments()
	{
		return this.n_of_comments;
	}
	
	public void set_n_of_shares(int nos)
	{
		this.n_of_shares = nos;
	}
	
	public int get_n_of_shares()
	{
		return this.n_of_shares;
	}
	
	public void set_post_content(String pc)
	{
		this.post_content = pc;
	}
	
	public String get_post_content()
	{
		return this.post_content;
	}
	
	public void set_n_of_reactions(int nor)
	{
		this.n_of_reactions = nor;
	}
	
	public int get_n_of_reactions()
	{
		return this.n_of_reactions;
	}
	
	public void set_post_date(String pd)
	{
		this.post_date = pd;
	}
	
	public String get_post_date()
	{
		return this.post_date;
	}
	
	public void set_post_url(String purl)
	{
		this.post_url = purl;
	}
	
	public String get_post_url()
	{
		return this.post_url;
	}
}
