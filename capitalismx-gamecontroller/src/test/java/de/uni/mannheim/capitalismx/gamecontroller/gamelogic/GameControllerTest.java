package de.uni.mannheim.capitalismx.gamecontroller.gamelogic;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.internal.thread.ThreadTimeoutException;

import java.time.LocalDate;

public class GameControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameControllerTest.class);

    @Test(timeOut = 5000, expectedExceptions = {ThreadTimeoutException.class})
    public void nextDayTest() {
        GameState.getInstance().initiate();
        GameController.getInstance().start();
    }

    @Test(dependsOnMethods = "nextDayTest")
    public void gameDateValueTest() {
        LocalDate gameDate = GameState.getInstance().getGameDate();
        Assert.assertTrue(gameDate.isAfter(LocalDate.of(1990, 1, 1)));
    }

    @AfterTest
    public void cleanUp() {
        LOGGER.info("Clean Up: Destroy GameState Singleton.");
        GameState.setInstance(GameState.createInstance());
    }
}
