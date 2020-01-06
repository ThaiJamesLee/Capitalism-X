package de.uni.mannheim.capitalismx.domain.department;

import java.io.Serializable;
import java.util.Locale;

/**
 * An interface for skills a department can unlock when leveling up.
 * @author duly
 *
 * @since 1.0.0
 */
public interface DepartmentSkill extends Serializable {

    /**
     * @return Returns a description String for the skill.
     */
    String getDescription();

    /**
     * Support for multi-language description.
     * @param l Locale
     * @return Returns a description String for the skill.
     */
    String getDescription(Locale l);
}
