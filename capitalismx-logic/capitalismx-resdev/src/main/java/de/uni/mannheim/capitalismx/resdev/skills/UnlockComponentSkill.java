package de.uni.mannheim.capitalismx.resdev.skills;

import de.uni.mannheim.capitalismx.procurement.component.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Skill to unlock component.
 * Set the unlocked components, when this skill is unlocked and the current year is fulfilled.
 */
public class UnlockComponentSkill extends ResDevSkillImpl {

    private List<Component> unlockedComponents;

    private String description;

    public UnlockComponentSkill(int level, int year, double cost) {
        super(level, year, cost);
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
        return description;
    }

    @Override
    public String getDescription(Locale l) {
        return null;
    }
}
