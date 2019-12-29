package de.uni.mannheim.capitalismx.resdev.skills;

import de.uni.mannheim.capitalismx.procurement.component.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Skill to unlock component.
 */
public class UnlockComponentSkill extends ResDevSkillImpl {

    private List<Component> unlockedComponents;

    public UnlockComponentSkill(int level, int year) {
        super(level, year);
        unlockedComponents = new ArrayList<>();
    }

    public List<Component> getUnlockedComponents() {
        return unlockedComponents;
    }

    public void setUnlockedComponents(List<Component> unlockedComponents) {
        this.unlockedComponents = unlockedComponents;
    }
}
