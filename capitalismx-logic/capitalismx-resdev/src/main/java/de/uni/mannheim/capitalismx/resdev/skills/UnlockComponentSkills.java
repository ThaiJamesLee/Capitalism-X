package de.uni.mannheim.capitalismx.resdev.skills;

/**
 *
 * Definition of skills to unlock components in the ResearchAndDevelopmentDepartment.
 * @author duly
 *
 * @since 1.0.0
 */
public enum UnlockComponentSkills {

    /* TODO balance cost and level */
    LEVEL_1(new UnlockComponentSkill(1, 1990, 50000)),
    LEVEL_2(new UnlockComponentSkill(2, 1995, 100000)),
    LEVEL_3(new UnlockComponentSkill(3, 1997, 200000)),
    LEVEL_4(new UnlockComponentSkill(4, 2000, 500000)),
    LEVEL_5(new UnlockComponentSkill(5, 2002, 1000000)),
    LEVEL_6(new UnlockComponentSkill(6, 2005, 1500000)),
    LEVEL_7(new UnlockComponentSkill(7, 2008, 5000000)),
    LEVEL_8(new UnlockComponentSkill(8, 2012, 10000000));

    private UnlockComponentSkill skill;
    private String description;

    private static final String DEFAULT_DESCRIPTION = "Unlock Components skill.";

    /* Constructor */
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
        for(UnlockComponentSkills uSkill : skills) {

            if(uSkill.getSkill().getYear() == year) {
                return uSkill;
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
        for(UnlockComponentSkills uSkill : skills) {

            if(uSkill.getSkill().getLevel() == level) {
                return uSkill;
            }
        }
        return null;
    }

    /**
     *
     * @return Returns true, if this skill is the maximum level.
     */
    public boolean isMaxLevel() {
        UnlockComponentSkills[] skills = UnlockComponentSkills.values();
        for (UnlockComponentSkills uSkill : skills) {
            if(this.getSkill().getLevel() < uSkill.getSkill().getLevel()) {
                return false;
            }
        }
        return true;
    }
}
