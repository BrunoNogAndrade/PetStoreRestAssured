package org.example;

import io.restassured.config.RestAssuredConfig;
import org.testng.annotations.Test;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import static io.restassured.RestAssured.*;
import static io.restassured.config.JsonConfig.jsonConfig;
import static org.hamcrest.Matchers.*;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;

public class TestesCompletos extends Relatorio{
    JSONObject pet = new JSONObject();
    JSONObject category = new JSONObject();
    JSONObject tag1 = new JSONObject();
    JSONObject tag2 = new JSONObject();
    JSONArray photoUrls = new JSONArray();
    JSONArray tags = new JSONArray();

    public TestesCompletos(){
        pet.put("id", 99998);

        category.put("id", 99998);
        category.put("name", "felino");
        pet.put("category", category);

        pet.put("name", "Shepherd");

        photoUrls.add("http://fotosdegato.com.br/foto1.png");
        photoUrls.add("http://fotosdegato.com.br/foto2.png");
        pet.put("photoUrls", photoUrls);

        tag1.put("id", 99998);
        tag1.put("name", "Sem raça definida");
        tag2.put("id", 99999);
        tag2.put("name", "Amarelo");
        tags.add(tag1);
        tags.add(tag2);
        pet.put("tags", tags);

        pet.put("status", "available");
    }

    @Test
    public void adicionarNovoPetComSucesso(){
        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet)
        .when()
                .post()
        .then()
                .statusCode(200)
                .header("content-type", equalToIgnoringCase("Application/json"))
                .body("id", equalTo(99998),
                        "category.id", equalTo(99998),
                        "category.name", equalTo("felino"),
                        "name", equalTo("Shepherd"),
                        "photoUrls[0]", equalTo("http://fotosdegato.com.br/foto1.png"),
                        "photoUrls[1]", equalTo("http://fotosdegato.com.br/foto2.png"),
                        "tags[0].id", equalTo(99998),
                        "tags[0].name", equalTo("Sem raça definida"),
                        "tags[1].id", equalTo(99999),
                        "tags[1].name", equalTo("Amarelo"),
                        "status", equalTo("available"));

    }

    @Test
    public void verificaSeContemChaveStatus(){
        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet)
        .when()
                .post()
        .then()
                .statusCode(200)
                .body("$", hasKey("status"));
    }

    @Test
    public void verificaSeContemArrayDeTagsContemObjetoCujoValorParaNameEIgualAmarelo(){
        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet)
        .when()
                .post()
        .then()
                .statusCode(200)
                .body("tags", hasItem(hasEntry("name", "Amarelo")));
    }

    @Test
    public void verificaSeContemArrayDeTagsTemTamanhoIgualA2(){
        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet)
        .when()
                .post()
        .then()
                .statusCode(200)
                .body("tags", hasSize(2));
    }

    @Test
    public void verificaSeIdNaoEVazio(){
        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet)
        .when()
                .post()
        .then()
                .statusCode(200)
                .body("id", not(empty()));
    }

    @Test
    public void verificaSeArrayDeTagsTemMaisDeZeroItens(){
        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet)
        .when()
                .post()
        .then()
                .statusCode(200)
                .body("tags", hasSize(greaterThan(0)));
    }

    @Test
    public void verificaSeArrayPhotoUrlTemMenosDeTresItens(){
        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet)
        .when()
                .post()
        .then()
                .statusCode(200)
                .body("photoUrls", hasSize(lessThan(3)));
    }

    @Test
    public void verificaSeArrayPhotoUrlNaoEVazio(){
        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet)
        .when()
                .post()
        .then()
                .statusCode(200)
                .body("photoUrls", not(emptyArray()));
    }
}
