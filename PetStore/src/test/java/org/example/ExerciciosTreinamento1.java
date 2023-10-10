package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class ExerciciosTreinamento1 extends org.example.Relatorio{

    // Exercicio 1
    @Test
    public void adicionaNovoPet() {

        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/store/order")
                .header("content-type", "application/json")
                .body("{\n" +
                        "  \"id\": 666,\n" +
                        "  \"petId\": 6661,\n" +
                        "  \"quantity\": 5,\n" +
                        "  \"shipDate\": \"2020-06-26T20:47:13.703Z\",\n" +
                        "  \"status\": \"pending\",\n" +
                        "  \"complete\": true\n" +
                        "}")
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("id", equalTo(666),
        "petId", equalTo(6661),
        "quantity", equalTo(5),
        "shipDate", containsString("2020-06-26T20:47:13.703+0000"),
        "status", equalTo("pending"),
        "complete", equalTo( true));
    }

    // Exercicio 2
    @Test
    public void pesquisaPetInexistente() {
        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/pet/999")
                .header("accept", "application/json")
                .when()
                .get()
                .then()
                .statusCode(404)
                .body("code", equalTo(1),
                "type", equalTo("error"),
                "message", equalTo("Pet not found"));
    }

    // Exercicio 3
    @Test
    public void atualizaPetExistente() {
        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body("{\n" +
                        "  \"id\": 666,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 6661,\n" +
                        "    \"name\": \"Teste nome atualiza pet id inválido\"\n" +
                        "  },\n" +
                        "  \"name\": \"Dona onça\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"https://www.bing.com/images/search?view=detailV2&ccid=cZgoZkBL&id=2106753F0D52C4F54BBCE3BBA9FCC54F31234192&thid=OIP.cZgoZkBLLFTOBhw84VAjtwHaE6&mediaurl=https%3a%2f%2fpantanalexplorer.com.br%2fwp-content%2fuploads%2f2018%2f02%2fPantanal-Explorer-on%25C3%25A7a-01.jpg&exph=1860&expw=2800&q=on%c3%a7a&simid=608019952761179787&ck=CEEEEDB1AC166D86ACA7CF3B94319E10&selectedIndex=4\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 654,\n" +
                        "      \"name\": \"onça pardus\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"pending\"\n" +
                        "}")
                .when()
                .put()
                .then()
                .statusCode(200)
                .body("id", equalTo(666),
                        "category.id", equalTo(6661),
                        "category.name", equalTo("Teste nome atualiza pet id inválido"),
                        "name", equalTo("Dona onça"),
                        "photoUrls[0]", containsString("https://www.bing.com/images/search?view=detailV2&ccid=cZgoZkBL&id=2106753F0D52C4F54BBCE3BBA9FCC54F31234192&thid=OIP.cZgoZkBLLFTOBhw84VAjtwHaE6&mediaurl=https%3a%2f%2fpantanalexplorer.com.br%2fwp-content%2fuploads%2f2018%2f02%2fPantanal-Explorer-on%25C3%25A7a-01.jpg&exph=1860&expw=2800&q=on%c3%a7a&simid=608019952761179787&ck=CEEEEDB1AC166D86ACA7CF3B94319E10&selectedIndex=4"),
                        "tags[0].id", equalTo(654),
                        "tags[0].name", equalTo("onça pardus"),
                        "status", equalTo( "pending"));
    }

    // Exercicio 4
    @Test
    public void atualizaPetIdInexistente() {
        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body("{\n" +
                        "    \"id\": 9999999999999999999999999,\n" +
                        "    \"category\": {\n" +
                        "        \"id\": 0,\n" +
                        "        \"name\": \"string\"\n" +
                        "    },\n" +
                        "    \"name\": \"doggie\",\n" +
                        "    \"photoUrls\": [\n" +
                        "        \"string\"\n" +
                        "    ],\n" +
                        "    \"tags\": [\n" +
                        "        {\n" +
                        "            \"id\": 662,\n" +
                        "            \"name\": \"string\"\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"status\": \"available\"\n" +
                        "}")
                .when()
                .put()
                .then()
                .statusCode(400);
    }

    // Exercicio 5
    @Test
    public void pesquisaPestFindByStatus() {
        given().param("status","pending")
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/pet/findByStatus")
                .header("accept", "application/json")
                .when()
                .get()
                .then()
                .statusCode(200)
        .body("status", hasItem("pending"));
    }

    // Exercicio 6
    @Test
    public void metodoInvalido() {
        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("accept", "application/json")
                .when()
                .get()
                .then()
                .statusCode(405)
                .body("code", equalTo(405),
                        "type", equalTo("unknown"));
    }
}


