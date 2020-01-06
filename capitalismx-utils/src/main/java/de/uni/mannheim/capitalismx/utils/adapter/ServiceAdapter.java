package de.uni.mannheim.capitalismx.utils.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * This class is responsible for the API calls.
 * It handles requests and returns the raw json Strings.
 * @author duly
 *
 * @since 1.0.0
 */
public class ServiceAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ServiceAdapter.class);

    /**
     * Makes a GET request to an api service.
     * @return Returns a json String containing some fakedata if request was successful. Return empty String otherwise.
     * @throws IOException throws Exception if no connection, or URL not resolvable.
     */
    public String getGeneratedUser(String apiUrl) throws IOException {
        String userJson = "";

        URL urlForGetRequest = new URL(apiUrl);

        String readLine;
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
            String errorMessage = "Failed GET Request to " + apiUrl;
            logger.error(errorMessage);
        }
        return userJson;
    }

}
