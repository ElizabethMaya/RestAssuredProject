package com.hereapi.rap.geocode.test;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hereapi.rap.geocode.data.GeoCodeSearchResponse;
import com.hereapi.rap.geocode.data.Item;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestGetRequest {
	
	final ObjectMapper objectMapper = new ObjectMapper();
	
	public static final String PROPER_INFO_URL = "https://geocode.search.hereapi.com/v1/geocode?q=Kochi&apiKey=NBsAy11pem0tru4e4-3P_reMQHc3y-81M_6AF94ygHo";
	public static final String INVALID_INFO_URL = "https://geocode.search.hereapi.com/v1/geocode?apiKey=NBsAy11pem0tru4e4-3P_reMQHc3y-81M_6AF94ygHo";
	public static final String INCORRECT_FORMAT_URL = "https://geocode.search.hereapi.com/v1/geocode?q=Kochi&";

	

	@Test
	void properDataTestCase()
	{
		Response response=RestAssured.get(PROPER_INFO_URL);
		System.out.println(response.asString());
		System.out.println(response.getBody().asString());
		System.out.println(response.getStatusCode());
		System.out.println(response.getTime());
		
		int statusCode=response.getStatusCode();
		Assert.assertEquals(statusCode, 200, "Verifying Valid Response");
		
	}
	@Test
	void invalidInputDataTestCase()
	{
		Response response=RestAssured.get(INVALID_INFO_URL);
		System.out.println(response.asString());
		System.out.println(response.getBody().asString());
		System.out.println(response.getStatusCode());
		System.out.println(response.getTime());
				
		int statusCode=response.getStatusCode();
		Assert.assertEquals(statusCode, 400, "Verifying Invalid Input");
		
	}
	@Test
	void incorrectApiKeyTestCase()
	{
		Response response=RestAssured.get(INCORRECT_FORMAT_URL);
		System.out.println(response.asString());
		System.out.println(response.getBody().asString());
		System.out.println(response.getStatusCode());
		System.out.println(response.getTime());
				
		int statusCode=response.getStatusCode();
		Assert.assertEquals(statusCode, 401, "Verifying Unauthorized Access");
		
	}
	

	@Test
	void postalCodeVerification() throws JsonMappingException, JsonProcessingException
	{
		Response response=RestAssured.get(PROPER_INFO_URL);
		
		int statusCode=response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		String jsonResponse = response.getBody().asString();
		System.out.println(jsonResponse);
		GeoCodeSearchResponse gcsResponse=objectMapper.readValue(jsonResponse, GeoCodeSearchResponse.class);
		Assert.assertNotNull(gcsResponse);
		Assert.assertTrue(gcsResponse.getItems().length == 3);
		String expectedPostalCode="682005";
		String actualPostalCode=(gcsResponse.getItems())[0].getAddress().getPostalCode();
		Assert.assertEquals(actualPostalCode, expectedPostalCode, "Verifying Postal Code for Kochi Location");
	}
	
	
	
}
