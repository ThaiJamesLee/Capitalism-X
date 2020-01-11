package de.uni.mannheim.capitalismx.hr.domain;

/**
 * Define the employee tiers.
 *
 * @author duly
 *
 * @since 1.0.0
 */
public enum EmployeeTier {

    TIER_1("worker", 1, 20),
    TIER_2("student", 2, 30),
    TIER_3("graduate", 3, 40),
    TIER_4("specialist", 4, 60),
    TIER_5("expert", 5, 80);

    /**
     * Name of the {@link EmployeeTier}.
     */
    private String name;
    private int initLevel;
    private int tier;

    EmployeeTier(String name, int tier, int initLevel) {
        this.name = name;
        this.initLevel = initLevel;
        this.tier = tier;
    }

    /**
     *
     * @return Returns the {@link EmployeeTier#initLevel}.
     */
    public int getInitLevel() {
        return initLevel;
    }

    /**
     *
     * @return Returns the {@link EmployeeTier#tier}.
     */
    public int getTier() {
        return tier;
    }

    /**
     *
     * @return Returns the {@link EmployeeTier#name}.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return Returns the {@link EmployeeTier#name}.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     *
     * @param name Name of the tier.
     * @return Return the {@link EmployeeTier} with the matching name.
     */
    public static EmployeeTier getEmployeeTierByName(String name) {
        EmployeeTier[] employeeTiers = EmployeeTier.values();
        for(EmployeeTier employeeTier : employeeTiers) {
            if(employeeTier.getName().equals(name)) {
                return employeeTier;
            }
        }
        return null;
    }
}
