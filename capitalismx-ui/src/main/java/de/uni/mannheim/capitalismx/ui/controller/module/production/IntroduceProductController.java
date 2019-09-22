package de.uni.mannheim.capitalismx.ui.controller.module.production;

import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import de.uni.mannheim.capitalismx.ui.application.Main;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import javax.swing.*;
import java.net.URL;
import java.util.*;

public class IntroduceProductController extends GameModuleController {

    /****** Supplier Options ******/
    @FXML
    private ChoiceBox tvScreenChoiceBox;

    /****** TV Components ******/
    @FXML
    private Label tvScreenLabel0;
    @FXML
    private Label tvScreenLabel1;
    @FXML
    private Label tvScreenLabel2;
    @FXML
    private Label tvScreenLabel3;
    @FXML
    private Label tvScreenLabel4;
    @FXML
    private Label tvScreenLabel5;
    @FXML
    private Label tvScreenLabel6;

    @FXML
    private Label tvAudioLabel0;
    @FXML
    private Label tvAudioLabel1;
    @FXML
    private Label tvAudioLabel2;
    @FXML
    private Label tvAudioLabel3;
    @FXML
    private Label tvAudioLabel4;
    @FXML
    private Label tvAudioLabel5;
    @FXML
    private Label tvAudioLabel6;

    @FXML
    private Label tvOSLabel0;
    @FXML
    private Label tvOSLabel1;
    @FXML
    private Label tvOSLabel2;
    @FXML
    private Label tvOSLabel3;
    @FXML
    private Label tvOSLabel4;
    @FXML
    private Label tvOSLabel5;
    @FXML
    private Label tvOSLabel6;

    @FXML
    private Label tvCaseLabel0;
    @FXML
    private Label tvCaseLabel1;
    @FXML
    private Label tvCaseLabel2;
    @FXML
    private Label tvCaseLabel3;

    /****** Console Components ******/
    @FXML
    private Label consoleCPULabel0;
    @FXML
    private Label consoleCPULabel1;
    @FXML
    private Label consoleCPULabel2;
    @FXML
    private Label consoleCPULabel3;
    @FXML
    private Label consoleCPULabel4;
    @FXML
    private Label consoleCPULabel5;
    @FXML
    private Label consoleCPULabel6;

    @FXML
    private Label consoleScreenLabel0;
    @FXML
    private Label consoleScreenLabel1;
    @FXML
    private Label consoleScreenLabel2;
    @FXML
    private Label consoleScreenLabel3;

    @FXML
    private Label consolePowersupplyLabel0;
    @FXML
    private Label consolePowersupplyLabel1;
    @FXML
    private Label consolePowersupplyLabel2;
    @FXML
    private Label consolePowersupplyLabel3;
    @FXML
    private Label consolePowersupplyLabel4;
    @FXML
    private Label consolePowersupplyLabel5;

    @FXML
    private Label consoleConnectivityLabel0;
    @FXML
    private Label consoleConnectivityLabel1;
    @FXML
    private Label consoleConnectivityLabel2;

    @FXML
    private Label consoleCameraLabel0;
    @FXML
    private Label consoleCameraLabel1;

    /****** Notebook Components ******/
    @FXML
    private Label notebookCPULabel0;
    @FXML
    private Label notebookCPULabel1;
    @FXML
    private Label notebookCPULabel2;
    @FXML
    private Label notebookCPULabel3;
    @FXML
    private Label notebookCPULabel4;
    @FXML
    private Label notebookCPULabel5;
    @FXML
    private Label notebookCPULabel6;
    @FXML
    private Label notebookCPULabel7;

    @FXML
    private Label notebookStorageLabel0;
    @FXML
    private Label notebookStorageLabel1;
    @FXML
    private Label notebookStorageLabel2;
    @FXML
    private Label notebookStorageLabel3;
    @FXML
    private Label notebookStorageLabel4;
    @FXML
    private Label notebookStorageLabel5;
    @FXML
    private Label notebookStorageLabel6;
    @FXML
    private Label notebookStorageLabel7;

    @FXML
    private Label notebookScreenLabel0;
    @FXML
    private Label notebookScreenLabel1;
    @FXML
    private Label notebookScreenLabel2;
    @FXML
    private Label notebookScreenLabel3;
    @FXML
    private Label notebookScreenLabel4;

    @FXML
    private Label notebookSoftwareLabel0;
    @FXML
    private Label notebookSoftwareLabel1;
    @FXML
    private Label notebookSoftwareLabel2;
    @FXML
    private Label notebookSoftwareLabel3;
    @FXML
    private Label notebookSoftwareLabel4;
    @FXML
    private Label notebookSoftwareLabel5;

    @FXML
    private Label notebookPowersupplyLabel0;

    /****** Phone Components ******/
    @FXML
    private Label phoneCPULabel0;
    @FXML
    private Label phoneCPULabel1;
    @FXML
    private Label phoneCPULabel2;
    @FXML
    private Label phoneCPULabel3;
    @FXML
    private Label phoneCPULabel4;
    @FXML
    private Label phoneCPULabel5;

    @FXML
    private Label phoneScreenLabel0;
    @FXML
    private Label phoneScreenLabel1;
    @FXML
    private Label phoneScreenLabel2;
    @FXML
    private Label phoneScreenLabel3;
    @FXML
    private Label phoneScreenLabel4;
    @FXML
    private Label phoneScreenLabel5;

    @FXML
    private Label phoneCameraLabel0;
    @FXML
    private Label phoneCameraLabel1;
    @FXML
    private Label phoneCameraLabel2;
    @FXML
    private Label phoneCameraLabel3;
    @FXML
    private Label phoneCameraLabel4;

    @FXML
    private Label phoneConnectivyLabel0;
    @FXML
    private Label phoneConnectivyLabel1;
    @FXML
    private Label phoneConnectivyLabel2;

    @FXML
    private Label phonePowersupplyLabel0;
    @FXML
    private Label phonePowersupplyLabel1;
    @FXML
    private Label phonePowersupplyLabel2;

    @FXML
    private Label phoneKeypadLabel0;
    @FXML
    private Label phoneKeypadLabel1;

    private Map<Label, Component> tvScreens;
    private Map<Label, Component> tvAudios;
    private Map<Label, Component> tvOSs;
    private Map<Label, Component> tvCases;
    private Map<Label, Component> consoleCPUs;
    private Map<Label, Component> consoleScreens;
    private Map<Label, Component> consolePowersupplies;
    private Map<Label, Component> consoleConnectivities;
    private Map<Label, Component> consoleCameras;
    private Map<Label, Component> notebookCPUs;
    private Map<Label, Component> notebeookStorages;
    private Map<Label, Component> notebookScreens;
    private Map<Label, Component> notebookSoftwares;
    private Map<Label, Component> notebookPowersupplies;
    private Map<Label, Component> phoneCPUs;
    private Map<Label, Component> phoneScreens;
    private Map<Label, Component> phoneCameras;
    private Map<Label, Component> phoneConnectivities;
    private Map<Label, Component> phonePowersupplies;
    private Map<Label, Component> phoneKeypads;

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tvScreens = new HashMap<>();
        this.tvScreens.put(tvScreenLabel0, Component.T_DISPLAY_LEVEL_1);
        this.tvScreens.put(tvScreenLabel1, Component.T_DISPLAY_LEVEL_2);
        this.tvScreens.put(tvScreenLabel2, Component.T_DISPLAY_LEVEL_3);
        this.tvScreens.put(tvScreenLabel3, Component.T_DISPLAY_LEVEL_4);
        this.tvScreens.put(tvScreenLabel4, Component.T_DISPLAY_LEVEL_5);
        this.tvScreens.put(tvScreenLabel5, Component.T_DISPLAY_LEVEL_6);
        this.tvScreens.put(tvScreenLabel6, Component.T_DISPLAY_LEVEL_7);

        this.tvAudios = new HashMap<>();
        this.tvAudios.put(tvAudioLabel0, Component.T_SOUND_LEVEL_1);
        this.tvAudios.put(tvAudioLabel1, Component.T_SOUND_LEVEL_2);
        this.tvAudios.put(tvAudioLabel2, Component.T_SOUND_LEVEL_3);
        this.tvAudios.put(tvAudioLabel3, Component.T_SOUND_LEVEL_4);
        this.tvAudios.put(tvAudioLabel4, Component.T_SOUND_LEVEL_5);
        this.tvAudios.put(tvAudioLabel5, Component.T_SOUND_LEVEL_6);
        this.tvAudios.put(tvAudioLabel6, Component.T_SOUND_LEVEL_7);

        this.tvOSs = new HashMap<>();
        this.tvOSs.put(tvOSLabel0, Component.T_OS_LEVEL_1);
        this.tvOSs.put(tvOSLabel1, Component.T_OS_LEVEL_2);
        this.tvOSs.put(tvOSLabel2, Component.T_OS_LEVEL_3);
        this.tvOSs.put(tvOSLabel3, Component.T_OS_LEVEL_4);
        this.tvOSs.put(tvOSLabel4, Component.T_OS_LEVEL_5);
        this.tvOSs.put(tvOSLabel5, Component.T_OS_LEVEL_6);
        this.tvOSs.put(tvOSLabel6, Component.T_OS_LEVEL_7);

        this.tvCases = new HashMap<>();
        this.tvCases.put(tvCaseLabel0, Component.T_CASE_LEVEL_1);
        this.tvCases.put(tvCaseLabel1, Component.T_CASE_LEVEL_2);
        this.tvCases.put(tvCaseLabel2, Component.T_CASE_LEVEL_3);
        this.tvCases.put(tvCaseLabel3, Component.T_CASE_LEVEL_4);

        this.consoleCPUs = new HashMap<>();
        this.consoleCPUs.put(consoleCPULabel0, Component.G_CPU_LEVEL_1);
        this.consoleCPUs.put(consoleCPULabel1, Component.G_CPU_LEVEL_2);
        this.consoleCPUs.put(consoleCPULabel2, Component.G_CPU_LEVEL_3);
        this.consoleCPUs.put(consoleCPULabel3, Component.G_CPU_LEVEL_4);
        this.consoleCPUs.put(consoleCPULabel4, Component.G_CPU_LEVEL_5);
        this.consoleCPUs.put(consoleCPULabel5, Component.G_CPU_LEVEL_6);
        this.consoleCPUs.put(consoleCPULabel6, Component.G_CPU_LEVEL_7);

        this.consoleScreens = new HashMap<>();
        this.consoleScreens.put(consoleScreenLabel0, Component.G_DISPLAYCASE_LEVEL_1);
        this.consoleScreens.put(consoleScreenLabel1, Component.G_DISPLAYCASE_LEVEL_2);
        this.consoleScreens.put(consoleScreenLabel2, Component.G_DISPLAYCASE_LEVEL_3);
        this.consoleScreens.put(consoleScreenLabel3, Component.G_DISPLAYCASE_LEVEL_4);

        this.consolePowersupplies = new HashMap<>();
        this.consolePowersupplies.put(consolePowersupplyLabel0, Component.G_POWERSUPPLY_LEVEL_1);
        this.consolePowersupplies.put(consolePowersupplyLabel1, Component.G_POWERSUPPLY_LEVEL_2);
        this.consolePowersupplies.put(consolePowersupplyLabel2, Component.G_POWERSUPPLY_LEVEL_3);
        this.consolePowersupplies.put(consolePowersupplyLabel3, Component.G_POWERSUPPLY_LEVEL_4);
        this.consolePowersupplies.put(consolePowersupplyLabel4, Component.G_POWERSUPPLY_LEVEL_5);
        this.consolePowersupplies.put(consolePowersupplyLabel5, Component.G_POWERSUPPLY_LEVEL_6);

        this.consoleConnectivities = new HashMap<>();
        this.consoleConnectivities.put(consoleConnectivityLabel0, Component.G_CONNECTIVITY_LEVEL_1);
        this.consoleConnectivities.put(consoleConnectivityLabel1, Component.G_CONNECTIVITY_LEVEL_2);
        this.consoleConnectivities.put(consoleConnectivityLabel2, Component.G_CONNECTIVITY_LEVEL_3);

        this.consoleCameras = new HashMap<>();
        this.consoleCameras.put(consoleCameraLabel0, Component.G_CAMERA_LEVEL_1);
        this.consoleCameras.put(consoleCameraLabel1, Component.G_CAMERA_LEVEL_2);

        this.notebookCPUs = new HashMap<>();
        this.notebookCPUs.put(notebookCPULabel0, Component.N_CPU_LEVEL_1);
        this.notebookCPUs.put(notebookCPULabel1, Component.N_CPU_LEVEL_2);
        this.notebookCPUs.put(notebookCPULabel2, Component.N_CPU_LEVEL_3);
        this.notebookCPUs.put(notebookCPULabel3, Component.N_CPU_LEVEL_4);
        this.notebookCPUs.put(notebookCPULabel4, Component.N_CPU_LEVEL_5);
        this.notebookCPUs.put(notebookCPULabel5, Component.N_CPU_LEVEL_6);
        this.notebookCPUs.put(notebookCPULabel6, Component.N_CPU_LEVEL_7);
        this.notebookCPUs.put(notebookCPULabel7, Component.N_CPU_LEVEL_8);

        this.notebeookStorages = new HashMap<>();
        this.notebeookStorages.put(notebookStorageLabel0, Component.N_STORAGE_LEVEL_1);
        this.notebeookStorages.put(notebookStorageLabel1, Component.N_STORAGE_LEVEL_2);
        this.notebeookStorages.put(notebookStorageLabel2, Component.N_STORAGE_LEVEL_3);
        this.notebeookStorages.put(notebookStorageLabel3, Component.N_STORAGE_LEVEL_4);
        this.notebeookStorages.put(notebookStorageLabel4, Component.N_STORAGE_LEVEL_5);
        this.notebeookStorages.put(notebookStorageLabel5, Component.N_STORAGE_LEVEL_6);
        this.notebeookStorages.put(notebookStorageLabel6, Component.N_STORAGE_LEVEL_7);
        this.notebeookStorages.put(notebookStorageLabel7, Component.N_STORAGE_LEVEL_8);

        this.notebookScreens  = new HashMap<>();
        this.notebookScreens.put(notebookScreenLabel0, Component.N_DISPLAYCASE_LEVEL_1);
        this.notebookScreens.put(notebookScreenLabel1, Component.N_DISPLAYCASE_LEVEL_2);
        this.notebookScreens.put(notebookScreenLabel2, Component.N_DISPLAYCASE_LEVEL_3);
        this.notebookScreens.put(notebookScreenLabel3, Component.N_DISPLAYCASE_LEVEL_4);
        this.notebookScreens.put(notebookScreenLabel4, Component.N_DISPLAYCASE_LEVEL_5);

        this.notebookSoftwares = new HashMap<>();
        this.notebookSoftwares.put(notebookSoftwareLabel0, Component.N_SOFTWARE_LEVEL_1);
        this.notebookSoftwares.put(notebookSoftwareLabel1, Component.N_SOFTWARE_LEVEL_2);
        this.notebookSoftwares.put(notebookSoftwareLabel2, Component.N_SOFTWARE_LEVEL_3);
        this.notebookSoftwares.put(notebookSoftwareLabel3, Component.N_SOFTWARE_LEVEL_4);
        this.notebookSoftwares.put(notebookSoftwareLabel4, Component.N_SOFTWARE_LEVEL_5);
        this.notebookSoftwares.put(notebookSoftwareLabel5, Component.N_SOFTWARE_LEVEL_6);

        this.notebookPowersupplies = new HashMap<>();
        this.notebookPowersupplies.put(notebookPowersupplyLabel0, Component.N_POWERSUPPLY_LEVEL_1);

        this.phoneCPUs = new HashMap<>();
        this.phoneCPUs.put(phoneCPULabel0, Component.P_CPU_LEVEL_1);
        this.phoneCPUs.put(phoneCPULabel1, Component.P_CPU_LEVEL_2);
        this.phoneCPUs.put(phoneCPULabel2, Component.P_CPU_LEVEL_3);
        this.phoneCPUs.put(phoneCPULabel3, Component.P_CPU_LEVEL_4);
        this.phoneCPUs.put(phoneCPULabel4, Component.P_CPU_LEVEL_5);
        this.phoneCPUs.put(phoneCPULabel5, Component.P_CPU_LEVEL_6);

        this.phoneScreens = new HashMap<>();
        this.phoneScreens.put(phoneScreenLabel0, Component.P_DISPLAYCASE_LEVEL_1);
        this.phoneScreens.put(phoneScreenLabel1, Component.P_DISPLAYCASE_LEVEL_2);
        this.phoneScreens.put(phoneScreenLabel2, Component.P_DISPLAYCASE_LEVEL_3);
        this.phoneScreens.put(phoneScreenLabel3, Component.P_DISPLAYCASE_LEVEL_4);
        this.phoneScreens.put(phoneScreenLabel4, Component.P_DISPLAYCASE_LEVEL_5);
        this.phoneScreens.put(phoneScreenLabel5, Component.P_DISPLAYCASE_LEVEL_6);

        this.phoneCameras = new HashMap<>();
        this.phoneCameras.put(phoneCameraLabel0, Component.P_CAMERA_LEVEL_1);
        this.phoneCameras.put(phoneCameraLabel1, Component.P_CAMERA_LEVEL_2);
        this.phoneCameras.put(phoneCameraLabel2, Component.P_CAMERA_LEVEL_3);
        this.phoneCameras.put(phoneCameraLabel3, Component.P_CAMERA_LEVEL_4);
        this.phoneCameras.put(phoneCameraLabel4, Component.P_CAMERA_LEVEL_5);

        this.phoneConnectivities = new HashMap<>();
        this.phoneConnectivities.put(phoneConnectivyLabel0, Component.P_CONNECTIVITY_LEVEL_1);
        this.phoneConnectivities.put(phoneConnectivyLabel1, Component.P_CONNECTIVITY_LEVEL_2);
        this.phoneConnectivities.put(phoneConnectivyLabel2, Component.P_CONNECTIVITY_LEVEL_3);

        this.phonePowersupplies = new HashMap<>();
        this.phonePowersupplies.put(phonePowersupplyLabel0, Component.P_POWERSUPPLY_LEVEL_1);
        this.phonePowersupplies.put(phonePowersupplyLabel1, Component.P_POWERSUPPLY_LEVEL_2);
        this.phonePowersupplies.put(phonePowersupplyLabel2, Component.P_POWERSUPPLY_LEVEL_3);

        this.phoneKeypads = new HashMap<>();
        this.phoneKeypads.put(phoneKeypadLabel0, Component.P_KEYPAD_LEVEL_1);
        this.phoneKeypads.put(phoneKeypadLabel1, Component.P_KEYPAD_LEVEL_2);

        List<Map<Label, Component>> allComponents = new ArrayList<>();
        allComponents.add(tvScreens);
        allComponents.add(tvAudios);
        allComponents.add(tvOSs);
        allComponents.add(tvCases);
        allComponents.add(consoleCPUs);
        allComponents.add(consoleScreens);
        allComponents.add(consolePowersupplies);
        allComponents.add(consoleConnectivities);
        allComponents.add(consoleCameras);
        allComponents.add(notebookCPUs);
        allComponents.add(notebeookStorages);
        allComponents.add(notebookScreens);
        allComponents.add(notebookSoftwares);
        allComponents.add(notebookPowersupplies);
        allComponents.add(phoneCPUs);
        allComponents.add(phoneScreens);
        allComponents.add(phoneCameras);
        allComponents.add(phoneConnectivities);
        allComponents.add(phonePowersupplies);
        allComponents.add(phoneKeypads);

        for(Map<Label, Component> componentMap : allComponents) {
            for(Map.Entry<Label, Component> componentEntry : componentMap.entrySet()) {
                componentEntry.getKey().setText(componentEntry.getValue().getComponentName());
            }
        }
        /*
        Component tvScreenCheap1 = Component.T_DISPLAY_LEVEL_1;
        Component tvScreenCheap2 = Component.T_DISPLAY_LEVEL_1;
        Component tvScreenCheap3 = Component.T_DISPLAY_LEVEL_1;
        tvScreenCheap1.setSupplierCategory(SupplierCategory.CHEAP);
        tvScreenCheap2.setSupplierCategory(SupplierCategory.REGULAR);
        tvScreenCheap3.setSupplierCategory(SupplierCategory.PREMIUM);

        ArrayList<Component> arrayList = new ArrayList<>();

        arrayList.add(tvScreenCheap1);
        arrayList.add(tvScreenCheap2);
        arrayList.add(tvScreenCheap3);

        tvScreenChoiceBox.setItems(FXCollections.observableArrayList(tvScreenCheap1 + "" + tvScreenCheap1.calculateBaseCost(GameState.getInstance().getGameDate()),"was","krass"));
        tvScreenChoiceBox.setValue("das");
        */
    }

    public void showSupplierOptions() {
        Main.getManager().getGamePageController().showOverlay(UIElementType.PRODUCTION_NEW_PRODUCT_OVERVIEW);
    }
}
