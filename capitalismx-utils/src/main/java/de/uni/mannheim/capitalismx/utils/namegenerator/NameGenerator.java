package de.uni.mannheim.capitalismx.utils.namegenerator;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.uni.mannheim.capitalismx.utils.data.LocationData;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author duly
 */
public class NameGenerator {

    private static final Logger logger = LoggerFactory.getLogger(NameGenerator.class);

    public NameGenerator() {}

    /**
     * Makes a get request to https://randomuser.me/api/
     * @return Returns a json String containing some fakedata
     * @throws IOException throws Exception if no connection, or URL not resolvable.
     */
    private String getGeneratedUser() throws IOException {
        String userJson = "";

        URL urlForGetRequest = new URL("https://randomuser.me/api/");

        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        int responseCode = conection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuilder response = new StringBuilder();
            while ((readLine = in .readLine()) != null) {
                response.append(readLine);
            } in .close();
            userJson = response.toString();
        } else {
            logger.error("GET NOT WORKED");
        }
        return userJson;
    }

    /**
     * Gets the Fake Person Data from API and parse the Json.
     * @return Returns a PersonMeta object.
     */
    public PersonMeta getGeneratedPersonMeta() {
        PersonMeta pm = null;
        try {
            String jsonData = getGeneratedUser();

            //json string to object conversion
            JsonParser parser = new JsonParser();
            JsonElement ele = parser.parse(jsonData);
            JsonObject jsonObject = ele.getAsJsonObject();

            JsonArray results = jsonObject.getAsJsonArray("results");

            JsonElement jname = results.get(0).getAsJsonObject().get("name");
            JsonElement jgender = results.get(0).getAsJsonObject().get("gender");

            String firstName = toName(jname.getAsJsonObject().get("first").getAsString());

            String lastName = toName(jname.getAsJsonObject().get("last").getAsString());

            String title = toName(jname.getAsJsonObject().get("title").getAsString());

            String eMail = results.get(0).getAsJsonObject().get("email").getAsString();

            String gender = jgender.toString();

            JsonElement jLoc = results.get(0).getAsJsonObject().get("location");

            String street = jLoc.getAsJsonObject().get("street").getAsString();
            String city = toName(jLoc.getAsJsonObject().get("city").getAsString());
            int postcode = Integer.parseInt(jLoc.getAsJsonObject().get("postcode").toString());

            LocationData ld = new LocationData(postcode, street, city);

            pm = new PersonMeta().createPersonMeta(firstName, lastName, gender, title, eMail);
            pm.setLocData(ld);
        } catch (IOException e) {
           logger.error(e.getMessage());
        }
        return pm;
    }


    public String toName(String name) {
        String letter = name.substring(0, 1).toUpperCase();
        return letter + name.substring(1);
    }

}
