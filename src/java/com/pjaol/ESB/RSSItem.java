package com.pjaol.ESB;

public class RSSItem {
	private String title;
	private String link;
	private String description;
	
	public RSSItem(String title, String link, String description){
		this.title = title;
		this.link = link;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		
		return "title:"+title+"\n"
			+"link:"+link+"\n"
			+"description:"+description;
	}

}
