package de.uni.mannheim.capitalismx.procurement.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * An enum component type.
 * It basically defines the most important elements of a component.
 * The types were defined by the predecessor group. Some of the initial component prices were not defined by them, so they were
 * chosen based on the other component types of the same component category.
 * The initial sales price was set to half of the initial component price.
 *
 * @author dzhao
 */
public enum ComponentType implements Serializable {

    DUMMY(ComponentCategory.DUMMY, "Dummy Component", 1, 0, 0, 1990, 0),

    N_CPU_LEVEL_1 (ComponentCategory.N_CPU,"Pentium Processor",1,550, 30, 1990, 275),
    N_CPU_LEVEL_2 (ComponentCategory.N_CPU,"Pentium Pro",2,710, 50, 1995, 355),
    N_CPU_LEVEL_3 (ComponentCategory.N_CPU,"Pentium II",3,570, 60, 1997, 385),
    N_CPU_LEVEL_4 (ComponentCategory.N_CPU,"Pentium III",4,430, 80, 2000, 215),
    N_CPU_LEVEL_5 (ComponentCategory.N_CPU,"Pentium IV",5,610, 100, 2002, 305),
    N_CPU_LEVEL_6 (ComponentCategory.N_CPU,"Pentium M",6,680, 120, 2005, 340),
    N_CPU_LEVEL_7 (ComponentCategory.N_CPU,"Pentium Core",7,435, 150, 2008, 217.5),
    N_CPU_LEVEL_8 (ComponentCategory.N_CPU,"Pentium Core 2",8,540, 200, 2012, 270),

    N_DISPLAYCASE_LEVEL_1 (ComponentCategory.N_DISPLAYCASE, "Tube one", 1, 300, 20, 1990, 150),
    N_DISPLAYCASE_LEVEL_2 (ComponentCategory.N_DISPLAYCASE, "Tube two", 2, 390, 40, 1995, 345),
    N_DISPLAYCASE_LEVEL_3 (ComponentCategory.N_DISPLAYCASE, "Plasma", 3, 310, 80, 2003, 155),
    N_DISPLAYCASE_LEVEL_4 (ComponentCategory.N_DISPLAYCASE, "LCD", 4, 235, 100, 2008, 172.5),
    N_DISPLAYCASE_LEVEL_5 (ComponentCategory.N_DISPLAYCASE, "OLED", 5, 350, 120, 2013, 375),

    N_SOFTWARE_LEVEL_1 (ComponentCategory.N_SOFTWARE, "WAC OS X", 1, 250, 20, 1990, 125),
    N_SOFTWARE_LEVEL_2 (ComponentCategory.N_SOFTWARE, "WAC OS X Panther", 2, 320, 40, 1995, 160),
    N_SOFTWARE_LEVEL_3 (ComponentCategory.N_SOFTWARE, "WAC OS X Tiger", 3, 250, 60, 2000, 125),
    N_SOFTWARE_LEVEL_4 (ComponentCategory.N_SOFTWARE, "WAC OS X Leopard", 4, 190, 80, 2005, 95),
    N_SOFTWARE_LEVEL_5 (ComponentCategory.N_SOFTWARE, "WAC OS X Lion", 5, 280, 110, 2010, 140),
    N_SOFTWARE_LEVEL_6 (ComponentCategory.N_SOFTWARE, "WAC OS X Mountain Lion", 6, 320, 150, 2015, 160),

    N_STORAGE_LEVEL_1 (ComponentCategory.N_STORAGE, "Seagate 2-8 MB", 1, 200, 30, 1990, 100),
    N_STORAGE_LEVEL_2 (ComponentCategory.N_STORAGE, "Seagate 256 MB", 2, 250, 50, 2001, 125),
    N_STORAGE_LEVEL_3 (ComponentCategory.N_STORAGE, "Seagate 1 GB", 3, 190, 70, 2004, 95),
    N_STORAGE_LEVEL_4 (ComponentCategory.N_STORAGE, "Seagate 4 GB", 4, 145, 80, 2006, 72.5),
    N_STORAGE_LEVEL_5 (ComponentCategory.N_STORAGE, "Seagate 16 GB", 5, 220, 100, 2009, 110),
    N_STORAGE_LEVEL_6 (ComponentCategory.N_STORAGE, "Seagate 64 GB", 6, 250, 130, 2012, 125),
    N_STORAGE_LEVEL_7 (ComponentCategory.N_STORAGE, "Seagate 256 GB", 7, 135, 150, 2015, 67.5),
    N_STORAGE_LEVEL_8 (ComponentCategory.N_STORAGE, "Seagate 1 TB", 8, 210, 180, 2018, 105),

    N_POWERSUPPLY_LEVEL_1 (ComponentCategory.N_POWERSUPPLY, "Notebook Power Supply", 1, 80, 20, 1990, 40),

    P_POWERSUPPLY_LEVEL_1 (ComponentCategory.P_POWERSUPPLY, "Lithium Cobalt Oxide Battery", 1, 150, 20, 1990, 75),
    P_POWERSUPPLY_LEVEL_2 (ComponentCategory.P_POWERSUPPLY, "NiMH/Li-ion Battery", 2, 180, 50, 1995, 90),
    P_POWERSUPPLY_LEVEL_3 (ComponentCategory.P_POWERSUPPLY, "1,821mAh Li-ion Battery", 3, 16, 100, 2007, 8),

    P_DISPLAYCASE_LEVEL_1 (ComponentCategory.P_DISPLAYCASE, "1.2\" Black-and-White Display", 1, 300, 20, 1990, 150),
    P_DISPLAYCASE_LEVEL_2 (ComponentCategory.P_DISPLAYCASE, "2\" Black-and-White Display", 2, 200, 40, 2000, 100),
    P_DISPLAYCASE_LEVEL_3 (ComponentCategory.P_DISPLAYCASE, "Color Display", 3, 100, 70, 2004, 50),
    P_DISPLAYCASE_LEVEL_4 (ComponentCategory.P_DISPLAYCASE, "Retina Touchscreen", 4, 130, 90, 2007, 65),
    P_DISPLAYCASE_LEVEL_5 (ComponentCategory.P_DISPLAYCASE, "Waterproof Case", 5, 150, 120, 2015, 75),
    P_DISPLAYCASE_LEVEL_6 (ComponentCategory.P_DISPLAYCASE, "OLED Display", 6, 180, 150, 2017, 90),

    P_KEYPAD_LEVEL_1 (ComponentCategory.P_KEYPAD,"Mobile Key Pad", 1, 10, 20, 1990, 5),
    P_KEYPAD_LEVEL_2 (ComponentCategory.P_KEYPAD,"QWERTY Key Pad", 2, 5, 40, 2000, 2.5),

    P_CPU_LEVEL_1 (ComponentCategory.P_CPU, "CPU 3200", 1, 150, 20, 1990, 75),
    P_CPU_LEVEL_2 (ComponentCategory.P_CPU, "Intel 386", 2, 150, 40, 1995, 75),
    P_CPU_LEVEL_3 (ComponentCategory.P_CPU, "x86-CPU", 3, 100, 50, 1997, 50),
    P_CPU_LEVEL_4 (ComponentCategory.P_CPU, "ARM 1176", 4, 100, 90, 2007, 50),
    P_CPU_LEVEL_5 (ComponentCategory.P_CPU, "8-Core SoC", 5, 80, 120, 2013, 40),
    P_CPU_LEVEL_6 (ComponentCategory.P_CPU, "ARM Cortex-A55", 6, 80, 160, 2017, 40),

    P_CAMERA_LEVEL_1 (ComponentCategory.P_CAMERA, "1.2 MP", 1, 150, 40, 2004, 75),
    P_CAMERA_LEVEL_2 (ComponentCategory.P_CAMERA, "2 MP", 2, 150, 50, 2007, 75),
    P_CAMERA_LEVEL_3 (ComponentCategory.P_CAMERA, "5 MP", 3, 100, 100, 2013, 50),
    P_CAMERA_LEVEL_4 (ComponentCategory.P_CAMERA, "8 MP", 4, 100, 130, 2015, 50),
    P_CAMERA_LEVEL_5 (ComponentCategory.P_CAMERA, "12 MP", 5, 100, 150, 2017, 50),

    P_CONNECTIVITY_LEVEL_1 (ComponentCategory.P_CONNECTIVITY, "GSM / CDMA", 1, 70, 20, 1990, 35),
    P_CONNECTIVITY_LEVEL_2 (ComponentCategory.P_CONNECTIVITY, "Bluetooth 2.1 + EDR", 2, 40, 50, 2007, 20),
    P_CONNECTIVITY_LEVEL_3 (ComponentCategory.P_CONNECTIVITY, "Bluetooth 4.1", 3, 20, 70, 2013, 10),

    G_DISPLAYCASE_LEVEL_1 (ComponentCategory.G_DISPLAYCASE, "Non Color", 1, 10, 30, 1990, 5),
    G_DISPLAYCASE_LEVEL_2 (ComponentCategory.G_DISPLAYCASE, "Color", 2, 22, 50, 1999, 11),
    G_DISPLAYCASE_LEVEL_3 (ComponentCategory.G_DISPLAYCASE, "Touch Screen + Normal", 3, 34, 80, 2004, 17),
    G_DISPLAYCASE_LEVEL_4 (ComponentCategory.G_DISPLAYCASE, "3D Screen + Touch", 4, 30, 120, 2011, 15),

    G_POWERSUPPLY_LEVEL_1 (ComponentCategory.G_POWERSUPPLY, "External", 1, 2, 20, 1990, 1),
    G_POWERSUPPLY_LEVEL_2 (ComponentCategory.G_POWERSUPPLY, "Lithium Ion 300mA", 2, 4, 50, 2003, 2),
    G_POWERSUPPLY_LEVEL_3 (ComponentCategory.G_POWERSUPPLY, "Lithium Ion 800mA", 3, 5, 60, 2004, 2.5),
    G_POWERSUPPLY_LEVEL_4 (ComponentCategory.G_POWERSUPPLY, "Lithium Ion 1000mA", 4, 8, 85, 2006, 4),
    G_POWERSUPPLY_LEVEL_5 (ComponentCategory.G_POWERSUPPLY, "Lithium Ion 1300mA", 5, 8, 110, 2011, 4),
    G_POWERSUPPLY_LEVEL_6 (ComponentCategory.G_POWERSUPPLY, "Lithium Ion 1700mA", 6, 8, 130, 2015, 4),

    G_CPU_LEVEL_1 (ComponentCategory.G_CPU, "4,19 MHz 8kB RAM", 1, 13, 30, 1990, 6.5),
    G_CPU_LEVEL_2 (ComponentCategory.G_CPU, "8MHz 32kB RAM", 2, 13, 60, 1996, 6.5),
    G_CPU_LEVEL_3 (ComponentCategory.G_CPU, "16,77 MHz 32kB RAM", 3, 10, 80, 1999, 5),
    G_CPU_LEVEL_4 (ComponentCategory.G_CPU, "67 MHz 4MB RAM", 4, 10, 100, 2003, 5),
    G_CPU_LEVEL_5 (ComponentCategory.G_CPU, "133 MHz 16 MB RAM", 5, 12, 130, 2008,6),
    G_CPU_LEVEL_6 (ComponentCategory.G_CPU, "268 MHz 128 MB RAM ", 6, 12, 140, 2011, 6),
    G_CPU_LEVEL_7 (ComponentCategory.G_CPU, "804 MHz 256 MB RAM ", 7, 10, 160, 2015, 5),

    G_CONNECTIVITY_LEVEL_1 (ComponentCategory.G_CONNECTIVITY, "Wire", 1, 2, 20, 1990, 1),
    G_CONNECTIVITY_LEVEL_2 (ComponentCategory.G_CONNECTIVITY, "Wifi", 2, 4, 50, 2001, 2),
    G_CONNECTIVITY_LEVEL_3 (ComponentCategory.G_CONNECTIVITY, "Wifi + NFC", 3, 4, 100, 2013, 2),

    G_CAMERA_LEVEL_1 (ComponentCategory.G_CAMERA, "2 Cameras", 1, 15, 80, 2008, 7.5),
    G_CAMERA_LEVEL_2 (ComponentCategory.G_CAMERA, "2 inside, 1 outside", 2, 15, 120, 2013, 7.5),

    T_DISPLAY_LEVEL_1 (ComponentCategory.T_DISPLAY, "20\" Tube", 1, 250, 40, 1990, 125),
    T_DISPLAY_LEVEL_2 (ComponentCategory.T_DISPLAY, "30\" Tube", 2, 200, 60, 1995, 100),
    T_DISPLAY_LEVEL_3 (ComponentCategory.T_DISPLAY, "Plasma 32\"", 3, 230, 90, 2000, 115),
    T_DISPLAY_LEVEL_4 (ComponentCategory.T_DISPLAY, "LCD 42\"", 4, 330, 110, 2005, 115),
    T_DISPLAY_LEVEL_5 (ComponentCategory.T_DISPLAY, " LCD 52", 5, 250, 150, 2010, 165),
    T_DISPLAY_LEVEL_6 (ComponentCategory.T_DISPLAY, " OLED 52\"", 6, 160, 180, 2015, 80),
    /* initialPrice was not defined for T_DISPLAY_LEVEL_7, new value was chosen arbitrarily*/
    T_DISPLAY_LEVEL_7 (ComponentCategory.T_DISPLAY, "OLED 72\"", 7, 140, 210, 2017, 70),

    T_OS_LEVEL_1 (ComponentCategory.T_OS, "2 Channels", 1, 10, 20, 1990, 5),
    T_OS_LEVEL_2 (ComponentCategory.T_OS, "10 Channels", 2, 10, 40, 1995, 5),
    T_OS_LEVEL_3 (ComponentCategory.T_OS, "50 Channels", 3, 10, 80, 2000, 5),
    T_OS_LEVEL_4 (ComponentCategory.T_OS, "100 Channels, Weather App", 4, 15, 110, 2005, 7.5),
    T_OS_LEVEL_5 (ComponentCategory.T_OS, "Netflix", 5, 10, 150, 2010, 5),
    T_OS_LEVEL_6 (ComponentCategory.T_OS, "Netflix, Live Streaming", 6, 5, 170, 2015, 2.5),
    /* initialPrice was not defined for T_OS_LEVEL_7, new value was chosen arbitrarily*/
    T_OS_LEVEL_7 (ComponentCategory.T_OS, "Complete Smart TV", 7, 15, 190, 2017, 7.5),

    T_SOUND_LEVEL_1 (ComponentCategory.T_SOUND, "Mono Sound", 1, 70, 30, 1990, 35),
    T_SOUND_LEVEL_2 (ComponentCategory.T_SOUND, "Stereo Sound", 2, 55, 40, 1995, 27.5),
    T_SOUND_LEVEL_3 (ComponentCategory.T_SOUND, "Dolby 2.1", 3, 65, 80, 2000, 32.5),
    T_SOUND_LEVEL_4 (ComponentCategory.T_SOUND, "Dolby 5.1", 4, 100, 100, 2005, 50),
    T_SOUND_LEVEL_5 (ComponentCategory.T_SOUND, "Dolby 7.1", 5, 70, 140, 2010, 35),
    T_SOUND_LEVEL_6 (ComponentCategory.T_SOUND, "Dolby Atmos", 6, 45, 160, 2015, 22.5),
    /* initialPrice was not defined for T_OS_SOUND_7, new value was chosen arbitrarily*/
    T_SOUND_LEVEL_7 (ComponentCategory.T_SOUND, "Full-Fledged Home Cinema", 7, 100, 180, 2017, 50),

    T_CASE_LEVEL_1 (ComponentCategory.T_CASE, "Box Casing", 1, 40, 201, 1990, 20),
    T_CASE_LEVEL_2 (ComponentCategory.T_CASE, "Box Casing 2.0", 2, 35, 30, 1995, 17.5),
    T_CASE_LEVEL_3 (ComponentCategory.T_CASE, "Fullscreen", 3, 30, 70, 2000, 15),
    T_CASE_LEVEL_4 (ComponentCategory.T_CASE, "No Borders", 4, 20, 120, 2017, 10);

    private ComponentCategory componentCategory;
    private String componentName;
    private int componentLevel;
    private double initialComponentPrice;
    private int baseUtility;
    private int availabilityDate;
    private double initialSalesPrice;

    /**
     * Instantiates a component type.
     *
     * @param componentCategory
     * @param componentName
     * @param componentLevel
     * @param initialComponentPrice
     * @param baseUtility
     * @param availabilityDate
     * @param initialSalesPrice
     */
    ComponentType(ComponentCategory componentCategory, String componentName, int componentLevel, double initialComponentPrice, int baseUtility, int availabilityDate, double initialSalesPrice) {
        this.componentCategory = componentCategory;
        this.componentName = componentName;
        this.componentLevel = componentLevel;
        this.initialComponentPrice = initialComponentPrice;
        this.baseUtility = baseUtility;
        this.availabilityDate = availabilityDate;
        this.initialSalesPrice = initialSalesPrice;
    }

    /**
     * Gets component category.
     *
     * @return the component category
     */
    public ComponentCategory getComponentCategory() {
        return this.componentCategory;
    }

    /**
     * Gets component name.
     *
     * @return the component name
     */
    public String getComponentName() {
        return this.componentName;
    }

    /**
     * Gets component level.
     *
     * @return the component level.
     */
    public int getComponentLevel() {
        return this.componentLevel;
    }

    /**
     * Gets initial component price
     *
     * @return the initial component price
     */
    public double getInitialComponentPrice() {
        return this.initialComponentPrice;
    }

    /**
     * Gets base utility.
     *
     * @return the base utility
     */
    public int getBaseUtility() {
        return this.baseUtility;
    }

    /**
     * Gets availability date.
     *
     * @return the availability date
     */
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

    /**
     * Gets initial sales price.
     *
     * @return the initial sales price
     */
	public double getInitialSalesPrice() {
	    return this.initialSalesPrice;
    }
}