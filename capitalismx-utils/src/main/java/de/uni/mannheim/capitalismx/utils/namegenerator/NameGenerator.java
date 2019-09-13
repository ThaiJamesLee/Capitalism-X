package de.uni.mannheim.capitalismx.utils.namegenerator;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import de.uni.mannheim.capitalismx.utils.adapter.ServiceAdapter;
import de.uni.mannheim.capitalismx.utils.adapter.ServiceHandler;
import de.uni.mannheim.capitalismx.utils.data.LocationData;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;
import de.uni.mannheim.capitalismx.utils.exception.NoServiceAvailableException;
import de.uni.mannheim.capitalismx.utils.reader.JsonFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The NameGenerator contains functions to make an API-call and generates random persons.
 * @author duly
 */
public class NameGenerator {

    private static final Logger logger = LoggerFactory.getLogger(NameGenerator.class);

    private static NameGenerator instance;

    private String[] api1 = {"https://uinames.com", "/api/?region=united%20states&ext"};
    private String[] api2 = {"https://randomuser.me", "/api"};

    private ServiceAdapter adapter;

    private NameGenerator() {
        adapter = new ServiceAdapter();
    }

    public static NameGenerator getInstance() {
        if(instance == null) {
            instance = new NameGenerator();
        }
        return instance;
    }

    /**
     * Gets the Fake Person Data from API and parse the Json.
     * @return Returns a {@link PersonMeta} List.
     */
    public List<PersonMeta> getGeneratedPersonMeta(int samplesize) {
        List<PersonMeta> pm = new ArrayList<>();

        try {
            if(ServiceHandler.getInstance().hostIsReachable(api1[0]) || ServiceHandler.getInstance().hostIsReachable(api2[0])) {
                String jsonData = adapter.getGeneratedUser(api1[0] + api1[1] + "&amount="+samplesize);
                // if api1 return null json then use api2
                if(jsonData != null) {
                    if(samplesize <= 1) {
                        //if sample size = 1, then parse directly
                        pm.add(parseAPI1(jsonData));
                    } else {
                        //if sample size > 1, then parse the json array first
                        List<String> jsonList = new JsonFileReader().parseJsonArrayToStringList(jsonData);
                        for(String json : jsonList) {
                            pm.add(parseAPI1(json));
                        }
                    }
                } else {
                    //iterate samplesize times and create personmeta
                    for (int i = 0; i < samplesize; i++) {
                        String json = adapter.getGeneratedUser(api2[0] + api2[1]);
                        pm.add(parseAPI2(json));
                    }
                }
            } else {
                throw new NoServiceAvailableException("All services are down:\n" + api1[0] + "\n" + api2[0]);
            }
        } catch (IOException e) {
           logger.error(e.getMessage());
        }
        return pm;
    }

    /**
     * Parse the json of https://uinames.com/api/?ext
     * @param json one person entity of json.
     * @return Returns parsed json as PersonMeta
     */
    public PersonMeta parseAPI1(String json) {
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

    /**
     * Parse the json of https://randomuser.me
     * @param json one person entity of json.
     * @return Returns parsed json as PersonMeta
     */
    public PersonMeta parseAPI2(String json) {
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


    public String[] getApi1() {
        return api1;
    }

    public String[] getApi2() {
        return api2;
    }

    public void setAdapter(ServiceAdapter adapter) {
        this.adapter = adapter;
    }
}
