package de.uni.mannheim.capitalismx.utils.data;

/**
 * This class contains is a POJO holding meta data of a person.
 * @author duly
 */
public class PersonMeta {

    private String firstName;

    private String lastName;

    private String gender;

    private String title;

    private String eMail;

    private LocationData locData;

    public PersonMeta(){}

    private PersonMeta(String firstName, String lastName, String gender, String title, String eMail) {
        this.eMail = eMail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.gender = gender;

        this.locData = null;
    }

    /**
     * Creates an instance of PersonMeta
     * @param firstName
     * @param lastName
     * @param gender
     * @param title
     * @param eMail
     * @return Returns a PersonMeta object.
     */
    public PersonMeta createPersonMeta(String firstName, String lastName, String gender, String title, String eMail) {
        return new PersonMeta(firstName, lastName, gender, title, eMail);
    }

    /**
     *
     * @param locData
     */
    public void setLocData(LocationData locData) {
        this.locData = locData;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getTitle() {
        return title;
    }

    public String geteMail() {
        return eMail;
    }

    public LocationData getLocData() {
        return locData;
    }
}
