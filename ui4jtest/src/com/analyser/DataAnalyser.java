package com.analyser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.entity.Product;

import info.debatty.java.stringsimilarity.*;


public class DataAnalyser {
	
	

	public static void main(String[] args)
	{
		String productsFile = "products.txt";
		String fileName="scoop.txt";
		
		File file = new File(fileName);
		try {
			FileReader reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.err.println("Could not Create File Reader");
		}
		
		Product p1 = new Product( "ASUS X540SA XX081D" );
		Product p2 = new Product( "Pc Portable Lenovo Ideapad 110-15IBR / Dual Core / 2 Go" );
		Product p3 = new Product( "Pc portable Asus X540SA / Dual Core / 4 Go / Marron" );
		Product p4 = new Product( "Galaxy J3 / 4G / Double SIM / Gold + SIM Offerte + Gratuité 10DT" );
		Product p5 = new Product( "GALAXY J2-4G-GOLD" );
		Product p6 = new Product( "Galaxy J3 (2016) 4G Blanc" );
		Product p7 = new Product( "Galaxy J2 - 4G Blanc" );

		
		
		
	}
}
