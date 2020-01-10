package de.uni.mannheim.capitalismx.resdev.skills;

import de.uni.mannheim.capitalismx.procurement.component.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Skill to unlock component.
 * Set the unlocked components, when this skill is unlocked and the current year is fulfilled.
 */
public class UnlockComponentSkill extends ResDevSkillImpl {

    private List<Component> unlockedComponents;

    private String description;

    private int level;

    private static final String PROPERTIES_FILE = "resdev-module";
    private static final String DESCRIPTION_PROPERTY_PREFIX = "resdev.skill.components.description.";

    public UnlockComponentSkill(int level, int year, double cost) {
        super(year, cost);
        this.level = level;
        unlockedComponents = new ArrayList<>();
    }

    public List<Component> getUnlockedComponents() {
        return unlockedComponents;
    }

    public void setUnlockedComponents(List<Component> unlockedComponents) {
        this.unlockedComponents = unlockedComponents;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_FILE);
        return bundle.getString(DESCRIPTION_PROPERTY_PREFIX);
    }

    @Override
    public String getDescription(Locale l) {
        ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_FILE, l);
        return bundle.getString(DESCRIPTION_PROPERTY_PREFIX);
    }

    /**
     *
     * @return Returns the level of the skill.
     */
    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
