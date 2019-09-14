package de.uni.mannheim.capitalismx.utils.data;

import java.io.Serializable;

/**
 * @author duly
 */
public class LocationData implements Serializable {

    private String street;
    private String city;
    private String postCode;

    public LocationData(String postCode, String street, String city) {
        this.city = city;
        this.street = street;
        this.postCode = postCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getPostCode() {
        return postCode;
    }

    public String toString() {
        return street + " " + postCode + " " + city;
    }
}
