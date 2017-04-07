package com.model.beans;

import java.util.List;

public class Product {
	int id;
	int catid;
	int brandid;
	String name;
	String description;
	String imglink;
	List<Productprice> pricedetails;
	int bestprice;
	public int getBestprice() {
		return bestprice;
	}
	public void setBestprice() {
		int min;
		min = 0;
		if (pricedetails.size()>0)
			min =pricedetails.get(0).price;
		for(int i = 0 ; i< this.pricedetails.size(); i++)
		{
			if (min > pricedetails.get(i).price)
				min = pricedetails.get(i).price;
		}
		
		this.bestprice = min;
	}
	
	
	public List<Productprice> getPricedetails() {
		return pricedetails;
	}
	public void setPricedetails(List<Productprice> pricedetails) {
		this.pricedetails = pricedetails;
	}
	public String getImglink() {
		return imglink;
	}
	public void setImglink(String imglink) {
		this.imglink = imglink;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCatid() {
		return catid;
	}
	public void setCatid(int catid) {
		this.catid = catid;
	}
	
	public int getBrandid() {
		return brandid;
	}
	public void setBrandid(int brandid) {
		this.brandid = brandid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
