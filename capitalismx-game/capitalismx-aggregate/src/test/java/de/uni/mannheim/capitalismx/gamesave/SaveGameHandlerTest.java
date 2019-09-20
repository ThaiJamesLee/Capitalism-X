package de.uni.mannheim.capitalismx.gamesave;

import de.uni.mannheim.capitalismx.gamelogic.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.File;


/**
 * @author duly
 */
public class SaveGameHandlerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveGameHandlerTest.class);
    private SaveGameHandler saveGameHandler;

    @BeforeTest
    public void setUp() {
        GameState.setInstance(null);
        GameState state = GameState.getInstance();
        state.initiate();

        saveGameHandler = new SaveGameHandler();
    }

    /**
     * Save GameState instance into a savegame file.
     */
    @Test
    public void saveGameTest() {
        saveGameHandler.saveGameState(GameState.getInstance());
    }

    /**
     * Load the save game file.
     */
    @Test(dependsOnMethods = "saveGameTest")
    public void loadGameTest() {

        try {
            GameState state = saveGameHandler.loadGameState();
            GameState.setInstance(state);

            Assert.assertNotNull(state);
            Assert.assertNotNull(state.getHrDepartment());

            Assert.assertNotNull(state.getCompanyEcoIndex());

            Assert.assertNotNull(state.getCustomerDemand());
            Assert.assertNotNull(state.getCustomerSatisfaction());

            Assert.assertNotNull(state.getExternalEvents());
            Assert.assertNotNull(state.getFinanceDepartment());
            Assert.assertNotNull(state.getLogisticsDepartment());

            Assert.assertNotNull(state.getCompanyEcoIndex());

            Assert.assertNotNull(state.getMarketingDepartment());
            Assert.assertNotNull(state.getProductionDepartment());
            Assert.assertNotNull(state.getWarehousingDepartment());

            Assert.assertTrue(state.getGameDate().toString().matches("1990-01-0[1-9]"));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    public void cleanUp() {
        String saveGameFile = saveGameHandler.getFilePath();
        File f = new File(saveGameFile);
        if(f.delete()) {
            LOGGER.info("Savegame file deleted.");
        }
        LOGGER.info("Clean Uo: Destroy GameState Singleton.");
        GameState.setInstance(null);
    }
}
