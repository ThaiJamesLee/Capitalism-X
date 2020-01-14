package de.uni.mannheim.capitalismx.ui.controller.module.production;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import de.uni.mannheim.capitalismx.production.InvalidSetOfComponentsException;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.ProductCategory;
import de.uni.mannheim.capitalismx.ui.components.production.LaunchedProductsListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProduceProductController extends GameModuleController {

    @FXML
    ListView<Product> launchedProductsListView;

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GameController controller = GameController.getInstance();
        LocalDate gameDate = GameState.getInstance().getGameDate();

        launchedProductsListView.setCellFactory(launchedProductsListView -> new LaunchedProductsListViewCell(launchedProductsListView));

        List<Component> components = new ArrayList<>();
        components.add(new Component(ComponentType.T_DISPLAY_LEVEL_1, SupplierCategory.CHEAP, gameDate));
        components.add(new Component(ComponentType.T_CASE_LEVEL_1, SupplierCategory.CHEAP, gameDate));
        components.add(new Component(ComponentType.T_SOUND_LEVEL_1, SupplierCategory.CHEAP, gameDate));
        components.add(new Component(ComponentType.T_OS_LEVEL_1, SupplierCategory.CHEAP, gameDate));
        try {
            Product p = new Product("CAPTV", ProductCategory.TELEVISION, components);
            launchedProductsListView.getItems().add(p);
        } catch (InvalidSetOfComponentsException e) {
            e.printStackTrace();
        }
    }
}
