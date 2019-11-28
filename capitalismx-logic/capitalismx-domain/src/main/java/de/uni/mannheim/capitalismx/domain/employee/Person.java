package de.uni.mannheim.capitalismx.domain.employee;

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

    String getGender();
    String getFirstName();
    String getLastName();

}
