package de.uni.mannheim.capitalismx.utils.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * This class is for checking if the services are available.
 * @author duly
 */
public class ServiceHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceHandler.class);

    private static ServiceHandler instance;

    private ServiceHandler(){}

    public static ServiceHandler getInstance() {
        if(instance == null) {
            instance = new ServiceHandler();
        }
        return instance;
    }

    /**
     *  Check if the specified host url is reachable.
     * @param url URL of the host or service that needs to be checked.
     * @return Returns true if we receive a 200 response code from the host. Otherwise return false.
     */
    public boolean hostIsReachable(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
