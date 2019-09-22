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
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.ChoiceBoxListCell;

import javax.swing.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

public class IntroduceProductController extends GameModuleController {

    /****** Supplier Options ******/
    @FXML
    private ChoiceBox tvScreensChoiceBox;
    @FXML
    private ChoiceBox tvAudiosChoiceBox;
    @FXML
    private ChoiceBox tvOSsChoiceBox;
    @FXML
    private ChoiceBox tvCasesChoiceBox;
    @FXML
    private ChoiceBox consoleCPUsChoiceBox;
    @FXML
    private ChoiceBox consoleScreensChoiceBox;
    @FXML
    private ChoiceBox consolePowersuppliesChoiceBox;
    @FXML
    private ChoiceBox consoleConnectivitiesChoiceBox;
    @FXML
    private ChoiceBox consoleCamerasChoiceBox;
    @FXML
    private ChoiceBox notebookCPUsChoiceBox;
    @FXML
    private ChoiceBox notebeookStoragesChoiceBox;
    @FXML
    private ChoiceBox notebookScreensChoiceBox;
    @FXML
    private ChoiceBox notebookSoftwaresChoiceBox;
    @FXML
    private ChoiceBox notebookPowersuppliesChoiceBox;
    @FXML
    private ChoiceBox phoneCPUsChoiceBox;
    @FXML
    private ChoiceBox phoneScreensChoiceBox;
    @FXML
    private ChoiceBox phoneCamerasChoiceBox;
    @FXML
    private ChoiceBox phoneConnectivitiesChoiceBox;
    @FXML
    private ChoiceBox phonePowersuppliesChoiceBox;
    @FXML
    private ChoiceBox phoneKeypadsChoiceBox;

    /****** TV Components ******/
    @FXML
    private ToggleButton tvScreenToggleButton0;
    @FXML
    private ToggleButton tvScreenToggleButton1;
    @FXML
    private ToggleButton tvScreenToggleButton2;
    @FXML
    private ToggleButton tvScreenToggleButton3;
    @FXML
    private ToggleButton tvScreenToggleButton4;
    @FXML
    private ToggleButton tvScreenToggleButton5;
    @FXML
    private ToggleButton tvScreenToggleButton6;

    @FXML
    private ToggleButton tvAudioToggleButton0;
    @FXML
    private ToggleButton tvAudioToggleButton1;
    @FXML
    private ToggleButton tvAudioToggleButton2;
    @FXML
    private ToggleButton tvAudioToggleButton3;
    @FXML
    private ToggleButton tvAudioToggleButton4;
    @FXML
    private ToggleButton tvAudioToggleButton5;
    @FXML
    private ToggleButton tvAudioToggleButton6;

    @FXML
    private ToggleButton tvOSToggleButton0;
    @FXML
    private ToggleButton tvOSToggleButton1;
    @FXML
    private ToggleButton tvOSToggleButton2;
    @FXML
    private ToggleButton tvOSToggleButton3;
    @FXML
    private ToggleButton tvOSToggleButton4;
    @FXML
    private ToggleButton tvOSToggleButton5;
    @FXML
    private ToggleButton tvOSToggleButton6;

    @FXML
    private ToggleButton tvCaseToggleButton0;
    @FXML
    private ToggleButton tvCaseToggleButton1;
    @FXML
    private ToggleButton tvCaseToggleButton2;
    @FXML
    private ToggleButton tvCaseToggleButton3;

    /****** Console Components ******/
    @FXML
    private ToggleButton consoleCPUToggleButton0;
    @FXML
    private ToggleButton consoleCPUToggleButton1;
    @FXML
    private ToggleButton consoleCPUToggleButton2;
    @FXML
    private ToggleButton consoleCPUToggleButton3;
    @FXML
    private ToggleButton consoleCPUToggleButton4;
    @FXML
    private ToggleButton consoleCPUToggleButton5;
    @FXML
    private ToggleButton consoleCPUToggleButton6;

    @FXML
    private ToggleButton consoleScreenToggleButton0;
    @FXML
    private ToggleButton consoleScreenToggleButton1;
    @FXML
    private ToggleButton consoleScreenToggleButton2;
    @FXML
    private ToggleButton consoleScreenToggleButton3;

    @FXML
    private ToggleButton consolePowersupplyToggleButton0;
    @FXML
    private ToggleButton consolePowersupplyToggleButton1;
    @FXML
    private ToggleButton consolePowersupplyToggleButton2;
    @FXML
    private ToggleButton consolePowersupplyToggleButton3;
    @FXML
    private ToggleButton consolePowersupplyToggleButton4;
    @FXML
    private ToggleButton consolePowersupplyToggleButton5;

    @FXML
    private ToggleButton consoleConnectivityToggleButton0;
    @FXML
    private ToggleButton consoleConnectivityToggleButton1;
    @FXML
    private ToggleButton consoleConnectivityToggleButton2;

    @FXML
    private ToggleButton consoleCameraToggleButton0;
    @FXML
    private ToggleButton consoleCameraToggleButton1;

    /****** Notebook Components ******/
    @FXML
    private ToggleButton notebookCPUToggleButton0;
    @FXML
    private ToggleButton notebookCPUToggleButton1;
    @FXML
    private ToggleButton notebookCPUToggleButton2;
    @FXML
    private ToggleButton notebookCPUToggleButton3;
    @FXML
    private ToggleButton notebookCPUToggleButton4;
    @FXML
    private ToggleButton notebookCPUToggleButton5;
    @FXML
    private ToggleButton notebookCPUToggleButton6;
    @FXML
    private ToggleButton notebookCPUToggleButton7;

    @FXML
    private ToggleButton notebookStorageToggleButton0;
    @FXML
    private ToggleButton notebookStorageToggleButton1;
    @FXML
    private ToggleButton notebookStorageToggleButton2;
    @FXML
    private ToggleButton notebookStorageToggleButton3;
    @FXML
    private ToggleButton notebookStorageToggleButton4;
    @FXML
    private ToggleButton notebookStorageToggleButton5;
    @FXML
    private ToggleButton notebookStorageToggleButton6;
    @FXML
    private ToggleButton notebookStorageToggleButton7;

    @FXML
    private ToggleButton notebookScreenToggleButton0;
    @FXML
    private ToggleButton notebookScreenToggleButton1;
    @FXML
    private ToggleButton notebookScreenToggleButton2;
    @FXML
    private ToggleButton notebookScreenToggleButton3;
    @FXML
    private ToggleButton notebookScreenToggleButton4;

    @FXML
    private ToggleButton notebookSoftwareToggleButton0;
    @FXML
    private ToggleButton notebookSoftwareToggleButton1;
    @FXML
    private ToggleButton notebookSoftwareToggleButton2;
    @FXML
    private ToggleButton notebookSoftwareToggleButton3;
    @FXML
    private ToggleButton notebookSoftwareToggleButton4;
    @FXML
    private ToggleButton notebookSoftwareToggleButton5;

    @FXML
    private ToggleButton notebookPowersupplyToggleButton0;

    /****** Phone Components ******/
    @FXML
    private ToggleButton phoneCPUToggleButton0;
    @FXML
    private ToggleButton phoneCPUToggleButton1;
    @FXML
    private ToggleButton phoneCPUToggleButton2;
    @FXML
    private ToggleButton phoneCPUToggleButton3;
    @FXML
    private ToggleButton phoneCPUToggleButton4;
    @FXML
    private ToggleButton phoneCPUToggleButton5;

    @FXML
    private ToggleButton phoneScreenToggleButton0;
    @FXML
    private ToggleButton phoneScreenToggleButton1;
    @FXML
    private ToggleButton phoneScreenToggleButton2;
    @FXML
    private ToggleButton phoneScreenToggleButton3;
    @FXML
    private ToggleButton phoneScreenToggleButton4;
    @FXML
    private ToggleButton phoneScreenToggleButton5;

    @FXML
    private ToggleButton phoneCameraToggleButton0;
    @FXML
    private ToggleButton phoneCameraToggleButton1;
    @FXML
    private ToggleButton phoneCameraToggleButton2;
    @FXML
    private ToggleButton phoneCameraToggleButton3;
    @FXML
    private ToggleButton phoneCameraToggleButton4;

    @FXML
    private ToggleButton phoneConnectivyToggleButton0;
    @FXML
    private ToggleButton phoneConnectivyToggleButton1;
    @FXML
    private ToggleButton phoneConnectivyToggleButton2;

    @FXML
    private ToggleButton phonePowersupplyToggleButton0;
    @FXML
    private ToggleButton phonePowersupplyToggleButton1;
    @FXML
    private ToggleButton phonePowersupplyToggleButton2;

    @FXML
    private ToggleButton phoneKeypadToggleButton0;
    @FXML
    private ToggleButton phoneKeypadToggleButton1;

    private ObservableList<Component> tvScreenList;
    private ObservableList<Component> tvAudioList;
    private ObservableList<Component> tvOSList;
    private ObservableList<Component> tvCaseList;
    private ObservableList<Component> consoleCPUList;
    private ObservableList<Component> consoleScreenList;
    private ObservableList<Component> consolePowersupplieList;
    private ObservableList<Component> consoleConnectivitieList;
    private ObservableList<Component> consoleCameraList;
    private ObservableList<Component> notebookCPUList;
    private ObservableList<Component> notebeookStorageList;
    private ObservableList<Component> notebookScreenList;
    private ObservableList<Component> notebookSoftwareList;
    private ObservableList<Component> notebookPowersupplieList;
    private ObservableList<Component> phoneCPUList;
    private ObservableList<Component> phoneScreenList;
    private ObservableList<Component> phoneCameraList;
    private ObservableList<Component> phoneConnectivitieList;
    private ObservableList<Component> phonePowersupplieList;
    private ObservableList<Component> phoneKeypadList;

    private Map<ToggleButton, Component> tvScreens;
    private Map<ToggleButton, Component> tvAudios;
    private Map<ToggleButton, Component> tvOSs;
    private Map<ToggleButton, Component> tvCases;
    private Map<ToggleButton, Component> consoleCPUs;
    private Map<ToggleButton, Component> consoleScreens;
    private Map<ToggleButton, Component> consolePowersupplies;
    private Map<ToggleButton, Component> consoleConnectivities;
    private Map<ToggleButton, Component> consoleCameras;
    private Map<ToggleButton, Component> notebookCPUs;
    private Map<ToggleButton, Component> notebeookStorages;
    private Map<ToggleButton, Component> notebookScreens;
    private Map<ToggleButton, Component> notebookSoftwares;
    private Map<ToggleButton, Component> notebookPowersupplies;
    private Map<ToggleButton, Component> phoneCPUs;
    private Map<ToggleButton, Component> phoneScreens;
    private Map<ToggleButton, Component> phoneCameras;
    private Map<ToggleButton, Component> phoneConnectivities;
    private Map<ToggleButton, Component> phonePowersupplies;
    private Map<ToggleButton, Component> phoneKeypads;

    private ToggleGroup tvScreensToggleGroup;
    private ToggleGroup tvAudiosToggleGroup;
    private ToggleGroup tvOSsToggleGroup;
    private ToggleGroup tvCasesToggleGroup;
    private ToggleGroup consoleCPUsToggleGroup;
    private ToggleGroup consoleScreensToggleGroup;
    private ToggleGroup consolePowersuppliesToggleGroup;
    private ToggleGroup consoleConnectivitiesToggleGroup;
    private ToggleGroup consoleCamerasToggleGroup;
    private ToggleGroup notebookCPUsToggleGroup;
    private ToggleGroup notebookStoragesToggleGroup;
    private ToggleGroup notebookScreensToggleGroup;
    private ToggleGroup notebookSoftwaresToggleGroup;
    private ToggleGroup notebookPowersuppliesToggleGroup;
    private ToggleGroup phoneCPUsToggleGroup;
    private ToggleGroup phoneScreensToggleGroup;
    private ToggleGroup phoneCamerasToggleGroup;
    private ToggleGroup phoneConnectivitiesToggleGroup;
    private ToggleGroup phonePowersuppliesToggleGroup;
    private ToggleGroup phoneKeypadsToggleGroup;

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tvScreens = new HashMap<>();
        this.tvScreens.put(tvScreenToggleButton0, Component.T_DISPLAY_LEVEL_1);
        this.tvScreens.put(tvScreenToggleButton1, Component.T_DISPLAY_LEVEL_2);
        this.tvScreens.put(tvScreenToggleButton2, Component.T_DISPLAY_LEVEL_3);
        this.tvScreens.put(tvScreenToggleButton3, Component.T_DISPLAY_LEVEL_4);
        this.tvScreens.put(tvScreenToggleButton4, Component.T_DISPLAY_LEVEL_5);
        this.tvScreens.put(tvScreenToggleButton5, Component.T_DISPLAY_LEVEL_6);
        this.tvScreens.put(tvScreenToggleButton6, Component.T_DISPLAY_LEVEL_7);

        this.tvAudios = new HashMap<>();
        this.tvAudios.put(tvAudioToggleButton0, Component.T_SOUND_LEVEL_1);
        this.tvAudios.put(tvAudioToggleButton1, Component.T_SOUND_LEVEL_2);
        this.tvAudios.put(tvAudioToggleButton2, Component.T_SOUND_LEVEL_3);
        this.tvAudios.put(tvAudioToggleButton3, Component.T_SOUND_LEVEL_4);
        this.tvAudios.put(tvAudioToggleButton4, Component.T_SOUND_LEVEL_5);
        this.tvAudios.put(tvAudioToggleButton5, Component.T_SOUND_LEVEL_6);
        this.tvAudios.put(tvAudioToggleButton6, Component.T_SOUND_LEVEL_7);

        this.tvOSs = new HashMap<>();
        this.tvOSs.put(tvOSToggleButton0, Component.T_OS_LEVEL_1);
        this.tvOSs.put(tvOSToggleButton1, Component.T_OS_LEVEL_2);
        this.tvOSs.put(tvOSToggleButton2, Component.T_OS_LEVEL_3);
        this.tvOSs.put(tvOSToggleButton3, Component.T_OS_LEVEL_4);
        this.tvOSs.put(tvOSToggleButton4, Component.T_OS_LEVEL_5);
        this.tvOSs.put(tvOSToggleButton5, Component.T_OS_LEVEL_6);
        this.tvOSs.put(tvOSToggleButton6, Component.T_OS_LEVEL_7);

        this.tvCases = new HashMap<>();
        this.tvCases.put(tvCaseToggleButton0, Component.T_CASE_LEVEL_1);
        this.tvCases.put(tvCaseToggleButton1, Component.T_CASE_LEVEL_2);
        this.tvCases.put(tvCaseToggleButton2, Component.T_CASE_LEVEL_3);
        this.tvCases.put(tvCaseToggleButton3, Component.T_CASE_LEVEL_4);

        this.consoleCPUs = new HashMap<>();
        this.consoleCPUs.put(consoleCPUToggleButton0, Component.G_CPU_LEVEL_1);
        this.consoleCPUs.put(consoleCPUToggleButton1, Component.G_CPU_LEVEL_2);
        this.consoleCPUs.put(consoleCPUToggleButton2, Component.G_CPU_LEVEL_3);
        this.consoleCPUs.put(consoleCPUToggleButton3, Component.G_CPU_LEVEL_4);
        this.consoleCPUs.put(consoleCPUToggleButton4, Component.G_CPU_LEVEL_5);
        this.consoleCPUs.put(consoleCPUToggleButton5, Component.G_CPU_LEVEL_6);
        this.consoleCPUs.put(consoleCPUToggleButton6, Component.G_CPU_LEVEL_7);

        this.consoleScreens = new HashMap<>();
        this.consoleScreens.put(consoleScreenToggleButton0, Component.G_DISPLAYCASE_LEVEL_1);
        this.consoleScreens.put(consoleScreenToggleButton1, Component.G_DISPLAYCASE_LEVEL_2);
        this.consoleScreens.put(consoleScreenToggleButton2, Component.G_DISPLAYCASE_LEVEL_3);
        this.consoleScreens.put(consoleScreenToggleButton3, Component.G_DISPLAYCASE_LEVEL_4);

        this.consolePowersupplies = new HashMap<>();
        this.consolePowersupplies.put(consolePowersupplyToggleButton0, Component.G_POWERSUPPLY_LEVEL_1);
        this.consolePowersupplies.put(consolePowersupplyToggleButton1, Component.G_POWERSUPPLY_LEVEL_2);
        this.consolePowersupplies.put(consolePowersupplyToggleButton2, Component.G_POWERSUPPLY_LEVEL_3);
        this.consolePowersupplies.put(consolePowersupplyToggleButton3, Component.G_POWERSUPPLY_LEVEL_4);
        this.consolePowersupplies.put(consolePowersupplyToggleButton4, Component.G_POWERSUPPLY_LEVEL_5);
        this.consolePowersupplies.put(consolePowersupplyToggleButton5, Component.G_POWERSUPPLY_LEVEL_6);

        this.consoleConnectivities = new HashMap<>();
        this.consoleConnectivities.put(consoleConnectivityToggleButton0, Component.G_CONNECTIVITY_LEVEL_1);
        this.consoleConnectivities.put(consoleConnectivityToggleButton1, Component.G_CONNECTIVITY_LEVEL_2);
        this.consoleConnectivities.put(consoleConnectivityToggleButton2, Component.G_CONNECTIVITY_LEVEL_3);

        this.consoleCameras = new HashMap<>();
        this.consoleCameras.put(consoleCameraToggleButton0, Component.G_CAMERA_LEVEL_1);
        this.consoleCameras.put(consoleCameraToggleButton1, Component.G_CAMERA_LEVEL_2);

        this.notebookCPUs = new HashMap<>();
        this.notebookCPUs.put(notebookCPUToggleButton0, Component.N_CPU_LEVEL_1);
        this.notebookCPUs.put(notebookCPUToggleButton1, Component.N_CPU_LEVEL_2);
        this.notebookCPUs.put(notebookCPUToggleButton2, Component.N_CPU_LEVEL_3);
        this.notebookCPUs.put(notebookCPUToggleButton3, Component.N_CPU_LEVEL_4);
        this.notebookCPUs.put(notebookCPUToggleButton4, Component.N_CPU_LEVEL_5);
        this.notebookCPUs.put(notebookCPUToggleButton5, Component.N_CPU_LEVEL_6);
        this.notebookCPUs.put(notebookCPUToggleButton6, Component.N_CPU_LEVEL_7);
        this.notebookCPUs.put(notebookCPUToggleButton7, Component.N_CPU_LEVEL_8);

        this.notebeookStorages = new HashMap<>();
        this.notebeookStorages.put(notebookStorageToggleButton0, Component.N_STORAGE_LEVEL_1);
        this.notebeookStorages.put(notebookStorageToggleButton1, Component.N_STORAGE_LEVEL_2);
        this.notebeookStorages.put(notebookStorageToggleButton2, Component.N_STORAGE_LEVEL_3);
        this.notebeookStorages.put(notebookStorageToggleButton3, Component.N_STORAGE_LEVEL_4);
        this.notebeookStorages.put(notebookStorageToggleButton4, Component.N_STORAGE_LEVEL_5);
        this.notebeookStorages.put(notebookStorageToggleButton5, Component.N_STORAGE_LEVEL_6);
        this.notebeookStorages.put(notebookStorageToggleButton6, Component.N_STORAGE_LEVEL_7);
        this.notebeookStorages.put(notebookStorageToggleButton7, Component.N_STORAGE_LEVEL_8);

        this.notebookScreens  = new HashMap<>();
        this.notebookScreens.put(notebookScreenToggleButton0, Component.N_DISPLAYCASE_LEVEL_1);
        this.notebookScreens.put(notebookScreenToggleButton1, Component.N_DISPLAYCASE_LEVEL_2);
        this.notebookScreens.put(notebookScreenToggleButton2, Component.N_DISPLAYCASE_LEVEL_3);
        this.notebookScreens.put(notebookScreenToggleButton3, Component.N_DISPLAYCASE_LEVEL_4);
        this.notebookScreens.put(notebookScreenToggleButton4, Component.N_DISPLAYCASE_LEVEL_5);

        this.notebookSoftwares = new HashMap<>();
        this.notebookSoftwares.put(notebookSoftwareToggleButton0, Component.N_SOFTWARE_LEVEL_1);
        this.notebookSoftwares.put(notebookSoftwareToggleButton1, Component.N_SOFTWARE_LEVEL_2);
        this.notebookSoftwares.put(notebookSoftwareToggleButton2, Component.N_SOFTWARE_LEVEL_3);
        this.notebookSoftwares.put(notebookSoftwareToggleButton3, Component.N_SOFTWARE_LEVEL_4);
        this.notebookSoftwares.put(notebookSoftwareToggleButton4, Component.N_SOFTWARE_LEVEL_5);
        this.notebookSoftwares.put(notebookSoftwareToggleButton5, Component.N_SOFTWARE_LEVEL_6);

        this.notebookPowersupplies = new HashMap<>();
        this.notebookPowersupplies.put(notebookPowersupplyToggleButton0, Component.N_POWERSUPPLY_LEVEL_1);

        this.phoneCPUs = new HashMap<>();
        this.phoneCPUs.put(phoneCPUToggleButton0, Component.P_CPU_LEVEL_1);
        this.phoneCPUs.put(phoneCPUToggleButton1, Component.P_CPU_LEVEL_2);
        this.phoneCPUs.put(phoneCPUToggleButton2, Component.P_CPU_LEVEL_3);
        this.phoneCPUs.put(phoneCPUToggleButton3, Component.P_CPU_LEVEL_4);
        this.phoneCPUs.put(phoneCPUToggleButton4, Component.P_CPU_LEVEL_5);
        this.phoneCPUs.put(phoneCPUToggleButton5, Component.P_CPU_LEVEL_6);

        this.phoneScreens = new HashMap<>();
        this.phoneScreens.put(phoneScreenToggleButton0, Component.P_DISPLAYCASE_LEVEL_1);
        this.phoneScreens.put(phoneScreenToggleButton1, Component.P_DISPLAYCASE_LEVEL_2);
        this.phoneScreens.put(phoneScreenToggleButton2, Component.P_DISPLAYCASE_LEVEL_3);
        this.phoneScreens.put(phoneScreenToggleButton3, Component.P_DISPLAYCASE_LEVEL_4);
        this.phoneScreens.put(phoneScreenToggleButton4, Component.P_DISPLAYCASE_LEVEL_5);
        this.phoneScreens.put(phoneScreenToggleButton5, Component.P_DISPLAYCASE_LEVEL_6);

        this.phoneCameras = new HashMap<>();
        this.phoneCameras.put(phoneCameraToggleButton0, Component.P_CAMERA_LEVEL_1);
        this.phoneCameras.put(phoneCameraToggleButton1, Component.P_CAMERA_LEVEL_2);
        this.phoneCameras.put(phoneCameraToggleButton2, Component.P_CAMERA_LEVEL_3);
        this.phoneCameras.put(phoneCameraToggleButton3, Component.P_CAMERA_LEVEL_4);
        this.phoneCameras.put(phoneCameraToggleButton4, Component.P_CAMERA_LEVEL_5);

        this.phoneConnectivities = new HashMap<>();
        this.phoneConnectivities.put(phoneConnectivyToggleButton0, Component.P_CONNECTIVITY_LEVEL_1);
        this.phoneConnectivities.put(phoneConnectivyToggleButton1, Component.P_CONNECTIVITY_LEVEL_2);
        this.phoneConnectivities.put(phoneConnectivyToggleButton2, Component.P_CONNECTIVITY_LEVEL_3);

        this.phonePowersupplies = new HashMap<>();
        this.phonePowersupplies.put(phonePowersupplyToggleButton0, Component.P_POWERSUPPLY_LEVEL_1);
        this.phonePowersupplies.put(phonePowersupplyToggleButton1, Component.P_POWERSUPPLY_LEVEL_2);
        this.phonePowersupplies.put(phonePowersupplyToggleButton2, Component.P_POWERSUPPLY_LEVEL_3);

        this.phoneKeypads = new HashMap<>();
        this.phoneKeypads.put(phoneKeypadToggleButton0, Component.P_KEYPAD_LEVEL_1);
        this.phoneKeypads.put(phoneKeypadToggleButton1, Component.P_KEYPAD_LEVEL_2);

        tvScreensToggleGroup = new ToggleGroup();
        tvAudiosToggleGroup = new ToggleGroup();
        tvOSsToggleGroup = new ToggleGroup();
        tvCasesToggleGroup = new ToggleGroup();
        consoleCPUsToggleGroup = new ToggleGroup();
        consoleScreensToggleGroup = new ToggleGroup();
        consolePowersuppliesToggleGroup = new ToggleGroup();
        consoleConnectivitiesToggleGroup = new ToggleGroup();
        consoleCamerasToggleGroup = new ToggleGroup();
        notebookCPUsToggleGroup = new ToggleGroup();
        notebookStoragesToggleGroup = new ToggleGroup();
        notebookScreensToggleGroup = new ToggleGroup();
        notebookSoftwaresToggleGroup = new ToggleGroup();
        notebookPowersuppliesToggleGroup = new ToggleGroup();
        phoneCPUsToggleGroup = new ToggleGroup();
        phoneScreensToggleGroup = new ToggleGroup();
        phoneCamerasToggleGroup = new ToggleGroup();
        phoneConnectivitiesToggleGroup = new ToggleGroup();
        phonePowersuppliesToggleGroup = new ToggleGroup();
        phoneKeypadsToggleGroup = new ToggleGroup();


        this.tvScreenToggleButton0.setToggleGroup(tvScreensToggleGroup);
        this.tvScreenToggleButton1.setToggleGroup(tvScreensToggleGroup);
        this.tvScreenToggleButton2.setToggleGroup(tvScreensToggleGroup);
        this.tvScreenToggleButton3.setToggleGroup(tvScreensToggleGroup);
        this.tvScreenToggleButton4.setToggleGroup(tvScreensToggleGroup);
        this.tvScreenToggleButton5.setToggleGroup(tvScreensToggleGroup);
        this.tvScreenToggleButton6.setToggleGroup(tvScreensToggleGroup);

        this.tvAudioToggleButton0.setToggleGroup(tvAudiosToggleGroup);
        this.tvAudioToggleButton1.setToggleGroup(tvAudiosToggleGroup);
        this.tvAudioToggleButton2.setToggleGroup(tvAudiosToggleGroup);
        this.tvAudioToggleButton3.setToggleGroup(tvAudiosToggleGroup);
        this.tvAudioToggleButton4.setToggleGroup(tvAudiosToggleGroup);
        this.tvAudioToggleButton5.setToggleGroup(tvAudiosToggleGroup);
        this.tvAudioToggleButton6.setToggleGroup(tvAudiosToggleGroup);

        this.tvOSToggleButton0.setToggleGroup(tvOSsToggleGroup);
        this.tvOSToggleButton1.setToggleGroup(tvOSsToggleGroup);
        this.tvOSToggleButton2.setToggleGroup(tvOSsToggleGroup);
        this.tvOSToggleButton3.setToggleGroup(tvOSsToggleGroup);
        this.tvOSToggleButton4.setToggleGroup(tvOSsToggleGroup);
        this.tvOSToggleButton5.setToggleGroup(tvOSsToggleGroup);
        this.tvOSToggleButton6.setToggleGroup(tvOSsToggleGroup);

        this.tvCaseToggleButton0.setToggleGroup(tvCasesToggleGroup);
        this.tvCaseToggleButton1.setToggleGroup(tvCasesToggleGroup);
        this.tvCaseToggleButton2.setToggleGroup(tvCasesToggleGroup);
        this.tvCaseToggleButton3.setToggleGroup(tvCasesToggleGroup);

        this.consoleCPUToggleButton0.setToggleGroup(consoleCPUsToggleGroup);
        this.consoleCPUToggleButton1.setToggleGroup(consoleCPUsToggleGroup);
        this.consoleCPUToggleButton2.setToggleGroup(consoleCPUsToggleGroup);
        this.consoleCPUToggleButton3.setToggleGroup(consoleCPUsToggleGroup);
        this.consoleCPUToggleButton4.setToggleGroup(consoleCPUsToggleGroup);
        this.consoleCPUToggleButton5.setToggleGroup(consoleCPUsToggleGroup);
        this.consoleCPUToggleButton6.setToggleGroup(consoleCPUsToggleGroup);

        this.consoleScreenToggleButton0.setToggleGroup(consoleScreensToggleGroup);
        this.consoleScreenToggleButton1.setToggleGroup(consoleScreensToggleGroup);
        this.consoleScreenToggleButton2.setToggleGroup(consoleScreensToggleGroup);
        this.consoleScreenToggleButton3.setToggleGroup(consoleScreensToggleGroup);

        this.consolePowersupplyToggleButton0.setToggleGroup(consolePowersuppliesToggleGroup);
        this.consolePowersupplyToggleButton1.setToggleGroup(consolePowersuppliesToggleGroup);
        this.consolePowersupplyToggleButton2.setToggleGroup(consolePowersuppliesToggleGroup);
        this.consolePowersupplyToggleButton3.setToggleGroup(consolePowersuppliesToggleGroup);
        this.consolePowersupplyToggleButton4.setToggleGroup(consolePowersuppliesToggleGroup);
        this.consolePowersupplyToggleButton5.setToggleGroup(consolePowersuppliesToggleGroup);

        this.consoleConnectivityToggleButton0.setToggleGroup(consoleConnectivitiesToggleGroup);
        this.consoleConnectivityToggleButton1.setToggleGroup(consoleConnectivitiesToggleGroup);
        this.consoleConnectivityToggleButton2.setToggleGroup(consoleConnectivitiesToggleGroup);

        this.consoleCameraToggleButton0.setToggleGroup(consoleCamerasToggleGroup);
        this.consoleCameraToggleButton1.setToggleGroup(consoleCamerasToggleGroup);

        this.notebookCPUToggleButton0.setToggleGroup(notebookCPUsToggleGroup);
        this.notebookCPUToggleButton1.setToggleGroup(notebookCPUsToggleGroup);
        this.notebookCPUToggleButton2.setToggleGroup(notebookCPUsToggleGroup);
        this.notebookCPUToggleButton3.setToggleGroup(notebookCPUsToggleGroup);
        this.notebookCPUToggleButton4.setToggleGroup(notebookCPUsToggleGroup);
        this.notebookCPUToggleButton5.setToggleGroup(notebookCPUsToggleGroup);
        this.notebookCPUToggleButton6.setToggleGroup(notebookCPUsToggleGroup);
        this.notebookCPUToggleButton7.setToggleGroup(notebookCPUsToggleGroup);

        this.notebookStorageToggleButton0.setToggleGroup(notebookStoragesToggleGroup);
        this.notebookStorageToggleButton1.setToggleGroup(notebookStoragesToggleGroup);
        this.notebookStorageToggleButton2.setToggleGroup(notebookStoragesToggleGroup);
        this.notebookStorageToggleButton3.setToggleGroup(notebookStoragesToggleGroup);
        this.notebookStorageToggleButton4.setToggleGroup(notebookStoragesToggleGroup);
        this.notebookStorageToggleButton5.setToggleGroup(notebookStoragesToggleGroup);
        this.notebookStorageToggleButton6.setToggleGroup(notebookStoragesToggleGroup);
        this.notebookStorageToggleButton7.setToggleGroup(notebookStoragesToggleGroup);

        this.notebookScreenToggleButton0.setToggleGroup(notebookScreensToggleGroup);
        this.notebookScreenToggleButton1.setToggleGroup(notebookScreensToggleGroup);
        this.notebookScreenToggleButton2.setToggleGroup(notebookScreensToggleGroup);
        this.notebookScreenToggleButton3.setToggleGroup(notebookScreensToggleGroup);
        this.notebookScreenToggleButton4.setToggleGroup(notebookScreensToggleGroup);

        this.notebookSoftwareToggleButton0.setToggleGroup(notebookSoftwaresToggleGroup);
        this.notebookSoftwareToggleButton1.setToggleGroup(notebookSoftwaresToggleGroup);
        this.notebookSoftwareToggleButton2.setToggleGroup(notebookSoftwaresToggleGroup);
        this.notebookSoftwareToggleButton3.setToggleGroup(notebookSoftwaresToggleGroup);
        this.notebookSoftwareToggleButton4.setToggleGroup(notebookSoftwaresToggleGroup);
        this.notebookSoftwareToggleButton5.setToggleGroup(notebookSoftwaresToggleGroup);

        this.notebookPowersupplyToggleButton0.setToggleGroup(notebookPowersuppliesToggleGroup);

        this.phoneCPUToggleButton0.setToggleGroup(phoneCPUsToggleGroup);
        this.phoneCPUToggleButton1.setToggleGroup(phoneCPUsToggleGroup);
        this.phoneCPUToggleButton2.setToggleGroup(phoneCPUsToggleGroup);
        this.phoneCPUToggleButton3.setToggleGroup(phoneCPUsToggleGroup);
        this.phoneCPUToggleButton4.setToggleGroup(phoneCPUsToggleGroup);
        this.phoneCPUToggleButton5.setToggleGroup(phoneCPUsToggleGroup);

        this.phoneScreenToggleButton0.setToggleGroup(phoneScreensToggleGroup);
        this.phoneScreenToggleButton1.setToggleGroup(phoneScreensToggleGroup);
        this.phoneScreenToggleButton2.setToggleGroup(phoneScreensToggleGroup);
        this.phoneScreenToggleButton3.setToggleGroup(phoneScreensToggleGroup);
        this.phoneScreenToggleButton4.setToggleGroup(phoneScreensToggleGroup);
        this.phoneScreenToggleButton5.setToggleGroup(phoneScreensToggleGroup);

        this.phoneCameraToggleButton0.setToggleGroup(phoneCamerasToggleGroup);
        this.phoneCameraToggleButton1.setToggleGroup(phoneCamerasToggleGroup);
        this.phoneCameraToggleButton2.setToggleGroup(phoneCamerasToggleGroup);
        this.phoneCameraToggleButton3.setToggleGroup(phoneCamerasToggleGroup);
        this.phoneCameraToggleButton4.setToggleGroup(phoneCamerasToggleGroup);

        this.phoneConnectivyToggleButton0.setToggleGroup(phoneConnectivitiesToggleGroup);
        this.phoneConnectivyToggleButton1.setToggleGroup(phoneConnectivitiesToggleGroup);
        this.phoneConnectivyToggleButton2.setToggleGroup(phoneConnectivitiesToggleGroup);

        this.phonePowersupplyToggleButton0.setToggleGroup(phonePowersuppliesToggleGroup);
        this.phonePowersupplyToggleButton1.setToggleGroup(phonePowersuppliesToggleGroup);
        this.phonePowersupplyToggleButton2.setToggleGroup(phonePowersuppliesToggleGroup);

        this.phoneKeypadToggleButton0.setToggleGroup(phoneKeypadsToggleGroup);
        this.phoneKeypadToggleButton1.setToggleGroup(phoneKeypadsToggleGroup);

        List<Map<ToggleButton, Component>> allComponents = new ArrayList<>();
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

        for(Map<ToggleButton, Component> componentMap : allComponents) {
            for(Map.Entry<ToggleButton, Component> componentEntry : componentMap.entrySet()) {
                componentEntry.getKey().setText(componentEntry.getValue().getComponentName());
            }
        }

        this.updateSuppliers();

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


    public void updateSuppliers() {
        Component selectedTvScreen = tvScreens.get(tvScreensToggleGroup.getSelectedToggle());
        Component selectedTvAudio = tvAudios.get(tvAudiosToggleGroup.getSelectedToggle());
        Component selectedTvOS = tvOSs.get(tvOSsToggleGroup.getSelectedToggle());
        Component selectedTvCase = tvCases.get(tvCasesToggleGroup.getSelectedToggle());
        Component selectedConsoleCPU = consoleCPUs.get(consoleCPUsToggleGroup.getSelectedToggle());
        Component selectedConsoleScreen = consoleScreens.get(consoleScreensToggleGroup.getSelectedToggle());
        Component selectedConsolePowersupply = consolePowersupplies.get(consolePowersuppliesToggleGroup.getSelectedToggle());
        Component selectedConsoleConnectivity = consoleConnectivities.get(consoleConnectivitiesToggleGroup.getSelectedToggle());
        Component selectedConsoleCamera = consoleCameras.get(consoleCamerasToggleGroup.getSelectedToggle());
        Component selectedNotebookCPU = notebookCPUs.get(notebookCPUsToggleGroup.getSelectedToggle());
        Component selectedNotebookStorage = notebeookStorages.get(notebookStoragesToggleGroup.getSelectedToggle());
        Component selectedNotebookScreen = notebookScreens.get(notebookScreensToggleGroup.getSelectedToggle());
        Component selectedNotebookSoftware = notebookSoftwares.get(notebookSoftwaresToggleGroup.getSelectedToggle());
        Component selectedNotebookPowersupply = notebookPowersupplies.get(notebookPowersuppliesToggleGroup.getSelectedToggle());
        Component selectedPhoneCPU = phoneCPUs.get(phoneCPUsToggleGroup.getSelectedToggle());
        Component selectedPhoneScreen = phoneScreens.get(phoneScreensToggleGroup.getSelectedToggle());
        Component selectedPhoneCamera = phoneCameras.get(phoneCamerasToggleGroup.getSelectedToggle());
        Component selectedPhoneConnectivity = phoneConnectivities.get(phoneConnectivitiesToggleGroup.getSelectedToggle());
        Component selectedPhonePowersupply = phonePowersupplies.get(phonePowersuppliesToggleGroup.getSelectedToggle());
        Component selectedPhoneKeypads = phoneKeypads.get(phoneKeypadsToggleGroup.getSelectedToggle());

        this.tvScreenList = FXCollections.observableArrayList();
        this.tvAudioList = FXCollections.observableArrayList();
        this.tvOSList = FXCollections.observableArrayList();
        this.tvCaseList = FXCollections.observableArrayList();
        this.consoleCPUList = FXCollections.observableArrayList();
        this.consoleScreenList = FXCollections.observableArrayList();
        this.consolePowersupplieList = FXCollections.observableArrayList();
        this.consoleConnectivitieList = FXCollections.observableArrayList();
        this.consoleCameraList = FXCollections.observableArrayList();
        this.notebookCPUList = FXCollections.observableArrayList();
        this.notebeookStorageList = FXCollections.observableArrayList();
        this.notebookScreenList = FXCollections.observableArrayList();
        this.notebookSoftwareList = FXCollections.observableArrayList();
        this.notebookPowersupplieList = FXCollections.observableArrayList();
        this.phoneCPUList = FXCollections.observableArrayList();
        this.phoneScreenList = FXCollections.observableArrayList();
        this.phoneCameraList = FXCollections.observableArrayList();
        this.phoneConnectivitieList = FXCollections.observableArrayList();
        this.phonePowersupplieList = FXCollections.observableArrayList();
        this.phoneKeypadList = FXCollections.observableArrayList();

        for(int i=0; i <3; i++) {
            Component tmpComp = selectedTvScreen;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.tvScreenList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedTvAudio;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.tvAudioList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedTvOS;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.tvOSList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedTvCase;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.tvCaseList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedConsoleCPU;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.consoleCPUList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedConsoleScreen;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.consoleScreenList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedConsolePowersupply;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.consolePowersupplieList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedConsoleConnectivity;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.consoleConnectivitieList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedConsoleCamera;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.consoleCameraList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedNotebookCPU;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.notebookCPUList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedNotebookStorage;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.notebeookStorageList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedNotebookScreen;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.notebookScreenList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedNotebookSoftware;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.notebookSoftwareList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedNotebookPowersupply;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.notebookPowersupplieList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedPhoneCPU;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.phoneCPUList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedPhoneScreen;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.phoneScreenList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedPhoneCamera;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.phoneCameraList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedPhoneConnectivity;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.phoneConnectivitieList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedPhonePowersupply;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.phonePowersupplieList.add(tmpComp);
        }
        for(int i=0; i <3; i++) {
            Component tmpComp = selectedPhoneKeypads;
            switch(i) {
                case 0: tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                case 1: tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                case 2: tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                default:
            }
            this.phoneKeypadList.add(tmpComp);
        }

        DecimalFormat decimalFormat = new DecimalFormat("$###,###.##");
        LocalDate gameDate = GameState.getInstance().getGameDate();

        this.tvScreensChoiceBox.setItems(this.tvScreenList);
        ArrayList<String> tvScreenStringList = new ArrayList<>();
        for(Component component : this.tvScreenList) {
            tvScreenStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.tvScreensChoiceBox.setUserData(tvScreenStringList);

        this.tvAudiosChoiceBox.setItems(this.tvAudioList);
        ArrayList<String> tvAudioStringList = new ArrayList<>();
        for(Component component : this.tvAudioList) {
            tvAudioStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.tvAudiosChoiceBox.setUserData(tvAudioStringList);

        this.tvOSsChoiceBox.setItems(this.tvOSList);
        ArrayList<String> tvOSStringList = new ArrayList<>();
        for(Component component : this.tvOSList) {
            tvOSStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.tvOSsChoiceBox.setUserData(tvOSStringList);

        this.tvCasesChoiceBox.setItems(this.tvCaseList);
        ArrayList<String> tvCaseStringList = new ArrayList<>();
        for(Component component : this.tvCaseList) {
            tvCaseStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.tvCasesChoiceBox.setUserData(tvCaseStringList);

        this.consoleCPUsChoiceBox.setItems(this.consoleCPUList);
        ArrayList<String> consoleCPUStringList = new ArrayList<>();
        for(Component component : this.consoleCPUList) {
            consoleCPUStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.consoleCPUsChoiceBox.setUserData(consoleCPUStringList);

        this.consoleScreensChoiceBox.setItems(this.consoleScreenList);
        ArrayList<String> consoleScreenStringList = new ArrayList<>();
        for(Component component : this.consoleScreenList) {
            consoleScreenStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.consoleScreensChoiceBox.setUserData(consoleScreenStringList);

        this.consolePowersuppliesChoiceBox.setItems(this.consolePowersupplieList);
        ArrayList<String> consolePowersupplieStringList = new ArrayList<>();
        for(Component component : this.consolePowersupplieList) {
            consolePowersupplieStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.consolePowersuppliesChoiceBox.setUserData(consolePowersupplieStringList);

        this.consoleConnectivitiesChoiceBox.setItems(this.consoleConnectivitieList);
        ArrayList<String> consoleConnectivitieStringList = new ArrayList<>();
        for(Component component : this.consoleConnectivitieList) {
            consoleConnectivitieStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.consoleConnectivitiesChoiceBox.setUserData(consoleConnectivitieStringList);

        this.consoleCamerasChoiceBox.setItems(this.consoleCameraList);
        ArrayList<String> consoleCameraStringList = new ArrayList<>();
        for(Component component : this.consoleCameraList) {
            consoleCameraStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.consoleCamerasChoiceBox.setUserData(consoleCameraStringList);

        this.notebookCPUsChoiceBox.setItems(this.notebookCPUList);
        ArrayList<String> notebookCPUStringList = new ArrayList<>();
        for(Component component : this.notebookCPUList) {
            notebookCPUStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.notebookCPUsChoiceBox.setUserData(notebookCPUStringList);

        this.notebeookStoragesChoiceBox.setItems(this.notebeookStorageList);
        ArrayList<String> notebeookStorageStringList = new ArrayList<>();
        for(Component component : this.notebeookStorageList) {
            notebeookStorageStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.notebeookStoragesChoiceBox.setUserData(notebeookStorageStringList);

        this.notebookScreensChoiceBox.setItems(this.notebookScreenList);
        ArrayList<String> notebookScreenStringList = new ArrayList<>();
        for(Component component : this.notebookScreenList) {
            notebookScreenStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.notebookScreensChoiceBox.setUserData(notebookScreenStringList);

        this.notebookSoftwaresChoiceBox.setItems(this.notebookSoftwareList);
        ArrayList<String> notebookSoftwareStringList = new ArrayList<>();
        for(Component component : this.notebookSoftwareList) {
            notebookSoftwareStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.notebookSoftwaresChoiceBox.setUserData(notebookSoftwareStringList);

        this.notebookPowersuppliesChoiceBox.setItems(this.notebookPowersupplieList);
        ArrayList<String> notebookPowersupplieStringList = new ArrayList<>();
        for(Component component : this.notebookPowersupplieList) {
            notebookPowersupplieStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.notebookPowersuppliesChoiceBox.setUserData(notebookPowersupplieStringList);

        this.phoneCPUsChoiceBox.setItems(this.phoneCPUList);
        ArrayList<String> phoneCPUStringList = new ArrayList<>();
        for(Component component : this.phoneCPUList) {
            phoneCPUStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.phoneCPUsChoiceBox.setUserData(phoneCPUStringList);

        this.phoneScreensChoiceBox.setItems(this.phoneScreenList);
        ArrayList<String> phoneScreenStringList = new ArrayList<>();
        for(Component component : this.phoneScreenList) {
            phoneScreenStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.phoneScreensChoiceBox.setUserData(phoneScreenStringList);

        this.phoneCamerasChoiceBox.setItems(this.phoneCameraList);
        ArrayList<String> phoneCameraStringList = new ArrayList<>();
        for(Component component : this.phoneCameraList) {
            phoneCameraStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.phoneCamerasChoiceBox.setUserData(phoneCameraStringList);

        this.phoneConnectivitiesChoiceBox.setItems(this.phoneConnectivitieList);
        ArrayList<String> phoneConnectivitieStringList = new ArrayList<>();
        for(Component component : this.phoneConnectivitieList) {
            phoneConnectivitieStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.phoneConnectivitiesChoiceBox.setUserData(phoneConnectivitieStringList);

        this.phonePowersuppliesChoiceBox.setItems(this.phonePowersupplieList);
        ArrayList<String> phonePowersupplieStringList = new ArrayList<>();
        for(Component component : this.phonePowersupplieList) {
            phonePowersupplieStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.phonePowersuppliesChoiceBox.setUserData(phonePowersupplieStringList);

        this.phoneKeypadsChoiceBox.setItems(this.phoneKeypadList);
        ArrayList<String> phoneKeypadStringList = new ArrayList<>();
        for(Component component : this.phoneKeypadList) {
            phoneKeypadStringList.add(component.getSupplierQuality() + " (" + decimalFormat.format(component.calculateBaseCost(gameDate)) +")");
        }
        this.phoneKeypadsChoiceBox.setUserData(phoneKeypadStringList);


        this.tvScreensChoiceBox.setValue(this.tvScreenList.get(0));
        this.tvAudiosChoiceBox.setValue(this.tvAudioList.get(0));
        this.tvOSsChoiceBox.setValue(this.tvOSList.get(0));
        this.tvCasesChoiceBox.setValue(this.tvCaseList.get(0));
        this.consoleCPUsChoiceBox.setValue(this.consoleCPUList.get(0));
        this.consoleScreensChoiceBox.setValue(this.consoleScreenList.get(0));
        this.consolePowersuppliesChoiceBox.setValue(this.consolePowersupplieList.get(0));
        this.consoleConnectivitiesChoiceBox.setValue(this.consoleConnectivitieList.get(0));
        this.consoleCamerasChoiceBox.setValue(this.consoleCameraList.get(0));
        this.notebookCPUsChoiceBox.setValue(this.notebookCPUList.get(0));
        this.notebeookStoragesChoiceBox.setValue(this.notebeookStorageList.get(0));
        this.notebookScreensChoiceBox.setValue(this.notebookScreenList.get(0));
        this.notebookSoftwaresChoiceBox.setValue(this.notebookSoftwareList.get(0));
        this.notebookPowersuppliesChoiceBox.setValue(this.notebookPowersupplieList.get(0));
        this.phoneCPUsChoiceBox.setValue(this.phoneCPUList.get(0));
        this.phoneScreensChoiceBox.setValue(this.phoneScreenList.get(0));
        this.phoneCamerasChoiceBox.setValue(this.phoneCameraList.get(0));
        this.phoneConnectivitiesChoiceBox.setValue(this.phoneConnectivitieList.get(0));
        this.phonePowersuppliesChoiceBox.setValue(this.phonePowersupplieList.get(0));
        this.phoneKeypadsChoiceBox.setValue(this.phoneKeypadList.get(0));
    }

    public void showSupplierOptions() {
        Main.getManager().getGamePageController().showOverlay(UIElementType.PRODUCTION_NEW_PRODUCT_OVERVIEW);
    }

    /*
    class SupplierListViewCell extends ChoiceBoxListCell<Component> {

        public void updateItem(Component component, boolean empty) {
            super.updateItem(component, empty);
            DecimalFormat decimalFormat = new DecimalFormat("$###,###.##");
            setText(component.getSupplierCategory() + decimalFormat.format(component.calculateBaseCost(GameState.getInstance().getGameDate())));
        }
    }
    */
}
