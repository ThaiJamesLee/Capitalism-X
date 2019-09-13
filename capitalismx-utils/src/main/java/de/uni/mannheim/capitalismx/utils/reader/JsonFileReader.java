package de.uni.mannheim.capitalismx.utils.reader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonFileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonFileReader.class);

    /**
     * Reads the generated_users.json from resources.
     * @return Returns the json files content as a String.
     */
    public String readJsonFileFromResource() {
        StringBuilder sb = new StringBuilder();
        File f = new File(getClass().getClassLoader().getResource("generated_users.json").getFile());

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

    public List<String> parseJsonArrayToStringList(String json){
        List<String> entries = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonArray array = (JsonArray)parser.parse(json);
        for(JsonElement entry : array) {
            System.out.println(entry.toString());
            entries.add(entry.toString());
        }
        return entries;
    }

    public static void main(String[] args) {
        new JsonFileReader().parseJsonArrayToStringList(new JsonFileReader().readJsonFileFromResource());
    }
}
