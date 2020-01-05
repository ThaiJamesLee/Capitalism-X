package de.uni.mannheim.capitalismx.procurement.component;

import java.io.Serializable;

public enum ComponentCategory implements Serializable {

    DUMMY("Dummy Category"),

    N_CPU ("Notebook CPU"),
    N_DISPLAYCASE ("Notebook Display Case"),
    N_SOFTWARE ("Notebook Software"),
    N_STORAGE ("Notebook Storage"),
    N_POWERSUPPLY ("Notebook Power Supply"),

    P_POWERSUPPLY ("Phone Power Supply"),
    P_DISPLAYCASE ("Phone Display Case"),
    P_KEYPAD ("Phone Keypad"),
    P_CPU ("Phone CPU"),
    P_CAMERA ("Phone Camera"),
    P_CONNECTIVITY ("Phone Connectivity"),

    G_DISPLAYCASE ("Gameboy Display Case"),
    G_POWERSUPPLY ("Gameboy Power Supply"),
    G_CPU ("Gameboy CPU"),
    G_CONNECTIVITY ("Gameboy Connectivity"),
    G_CAMERA ("Gameboy Camera"),

    T_DISPLAY ("Television Display"),
    T_OS ("Television OS"),
    T_SOUND ("Television Sound"),
    T_CASE ("Television Case");

    private String category;

    ComponentCategory(String category) {
        this.category = category;
    }

    public String toString() {
        return this.category;
    }
}