package de.uni.mannheim.capitalismx.domain.employee;

import de.uni.mannheim.capitalismx.utils.data.PersonMeta;
import de.uni.mannheim.capitalismx.utils.namegenerator.NameGenerator;
import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;
import de.uni.mannheim.capitalismx.utils.reader.JsonFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * This class caches pre-generated person entities.
 * It is a stack of persons that are pre-generated.
 * The goal is to reduce the number API-calls.
 * @author duly
 */
public class EmployeeMarketSample implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeMarketSample.class);

    private static final int MINIMUM_SAMPLE_SIZE = 500;
    private static final String EMPLOYEE_SAMPLE_FILE_DIR = "data" + File.separator + "employeesample.capx";

    private String rootDir;

    /* Idea: depending on level of progress, the chance of being able to higher employees with higher skill level increases.
    * Therefore, we currently have 4 different distribution of the employee sample */
    private static final double[][] DISTRIBUTION =
            {
                    {50, 24, 21, 3, 2},
                    {50, 22, 20, 5, 3},
                    {45, 20, 15, 6, 4},
                    {40, 15, 20, 15, 10}
            };

    private List<PersonMeta> personMetas;

    private String filePath;
    private List<Employee> potentialEmployeeSample;


    public EmployeeMarketSample() {
        rootDir = System.getProperty("user.dir");
        filePath = rootDir + File.separator + EMPLOYEE_SAMPLE_FILE_DIR;
        potentialEmployeeSample = new ArrayList<>();
        personMetas = new ArrayList<>();
        loadInitialPersonList();
    }

    /**
     *
     * @param rootDir The parent folder of the data folder, that contains the employeesample.capx file.
     */
    public EmployeeMarketSample(String rootDir) {
        this.rootDir = rootDir;
        filePath = this.rootDir + File.separator + EMPLOYEE_SAMPLE_FILE_DIR;
        potentialEmployeeSample = new ArrayList<>();
        personMetas = new ArrayList<>();
        loadInitialPersonList();
    }

    /**
     * Load the json file a
     */
    private void loadInitialPersonList() {
        // load employeesample.capx file first.
        loadSample();

        // use jsons if no file exists.
        if(personMetas.isEmpty()) {
            JsonFileReader jReader = new JsonFileReader();
            String jsonArrayDE = jReader.readJsonFileFromResourceAsStream(jReader.getFileNameDE());
            String jsonArrayBE = jReader.readJsonFileFromResourceAsStream(jReader.getFileNameBE());
            String jsonArrayUS = jReader.readJsonFileFromResourceAsStream(jReader.getFileNameUS());

            List<String> entitiesDE = jReader.parseJsonArrayToStringList(jsonArrayDE);
            List<String> entitiesBE = jReader.parseJsonArrayToStringList(jsonArrayBE);
            List<String> entitiesUS = jReader.parseJsonArrayToStringList(jsonArrayUS);

            Set<String> entities = new HashSet<>();
            entities.addAll(entitiesBE);
            entities.addAll(entitiesDE);
            entities.addAll(entitiesUS);

            for(String person : entities) {
                personMetas.add(NameGenerator.getInstance().parseAPI1(person));
            }

            Collections.shuffle(personMetas);
        }

    }



    /**
     * Load the employee stack file.
     */
    public void loadSample() {
        List<PersonMeta> personStack = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(filePath)))) {
            personStack = (List<PersonMeta>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error(e.getMessage());
        }

        if(personStack == null) {
            logger.error("Loading was not succesfull! No employeesample.capx file.");
        } else {
            personMetas = personStack;
        }
    }

    /**
     *
     * @param stack the pre-generated PersonMeta List to be saved.
     */
    public void saveSample(List<PersonMeta> stack) {
        File f = new File(rootDir + File.separator + "data");

        if(f.mkdir()) {
            logger.info("The /data folder was created.");
        }

        File file = new File(filePath);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(stack);

            objectOutputStream.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Chooses one personmeta from the sample randomly.
     * @return Returns a PersonMeta from the initial set.
     */
    public PersonMeta randomChoosing() {
        int index = RandomNumberGenerator.getRandomInt(0, personMetas.size()-1);
        PersonMeta e = personMetas.get(index);
        personMetas.remove(index);

        // when peronmetas is below 50, then use APIs to generate a new batch of 500.
        if(personMetas.size() < 50) {
            personMetas.addAll(NameGenerator.getInstance().getGeneratedPersonMeta(500));
        }

        return e;
    }

    public String getFilePath() {
        return filePath;
    }

}