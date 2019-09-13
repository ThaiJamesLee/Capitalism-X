package de.uni.mannheim.capitalismx.hr.salary;

import de.uni.mannheim.capitalismx.hr.employee.EmployeeGenerator;
import de.uni.mannheim.capitalismx.utils.adapter.ServiceAdapter;
import de.uni.mannheim.capitalismx.utils.namegenerator.NameGenerator;
import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;
import de.uni.mannheim.capitalismx.utils.reader.JsonFileReader;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class EmployeeGeneratorTest {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeGeneratorTest.class);

    private static final NameGenerator namegenerator = Mockito.mock(NameGenerator.class);
    private static final ServiceAdapter adapter = Mockito.mock(ServiceAdapter.class);

    @BeforeTest
    public void setUp() throws IOException {
        //String json = new JsonFileReader().readJsonFileFromResourceUnitTests();
        //List<String> personJson = new JsonFileReader().parseJsonArrayToStringList(json);
        String api1 = NameGenerator.getInstance().getApi1()[0] + NameGenerator.getInstance().getApi1()[1];

        //Mockito.when(adapter.getGeneratedUser(Mockito.anyString())).thenReturn(personJson.get(RandomNumberGenerator.getRandomInt(0, personJson.size()-1)));
        //Mockito.when(namegenerator.getGeneratedPersonMeta()).thenReturn(NameGenerator.getInstance().parseAPI1()));

        namegenerator.setAdapter(adapter);
    }


    @Test
    public void generateEngineerTest () {
        EmployeeGenerator generator = EmployeeGenerator.getInstance();

        for (int i = 0; i < 5 ; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
            Assert.assertNotNull(generator.generateEngineer(i*10));

        }
    }

    //@Test(expectedExceptions = NoDefinedTierException.class)
    @Test
    public void generateEngineerSkillLevelTestI () {
        EmployeeGenerator generator = EmployeeGenerator.getInstance();


        for (int i = 101; i < 110; i++) {
            Assert.assertNull(generator.generateEngineer(i));

        }
     }

    //@Test(expectedExceptions = NoDefinedTierException.class)
    @Test
    public void generateEngineerSkillLevelTestII () {
        EmployeeGenerator generator = EmployeeGenerator.getInstance();


        for (int i = -10; i < -1; i++) {
            Assert.assertNull(generator.generateEngineer(i));

        }
    }
}
