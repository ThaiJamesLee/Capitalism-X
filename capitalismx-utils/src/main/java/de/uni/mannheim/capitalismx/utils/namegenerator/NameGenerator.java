package de.uni.mannheim.capitalismx.utils.namegenerator;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.uni.mannheim.capitalismx.utils.adapter.ServiceHandler;
import de.uni.mannheim.capitalismx.utils.data.LocationData;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;
import de.uni.mannheim.capitalismx.utils.exception.NoServiceAvailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

/**
 * The NameGenerator contains functions to make an API-call and generates random persons.
 * @author duly
 */
public class NameGenerator {

    private static final Logger logger = LoggerFactory.getLogger(NameGenerator.class);

    private static NameGenerator instance;

    private static final String API_1 = "https://uinames.com";
    private static final String API_2 = "https://randomuser.me";

    private NameGenerator() {}

    public static NameGenerator getInstance() {
        if(instance == null) {
            instance = new NameGenerator();
        }
        return instance;
    }

    /**
     * Makes a get request to https://randomuser.me/api/
     * @return Returns a json String containing some fakedata
     * @throws IOException throws Exception if no connection, or URL not resolvable.
     */
    private String getGeneratedUser(String apiUrl) throws IOException {
        String userJson = "";

        URL urlForGetRequest = new URL(apiUrl);

        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in .close();
            userJson = response.toString();
        } else {
            logger.error("GET NOT WORKED");
        }
        return userJson;
    }

    /**
     * Gets the Fake Person Data from API and parse the Json.
     * @return Returns a {@link PersonMeta} object.
     */
    public PersonMeta getGeneratedPersonMeta() {
        PersonMeta pm = null;

        try {
            if(ServiceHandler.getInstance().hostIsReachable(API_1)) {
                String apiUrl = API_1 + "/api/?region=united%20states&ext";
                String jsonData = getGeneratedUser(apiUrl);
                pm = parseAPI1(jsonData);
            } else if(ServiceHandler.getInstance().hostIsReachable(API_2)) {
                String apiUrl = API_2 + "/api";
                String jsonData = getGeneratedUser(apiUrl);
                pm = parseAPI2(jsonData);
            } else {
                throw new NoServiceAvailableException("All services are down:\n" + API_1 + "\n" + API_2);
            }
        } catch (IOException e) {
           logger.error(e.getMessage());
        }
        return pm;
    }

    private PersonMeta parseAPI1(String json) {
        System.out.println(json);
        //json string to object conversion
        JsonParser parser = new JsonParser();
        JsonElement ele = parser.parse(json);
        JsonObject jsonObject = ele.getAsJsonObject();

        String jname = jsonObject.get("name").getAsString();
        String jsurname = jsonObject.get("surname").getAsString();
        String gender = jsonObject.get("gender").getAsString();
        String title = jsonObject.get("title").getAsString();
        String eMail = jsonObject.get("email").getAsString();

        return new PersonMeta().createPersonMeta(jname, jsurname, gender, title, eMail);
    }

    private PersonMeta parseAPI2(String json) {
        //json string to object conversion
        JsonParser parser = new JsonParser();
        JsonElement ele = parser.parse(json);
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
        String postcode = jLoc.getAsJsonObject().get("postcode").toString();

        LocationData ld = new LocationData(postcode, street, city);

        PersonMeta pm = new PersonMeta().createPersonMeta(firstName, lastName, gender, title, eMail);
        pm.setLocData(ld);

        return pm;
    }


    /**
     * Converts a String by replacing the first character to uppercase.
     * @param name The String that should be converted.
     * @return Returns the converted String.
     */
    public String toName(String name) {
        String letter = name.substring(0, 1).toUpperCase();
        return letter + name.substring(1);
    }

    public static void main(String[] args) {
        try {
            NameGenerator ng = new NameGenerator();

            System.out.println(ng.parseAPI1(ng.getGeneratedUser(API_1+"/api/?region=united%20states&ext")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
