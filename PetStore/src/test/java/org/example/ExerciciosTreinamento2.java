package org.example;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class ExerciciosTreinamento2 extends org.example.Relatorio{

    @DataProvider(name = "dataUsertObjectProvider")
    public Object[] dataUsuarioProvider() {
        Usuario Usuario1 = new Usuario(01, "Bruno", "andrade", "nogueira", "bruno.andrade@base2.com.br",
                "senha", "37991412535", 1
        );

        Usuario Usuario2 = new Usuario(02, "Bruno2", "andrade2", "nogueira2",
                "bruno.andrade2@base2.com.br", "senha2", "31991412535", 1

        );

        Usuario Usuario3 = new Usuario(03, "Bruno3", "andrade3", "nogueira3",
                "bruno.andrade3@base2.com.br", "senha3", "35991412535", 1

        );

        return new Usuario[]{Usuario1, Usuario2, Usuario3};
    }

    @Test(dataProvider = "dataUsertObjectProvider")
    public void cadastroUsuariosDataDriving(Usuario novoUsuario) {
        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/user")
                .header("content-type", "application/json")
                .body(novoUsuario)
                .when()
                .post()
                .then()
                .statusCode(200);
    }


}