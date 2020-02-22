package de.uni.mannheim.capitalismx.ui.controller.module.sales;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SalesContractInfoController implements Initializable {

    @FXML
    private Label productInfo;

    @FXML
    private Label contractorInfo;

    @FXML
    private Label unitNumberInfo;

    @FXML
    private Label totalVolumeInfo;

    @FXML
    private Label productPriceInfo;

    @FXML
    private Label timeToFinish;

    @FXML
    private Label startDate;

    @FXML
    private Label deadlineInfo;

    @FXML
    private Label penalty;

    public SalesContractInfoController(){

    }
/*
    public SalesContractInfoController(String productInfo, String contractorInfo, String unitNumberInfo, String totalNumberInfo,
                                       String productPriceInfo, String timeToFinish, String startDate, String deadlineInfo, String penalty){
        this.productInfo.setText("");
        this.contractorInfo.setText("");
        this.unitNumberInfo.setText("");
        this.totalNumberInfo.setText("");
        this.productPriceInfo.setText("");
        this.timeToFinish.setText("");
        this.startDate.setText("");
        this.deadlineInfo.setText("");
        this.penalty.setText("");
    }
*/
    public void setInfoPanel(String productInfo, String contractorInfo, String unitNumberInfo, String totalNumberInfo,
                                       String productPriceInfo, String timeToFinish, String startDate, String deadlineInfo, String penalty){
        this.productInfo.setText(productInfo);
        this.contractorInfo.setText(contractorInfo);
        this.unitNumberInfo.setText(unitNumberInfo);
        this.totalVolumeInfo.setText(totalNumberInfo);
        this.productPriceInfo.setText(productPriceInfo);
        this.timeToFinish.setText(timeToFinish);
        this.startDate.setText(startDate);
        this.deadlineInfo.setText(deadlineInfo);
        this.penalty.setText(penalty);
    }

    public void setProductInfo(Label productInfo) {
        this.productInfo = productInfo;
    }

    public void setContractorInfo(Label contractorInfo) {
        this.contractorInfo = contractorInfo;
    }

    public void setUnitNumberInfo(Label unitNumberInfo) {
        this.unitNumberInfo = unitNumberInfo;
    }

    public void setTotalVolumeInfo(Label totalNumberInfo) {
        this.totalVolumeInfo = totalNumberInfo;
    }

    public void setProductPriceInfo(Label productPriceInfo) {
        this.productPriceInfo = productPriceInfo;
    }

    public void setTimeToFinish(Label timeToFinish) {
        this.timeToFinish = timeToFinish;
    }

    public void setStartDate(Label startDate) {
        this.startDate = startDate;
    }

    public void setDeadlineInfo(Label deadlineInfo) {
        this.deadlineInfo = deadlineInfo;
    }

    public void setPenalty(Label penalty) {
        this.penalty = penalty;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.productInfo.setText("");
        this.contractorInfo.setText("");
        this.unitNumberInfo.setText("");
        this.totalVolumeInfo.setText("");
        this.productPriceInfo.setText("");
        this.timeToFinish.setText("");
        this.startDate.setText("");
        this.deadlineInfo.setText("");
        this.penalty.setText("");
    }
}
