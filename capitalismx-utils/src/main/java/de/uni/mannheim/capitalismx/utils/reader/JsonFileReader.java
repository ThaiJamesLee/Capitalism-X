package de.uni.mannheim.capitalismx.utils.reader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Resources folder contains a json with 500 user names.
 * This class reads and parses it.
 * @author duly
 */
public class JsonFileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonFileReader.class);

    private String fileNameUS = "generated_users.json";
    private String fileNameBE = "generated_users_be.json";
    private String fileNameDE = "generated_users_de.json";


    /**
     * Reads the generated_users.json from resources. Only works in its own maven module, but not outside.
     * @return Returns the json files content as a String.
     */
    public String readJsonFileFromResource(String jsonFile) {
        StringBuilder sb = new StringBuilder();
        File f = new File(getClass().getClassLoader().getResource(jsonFile).getFile());

        try(BufferedReader br = new BufferedReader(new FileReader(f))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                sb.append(sCurrentLine);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        return sb.toString();
    }

    /**
     * Read json file from resource folder. This method is compatible for unit testing.
     * @param jsonFile the .json file that is located at src/main/resources.
     * @return Returns json as a string.
     */
    public String readJsonFileFromResourceAsStream(String jsonFile) {
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(jsonFile)) {

            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            for (String line; (line = reader.readLine()) != null;) {
                sb.append(line);
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return sb.toString();
    }

    /**
     * Parses the json array and create String list of jsons.
     * @param json A json array.
     * @return Returns a list of strings. Each string element is a person as json.
     */
    public List<String> parseJsonArrayToStringList(String json){
        List<String> entries = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonArray array = (JsonArray)parser.parse(json);
        for(JsonElement entry : array) {
            entries.add(entry.toString());
        }
        return entries;
    }

    public String getFileNameBE() {
        return fileNameBE;
    }

    public String getFileNameDE() {
        return fileNameDE;
    }

    public String getFileNameUS() {
        return fileNameUS;
    }
}
