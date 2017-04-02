package com.entity;

//import java.lang.reflect.Array;
//import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
//import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import info.debatty.java.stringsimilarity.Levenshtein;

public class Product {

	// attributes
	private String name, brand, desc , category , reference ;

	private int categoryID;

	private int venderID;
	private int brandID;
	
	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	
	
	public int getBrandID() {
		return brandID;
	}

	public void setBrandID(int brandID) {
		this.brandID = brandID;
	}

	private Set<String> Specs;
	private int price;

	private String link;

	private String imgLink;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Set<String> getSpecs() {
		return Specs;
	}

	public void setSpecs(Set<String> specs) {
		Specs = specs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = cleanString(name);
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = cleanString(brand);
	}
	
	public String getRef() {
		return reference;
	}

	public void setRef(String reference) {
		this.reference = cleanString(reference);
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc.replaceAll("[^\\p{L}.,0-9 .,;]", "");
	}

	public String getCategory() {
		return category;
	}

	public int getVender() {
		return venderID;
	}

	public void setVender(int vender) {
		this.venderID = vender;
	}

	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}

	public void setCategory(String category) {
		this.category = category.toLowerCase().replaceAll("[^ \\p{L}]", " ").replaceAll("[ ]+", " ").replaceAll(getBrand(), "").trim();
	}

	public void deleteSpec(String Spec) {
		Specs.remove(Spec);
	}

	public void addSpec(String spec) {
		Specs.add(spec);
	}

	// constarctor
	public Product(String name) {
		this.setName(name);
	}

	public Product() {
		link = "";
		imgLink = "";
		desc = "";
		name = "";
		brand = "";
		reference="";
	}

	

	
	public String getLink() {
		return this.link;
	}
	public void setLink(String link) {
		this.link=link;
	}

	public String getImgaeLink() {
		return this.imgLink;
	}
	
	public void setImgaeLink(String imgLink) {
		this.imgLink=imgLink;
	}

	protected String cleanString(String source)
	{
		return source.toLowerCase().replaceAll(" +"," ").trim();
	}
}