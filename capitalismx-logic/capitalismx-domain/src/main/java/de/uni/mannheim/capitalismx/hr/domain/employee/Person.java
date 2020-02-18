package de.uni.mannheim.capitalismx.hr.domain.employee;

/**
 * Interface with predefined must have function.
 * @author duly
 */
public interface Person {

    /**
     * @return Returns the name.
     */
    String getName();

    /**
     *
     * Some Id.
     * @return Returns an Id.
     */
    String getID();

    /**
     * The gender.
     * @return Return the gender of the person.
     */
    String getGender();

    /**
     * The first name.
     * @return Return the first name of the person.
     */
    String getFirstName();

    /**
     * The last name.
     * @return Return the last name of the person.
     */
    String getLastName();

}
