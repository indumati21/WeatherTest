package com.qa.rest.weather;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WeatherTest 
{
		@Test
		public void testcode200() 
		{
			Response resp = RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q=pune&APPID=39375bac6270a0cc5d862aec8678c4a0");
			
			int code = resp.getStatusCode();
			
			System.out.println("****************************************************************"+"\n");
			
			System.out.println("Test Response Status Code for testcode200 is = "+code+"\n");
			
			Assert.assertEquals(code,200);
			
			System.out.println("****************************************************************"+"\n");
							
		}
		
		@Test
		public void testdata() 
		{
			Response resp = RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q=pune&APPID=39375bac6270a0cc5d862aec8678c4a0");
							
			String data = resp.asString();

					
			System.out.println("The Complete Data is :"+data+"\n");
			
			System.out.println("****************************************************************"+"\n");

		}
		
		@Test
		public void testcode404() 
		{
			Response resp = RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q=pune123&APPID=39375bac6270a0cc5d862aec8678c4a0");
					
			String responseBody = resp.getBody().asString();	
			
			System.out.println("Response json Body is "+responseBody+"\n");
			
			Assert.assertEquals(responseBody.contains("city not found"), true);
			
			int code = resp.getStatusCode();
			
			System.out.println("Test Response Status Code for testcode404 is = "+code+"\n");
			
			Assert.assertEquals(code,404);
			
			System.out.println("****************************************************************"+"\n");

		}
		
		@Test
		public void testcode400() 
		{
			Response resp = RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q=&APPID=39375bac6270a0cc5d862aec8678c4a0");
					
			String responseBody = resp.getBody().asString();	
			
			System.out.println("Response json Body is "+responseBody+"\n");
			
			Assert.assertEquals(responseBody.contains("Nothing to geocode"), true);
			
			int code = resp.getStatusCode();
			
			System.out.println("Test Response Status Code for testcode400 is = "+code+"\n");
			
			Assert.assertEquals(code,400);
			
			System.out.println("****************************************************************"+"\n");
		}
		
		@Test
		public void testcountry()
		{
			
			Response resp = RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q=pune&APPID=39375bac6270a0cc5d862aec8678c4a0");
				
			RequestSpecification httpRequest = RestAssured.given();
			
			httpRequest.header("Content-Type","application/json");
			
			JsonPath jsonPathEvaluator = resp.jsonPath();
			
			String City = jsonPathEvaluator.get("name");
			
			System.out.println("City received from Response is :" + City +"\n");
			
			System.out.println("sys details is "+jsonPathEvaluator.get("sys")+"\n");
			
			String Country = jsonPathEvaluator.getJsonObject("sys.country");
														
			System.out.println("Country for the " +City + " is : "+Country+"\n");
			
			Assert.assertEquals(Country,"IN");
			
			System.out.println("****************************************************************"+"\n");
			
		
		 
		}		
}


