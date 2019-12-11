package de.uni.mannheim.capitalismx.resdev.skills;

/**
 *
 * Definition of skills to unlock components in the ResearchAndDevelopmentDepartment.
 * @author duly
 *
 * @since 1.0.0
 */
public enum UnlockComponentSkills {


    LEVEL_1(new UnlockComponentSkill(1, 1990)),
    LEVEL_2(new UnlockComponentSkill(1, 1990));

    private UnlockComponentSkill skill;
    private String description;

    private static final String DEFAULT_DESCRIPTION = "Unlock Components skill.";

    UnlockComponentSkills(UnlockComponentSkill skill) {
        this.skill = skill;
    }

    public UnlockComponentSkill getSkill() {
        return skill;
    }

    /**
     *
     * @param year The skill that is bound to a specific year.
     * @return Returns the skill with the year. Returns null if no skill exists.
     */
    public UnlockComponentSkills getSkillByYear(int year) {
        UnlockComponentSkills[] skills = UnlockComponentSkills.values();
        for(UnlockComponentSkills skill : skills) {

            if(skill.getSkill().getYear() == year) {
                return skill;
            }
        }
        return null;
    }

    /**
     *
     * @param level The skill that is bound to a specific level.
     * @return Returns the skill with the level. Returns null if no skill exists.
     */
    public UnlockComponentSkills getSkillByLevel(int level) {
        UnlockComponentSkills[] skills = UnlockComponentSkills.values();
        for(UnlockComponentSkills skill : skills) {

            if(skill.getSkill().getLevel() == level) {
                return skill;
            }
        }
        return null;
    }
}
