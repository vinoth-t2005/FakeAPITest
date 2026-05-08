package com.krct;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class fakeAPITest {
    private int id;
    @BeforeClass
    public void setUp(){
        RestAssured.baseURI="https://api.escuelajs.co/api/v1";
    }


//    @Test
//    public void testGetproducts(){
//        RestAssured.given()
//                .when().get("/products")
//                .then().statusCode(200)
//                .body("size()",Matchers.greaterThan(0));
//    }
//
//    @Test
//    public void testFillerproductsByprice(){
//        RestAssured.given().queryParam("price",100)
//                .when().get("/products")
//                .then().statusCode(200)
//                .body("[0].price",Matchers.equalTo(100));
//    }
//
//    @Test
//    public void testGetCategory(){
//        RestAssured.given()
//                .when().get("/categories")
//                .then().statusCode(200)
//                .body("$",Matchers.instanceOf(List.class));
//    }
//
//    @Test
//    public void testFillerproductsByrangeprice(){
//        RestAssured.given().queryParam("price","0-1000")
//
//                .queryParam("categoryid",1)
//                .when().get("/products")
//                .then().statusCode(200)
//                .body("[0].price", greaterThanOrEqualTo(0))
//                .body("[0].price", lessThanOrEqualTo(1000));    }
//
//    @Test
//    public void getCategoryByID(){
//        given().pathParams("id",1).
//                when().get("/categories/{id}")
//                .then().statusCode(200)
//                .body("id",Matchers.equalTo(1));
//    }

    @Test(priority = 1)
    public void testCreateCategory(){
        String name= "zoro_"+System.currentTimeMillis();
        String image="https://placeimg.com/640/480/any";
        Map body=Map.of(
                "name",name,
                "image",image
        );

                Response response= RestAssured.given()
                        .contentType(ContentType.JSON)
                        .body(body)
                        .when().post("/categories");
                response
                        .then().log().all()
                        .statusCode(201)
                        .body("name",Matchers.equalTo(name));
                id=response.jsonPath().getInt("id");
    }
    @Test(priority = 2)
    public void testGetCayegory()
    {
        RestAssured.given().pathParams("id",id)
                .when().get("/categories/{id}")
                .then().log().all().statusCode(200);

    }
    @Test(priority = 3)
    public void Updatecategory(){
        String name1="category_"+System.currentTimeMillis();
        String image1="https://google.com";
        Map body=Map.of(
          "name",name1,
          "image",image1
        );
        RestAssured.given()
                .contentType(ContentType.JSON)

                .pathParams("id",id)
                .body(body)
                .when().put("/categories/{id}")
                .then().statusCode(200)
                .body("name",Matchers.equalTo(name1));
    }
    @Test(priority = 4)
    public void deletecategory(){
        RestAssured.given()
                .pathParams("id",id)
                .when().delete("/categories/{id}")
                .then().statusCode(201);
    }
}
