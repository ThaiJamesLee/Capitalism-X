package de.uni.mannheim.capitalismx.domain.department;

import java.io.Serializable;

/**
 * An interface for skills a department can unlock when leveling up.
 * @author duly
 *
 * @since 1.0.0
 */
public interface DepartmentSkill extends Serializable {

    /**
     *
     * @return Returns a description String for the skill.
     */
    String getDescription();
}
