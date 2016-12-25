package com.puhua.crm.entity;

public class SortModel
{
	private String id;
	private String name;
	private String info = "信息科供电部";
	private String numb="13717932974";
	private String sortLetters;
	
	
	public String getNumb() {
		return numb;
	}
	public void setNumb(String numb) {
		this.numb = numb;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getSortLetters()
	{
		return sortLetters;
	}
	public void setSortLetters(String sortLetters)
	{
		this.sortLetters = sortLetters;
	}
	public String getInfo()
	{
		return info;
	}
	public void setInfo(String info)
	{
		this.info = info;
	}
	
}
