package MassaDeTeste;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MassaDeTesteCadastroPet {
    JSONObject JsonPet = new JSONObject();

    public MassaDeTesteCadastroPet insereNovoPet() {
        JsonPet.put("id", 666);
        JsonPet.put("petId", 6661);
        JsonPet.put("quantity", 5);
        JsonPet.put("shipDate", "2020-06-26T17:48:13.703Z");
        JsonPet.put("status", "pending");
        JsonPet.put("complete", true);
        return this;
    }







}
