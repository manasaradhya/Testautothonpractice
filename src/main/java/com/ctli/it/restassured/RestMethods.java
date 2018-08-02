package com.ctli.it.restassured;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.testng.Assert;

import com.ctli.it.lib.DataTable;
import com.ctli.it.testcases.RestService;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.response.Response;

public class RestMethods {
	static int j = 2;

	// Methods for get
	public void verifyGetResponse() {
		RestAssured.baseURI = WebServicesReusables.getURI();
		Response response = given().
		// param("q", "turing").
		// param("radius","500").
		// param("type","restaurant").param("keyword", "cruise").param("key",
		// "AIzaSyCm80-cwgG82AAtGzR5hTT1eafVN49AV_g").
				when().get(WebServicesReusables.getResource());
		System.out.println(response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	public void responseAsString() {
		RestAssured.baseURI = WebServicesReusables.getURI();
		Response response = given().param("q", "turing").param("radius", "500").param("type", "restaurant")
				.param("keyword", "cruise").param("key", "AIzaSyCm80-cwgG82AAtGzR5hTT1eafVN49AV_g").when()
				.get(WebServicesReusables.getResource());

		String responseAsString = response.asString();
		System.out.println(responseAsString);
	}

	public void get(int i, int j) {
		RestAssured.baseURI = WebServicesReusables.getURI();
		Response response = given().when().get(WebServicesReusables.getResource());
		Assert.assertEquals(response.getStatusCode(), 200);
		int userId = response.then().contentType(ContentType.JSON).extract().path("[" + (i - 1) + "].userId");
		int id = response.then().contentType(ContentType.JSON).extract().path("[" + (i - 1) + "].id");
		String title = response.then().contentType(ContentType.JSON).extract().path("[" + (i - 1) + "].title");
		String body = response.then().contentType(ContentType.JSON).extract().path("[" + (i - 1) + "].body");
		String userIdString = String.valueOf(userId);
		String idString = String.valueOf(id);
		DataTable dTable = new DataTable("./Resources/FeatureInput.xlsx");
		dTable.setCellData("TEST", "userID", j, userIdString);
		dTable.setCellData("TEST", "id", j, idString);
		dTable.setCellData("TEST", "title", j, title);
		dTable.setCellData("TEST", "body", j, body);
	}

	public void verifyNumOfRecord() {
		RestAssured.baseURI = WebServicesReusables.getURI();
		Response response = given().when().get(WebServicesReusables.getResource());
		int i = 0;
		while (true) {
			try {
				int id = response.then().contentType(ContentType.JSON).extract().path("[" + i + "].id");
				i++;
			} catch (IllegalArgumentException e) {
				Assert.assertEquals(i, 100);
				System.out.println(i+" records verifed");
				break;
			}
		}
	}

	// post Methods
	public void post() {
		PostsByGetSet post = new PostsByGetSet();
		post.setId("1");
		post.setAuthor("Mohit");
		post.setTitle("Test");
		RestAssured.baseURI = WebServicesReusables.getURI();
		Response response = given().queryParam("key", WebServicesReusables.getKey()).when()
				.contentType(ContentType.JSON).body(post).post(WebServicesReusables.PostResource());

		String responseValue = response.asString();
		System.out.println(responseValue);
	}

	public void postDelete() {
		RestAssured.baseURI = WebServicesReusables.getURI();
		String response = given().queryParam("key", WebServicesReusables.getKey()).body(PayLoad.placePostBody()).when()
				.post(WebServicesReusables.PostResource()).then().assertThat().statusCode(200).and()
				.contentType(ContentType.JSON).and().extract().response().asString();
		System.out.println(response);

		JsonPath js = WebServicesReusables.StringtoJson(response);
		String placeid = js.get("place_id");
		given().queryParam("key", WebServicesReusables.getKey()).body(PayLoad.placeDeleteBody(placeid)).when()
				.post(WebServicesReusables.DeleteResource()).then().assertThat().statusCode(200);
	}

	// check with XML
	public void postDeleteXml() throws IOException {
		RestAssured.baseURI = WebServicesReusables.getURI();
		;
		String response = given().queryParam("key", WebServicesReusables.getKey())
				.body(WebServicesReusables
						.generateStringfromXML("C:\\TestAutothon\\TestAutothonFramework\\payload.xml"))
				.when().post("/maps/api/place/add/xml").then().assertThat().statusCode(200).and()
				.contentType(ContentType.XML).and().extract().response().asString();
		System.out.println(response);
		XmlPath x = WebServicesReusables.StringtoXML(response);
		String placeid = x.get("PlaceAddResponse.place_id");
		given().queryParam("key", WebServicesReusables.getKey())
				.body(WebServicesReusables
						.generateStringfromXML("C:\\TestAutothon\\TestAutothonFramework\\payloadDelete.xml"))
				.when().post("/maps/api/place/delete/xml").then().assertThat().statusCode(200);

	}

	// put or update
	public void putDelete() {
		RestAssured.baseURI = WebServicesReusables.getURI();
		;
		String response = given().queryParam("key", WebServicesReusables.getKey()).body(PayLoad.placePostBody()).when()
				.put(WebServicesReusables.PostResource()).then().assertThat().statusCode(200).and()
				.contentType(ContentType.JSON).and().extract().response().asString();
		System.out.println(response);

		JsonPath js = WebServicesReusables.StringtoJson(response);
		String placeid = js.get("place_id");
		given().queryParam("key", WebServicesReusables.getKey()).body(PayLoad.placeDeleteBody(placeid)).when()
				.post(WebServicesReusables.DeleteResource()).then().assertThat().statusCode(200);
	}

	// patch
	public void patch() {
		RestAssured.baseURI = WebServicesReusables.getURI();
		Response response = given().queryParam("key", WebServicesReusables.getKey()).when()
				.contentType(ContentType.JSON).body("{\"name\": \"Google Shoes!\"}")
				.patch(WebServicesReusables.PostResource());

		String responseValue = response.asString();
		System.out.println(responseValue);
	}

	public void getResponse() {
		ArrayList<Integer> hs = new ArrayList<Integer>();
		RestMethods rest = new RestMethods();
		rest.verifyNumOfRecord();
		int n = 10, t1 = 0, t2 = 1;

		for (int i = 0; i <= n; ++i) {

			int sum = t1 + t2;
			t1 = t2;
			t2 = sum;
			if (sum == 1) {

			} else {
				hs.add(t1);
			}
		}
		Collections.shuffle(hs);
		for (int i = 0; i < hs.size(); i++) {
			rest.get(hs.get(i), j++);
		}

	}

}
