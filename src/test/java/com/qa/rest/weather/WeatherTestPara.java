package com.qa.rest.weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WeatherTestPara
{
	public static String st;
	BufferedReader br ;
	File file;
	
		@BeforeTest
		public void test() throws Throwable
		{
			
			file = new File("C:\\Users\\vinod\\Desktop\\indu\\RestAPIRestAssuredWeather\\src\\main\\java\\resource\\WeatherData.txt");
			br = new BufferedReader(new FileReader(file));	 
		  
		}
		 
	@Test (priority=0)
	public void testcode200() throws IOException
	{
		while((st = br.readLine()) != null)
		{
		    System.out.println("The City is :"+st);
		
		    Response resp = RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q="+ st + "&APPID=39375bac6270a0cc5d862aec8678c4a0");
		
		    int code = resp.getStatusCode();
		
		    System.out.println("****************************************************************"+"\n");
		
		    System.out.println("Test Response Status Code is = "+code+"\n");
		
		    Assert.assertEquals(code,200);
		
		    RequestSpecification httpRequest = RestAssured.given();
			
		    httpRequest.header("Content-Type","application/json");
		
		    JsonPath jsonPathEvaluator = resp.jsonPath();
		
		    String City = jsonPathEvaluator.get("name");
		
		    System.out.println("City received from Response is :" + City +"\n");
		
		  //  System.out.println("sys details is "+jsonPathEvaluator.get("sys")+"\n");
		
		    String Country = jsonPathEvaluator.getJsonObject("sys.country");
													
		    System.out.println("Country for the " +City + " is : "+Country+"\n");
		
		 		
		    System.out.println("****************************************************************"+"\n");
		  }	
		
		if((st = br.readLine()) == null)
		{
		    System.out.println("The City is :"+st);
		
		    Response resp = RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q="+ st + "&APPID=39375bac6270a0cc5d862aec8678c4a0");
		
		    String responseBody = resp.getBody().asString();	
			
			System.out.println("Response json Body is "+responseBody+"\n");
			
			Assert.assertEquals(responseBody.contains("city not found"), true);
			
			int code = resp.getStatusCode();
			
			System.out.println("Test Response Status Code for testcode404 is = "+code+"\n");
			
			Assert.assertEquals(code,404);
		
		    System.out.println("****************************************************************"+"\n");
		  }	
		
	}
	
	@Test (priority=1)
	public void testcode400() 
	{
		System.out.println("IF NOT PASSING ANY CITY "+"\n");
		
		Response resp = RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q=&APPID=39375bac6270a0cc5d862aec8678c4a0");
				
		String responseBody = resp.getBody().asString();	
		
		System.out.println("Response json Body is "+responseBody+"\n");
		
		Assert.assertEquals(responseBody.contains("Nothing to geocode"), true);
		
		int code = resp.getStatusCode();
		
		System.out.println("Test Response Status Code for testcode400 is = "+code+"\n");
		
		Assert.assertEquals(code,400);
		
		System.out.println("****************************************************************"+"\n");
	}
	
		
}