package de.uni.mannheim.capitalismx.ui.controller.module.procurement;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.util.StringConverter;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

public class BuyComponentController extends GameModuleController {

    /****** Amount of Components ******/
    @FXML
    private TextField tvComponentAmountTextField, consoleComponentAmountTextField, notebookComponentAmountTextField, phoneComponentAmountTextField;

    /****** Supplier Options ******/
    @FXML
    private ChoiceBox<Component> tvScreensChoiceBox;
    @FXML
    private ChoiceBox<Component> tvAudiosChoiceBox;
    @FXML
    private ChoiceBox<Component> tvOSsChoiceBox;
    @FXML
    private ChoiceBox<Component> tvCasesChoiceBox;
    @FXML
    private ChoiceBox<Component> consoleCPUsChoiceBox;
    @FXML
    private ChoiceBox<Component> consoleScreensChoiceBox;
    @FXML
    private ChoiceBox<Component> consolePowersuppliesChoiceBox;
    @FXML
    private ChoiceBox<Component> consoleConnectivitiesChoiceBox;
    @FXML
    private ChoiceBox<Component> consoleCamerasChoiceBox;
    @FXML
    private ChoiceBox<Component> notebookCPUsChoiceBox;
    @FXML
    private ChoiceBox<Component> notebookStoragesChoiceBox;
    @FXML
    private ChoiceBox<Component> notebookScreensChoiceBox;
    @FXML
    private ChoiceBox<Component> notebookSoftwaresChoiceBox;
    @FXML
    private ChoiceBox<Component> notebookPowersuppliesChoiceBox;
    @FXML
    private ChoiceBox<Component> phoneCPUsChoiceBox;
    @FXML
    private ChoiceBox<Component> phoneScreensChoiceBox;
    @FXML
    private ChoiceBox<Component> phoneCamerasChoiceBox;
    @FXML
    private ChoiceBox<Component> phoneConnectivitiesChoiceBox;
    @FXML
    private ChoiceBox<Component> phonePowersuppliesChoiceBox;
    @FXML
    private ChoiceBox<Component> phoneKeypadsChoiceBox;

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

    private ObservableList<Component> tvComponentList;
    private ObservableList<Component> consoleComponentList;
    private ObservableList<Component> notebookComponentList;
    private ObservableList<Component> phoneComponentList;


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
    private Map<ToggleButton, Component> notebookStorages;
    private Map<ToggleButton, Component> notebookScreens;
    private Map<ToggleButton, Component> notebookSoftwares;
    private Map<ToggleButton, Component> notebookPowersupplies;
    private Map<ToggleButton, Component> phoneCPUs;
    private Map<ToggleButton, Component> phoneScreens;
    private Map<ToggleButton, Component> phoneCameras;
    private Map<ToggleButton, Component> phoneConnectivities;
    private Map<ToggleButton, Component> phonePowersupplies;
    private Map<ToggleButton, Component> phoneKeypads;

    private List<Map<ToggleButton, Component>> tvComponents;
    private List<Map<ToggleButton, Component>> consoleComponents;
    private List<Map<ToggleButton, Component>> notebookComponents;
    private List<Map<ToggleButton, Component>> phoneComponents;

    private ToggleGroup tvComponentsToggleGroup;
    private ToggleGroup consoleComponentsToggleGroup;
    private ToggleGroup notebookComponentsToggleGroup;
    private ToggleGroup phoneComponentsToggleGroup;


    @Override
    public void update() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tvScreens = new HashMap<>();
        this.tvScreens.put(tvScreenToggleButton0, new Component(ComponentType.T_DISPLAY_LEVEL_1));
        this.tvScreens.put(tvScreenToggleButton1, new Component(ComponentType.T_DISPLAY_LEVEL_2));
        this.tvScreens.put(tvScreenToggleButton2, new Component(ComponentType.T_DISPLAY_LEVEL_3));
        this.tvScreens.put(tvScreenToggleButton3, new Component(ComponentType.T_DISPLAY_LEVEL_4));
        this.tvScreens.put(tvScreenToggleButton4, new Component(ComponentType.T_DISPLAY_LEVEL_5));
        this.tvScreens.put(tvScreenToggleButton5, new Component(ComponentType.T_DISPLAY_LEVEL_6));
        this.tvScreens.put(tvScreenToggleButton6, new Component(ComponentType.T_DISPLAY_LEVEL_7));

        this.tvAudios = new HashMap<>();
        this.tvAudios.put(tvAudioToggleButton0, new Component(ComponentType.T_SOUND_LEVEL_1));
        this.tvAudios.put(tvAudioToggleButton1, new Component(ComponentType.T_SOUND_LEVEL_2));
        this.tvAudios.put(tvAudioToggleButton2, new Component(ComponentType.T_SOUND_LEVEL_3));
        this.tvAudios.put(tvAudioToggleButton3, new Component(ComponentType.T_SOUND_LEVEL_4));
        this.tvAudios.put(tvAudioToggleButton4, new Component(ComponentType.T_SOUND_LEVEL_5));
        this.tvAudios.put(tvAudioToggleButton5, new Component(ComponentType.T_SOUND_LEVEL_6));
        this.tvAudios.put(tvAudioToggleButton6, new Component(ComponentType.T_SOUND_LEVEL_7));

        this.tvOSs = new HashMap<>();
        this.tvOSs.put(tvOSToggleButton0, new Component(ComponentType.T_OS_LEVEL_1));
        this.tvOSs.put(tvOSToggleButton1, new Component(ComponentType.T_OS_LEVEL_2));
        this.tvOSs.put(tvOSToggleButton2, new Component(ComponentType.T_OS_LEVEL_3));
        this.tvOSs.put(tvOSToggleButton3, new Component(ComponentType.T_OS_LEVEL_4));
        this.tvOSs.put(tvOSToggleButton4, new Component(ComponentType.T_OS_LEVEL_5));
        this.tvOSs.put(tvOSToggleButton5, new Component(ComponentType.T_OS_LEVEL_6));
        this.tvOSs.put(tvOSToggleButton6, new Component(ComponentType.T_OS_LEVEL_7));

        this.tvCases = new HashMap<>();
        this.tvCases.put(tvCaseToggleButton0, new Component(ComponentType.T_CASE_LEVEL_1));
        this.tvCases.put(tvCaseToggleButton1, new Component(ComponentType.T_CASE_LEVEL_2));
        this.tvCases.put(tvCaseToggleButton2, new Component(ComponentType.T_CASE_LEVEL_3));
        this.tvCases.put(tvCaseToggleButton3, new Component(ComponentType.T_CASE_LEVEL_4));

        this.consoleCPUs = new HashMap<>();
        this.consoleCPUs.put(consoleCPUToggleButton0, new Component(ComponentType.G_CPU_LEVEL_1));
        this.consoleCPUs.put(consoleCPUToggleButton1, new Component(ComponentType.G_CPU_LEVEL_2));
        this.consoleCPUs.put(consoleCPUToggleButton2, new Component(ComponentType.G_CPU_LEVEL_3));
        this.consoleCPUs.put(consoleCPUToggleButton3, new Component(ComponentType.G_CPU_LEVEL_4));
        this.consoleCPUs.put(consoleCPUToggleButton4, new Component(ComponentType.G_CPU_LEVEL_5));
        this.consoleCPUs.put(consoleCPUToggleButton5, new Component(ComponentType.G_CPU_LEVEL_6));
        this.consoleCPUs.put(consoleCPUToggleButton6, new Component(ComponentType.G_CPU_LEVEL_7));

        this.consoleScreens = new HashMap<>();
        this.consoleScreens.put(consoleScreenToggleButton0, new Component(ComponentType.G_DISPLAYCASE_LEVEL_1));
        this.consoleScreens.put(consoleScreenToggleButton1, new Component(ComponentType.G_DISPLAYCASE_LEVEL_2));
        this.consoleScreens.put(consoleScreenToggleButton2, new Component(ComponentType.G_DISPLAYCASE_LEVEL_3));
        this.consoleScreens.put(consoleScreenToggleButton3, new Component(ComponentType.G_DISPLAYCASE_LEVEL_4));

        this.consolePowersupplies = new HashMap<>();
        this.consolePowersupplies.put(consolePowersupplyToggleButton0, new Component(ComponentType.G_POWERSUPPLY_LEVEL_1));
        this.consolePowersupplies.put(consolePowersupplyToggleButton1, new Component(ComponentType.G_POWERSUPPLY_LEVEL_2));
        this.consolePowersupplies.put(consolePowersupplyToggleButton2, new Component(ComponentType.G_POWERSUPPLY_LEVEL_3));
        this.consolePowersupplies.put(consolePowersupplyToggleButton3, new Component(ComponentType.G_POWERSUPPLY_LEVEL_4));
        this.consolePowersupplies.put(consolePowersupplyToggleButton4, new Component(ComponentType.G_POWERSUPPLY_LEVEL_5));
        this.consolePowersupplies.put(consolePowersupplyToggleButton5, new Component(ComponentType.G_POWERSUPPLY_LEVEL_6));

        this.consoleConnectivities = new HashMap<>();
        this.consoleConnectivities.put(consoleConnectivityToggleButton0, new Component(ComponentType.G_CONNECTIVITY_LEVEL_1));
        this.consoleConnectivities.put(consoleConnectivityToggleButton1, new Component(ComponentType.G_CONNECTIVITY_LEVEL_2));
        this.consoleConnectivities.put(consoleConnectivityToggleButton2, new Component(ComponentType.G_CONNECTIVITY_LEVEL_3));

        this.consoleCameras = new HashMap<>();
        this.consoleCameras.put(consoleCameraToggleButton0, new Component(ComponentType.G_CAMERA_LEVEL_1));
        this.consoleCameras.put(consoleCameraToggleButton1, new Component(ComponentType.G_CAMERA_LEVEL_2));

        this.notebookCPUs = new HashMap<>();
        this.notebookCPUs.put(notebookCPUToggleButton0, new Component(ComponentType.N_CPU_LEVEL_1));
        this.notebookCPUs.put(notebookCPUToggleButton1, new Component(ComponentType.N_CPU_LEVEL_2));
        this.notebookCPUs.put(notebookCPUToggleButton2, new Component(ComponentType.N_CPU_LEVEL_3));
        this.notebookCPUs.put(notebookCPUToggleButton3, new Component(ComponentType.N_CPU_LEVEL_4));
        this.notebookCPUs.put(notebookCPUToggleButton4, new Component(ComponentType.N_CPU_LEVEL_5));
        this.notebookCPUs.put(notebookCPUToggleButton5, new Component(ComponentType.N_CPU_LEVEL_6));
        this.notebookCPUs.put(notebookCPUToggleButton6, new Component(ComponentType.N_CPU_LEVEL_7));
        this.notebookCPUs.put(notebookCPUToggleButton7, new Component(ComponentType.N_CPU_LEVEL_8));

        this.notebookStorages = new HashMap<>();
        this.notebookStorages.put(notebookStorageToggleButton0, new Component(ComponentType.N_STORAGE_LEVEL_1));
        this.notebookStorages.put(notebookStorageToggleButton1, new Component(ComponentType.N_STORAGE_LEVEL_2));
        this.notebookStorages.put(notebookStorageToggleButton2, new Component(ComponentType.N_STORAGE_LEVEL_3));
        this.notebookStorages.put(notebookStorageToggleButton3, new Component(ComponentType.N_STORAGE_LEVEL_4));
        this.notebookStorages.put(notebookStorageToggleButton4, new Component(ComponentType.N_STORAGE_LEVEL_5));
        this.notebookStorages.put(notebookStorageToggleButton5, new Component(ComponentType.N_STORAGE_LEVEL_6));
        this.notebookStorages.put(notebookStorageToggleButton6, new Component(ComponentType.N_STORAGE_LEVEL_7));
        this.notebookStorages.put(notebookStorageToggleButton7, new Component(ComponentType.N_STORAGE_LEVEL_8));

        this.notebookScreens  = new HashMap<>();
        this.notebookScreens.put(notebookScreenToggleButton0, new Component(ComponentType.N_DISPLAYCASE_LEVEL_1));
        this.notebookScreens.put(notebookScreenToggleButton1, new Component(ComponentType.N_DISPLAYCASE_LEVEL_2));
        this.notebookScreens.put(notebookScreenToggleButton2, new Component(ComponentType.N_DISPLAYCASE_LEVEL_3));
        this.notebookScreens.put(notebookScreenToggleButton3, new Component(ComponentType.N_DISPLAYCASE_LEVEL_4));
        this.notebookScreens.put(notebookScreenToggleButton4, new Component(ComponentType.N_DISPLAYCASE_LEVEL_5));

        this.notebookSoftwares = new HashMap<>();
        this.notebookSoftwares.put(notebookSoftwareToggleButton0, new Component(ComponentType.N_SOFTWARE_LEVEL_1));
        this.notebookSoftwares.put(notebookSoftwareToggleButton1, new Component(ComponentType.N_SOFTWARE_LEVEL_2));
        this.notebookSoftwares.put(notebookSoftwareToggleButton2, new Component(ComponentType.N_SOFTWARE_LEVEL_3));
        this.notebookSoftwares.put(notebookSoftwareToggleButton3, new Component(ComponentType.N_SOFTWARE_LEVEL_4));
        this.notebookSoftwares.put(notebookSoftwareToggleButton4, new Component(ComponentType.N_SOFTWARE_LEVEL_5));
        this.notebookSoftwares.put(notebookSoftwareToggleButton5, new Component(ComponentType.N_SOFTWARE_LEVEL_6));

        this.notebookPowersupplies = new HashMap<>();
        this.notebookPowersupplies.put(notebookPowersupplyToggleButton0, new Component(ComponentType.N_POWERSUPPLY_LEVEL_1));

        this.phoneCPUs = new HashMap<>();
        this.phoneCPUs.put(phoneCPUToggleButton0, new Component(ComponentType.P_CPU_LEVEL_1));
        this.phoneCPUs.put(phoneCPUToggleButton1, new Component(ComponentType.P_CPU_LEVEL_2));
        this.phoneCPUs.put(phoneCPUToggleButton2, new Component(ComponentType.P_CPU_LEVEL_3));
        this.phoneCPUs.put(phoneCPUToggleButton3, new Component(ComponentType.P_CPU_LEVEL_4));
        this.phoneCPUs.put(phoneCPUToggleButton4, new Component(ComponentType.P_CPU_LEVEL_5));
        this.phoneCPUs.put(phoneCPUToggleButton5, new Component(ComponentType.P_CPU_LEVEL_6));

        this.phoneScreens = new HashMap<>();
        this.phoneScreens.put(phoneScreenToggleButton0, new Component(ComponentType.P_DISPLAYCASE_LEVEL_1));
        this.phoneScreens.put(phoneScreenToggleButton1, new Component(ComponentType.P_DISPLAYCASE_LEVEL_2));
        this.phoneScreens.put(phoneScreenToggleButton2, new Component(ComponentType.P_DISPLAYCASE_LEVEL_3));
        this.phoneScreens.put(phoneScreenToggleButton3, new Component(ComponentType.P_DISPLAYCASE_LEVEL_4));
        this.phoneScreens.put(phoneScreenToggleButton4, new Component(ComponentType.P_DISPLAYCASE_LEVEL_5));
        this.phoneScreens.put(phoneScreenToggleButton5, new Component(ComponentType.P_DISPLAYCASE_LEVEL_6));

        this.phoneCameras = new HashMap<>();
        this.phoneCameras.put(phoneCameraToggleButton0, new Component(ComponentType.P_CAMERA_LEVEL_1));
        this.phoneCameras.put(phoneCameraToggleButton1, new Component(ComponentType.P_CAMERA_LEVEL_2));
        this.phoneCameras.put(phoneCameraToggleButton2, new Component(ComponentType.P_CAMERA_LEVEL_3));
        this.phoneCameras.put(phoneCameraToggleButton3, new Component(ComponentType.P_CAMERA_LEVEL_4));
        this.phoneCameras.put(phoneCameraToggleButton4, new Component(ComponentType.P_CAMERA_LEVEL_5));

        this.phoneConnectivities = new HashMap<>();
        this.phoneConnectivities.put(phoneConnectivyToggleButton0, new Component(ComponentType.P_CONNECTIVITY_LEVEL_1));
        this.phoneConnectivities.put(phoneConnectivyToggleButton1, new Component(ComponentType.P_CONNECTIVITY_LEVEL_2));
        this.phoneConnectivities.put(phoneConnectivyToggleButton2, new Component(ComponentType.P_CONNECTIVITY_LEVEL_3));

        this.phonePowersupplies = new HashMap<>();
        this.phonePowersupplies.put(phonePowersupplyToggleButton0, new Component(ComponentType.P_POWERSUPPLY_LEVEL_1));
        this.phonePowersupplies.put(phonePowersupplyToggleButton1, new Component(ComponentType.P_POWERSUPPLY_LEVEL_2));
        this.phonePowersupplies.put(phonePowersupplyToggleButton2, new Component(ComponentType.P_POWERSUPPLY_LEVEL_3));

        this.phoneKeypads = new HashMap<>();
        this.phoneKeypads.put(phoneKeypadToggleButton0, new Component(ComponentType.P_KEYPAD_LEVEL_1));
        this.phoneKeypads.put(phoneKeypadToggleButton1, new Component(ComponentType.P_KEYPAD_LEVEL_2));

        this.tvComponents = new ArrayList<>();
        this.tvComponents.add(tvScreens);
        this.tvComponents.add(tvAudios);
        this.tvComponents.add(tvOSs);
        this.tvComponents.add(tvCases);

        this.consoleComponents = new ArrayList<>();
        this.consoleComponents.add(consoleCPUs);
        this.consoleComponents.add(consoleScreens);
        this.consoleComponents.add(consolePowersupplies);
        this.consoleComponents.add(consoleConnectivities);
        this.consoleComponents.add(consoleCameras);

        this.notebookComponents = new ArrayList<>();
        this.notebookComponents.add(notebookCPUs);
        this.notebookComponents.add(notebookStorages);
        this.notebookComponents.add(notebookScreens);
        this.notebookComponents.add(notebookSoftwares);
        this.notebookComponents.add(notebookPowersupplies);

        this.phoneComponents = new ArrayList<>();
        this.phoneComponents.add(phoneCPUs);
        this.phoneComponents.add(phoneScreens);
        this.phoneComponents.add(phoneCameras);
        this.phoneComponents.add(phoneConnectivities);
        this.phoneComponents.add(phonePowersupplies);
        this.phoneComponents.add(phoneKeypads);

        tvComponentsToggleGroup = new ToggleGroup();
        consoleComponentsToggleGroup = new ToggleGroup();
        notebookComponentsToggleGroup = new ToggleGroup();
        phoneComponentsToggleGroup = new ToggleGroup();


        this.tvScreenToggleButton0.setToggleGroup(tvComponentsToggleGroup);
        this.tvScreenToggleButton1.setToggleGroup(tvComponentsToggleGroup);
        this.tvScreenToggleButton2.setToggleGroup(tvComponentsToggleGroup);
        this.tvScreenToggleButton3.setToggleGroup(tvComponentsToggleGroup);
        this.tvScreenToggleButton4.setToggleGroup(tvComponentsToggleGroup);
        this.tvScreenToggleButton5.setToggleGroup(tvComponentsToggleGroup);
        this.tvScreenToggleButton6.setToggleGroup(tvComponentsToggleGroup);

        this.tvAudioToggleButton0.setToggleGroup(tvComponentsToggleGroup);
        this.tvAudioToggleButton1.setToggleGroup(tvComponentsToggleGroup);
        this.tvAudioToggleButton2.setToggleGroup(tvComponentsToggleGroup);
        this.tvAudioToggleButton3.setToggleGroup(tvComponentsToggleGroup);
        this.tvAudioToggleButton4.setToggleGroup(tvComponentsToggleGroup);
        this.tvAudioToggleButton5.setToggleGroup(tvComponentsToggleGroup);
        this.tvAudioToggleButton6.setToggleGroup(tvComponentsToggleGroup);

        this.tvOSToggleButton0.setToggleGroup(tvComponentsToggleGroup);
        this.tvOSToggleButton1.setToggleGroup(tvComponentsToggleGroup);
        this.tvOSToggleButton2.setToggleGroup(tvComponentsToggleGroup);
        this.tvOSToggleButton3.setToggleGroup(tvComponentsToggleGroup);
        this.tvOSToggleButton4.setToggleGroup(tvComponentsToggleGroup);
        this.tvOSToggleButton5.setToggleGroup(tvComponentsToggleGroup);
        this.tvOSToggleButton6.setToggleGroup(tvComponentsToggleGroup);

        this.tvCaseToggleButton0.setToggleGroup(tvComponentsToggleGroup);
        this.tvCaseToggleButton1.setToggleGroup(tvComponentsToggleGroup);
        this.tvCaseToggleButton2.setToggleGroup(tvComponentsToggleGroup);
        this.tvCaseToggleButton3.setToggleGroup(tvComponentsToggleGroup);

        this.consoleCPUToggleButton0.setToggleGroup(consoleComponentsToggleGroup);
        this.consoleCPUToggleButton1.setToggleGroup(consoleComponentsToggleGroup);
        this.consoleCPUToggleButton2.setToggleGroup(consoleComponentsToggleGroup);
        this.consoleCPUToggleButton3.setToggleGroup(consoleComponentsToggleGroup);
        this.consoleCPUToggleButton4.setToggleGroup(consoleComponentsToggleGroup);
        this.consoleCPUToggleButton5.setToggleGroup(consoleComponentsToggleGroup);
        this.consoleCPUToggleButton6.setToggleGroup(consoleComponentsToggleGroup);

        this.consoleScreenToggleButton0.setToggleGroup(consoleComponentsToggleGroup);
        this.consoleScreenToggleButton1.setToggleGroup(consoleComponentsToggleGroup);
        this.consoleScreenToggleButton2.setToggleGroup(consoleComponentsToggleGroup);
        this.consoleScreenToggleButton3.setToggleGroup(consoleComponentsToggleGroup);

        this.consolePowersupplyToggleButton0.setToggleGroup(consoleComponentsToggleGroup);
        this.consolePowersupplyToggleButton1.setToggleGroup(consoleComponentsToggleGroup);
        this.consolePowersupplyToggleButton2.setToggleGroup(consoleComponentsToggleGroup);
        this.consolePowersupplyToggleButton3.setToggleGroup(consoleComponentsToggleGroup);
        this.consolePowersupplyToggleButton4.setToggleGroup(consoleComponentsToggleGroup);
        this.consolePowersupplyToggleButton5.setToggleGroup(consoleComponentsToggleGroup);

        this.consoleConnectivityToggleButton0.setToggleGroup(consoleComponentsToggleGroup);
        this.consoleConnectivityToggleButton1.setToggleGroup(consoleComponentsToggleGroup);
        this.consoleConnectivityToggleButton2.setToggleGroup(consoleComponentsToggleGroup);

        this.consoleCameraToggleButton0.setToggleGroup(consoleComponentsToggleGroup);
        this.consoleCameraToggleButton1.setToggleGroup(consoleComponentsToggleGroup);

        this.notebookCPUToggleButton0.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookCPUToggleButton1.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookCPUToggleButton2.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookCPUToggleButton3.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookCPUToggleButton4.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookCPUToggleButton5.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookCPUToggleButton6.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookCPUToggleButton7.setToggleGroup(notebookComponentsToggleGroup);

        this.notebookStorageToggleButton0.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookStorageToggleButton1.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookStorageToggleButton2.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookStorageToggleButton3.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookStorageToggleButton4.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookStorageToggleButton5.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookStorageToggleButton6.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookStorageToggleButton7.setToggleGroup(notebookComponentsToggleGroup);

        this.notebookScreenToggleButton0.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookScreenToggleButton1.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookScreenToggleButton2.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookScreenToggleButton3.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookScreenToggleButton4.setToggleGroup(notebookComponentsToggleGroup);

        this.notebookSoftwareToggleButton0.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookSoftwareToggleButton1.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookSoftwareToggleButton2.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookSoftwareToggleButton3.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookSoftwareToggleButton4.setToggleGroup(notebookComponentsToggleGroup);
        this.notebookSoftwareToggleButton5.setToggleGroup(notebookComponentsToggleGroup);

        this.notebookPowersupplyToggleButton0.setToggleGroup(notebookComponentsToggleGroup);

        this.phoneCPUToggleButton0.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneCPUToggleButton1.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneCPUToggleButton2.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneCPUToggleButton3.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneCPUToggleButton4.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneCPUToggleButton5.setToggleGroup(phoneComponentsToggleGroup);

        this.phoneScreenToggleButton0.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneScreenToggleButton1.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneScreenToggleButton2.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneScreenToggleButton3.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneScreenToggleButton4.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneScreenToggleButton5.setToggleGroup(phoneComponentsToggleGroup);

        this.phoneCameraToggleButton0.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneCameraToggleButton1.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneCameraToggleButton2.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneCameraToggleButton3.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneCameraToggleButton4.setToggleGroup(phoneComponentsToggleGroup);

        this.phoneConnectivyToggleButton0.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneConnectivyToggleButton1.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneConnectivyToggleButton2.setToggleGroup(phoneComponentsToggleGroup);

        this.phonePowersupplyToggleButton0.setToggleGroup(phoneComponentsToggleGroup);
        this.phonePowersupplyToggleButton1.setToggleGroup(phoneComponentsToggleGroup);
        this.phonePowersupplyToggleButton2.setToggleGroup(phoneComponentsToggleGroup);

        this.phoneKeypadToggleButton0.setToggleGroup(phoneComponentsToggleGroup);
        this.phoneKeypadToggleButton1.setToggleGroup(phoneComponentsToggleGroup);

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
        allComponents.add(notebookStorages);
        allComponents.add(notebookScreens);
        allComponents.add(notebookSoftwares);
        allComponents.add(notebookPowersupplies);
        allComponents.add(phoneCPUs);
        allComponents.add(phoneScreens);
        allComponents.add(phoneCameras);
        allComponents.add(phoneConnectivities);
        allComponents.add(phonePowersupplies);
        allComponents.add(phoneKeypads);

        LocalDate gameDate = GameState.getInstance().getGameDate();

        for(Map<ToggleButton, Component> componentMap : allComponents) {
            for(Map.Entry<ToggleButton, Component> componentEntry : componentMap.entrySet()) {
                componentEntry.getKey().setText(componentEntry.getValue().getComponentName(UIManager.getInstance().getLanguage()));
                if(!componentEntry.getValue().isAvailable(gameDate)) {
                    componentEntry.getKey().setDisable(true);
                }
            }
        }

        this.updateSuppliers();
    }

    public void updateSuppliers() {
        Component selectedTvComponent = new Component(ComponentType.DUMMY);
        for(Map<ToggleButton, Component> tvComponentsMap : this.tvComponents) {
            if(tvComponentsMap.get(tvComponentsToggleGroup.getSelectedToggle()) != null) {
                selectedTvComponent = tvComponentsMap.get(tvComponentsToggleGroup.getSelectedToggle());
            }
        }

        Component selectedConsoleComponent = new Component(ComponentType.DUMMY);
        for(Map<ToggleButton, Component> consoleComponentsMap : this.consoleComponents) {
            if(consoleComponentsMap.get(consoleComponentsToggleGroup.getSelectedToggle()) != null) {
                selectedConsoleComponent = consoleComponentsMap.get(consoleComponentsToggleGroup.getSelectedToggle());
            }
        }

        Component selectedNotebookComponent = new Component(ComponentType.DUMMY);
        for(Map<ToggleButton, Component> notebookComponentsMap : this.notebookComponents) {
            if(notebookComponentsMap.get(notebookComponentsToggleGroup.getSelectedToggle()) != null) {
                selectedNotebookComponent = notebookComponentsMap.get(notebookComponentsToggleGroup.getSelectedToggle());
            }
        }
        Component selectedPhoneComponent = new Component(ComponentType.DUMMY);
        for(Map<ToggleButton, Component> phoneComponentsMap : this.phoneComponents) {
            if(phoneComponentsMap.get(phoneComponentsToggleGroup.getSelectedToggle()) != null) {
                selectedPhoneComponent = phoneComponentsMap.get(phoneComponentsToggleGroup.getSelectedToggle());
            }
        }

        this.tvComponentList = FXCollections.observableArrayList();
        this.consoleComponentList  = FXCollections.observableArrayList();
        this.notebookComponentList  = FXCollections.observableArrayList();
        this.phoneComponentList  = FXCollections.observableArrayList();

        for(int i=0; i <3; i++) {
            Component tmpComp = new Component(selectedTvComponent.getComponentType());
            switch (i) {
                case 0:
                    tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                    break;
                case 1:
                    tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                    break;
                case 2:
                    tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                    break;
                default:
                    break;
            }
            this.tvComponentList.add(tmpComp);
        }

        for(int i=0; i <3; i++) {
            Component tmpComp = new Component(selectedConsoleComponent.getComponentType());
            switch (i) {
                case 0:
                    tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                    break;
                case 1:
                    tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                    break;
                case 2:
                    tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                    break;
                default:
                    break;
            }
            this.consoleComponentList.add(tmpComp);
        }

        for(int i=0; i <3; i++) {
            Component tmpComp = new Component(selectedNotebookComponent.getComponentType());
            switch (i) {
                case 0:
                    tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                    break;
                case 1:
                    tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                    break;
                case 2:
                    tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                    break;
                default:
                    break;
            }
            this.notebookComponentList.add(tmpComp);
        }

        for(int i=0; i <3; i++) {
            Component tmpComp = new Component(selectedPhoneComponent.getComponentType());
            switch (i) {
                case 0:
                    tmpComp.setSupplierCategory(SupplierCategory.CHEAP);
                    break;
                case 1:
                    tmpComp.setSupplierCategory(SupplierCategory.REGULAR);
                    break;
                case 2:
                    tmpComp.setSupplierCategory(SupplierCategory.PREMIUM);
                    break;
                default:
                    break;
            }
            this.phoneComponentList.add(tmpComp);
        }

        DecimalFormat decimalFormat = new DecimalFormat("$###,###.##");
        LocalDate gameDate = GameState.getInstance().getGameDate();
        BuyComponentController.ComponentStringConverter componentStringConverter = new BuyComponentController.ComponentStringConverter();

        switch(tvComponentList.get(0).getComponentCategory()) {
            case T_DISPLAY:
                this.tvScreensChoiceBox.setItems(this.tvComponentList);
                this.tvScreensChoiceBox.setConverter(componentStringConverter);
                this.tvScreensChoiceBox.setValue(this.tvComponentList.get(0));
                break;
            case T_OS:
                this.tvOSsChoiceBox.setItems(this.tvComponentList);
                this.tvOSsChoiceBox.setConverter(componentStringConverter);
                this.tvOSsChoiceBox.setValue(this.tvComponentList.get(0));
                break;
            case T_SOUND:
                this.tvAudiosChoiceBox.setItems(this.tvComponentList);
                this.tvAudiosChoiceBox.setConverter(componentStringConverter);
                this.tvAudiosChoiceBox.setValue(this.tvComponentList.get(0));
                break;
            case T_CASE:
                this.tvCasesChoiceBox.setItems(this.tvComponentList);
                this.tvCasesChoiceBox.setConverter(componentStringConverter);
                this.tvCasesChoiceBox.setValue(this.tvComponentList.get(0));
                break;
            default:
        }

        switch(consoleComponentList.get(0).getComponentCategory()) {
            case G_DISPLAYCASE:
                this.consoleScreensChoiceBox.setItems(this.consoleComponentList);
                this.consoleScreensChoiceBox.setConverter(componentStringConverter);
                this.consoleScreensChoiceBox.setValue(this.consoleComponentList.get(0));
                break;
            case G_POWERSUPPLY:
                this.consolePowersuppliesChoiceBox.setItems(this.consoleComponentList);
                this.consolePowersuppliesChoiceBox.setConverter(componentStringConverter);
                this.consolePowersuppliesChoiceBox.setValue(this.consoleComponentList.get(0));
                break;
            case G_CPU:
                this.consoleCPUsChoiceBox.setItems(this.consoleComponentList);
                this.consoleCPUsChoiceBox.setConverter(componentStringConverter);
                this.consoleCPUsChoiceBox.setValue(this.consoleComponentList.get(0));
                break;
            case G_CONNECTIVITY:
                this.consoleConnectivitiesChoiceBox.setItems(this.consoleComponentList);
                this.consoleConnectivitiesChoiceBox.setConverter(componentStringConverter);
                this.consoleConnectivitiesChoiceBox.setValue(this.consoleComponentList.get(0));
                break;
            case G_CAMERA:
                this.consoleCamerasChoiceBox.setItems(this.consoleComponentList);
                this.consoleCamerasChoiceBox.setConverter(componentStringConverter);
                this.consoleCamerasChoiceBox.setValue(this.consoleComponentList.get(0));
                break;
            default:
        }

        switch(notebookComponentList.get(0).getComponentCategory()) {
            case N_CPU:
                this.notebookCPUsChoiceBox.setItems(this.notebookComponentList);
                this.notebookCPUsChoiceBox.setConverter(componentStringConverter);
                this.notebookCPUsChoiceBox.setValue(this.notebookComponentList.get(0));
                break;
            case N_DISPLAYCASE:
                this.notebookScreensChoiceBox.setItems(this.notebookComponentList);
                this.notebookScreensChoiceBox.setConverter(componentStringConverter);
                this.notebookScreensChoiceBox.setValue(this.notebookComponentList.get(0));
                break;
            case N_SOFTWARE:
                this.notebookSoftwaresChoiceBox.setItems(this.notebookComponentList);
                this.notebookSoftwaresChoiceBox.setConverter(componentStringConverter);
                this.notebookSoftwaresChoiceBox.setValue(this.notebookComponentList.get(0));
                break;
            case N_STORAGE:
                this.notebookStoragesChoiceBox.setItems(this.notebookComponentList);
                this.notebookStoragesChoiceBox.setConverter(componentStringConverter);
                this.notebookStoragesChoiceBox.setValue(this.notebookComponentList.get(0));
                break;
            case N_POWERSUPPLY:
                this.notebookPowersuppliesChoiceBox.setItems(this.notebookComponentList);
                this.notebookPowersuppliesChoiceBox.setConverter(componentStringConverter);
                this.notebookPowersuppliesChoiceBox.setValue(this.notebookComponentList.get(0));
                break;
        }

        switch(phoneComponentList.get(0).getComponentCategory()) {
            case P_POWERSUPPLY:
                this.phonePowersuppliesChoiceBox.setItems(this.phoneComponentList);
                this.phonePowersuppliesChoiceBox.setConverter(componentStringConverter);
                this.phonePowersuppliesChoiceBox.setValue(this.phoneComponentList.get(0));
                break;
            case P_DISPLAYCASE:
                this.phoneScreensChoiceBox.setItems(this.phoneComponentList);
                this.phoneScreensChoiceBox.setConverter(componentStringConverter);
                this.phoneScreensChoiceBox.setValue(this.phoneComponentList.get(0));
                break;
            case P_KEYPAD:
                this.phoneKeypadsChoiceBox.setItems(this.phoneComponentList);
                this.phoneKeypadsChoiceBox.setConverter(componentStringConverter);
                this.phoneKeypadsChoiceBox.setValue(this.phoneComponentList.get(0));
                break;
            case P_CPU:
                this.phoneCPUsChoiceBox.setItems(this.phoneComponentList);
                this.phoneCPUsChoiceBox.setConverter(componentStringConverter);
                this.phoneCPUsChoiceBox.setValue(this.phoneComponentList.get(0));
                break;
            case P_CAMERA:
                this.phoneCamerasChoiceBox.setItems(this.phoneComponentList);
                this.phoneCamerasChoiceBox.setConverter(componentStringConverter);
                this.phoneCamerasChoiceBox.setValue(this.phoneComponentList.get(0));
                break;
            case P_CONNECTIVITY:
                this.phoneConnectivitiesChoiceBox.setItems(this.phoneComponentList);
                this.phoneConnectivitiesChoiceBox.setConverter(componentStringConverter);
                this.phoneConnectivitiesChoiceBox.setValue(this.phoneComponentList.get(0));
                break;
            default:
        }
    }

    public void buyTvComponents() {
        LocalDate gameDate = GameState.getInstance().getGameDate();
        Component tmpComponent = new Component(ComponentType.DUMMY);
        switch(tvComponentList.get(0).getComponentCategory()) {
            case T_DISPLAY:
                tmpComponent = this.tvScreensChoiceBox.getValue();
                break;
            case T_OS:
                tmpComponent = this.tvOSsChoiceBox.getValue();
                break;
            case T_SOUND:
                tmpComponent = this.tvAudiosChoiceBox.getValue();
                break;
            case T_CASE:
                tmpComponent = this.tvCasesChoiceBox.getValue();
                break;
            default:
        }
        int amount = 0;
        if(tvComponentAmountTextField.getText().equals("")) {
            amount = 5;
        } else {
            Integer.valueOf(tvComponentAmountTextField.getText());
        }
        GameController.getInstance().buyComponents(gameDate, tmpComponent, amount, GameController.getInstance().getFreeStorage());
    }

    public void buyConsoleComponents() {
        LocalDate gameDate = GameState.getInstance().getGameDate();
        Component tmpComponent = new Component(ComponentType.DUMMY);
        switch(consoleComponentList.get(0).getComponentCategory()) {
            case G_DISPLAYCASE:
                tmpComponent = this.consoleScreensChoiceBox.getValue();
                break;
            case G_POWERSUPPLY:
                tmpComponent = this.consolePowersuppliesChoiceBox.getValue();
                break;
            case G_CPU:
                tmpComponent = this.consoleCPUsChoiceBox.getValue();
                break;
            case G_CONNECTIVITY:
                tmpComponent = this.consoleConnectivitiesChoiceBox.getValue();
                break;
            case G_CAMERA:
                tmpComponent = this.consoleCamerasChoiceBox.getValue();
                break;
            default:
        }
        int amount = 0;
        if(consoleComponentAmountTextField.getText().equals("")) {
            amount = 5;
        } else {
            Integer.valueOf(consoleComponentAmountTextField.getText());
        }
        GameController.getInstance().buyComponents(gameDate, tmpComponent, amount, GameController.getInstance().getFreeStorage());
    }

    public void buyNotebookComponents() {
        LocalDate gameDate = GameState.getInstance().getGameDate();
        Component tmpComponent = new Component(ComponentType.DUMMY);
        switch(notebookComponentList.get(0).getComponentCategory()) {
            case N_CPU:
                tmpComponent = this.notebookCPUsChoiceBox.getValue();
                break;
            case N_DISPLAYCASE:
                tmpComponent = this.notebookScreensChoiceBox.getValue();
                break;
            case N_SOFTWARE:
                tmpComponent = this.notebookSoftwaresChoiceBox.getValue();
                break;
            case N_STORAGE:
                tmpComponent = this.notebookStoragesChoiceBox.getValue();
                break;
            case N_POWERSUPPLY:
                tmpComponent = this.notebookPowersuppliesChoiceBox.getValue();
                break;
            default:
        }
        int amount = 0;
        if(notebookComponentAmountTextField.getText().equals("")) {
            amount = 5;
        } else {
            Integer.valueOf(notebookComponentAmountTextField.getText());
        }
        GameController.getInstance().buyComponents(gameDate, tmpComponent, amount, GameController.getInstance().getFreeStorage());
    }

    public void buyPhoneComponents() {
        LocalDate gameDate = GameState.getInstance().getGameDate();
        Component tmpComponent = new Component(ComponentType.DUMMY);
        switch(phoneComponentList.get(0).getComponentCategory()) {
            case P_POWERSUPPLY:
                tmpComponent = this.phonePowersuppliesChoiceBox.getValue();
                break;
            case P_DISPLAYCASE:
                tmpComponent = this.phoneScreensChoiceBox.getValue();
                break;
            case P_KEYPAD:
                tmpComponent = this.phoneKeypadsChoiceBox.getValue();
                break;
            case P_CPU:
                tmpComponent = this.phoneCPUsChoiceBox.getValue();
                break;
            case P_CAMERA:
                tmpComponent = this.phoneCamerasChoiceBox.getValue();
                break;
            case P_CONNECTIVITY:
                tmpComponent = this.phoneConnectivitiesChoiceBox.getValue();
                break;
            default:
        }
        int amount = 0;
        if(phoneComponentAmountTextField.getText().equals("")) {
            amount = 5;
        } else {
            Integer.valueOf(phoneComponentAmountTextField.getText());
        }
        GameController.getInstance().buyComponents(gameDate, tmpComponent, amount, GameController.getInstance().getFreeStorage());
    }


    class ComponentStringConverter extends StringConverter<Component> {
        @Override
        public String toString(Component component) {
            DecimalFormat decimalFormat = new DecimalFormat("$###,###.##");
            LocalDate gameDate = GameState.getInstance().getGameDate();
            return "" + component.getSupplierCategory().toString().substring(0, component.getSupplierCategory().toString().length() - 9) + " (" + decimalFormat.format(component.calculateBaseCost(GameState.getInstance().getGameDate())) + ")";
        }

        @Override
        public Component fromString(String s) {
            return null;
        }
    }
}
