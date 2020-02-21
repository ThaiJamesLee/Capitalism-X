package de.uni.mannheim.capitalismx.hr.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Define the employee tiers.
 *
 * @author duly
 *
 * @since 1.0.0
 */
public enum EmployeeTier {

    /**
     * The employee has the skill level of a worker.
     */
    TIER_1("worker", 1, 20),

    /**
     * The employee has the skill level of a student.
     */
    TIER_2("student", 2, 30),

    /**
     * The employee has the skill level of a graduate.
     */
    TIER_3("graduate", 3, 40),

    /**
     * The employee has the skill level of a specialist.
     */
    TIER_4("specialist", 4, 60),

    /**
     * The employee has the skill level of a expert.
     */
    TIER_5("expert", 5, 80);

    /**
     * Name of the {@link EmployeeTier}.
     */
    private String name;
    private int initLevel;
    private int tier;

    /**
     * The constructor.
     * @param name The name of the EmployeeTier.
     * @param tier The tier level in the hierarchy.
     * @param initLevel The initial skill level to be in the employee tier.
     */
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

    /**
     * The list is sorted by the enum definition order.
     * @return Returns a sorted list of all skill level names.
     */
    public static List<String> getSkillLevelNames() {
        EmployeeTier[] employeeTiers = EmployeeTier.values();
        List<String> names = new ArrayList<>();

        for(EmployeeTier e : employeeTiers) {
            names.add(e.getName());
        }
        return names;
    }}
