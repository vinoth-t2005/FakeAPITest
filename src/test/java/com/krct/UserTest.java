package com.krct;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class UserTest {
    private int id;
    @BeforeClass
    public void setUp(){
        RestAssured.baseURI="https://api.escuelajs.co/api/v1";
    }


    @Test(priority = 1)
    public void testCreateCategory(){

        String email="Zoro_"+System.currentTimeMillis()+"@gmail.com";
        String password="changeme";
        String name= "zoro_"+System.currentTimeMillis();
        String role="customer";
        String avatar="https://i.imgur.com/LDOO4Qs.jpg";
        Map body=Map.of(

                "email",email,
                "password",password,
                "name",name,
                "role",role,
                "avatar",avatar
        );

        Response response= RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/users");
        response
                .then().log().all()
                .statusCode(201)
                .body("name", Matchers.equalTo(name));
        id=response.jsonPath().getInt("id");
    }

    @Test(priority = 2)
    public void testGetCayegory()
    {
        RestAssured.given().pathParams("id",id)
                .when().get("/users/{id}")
                .then().log().all().statusCode(200);
    }

    @Test(priority = 3)
    public void Updatecategory(){
        String name1="luffy_"+System.currentTimeMillis();
        Map body=Map.of(
                "name",name1
        );
        RestAssured.given()
                .contentType(ContentType.JSON)

                .pathParams("id",id)
                .body(body)
                .when().put("/users/{id}")
                .then().statusCode(200)
                .body("name",Matchers.equalTo(name1));
    }
    @Test(priority = 4)
    public void deletecategory(){
        RestAssured.given()
                .pathParams("id",id)
                .when().delete("/users/{id}")
                .then().statusCode(200);
    }



}
