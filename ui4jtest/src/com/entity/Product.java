package com.entity;

//import java.lang.reflect.Array;
//import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
//import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

public class Product {

	// attributes
	private String name, brand, desc , category;

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
		this.name = name.toLowerCase().replaceAll("-|(/.*)", " ").trim();
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand.trim().toLowerCase();
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc.toLowerCase().replaceAll("[^\\p{L}.,0-9 ]", "");
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
		this.category = category.toLowerCase().replaceAll("[^\\p{L} ]", "").replaceAll(" +", " ").replaceAll(getBrand(), "").trim();
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
	}

	public boolean isSameProduct(Product p) {
		String[] tokens1;
		String[] tokens2;

		// if ( this.getCategory() != p.getCategory() ) return false;
		// if ( this.getBrand() != p.getBrand() ) return false;
		// if ( this.getName() != p.getName() ) return false;
		// if ( this.getCategory() != p.getCategory() ) return false;
		if (comparePrice(this, p))
			return false;

		tokens1 = name.replaceAll("[-\\s/]", " ").toUpperCase().split(" ");
		tokens2 = p.getName().replaceAll("-", " ").toUpperCase().split(" ");

		Set<String> tokensSet1 = new HashSet<String>(Arrays.asList(tokens1));
		Set<String> tokensSet2 = new HashSet<String>(Arrays.asList(tokens2));

		float total = Math.min(tokensSet1.size(), tokensSet2.size());

		Set<String> intersection = Sets.intersection(tokensSet1, tokensSet2);

		float similarity = intersection.size();

		System.out.println(similarity / total + " " + similarity + " " + total);

		if (similarity / total <= 0.5)
			return false;

		return true;
	}

	private boolean comparePrice(Product p1, Product p2) {
		int max = Math.max(p1.getPrice(), p2.getPrice());
		int min = Math.min(p1.getPrice(), p2.getPrice());

		return ((max * 0.93f) < min);
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
}