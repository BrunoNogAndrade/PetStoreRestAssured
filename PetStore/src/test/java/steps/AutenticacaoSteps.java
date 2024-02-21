package steps;

import Utils.GlobalParameters;
import requests.GetTokenRequest;
import io.restassured.response.Response;

public class AutenticacaoSteps {
    public static void gerarToken(String usuario, String senha){
        GetTokenRequest getToken = new GetTokenRequest(usuario, senha);
        Response response = getToken.executeRequestNoLog();
        GlobalParameters.setToken(response.body().jsonPath().get("token").toString());
    }
}