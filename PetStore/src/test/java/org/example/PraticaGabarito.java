package org.example;

import Relatorio.Relatorio;
import Utils.Utils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.*;

import static Utils.Constants.UrlBase;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PraticaGabarito extends Relatorio {

    public PraticaGabarito(){
        enableLoggingOfRequestAndResponseIfValidationFails();
        baseURI = UrlBase;
    }

    @Test
    public void cadastrarNovoPedidoDePetComSucesso(){
        //Parâmetros
        int id = Utils.getRandomNumber(5);
        int petId = 99998;
        int quantity = 1;
        String shipDate = Utils.getNowDate("yyyy-MM-dd");
        String status = "placed";
        boolean complete = true;
        Order order = new Order(id, petId, quantity, shipDate, status, complete);
        int statusCodeEsperado = 200;

        TEST.log(Status.INFO, "agora vou executar o teste para o pet de id: "+petId);
        //Teste
        given()
                .basePath("/store/order")
                .header("content-type", "application/json")
                .body(order)
        .when()
                .post()
        .then()
                .statusCode(statusCodeEsperado)
                .body("id", equalTo(id),
                        "petId", equalTo(petId),
                        "quantity", equalTo(quantity),
                        "shipDate", containsString(shipDate),
                        "status", equalTo(status),
                        "complete", equalTo(complete));
    }

    @Test
    public void pesquisarPorPetInexistente(){
        //Parâmetros
        int petId = 595751;
        int statusCodeEsperado = 404;
        String typeEsperado = "error";
        String messageEperada = "Pet not found";

        //Teste
        given()
                .basePath("/pet/{petId}")
                .pathParam("petId", petId)
        .when()
                .get()
        .then()
                .statusCode(statusCodeEsperado)
                .body("type", equalTo(typeEsperado),
                        "message", equalTo(messageEperada));
    }

    @Test
    public void atualizarDadosDeUmPetExistente(){
        //Parâmetros
        int petId = 88888;
        int categoryId = 77777;
        String categoryName = "felino atualizado";
        String name = "Teló atualizado";
        String photoURL = "http://fotodegato.com/telo_atualizado";
        int tagId = 77777;
        String tagName = "persa atualizado";
        String status = "pending";
        Pet pet = new Pet(petId,
                new Category(categoryId, categoryName),
                name,
                new String[]{photoURL},
                new Tag[]{new Tag(tagId, tagName)},
                status);
        int tamanhoPhotoUrlsEsperado = 1;
        int tamanhoTagsEsperado = 1;

        //Teste
        given()
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet)
        .when()
                .put()
        .then()
                .statusCode(200)
                .body("id", equalTo(petId),
                        "category.id", equalTo(categoryId),
                        "category.name", equalTo(categoryName),
                        "name", equalTo(name),
                        "photoUrls", hasSize(tamanhoPhotoUrlsEsperado),
                        "photoUrls[0]", equalTo(photoURL),
                        "tags", hasSize(tamanhoTagsEsperado),
                        "tags[0].id", equalTo(tagId),
                        "tags[0].name", equalTo(tagName),
                        "status", equalTo(status));
    }

    @Test
    public void atualizarDadosDeUmPetInformandoIdFormatoInvalido(){
        //Parâmetros
        String petId = "teste";
        int categoryId = 77777;
        String categoryName = "felino atualizado";
        String name = "Teló atualizado";
        String photoURL = "http://fotodegato.com/telo_atualizado";
        int tagId = 77777;
        String tagName = "persa atualizado";
        String status = "pending";

        //Serializacao
        JSONObject pet = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject tag = new JSONObject();
        JSONArray photoUrls = new JSONArray();
        JSONArray tags = new JSONArray();

        pet.put("id", petId);

        category.put("id", categoryId);
        category.put("name", categoryName);
        pet.put("category", category);

        pet.put("name", name);

        photoUrls.add(photoURL);
        pet.put("photoUrls", photoUrls);

        tag.put("id", tagId);
        tag.put("name", tagName);
        tags.add(tag);
        pet.put("tags", tags);

        pet.put("status", "available");

        //Teste
        given()
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet)
        .when()
                .put()
        .then()
                .statusCode(anyOf(equalTo(400), equalTo(406), equalTo(403)));
    }

    @Test
    public void pesquisarPorPetsComStatusPending(){
        //Parâmetros
        String status = "available";
        int statusCodeEsperado = 200;

        //Teste
        given()
                .basePath("/pet/findByStatus")
                .queryParam("status", status)
        .when()
                .get()
        .then()
                .statusCode(statusCodeEsperado)
                .body("status", everyItem(equalToIgnoringCase(status)));
    }
    //TODO:verificar o pq de não dar erro quando o array de response está vazio

    @Test
    public void realizarRequisicaoPetInformandoMetodoInvalido(){
        //Parâmetros
        int statusCodeEsperado = 405;

        //Teste
        given()
                .basePath("/pet")
        .when()
                .patch()
        .then()
                .statusCode(statusCodeEsperado);
    }
}
