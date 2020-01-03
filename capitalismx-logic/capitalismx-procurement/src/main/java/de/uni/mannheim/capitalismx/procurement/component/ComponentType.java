package de.uni.mannheim.capitalismx.procurement.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public enum ComponentType implements Serializable {

    DUMMY(ComponentCategory.DUMMY, "Dummy Component", 1, 0, 0, 1990),

    N_CPU_LEVEL_1 (ComponentCategory.N_CPU,"Pentium Processor",1,550, 30, 1990),
    N_CPU_LEVEL_2 (ComponentCategory.N_CPU,"Pentium Pro",2,710, 50, 1995),
    N_CPU_LEVEL_3 (ComponentCategory.N_CPU,"Pentium II",3,570, 60, 1997),
    N_CPU_LEVEL_4 (ComponentCategory.N_CPU,"Pentium III",4,430, 80, 2000),
    N_CPU_LEVEL_5 (ComponentCategory.N_CPU,"Pentium IV",5,610, 100, 2002),
    N_CPU_LEVEL_6 (ComponentCategory.N_CPU,"Pentium M",6,680, 120, 2005),
    N_CPU_LEVEL_7 (ComponentCategory.N_CPU,"Pentium Core",7,435, 150, 2008),
    N_CPU_LEVEL_8 (ComponentCategory.N_CPU,"Pentium Core 2",8,540, 200, 2012),

    N_DISPLAYCASE_LEVEL_1 (ComponentCategory.N_DISPLAYCASE, "Tube one", 1, 300, 20, 1990),
    N_DISPLAYCASE_LEVEL_2 (ComponentCategory.N_DISPLAYCASE, "Tube two", 2, 390, 40, 1995),
    N_DISPLAYCASE_LEVEL_3 (ComponentCategory.N_DISPLAYCASE, "Plasma", 3, 310, 80, 2003),
    N_DISPLAYCASE_LEVEL_4 (ComponentCategory.N_DISPLAYCASE, "LCD", 4, 235, 100, 2008),
    N_DISPLAYCASE_LEVEL_5 (ComponentCategory.N_DISPLAYCASE, "OLED", 5, 350, 120, 2013),

    N_SOFTWARE_LEVEL_1 (ComponentCategory.N_SOFTWARE, "WAC OS X", 1, 250, 20, 1990),
    N_SOFTWARE_LEVEL_2 (ComponentCategory.N_SOFTWARE, "WAC OS X Panther", 2, 320, 40, 1995),
    N_SOFTWARE_LEVEL_3 (ComponentCategory.N_SOFTWARE, "WAC OS X Tiger", 3, 250, 60, 2000),
    N_SOFTWARE_LEVEL_4 (ComponentCategory.N_SOFTWARE, "WAC OS X Leopard", 4, 190, 80, 2005),
    N_SOFTWARE_LEVEL_5 (ComponentCategory.N_SOFTWARE, "WAC OS X Lion", 5, 280, 110, 2010),
    N_SOFTWARE_LEVEL_6 (ComponentCategory.N_SOFTWARE, "WAC OS X Mountain Lion", 6, 320, 150, 2015),

    N_STORAGE_LEVEL_1 (ComponentCategory.N_STORAGE, "Seagate 2-8 MB", 1, 200, 30, 1990),
    N_STORAGE_LEVEL_2 (ComponentCategory.N_STORAGE, "Seagate 256 MB", 2, 250, 50, 2001),
    N_STORAGE_LEVEL_3 (ComponentCategory.N_STORAGE, "Seagate 1 GB", 3, 190, 70, 2004),
    N_STORAGE_LEVEL_4 (ComponentCategory.N_STORAGE, "Seagate 4 GB", 4, 145, 80, 2006),
    N_STORAGE_LEVEL_5 (ComponentCategory.N_STORAGE, "Seagate 16 GB", 5, 220, 100, 2009),
    N_STORAGE_LEVEL_6 (ComponentCategory.N_STORAGE, "Seagate 64 GB", 6, 250, 130, 2012),
    N_STORAGE_LEVEL_7 (ComponentCategory.N_STORAGE, "Seagate 256 GB", 7, 135, 150, 2015),
    N_STORAGE_LEVEL_8 (ComponentCategory.N_STORAGE, "Seagate 1 TB", 8, 210, 180, 2018),

    N_POWERSUPPLY_LEVEL_1 (ComponentCategory.N_POWERSUPPLY, "Notebook Power Supply", 1, 80, 20, 1990),

    P_POWERSUPPLY_LEVEL_1 (ComponentCategory.P_POWERSUPPLY, "Lithium Cobalt Oxide Battery", 1, 150, 20, 1990),
    P_POWERSUPPLY_LEVEL_2 (ComponentCategory.P_POWERSUPPLY, "NiMH/Li-ion Battery", 2, 180, 50, 1995),
    P_POWERSUPPLY_LEVEL_3 (ComponentCategory.P_POWERSUPPLY, "1,821mAh Li-ion Battery", 3, 16, 100, 2007),

    P_DISPLAYCASE_LEVEL_1 (ComponentCategory.P_DISPLAYCASE, "1.2\" Black-and-White Display", 1, 300, 20, 1990),
    P_DISPLAYCASE_LEVEL_2 (ComponentCategory.P_DISPLAYCASE, "2\" Black-and-White Display", 2, 200, 40, 2000),
    P_DISPLAYCASE_LEVEL_3 (ComponentCategory.P_DISPLAYCASE, "Color Display", 3, 100, 70, 2004),
    P_DISPLAYCASE_LEVEL_4 (ComponentCategory.P_DISPLAYCASE, "Retina Touchscreen", 4, 130, 90, 2007),
    P_DISPLAYCASE_LEVEL_5 (ComponentCategory.P_DISPLAYCASE, "Waterproof Case", 5, 150, 120, 2015),
    P_DISPLAYCASE_LEVEL_6 (ComponentCategory.P_DISPLAYCASE, "OLED Display", 6, 180, 150, 2017),

    P_KEYPAD_LEVEL_1 (ComponentCategory.P_KEYPAD,"Mobile Key Pad", 1, 10, 20, 1990),
    P_KEYPAD_LEVEL_2 (ComponentCategory.P_KEYPAD,"QWERTY Key Pad", 2, 5, 40, 2000),

    P_CPU_LEVEL_1 (ComponentCategory.P_CPU, "CPU 3200", 1, 150, 20, 1990),
    P_CPU_LEVEL_2 (ComponentCategory.P_CPU, "Intel 386", 2, 150, 40, 1995),
    P_CPU_LEVEL_3 (ComponentCategory.P_CPU, "x86-CPU", 3, 100, 50, 1997),
    P_CPU_LEVEL_4 (ComponentCategory.P_CPU, "ARM 1176", 4, 100, 90, 2007),
    P_CPU_LEVEL_5 (ComponentCategory.P_CPU, "8-Core SoC", 5, 80, 120, 2013),
    P_CPU_LEVEL_6 (ComponentCategory.P_CPU, "ARM Cortex-A55", 6, 80, 160, 2017),

    P_CAMERA_LEVEL_1 (ComponentCategory.P_CAMERA, "1.2 MP", 1, 150, 40, 2004),
    P_CAMERA_LEVEL_2 (ComponentCategory.P_CAMERA, "2 MP", 2, 150, 50, 2007),
    P_CAMERA_LEVEL_3 (ComponentCategory.P_CAMERA, "5 MP", 3, 100, 100, 2013),
    P_CAMERA_LEVEL_4 (ComponentCategory.P_CAMERA, "8 MP", 4, 100, 130, 2015),
    P_CAMERA_LEVEL_5 (ComponentCategory.P_CAMERA, "12 MP", 5, 100, 150, 2017),

    P_CONNECTIVITY_LEVEL_1 (ComponentCategory.P_CONNECTIVITY, "GSM / CDMA", 1, 70, 20, 1990),
    P_CONNECTIVITY_LEVEL_2 (ComponentCategory.P_CONNECTIVITY, "Bluetooth 2.1 + EDR", 2, 40, 50, 2007),
    P_CONNECTIVITY_LEVEL_3 (ComponentCategory.P_CONNECTIVITY, "Bluetooth 4.1", 3, 20, 70, 2013),

    G_DISPLAYCASE_LEVEL_1 (ComponentCategory.G_DISPLAYCASE, "Non Color", 1, 10, 30, 1990),
    G_DISPLAYCASE_LEVEL_2 (ComponentCategory.G_DISPLAYCASE, "Color", 2, 22, 50, 1999),
    G_DISPLAYCASE_LEVEL_3 (ComponentCategory.G_DISPLAYCASE, "Touch Screen + Normal", 3, 34, 80, 2004),
    G_DISPLAYCASE_LEVEL_4 (ComponentCategory.G_DISPLAYCASE, "3D Screen + Touch", 4, 30, 120, 2011),

    G_POWERSUPPLY_LEVEL_1 (ComponentCategory.G_POWERSUPPLY, "External", 1, 2, 20, 1990),
    G_POWERSUPPLY_LEVEL_2 (ComponentCategory.G_POWERSUPPLY, "Lithium Ion 300mA", 2, 4, 50, 2003),
    G_POWERSUPPLY_LEVEL_3 (ComponentCategory.G_POWERSUPPLY, "Lithium Ion 800mA", 3, 5, 60, 2004),
    G_POWERSUPPLY_LEVEL_4 (ComponentCategory.G_POWERSUPPLY, "Lithium Ion 1000mA", 4, 8, 85, 2006),
    G_POWERSUPPLY_LEVEL_5 (ComponentCategory.G_POWERSUPPLY, "Lithium Ion 1300mA", 5, 8, 110, 2011),
    G_POWERSUPPLY_LEVEL_6 (ComponentCategory.G_POWERSUPPLY, "Lithium Ion 1700mA", 6, 8, 130, 2015),

    G_CPU_LEVEL_1 (ComponentCategory.G_CPU, "4,19 MHz 8kB RAM", 1, 13, 30, 1990),
    G_CPU_LEVEL_2 (ComponentCategory.G_CPU, "8MHz 32kB RAM", 2, 13, 60, 1996),
    G_CPU_LEVEL_3 (ComponentCategory.G_CPU, "16,77 MHz 32kB RAM", 3, 10, 80, 1999),
    G_CPU_LEVEL_4 (ComponentCategory.G_CPU, "67 MHz 4MB RAM", 4, 10, 100, 2003),
    G_CPU_LEVEL_5 (ComponentCategory.G_CPU, "133 MHz 16 MB RAM", 5, 12, 130, 2008),
    G_CPU_LEVEL_6 (ComponentCategory.G_CPU, "268 MHz 128 MB RAM ", 6, 12, 140, 2011),
    G_CPU_LEVEL_7 (ComponentCategory.G_CPU, "804 MHz 256 MB RAM ", 7, 10, 160, 2015),

    G_CONNECTIVITY_LEVEL_1 (ComponentCategory.G_CONNECTIVITY, "Wire", 1, 2, 20, 1990),
    G_CONNECTIVITY_LEVEL_2 (ComponentCategory.G_CONNECTIVITY, "Wifi", 2, 4, 50, 2001),
    G_CONNECTIVITY_LEVEL_3 (ComponentCategory.G_CONNECTIVITY, "Wifi + NFC", 3, 4, 100, 2013),

    G_CAMERA_LEVEL_1 (ComponentCategory.G_CAMERA, "2 Cameras", 1, 15, 80, 2008),
    G_CAMERA_LEVEL_2 (ComponentCategory.G_CAMERA, "2 inside, 1 outside", 2, 15, 120, 2013),

    T_DISPLAY_LEVEL_1 (ComponentCategory.T_DISPLAY, "20\" Tube", 1, 250, 40, 1990),
    T_DISPLAY_LEVEL_2 (ComponentCategory.T_DISPLAY, "30\" Tube", 2, 200, 60, 1995),
    T_DISPLAY_LEVEL_3 (ComponentCategory.T_DISPLAY, "Plasma 32\"", 3, 230, 90, 2000),
    T_DISPLAY_LEVEL_4 (ComponentCategory.T_DISPLAY, "LCD 42\"", 4, 330, 110, 2005),
    T_DISPLAY_LEVEL_5 (ComponentCategory.T_DISPLAY, " LCD 52", 5, 250, 150, 2010),
    T_DISPLAY_LEVEL_6 (ComponentCategory.T_DISPLAY, " OLED 52\"", 6, 160, 180, 2015),
    /* initialPrice was not defined for T_DISPLAY_LEVEL_7, new value was chosen arbitrarily*/
    T_DISPLAY_LEVEL_7 (ComponentCategory.T_DISPLAY, "OLED 72\"", 7, 140, 210, 2017),

    T_OS_LEVEL_1 (ComponentCategory.T_OS, "2 Channels", 1, 10, 20, 1990),
    T_OS_LEVEL_2 (ComponentCategory.T_OS, "10 Channels", 2, 10, 40, 1995),
    T_OS_LEVEL_3 (ComponentCategory.T_OS, "50 Channels", 3, 10, 80, 2000),
    T_OS_LEVEL_4 (ComponentCategory.T_OS, "100 Channels, Weather App", 4, 15, 110, 2005),
    T_OS_LEVEL_5 (ComponentCategory.T_OS, "Netflix", 5, 10, 150, 2010),
    T_OS_LEVEL_6 (ComponentCategory.T_OS, "Netflix, Live Streaming", 6, 5, 170, 2015),
    /* initialPrice was not defined for T_OS_LEVEL_7, new value was chosen arbitrarily*/
    T_OS_LEVEL_7 (ComponentCategory.T_OS, "Complete Smart TV", 7, 15, 190, 2017),

    T_SOUND_LEVEL_1 (ComponentCategory.T_SOUND, "Mono Sound", 1, 70, 30, 1990),
    T_SOUND_LEVEL_2 (ComponentCategory.T_SOUND, "Stereo Sound", 2, 55, 40, 1995),
    T_SOUND_LEVEL_3 (ComponentCategory.T_SOUND, "Dolby 2.1", 3, 65, 80, 2000),
    T_SOUND_LEVEL_4 (ComponentCategory.T_SOUND, "Dolby 5.1", 4, 100, 100, 2005),
    T_SOUND_LEVEL_5 (ComponentCategory.T_SOUND, "Dolby 7.1", 5, 70, 140, 2010),
    T_SOUND_LEVEL_6 (ComponentCategory.T_SOUND, "Dolby Atmos", 6, 45, 160, 2015),
    /* initialPrice was not defined for T_OS_SOUND_7, new value was chosen arbitrarily*/
    T_SOUND_LEVEL_7 (ComponentCategory.T_SOUND, "Full-Fledged Home Cinema", 7, 100, 180, 2017),

    T_CASE_LEVEL_1 (ComponentCategory.T_CASE, "Box Casing", 1, 40, 201, 1990),
    T_CASE_LEVEL_2 (ComponentCategory.T_CASE, "Box Casing 2.0", 2, 35, 30, 1995),
    T_CASE_LEVEL_3 (ComponentCategory.T_CASE, "Fullscreen", 3, 30, 70, 2000),
    T_CASE_LEVEL_4 (ComponentCategory.T_CASE, "No Borders", 4, 20, 120, 2017);

    private ComponentCategory componentCategory;
    private String componentName;
    private int componentLevel;
    private double initialComponentPrice;
    private int baseUtility;
    private int availabilityDate;
    /*private SupplierCategory supplierCategory;
    private double supplierCostMultiplicator;
    private int supplierQuality;
    private int supplierEcoIndex;
    private double baseCost;*/

    ComponentType(ComponentCategory componentCategory, String componentName, int componentLevel, double initialComponentPrice, int baseUtility, int availabilityDate) {
        this.componentCategory = componentCategory;
        this.componentName = componentName;
        this.componentLevel = componentLevel;
        this.initialComponentPrice = initialComponentPrice;
        this.baseUtility = baseUtility;
        this.availabilityDate = availabilityDate;
    }

/*    Component(ComponentCategory componentCategory, String componentName, int componentLevel, double initialPrice, int baseUtility, int availabilityDate, SupplierCategory supplierCategory) {
        this.componentCategory = componentCategory;
        this.componentName = componentName;
        this.componentLevel = componentLevel;
        this.initialComponentPrice = initialPrice;
        this.baseUtility = baseUtility;
        this.availabilityDate = availabilityDate;
        this.supplierCategory = supplierCategory;
        switch(supplierCategory) {
            case CHEAP:
                *//* Component placeholder for RandomNumberGenerator of utils @sdupper*//*
                this.supplierCostMultiplicator = Component.getRandomDouble(1.1, 1.5);
                this.supplierQuality = Component.getRandomInt(80, 100);
                this.supplierEcoIndex = Component.getRandomInt(80, 100);
                break;
            case REGULAR:
                this.supplierCostMultiplicator = Component.getRandomDouble(0.85, 1.2);
                this.supplierQuality = Component.getRandomInt(55, 85);
                this.supplierEcoIndex = Component.getRandomInt(55, 85);
                break;
            case PREMIUM:
                this.supplierCostMultiplicator = Component.getRandomDouble(0.7, 1.0);
                this.supplierQuality = Component.getRandomInt(10, 60);
                this.supplierEcoIndex = Component.getRandomInt(10, 60);
        }
    }*/

    /*
    public void setSupplierCategory(SupplierCategory supplierCategory) {
        this.supplierCategory = supplierCategory;
        switch(supplierCategory) {
            case PREMIUM:
                this.supplierCostMultiplicator = ComponentType.getRandomDouble(1.1, 1.5);
                this.supplierQuality = ComponentType.getRandomInt(80, 100);
                this.supplierEcoIndex = ComponentType.getRandomInt(80, 100);
                break;
            case REGULAR:
                this.supplierCostMultiplicator = ComponentType.getRandomDouble(0.85, 1.2);
                this.supplierQuality = ComponentType.getRandomInt(55, 85);
                this.supplierEcoIndex = ComponentType.getRandomInt(55, 85);
                break;
            case CHEAP:
                this.supplierCostMultiplicator = ComponentType.getRandomDouble(0.7, 1.0);
                this.supplierQuality = ComponentType.getRandomInt(10, 60);
                this.supplierEcoIndex = ComponentType.getRandomInt(10, 60);
        }
    } */

    public ComponentCategory getComponentCategory() {
        return this.componentCategory;
    }

    public String getComponentName() {
        return this.componentName;
    }

    public int getComponentLevel() {
        return this.componentLevel;
    }

    public double getInitialComponentPrice() {
        return this.initialComponentPrice;
    }

    public int getBaseUtility() {
        return this.baseUtility;
    }

    public int getAvailabilityDate() {
        return this.availabilityDate;
    }
    
    /**
     * Reads the properties file.
     * @param locale the locale of the properties file (English or German).
     * @return Returns the name of the employee type.
     */
    public String getName(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("procurement", locale);
        return bundle.getString(this.name());
    }
    
    /**
	 * Get a {@link List} of {@link ComponentType}s for the given
	 * {@link ComponentCategory}.
	 * 
	 * @param category The {@link ComponentCategory} to find types for.
	 * @return List of {@link ComponentType}s, with the given category.
	 */
	public static List<ComponentType> getTypesByCategory(ComponentCategory category) {
		List<ComponentType> types = new ArrayList<ComponentType>();
		for (ComponentType type : ComponentType.values()) {
			if (type.componentCategory == category) {
				types.add(type);
			}
		}
		return types;
	}

    /*public SupplierCategory getSupplierCategory() {
        return this.supplierCategory;
    }

    public double getSupplierCostMultiplicator() {
        return this.supplierCostMultiplicator;
    }

    public int getSupplierQuality() {
        return this.supplierQuality;
    }

    public int getSupplierEcoIndex() {
        return this.supplierEcoIndex;
    }

    public double getBaseCost() {
        return this.baseCost;
    }*/

    /*
    public double calculateBaseCost(LocalDate gameDate) {
        int gameYear = gameDate.getYear();
        double tBPM = 0.0001 * Math.pow((gameYear - this.availabilityDate + 1), 5)
                - 0.0112 * Math.pow((gameYear - this.availabilityDate + 1), 4)
                - 0.4239 * Math.pow((gameYear - this.availabilityDate + 1), 3)
                + 7.3219 * Math.pow((gameYear - this.availabilityDate + 1), 2)
                - 49.698 * (gameYear - this.availabilityDate + 1)
                + 142.7889;
        double tBCP = this.initialComponentPrice * (tBPM / 100);
        this.baseCost = tBCP * this.supplierCostMultiplicator;
        return this.baseCost;
    } */
}