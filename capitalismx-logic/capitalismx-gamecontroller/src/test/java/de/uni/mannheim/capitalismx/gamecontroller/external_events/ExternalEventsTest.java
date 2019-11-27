package de.uni.mannheim.capitalismx.gamecontroller.external_events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class ExternalEventsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalEventsTest.class);

    @Test
    public void checkEventsTest () {
        ExternalEvents externalEvents = ExternalEvents.getInstance();

        Assert.assertNotNull(externalEvents.checkEvents(LocalDate.now()));
    }

}