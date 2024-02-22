package Testes;

import static io.restassured.RestAssured.given;

import bases.TestBase;
import org.testng.annotations.Test;
import JsonObjects.Category;
import JsonObjects.Pet;
import JsonObjects.Tag;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Map;
import static Utils.Constants.UrlBase;
import io.restassured.http.Header;
import io.restassured.http.Headers;


public class PetStoreRestAssured extends TestBase {

    @Test
    public void pesquisaPet() {
         given()
                .baseUri(UrlBase)
                .basePath("/pet/{petId}")
                .pathParam("petId", 99998)
        .when()
                .get()
        .then()
                .statusCode(200);
    }

    @Test
    public void testeComHeaders() {
        //forma simples
        given().header("content-type", "application/json")
               .header("Authorization", "bearer ey9659....");

        //forma com objeto MAP
        Map<String, Object> headersMap = new HashMap<String, Object>();
        headersMap.put("content-type", "application/json");
        headersMap.put("Authorization", "bearer ey541564...");
        given().headers(headersMap);

        //forma com objetos Header e Heders do RESTASSURED
        Header header1 = new Header("content-type", "application/json");
        given().header(header1);

        Header header2 = new Header("Authorization", "bearer ey1351435...");
        given().header(header1).header(header2);

        List<Header> headerList = new ArrayList<>();
        headerList.add(header1);
        headerList.add(header2);

        Headers headers = new Headers(headerList);
        given().headers(headers);
    }

    @Test
    public void testeComCookie(){
        given().cookie("isso é um cookie")
                .cookie("isso é outro cookie");
    }

   @Test
    public void testeComAutenticacao(){
        given().auth().basic("usuario", "senha");
        given().auth().oauth2("token oauth2");
        given().auth().form("usuario", "senha");
    }

    @Test
    public void testeComPathParameter(){
        given().pathParam("petId", 999998);
    }

    @Test
    public void testeComQueryParameter(){
        given().queryParam("status", "available"); //somente um valor para status
        given().queryParam("status", "available", "sold", "pending"); //mais de um valor para status

        //com Map Object
        Map<String, Object> queryParmsMap = new HashMap<String, Object>();
        queryParmsMap.put("status", "available");
        queryParmsMap.put("tag", "amarelo");

        given().queryParams(queryParmsMap);
    }

    @Test
    public void testeComFormParameter(){
        given().header("content-type", "multpart/formdata")
                .formParam("chave", "valor");

        given().header("content-type", "multpart/formdata")
                .multiPart("chave", "valor");

        given().header("content-type", "multpart/formdata")
                .multiPart("arquivo", new File("src/test/java/org/example/AppTest.java"));//upload de arquivo
    }

    @Test
    public void testeComBodyParameterEmObjetoJava(){
        Pet pet = new Pet();
        pet.setId(99998);

        Category category = new Category();
        category.setId(99998);
        category.setName("felino");

        pet.setCategory(category);
        pet.setName("Shepherd");

        String[] photoUrls = new String[]{"http://fotosdegato.com.br/foto1.png","http://fotosdegato.com.br/foto2.png"};
        pet.setPhotoUrls(photoUrls);

        Tag tag1 = new Tag();
        tag1.setId(99998);
        tag1.setName("Sem raça definida");

        Tag tag2 = new Tag();
        tag2.setId(99999);
        tag2.setName("Amarelo");

        Tag[] tags = new Tag[]{tag1, tag2};
        pet.setTags(tags);

        pet.setStatus("available");

        given().body(pet);
    }

    @Test
    public void serialziacaoComObjetoJsonSimple(){
        JSONObject pet = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject tag1 = new JSONObject();
        JSONObject tag2 = new JSONObject();
        JSONArray photoUrls = new JSONArray();
        JSONArray tags = new JSONArray();

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

        given().body(pet);
    }

    @Test
    public void serializacaoAtravesDeString(){
       String jsonBody = "{\n" +
               "  \"id\": 99998,\n" +
               "  \"category\": {\n" +
               "    \"id\": 99998,\n" +
               "    \"name\": \"felino\"\n" +
               "  },\n" +
               "  \"name\": \"Shepherd\",\n" +
               "  \"photoUrls\": [\n" +
               "    \"http://fotosdegato.com.br/foto1.png\",\n" +
               "    \"http://fotosdegato.com.br/foto2.png\"\n" +
               "  ],\n" +
               "  \"tags\": [\n" +
               "    {\n" +
               "      \"id\": 99998,\n" +
               "      \"name\": \"Sem raça definida\"\n" +
               "    },\n" +
               "    {\n" +
               "      \"id\": 99999,\n" +
               "      \"name\": \"Amarelo\"\n" +
               "    }\n" +
               "  ],\n" +
               "  \"status\": \"available\"\n" +
               "}";
       given().body(jsonBody);
    }
}
