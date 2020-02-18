package de.uni.mannheim.capitalismx.marketing.department.skill;

import java.util.Locale;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;

public class MarketingSkill implements DepartmentSkill {

    private int level;
    //private int newWarehouseSlots;
    
    private static final String LANG_PROPERTIES = "marketing-module";
    private static final String DESCRIPTION_PROPERTY_PREFIX = "marketing.skill.description.";

    public MarketingSkill(int level) {
        this.level = level;
        //this.newWarehouseSlots = newWarehouseSlots;
    }

    public int getLevel() {
        return level;
    }

//    public int getNewWarehouseSlots() {
//        return newWarehouseSlots;
//    }

    @Override
    public String getDescription() {
        ResourceBundle langBundle = ResourceBundle.getBundle(LANG_PROPERTIES);
        return langBundle.getString(DESCRIPTION_PROPERTY_PREFIX + level);
    }

    @Override
    public String getDescription(Locale l) {
        ResourceBundle langBundle = ResourceBundle.getBundle(LANG_PROPERTIES, l);
        return langBundle.getString(DESCRIPTION_PROPERTY_PREFIX + level);
    }
}
