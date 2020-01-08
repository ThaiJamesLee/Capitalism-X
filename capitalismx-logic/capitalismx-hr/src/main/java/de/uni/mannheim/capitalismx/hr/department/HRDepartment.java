package de.uni.mannheim.capitalismx.hr.department;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;

import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.domain.department.LevelingMechanism;
import de.uni.mannheim.capitalismx.domain.employee.impl.ProductionWorker;
import de.uni.mannheim.capitalismx.domain.exception.InconsistentLevelException;
import de.uni.mannheim.capitalismx.hr.department.skill.HRSkill;
import de.uni.mannheim.capitalismx.hr.domain.EmployeeTier;
import de.uni.mannheim.capitalismx.utils.formatter.DataFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.employee.Employee;
import de.uni.mannheim.capitalismx.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.domain.employee.Team;
import de.uni.mannheim.capitalismx.domain.employee.Training;
import de.uni.mannheim.capitalismx.domain.employee.impl.Engineer;
import de.uni.mannheim.capitalismx.domain.employee.impl.HRWorker;
import de.uni.mannheim.capitalismx.domain.employee.impl.SalesPerson;
import de.uni.mannheim.capitalismx.hr.domain.Benefit;
import de.uni.mannheim.capitalismx.hr.domain.BenefitType;
import de.uni.mannheim.capitalismx.hr.domain.JobSatisfaction;
import de.uni.mannheim.capitalismx.hr.training.EmployeeTraining;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportList;
import de.uni.mannheim.capitalismx.utils.exception.UnsupportedValueException;

/**
 * This class represents the HR Department.
 * Here, employees are hired and fired.
 * Additionally, the employee capacity is controlled in this department by controlling the number of HR employees.
 * Based on the report on p.21 - 28
 *
 * @author duly
 */
public class HRDepartment extends DepartmentImpl {

	private static final Logger logger = LoggerFactory.getLogger(HRDepartment.class);

	/**
	 * Contains the history of the employee number for each day.
	 */
	private Map<LocalDate, Integer> employeeNumberHistory;

	/**
	 * Base Cost used for hiring and firing.
	 * Loaded from LEVELING_PROPERTIES file.
	 */
	private int baseCost;

	/**
	 * HR worker capacity. You can not hire more HR Workers than this capacity.
	 * The player can increase this value by leveling up this department.
	 */
	private int hrCapacity;

	/**
	 * The initial value of the hrCapacity.
	 */
	private int initialHrCapacity;


	private static final String LEVELING_PROPERTIES = "hr-leveling-definition";
	private static final String MAX_LEVEL_PROPERTY = "hr.department.max.level";
	private static final String BASE_COST_PROPERTY = "hr.department.base.cost";
	private static final String INITIAL_CAPACITY_PROPERTY = "hr.department.init.capacity";

	private static final String SKILL_COST_PROPERTY_PREFIX = "hr.skill.cost.";
	private static final String SKILL_CAPACITY_PREFIX = "hr.skill.capacity.";
	private static final String EMPLOYEE_DISTRIBUTION_PROPERTY_PREFIX = "hr.skill.distribution.";


	/**
	 * This contains the settings for the employee benefits.
	 * It controls the job satisfaction factor of the employees.
	 */
	private BenefitSettings benefitSettings;

	/**
	 * A map of teams. The key is the {@link EmployeeType}, with a {@link Team} as a value.
	 * Hence, each Team contains only Employees of the corresponding EmployeeType.
	 */
	private Map<EmployeeType, Team> teams;

	/**
	 * List of hired employees.
	 */
	private PropertyChangeSupportList<Employee> hired;

	/**
	 * List of fired employees.
	 */
	private PropertyChangeSupportList<Employee> fired;

	/**
	 * The singleton pointer.
	 */
	private static HRDepartment instance = null;

	private HRDepartment() {
		super("Human Resources");
		this.benefitSettings = new BenefitSettings();
		this.teams = new EnumMap<>(EmployeeType.class);

		init();
	}

	/**
	 * Init every necessary variable and properties.
	 */
	private void init() {

		initProperties();
		initSkills();

		hired = new PropertyChangeSupportList<>();
		hired.setAddPropertyName("hireEmployee");
		hired.setRemovePropertyName("fireEmployee");

		fired = new PropertyChangeSupportList<>();
		fired.setAddPropertyName("addFiredList");
		fired.setRemovePropertyName("removeFiredList");

		// Create the teams of employee types
		EmployeeType engineer = EmployeeType.ENGINEER;
		EmployeeType sales = EmployeeType.SALESPERSON;
		EmployeeType hr = EmployeeType.HR_WORKER;
		EmployeeType production = EmployeeType.PRODUCTION_WORKER;

		teams.put(engineer, new Team(engineer).addPropertyName(engineer.getTeamEventPropertyChangedKey()));
		teams.put(sales, new Team(sales).addPropertyName(sales.getTeamEventPropertyChangedKey()));
		teams.put(production, new Team(production).addPropertyName(production.getTeamEventPropertyChangedKey()));
		teams.put(hr, new Team(hr).addPropertyName(hr.getTeamEventPropertyChangedKey()));

		employeeNumberHistory = new HashMap<>();
	}

	/**
	 * Init default values from properties.
	 */
	private void initProperties() {
		setMaxLevel(Integer.parseInt(ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(MAX_LEVEL_PROPERTY)));
		this.baseCost = Integer.parseInt(ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(BASE_COST_PROPERTY));
		this.initialHrCapacity = Integer.parseInt(ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(INITIAL_CAPACITY_PROPERTY));
		this.hrCapacity = initialHrCapacity;
	}

	/**
	 * Initialize HRSkills.
	 */
	private void initSkills() {
		Map<Integer, Double> costMap = initCostMap();
		try {
			setLevelingMechanism(new LevelingMechanism(this, costMap));
		} catch (InconsistentLevelException e) {
			String error = "The costMap size " + costMap.size() +  " does not match the maximum level " + this.getMaxLevel() + " of this department!";
			logger.error(error, e);
		}

		ResourceBundle skillBundle = ResourceBundle.getBundle(LEVELING_PROPERTIES);
		for (int i=1; i <= getMaxLevel(); i++) {
			int eCapacity = Integer.parseInt(skillBundle.getString(SKILL_CAPACITY_PREFIX + i));
			skillMap.put(i, new HRSkill(i, eCapacity, getDistribution(i)));
		}
	}

	/**
	 *
	 * @param level The Department level.
	 * @return Returns a map of probability distributions for employee generation.
	 */
	private Map<String, Double> getDistribution(int level) {
		String distributionString = ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(EMPLOYEE_DISTRIBUTION_PROPERTY_PREFIX + level);
		List<String> keys = EmployeeTier.getSkillLevelNames();

		return DataFormatter.stringToStringDoubleMap(keys, distributionString);
	}


	/**
	 * Initializes the cost map. This is used for the {@link LevelingMechanism}.
	 */
	private Map<Integer, Double> initCostMap() {
		// init cost map
		/* TODO BALANCING NEEDED*/
		Map<Integer, Double> costMap = new HashMap<>();

		ResourceBundle bundle = ResourceBundle.getBundle(LEVELING_PROPERTIES);
		for(int i = 1; i <= getMaxLevel(); i++) {
			double cost = Integer.parseInt(bundle.getString(SKILL_COST_PROPERTY_PREFIX + i));
			costMap.put(i, cost);
		}

		return costMap;
	}

	/**
	 * Calculates the current capacity and updates the variable.
	 */
	private void updateHRCapacity() {
		int newCapacity = this.initialHrCapacity;
		List<DepartmentSkill> availableSkills = getAvailableSkills();

		for(DepartmentSkill skill : availableSkills) {
			newCapacity += ((HRSkill) skill).getNewEmployeeCapacity();
		}

		this.hrCapacity = newCapacity;
	}

	@Override
	public void setLevel(int level) {
		super.setLevel(level);
		updateHRCapacity();
	}

	/**
	 * Use for Tests.
	 * @return Returns a new HRDepartment instance.
	 */
	public static HRDepartment createInstance() {
		return new HRDepartment();
	}

	/**
	 *
	 * @return Returns the singleton instance.
	 */
	public static HRDepartment getInstance() {
		if (instance == null) {
			instance = new HRDepartment();
		}
		return instance;
	}

	/**
	 * Iterates over all teams and get the skill level score of each employee.
	 * Calculate the average skill level.
	 *
	 * @return the average skill level.
	 */
	public double getAverageSkillLevel() {
		int totalEmployee = 0;
		double avgSkill = 0; // total sum of skill level
		for (Map.Entry<EmployeeType, Team> entry : teams.entrySet()) {
			for (Employee e : entry.getValue().getTeam()) {
				totalEmployee++;
				avgSkill += e.getSkillLevel();
			}
		}
		if (totalEmployee > 0) {
			avgSkill /= totalEmployee;
		}
		return avgSkill;
	}

	/**
	 * The report does not define the total job satisfaction score.
	 * p.24 mentioned that the job satisfaction score depends on each employee skill level.
	 * But the defined function does not provide that.
	 *
	 * @return total job satisfaction score
	 */
	public double getTotalJSS() {
		double og = getOriginalScale();
		double totalJss = 0;
		try {
			totalJss = new JobSatisfaction().getJobSatisfactionScore(og);
		} catch (UnsupportedValueException e) {
			logger.error(e.getMessage());
		}
		return totalJss;
	}

	/**
	 * The scale is between 0 - 27 (incorrectly defined in p.24 + p.26)
	 *
	 * @return Returns the sum of points of the current benefit settings.
	 */
	private double getOriginalScale() {
		double originalScale = 0;
		for (Map.Entry<BenefitType, Benefit> entry : benefitSettings.getBenefits().entrySet()) {
			originalScale += entry.getValue().getPoints();
		}
		return originalScale;
	}

	/**
	 * Update JSS and QoW for each employee.
	 * Since it was stated that the jss depends on the skill level, but is not defined,
	 * we define it as (1 - skillLevel/100) * totalJSS.
	 */
	public void calculateAndUpdateEmployeesMeta() {
		double totalJSS = getTotalJSS();
		for (Map.Entry<EmployeeType, Team> entry : teams.entrySet()) {
			for (Employee e : entry.getValue().getTeam()) {
				e.setJobSatisfaction(totalJSS * (1.0 - e.getSkillLevel()/100.0));
				e.setQualityOfWork(e.getJobSatisfaction() * 0.5 + e.getSkillLevel() * 0.5);
			}
		}
	}

	/**
	 * Iterates over all employees and calculate the avg. jss.
	 * @return Returns the average jss.
	 */
	private double getAverageJSS() {
		double totalJSS = 0.0;
		int totalEmployees = 0;
		for (Map.Entry<EmployeeType, Team> entry : teams.entrySet()) {
			totalEmployees += entry.getValue().getTeam().size();
			for (Employee e : entry.getValue().getTeam()) {
				totalJSS += e.getJobSatisfaction();
			}
		}
		if (totalEmployees > 0) {
			return totalJSS/totalEmployees;
		}

		return 0.0;
	}

	/**
	 * The report does not mention how to calculate the overall quality of work KPI.
	 * They only defined for single employee. We assume that the average QoW will
	 * reflect the total quality of work.
	 *
	 * It is defined as QoW = JSS * 0.5 + skillLevel * 0.5.
	 *
	 * We use the average jss of all employees.
	 *
	 * @return The overall quality of work.
	 */
	public double getTotalQualityOfWork() {
		return getAverageJSS() * 0.5 + getAverageSkillLevel() * 0.5;
	}

	/**
	 * Gets the total quality of work by employee type. The score is the sum of
	 * quality of work over all employees with the specified employee type.
	 *
	 * @param employeeType The employee type of interest
	 * @return Returns the Quality of Work of the specified team.
	 */
	public double getTotalQualityOfWorkByEmployeeType(EmployeeType employeeType) {
		Team team = teams.get(employeeType);
		List<Employee> teamList = team.getTeam();
		double jss = getTotalJSS();
		double totalQoW = 0.0;

		for (Employee e : teamList) {
			totalQoW += 0.5 * e.getSkillLevel() + 0.5 * jss;
		}
		return totalQoW;
	}

	public void setTeams(Map<EmployeeType, Team> teams) {
		this.teams = teams;
	}

	public Map<EmployeeType, Team> getTeams() {
		return teams;
	}

	public Team getTeamByEmployeeType(EmployeeType type) {
		return teams.get(type);
	}

	public Team getEngineerTeam() {
		return teams.get(EmployeeType.ENGINEER);
	}

	public Team getSalesTeam() {
		return teams.get(EmployeeType.SALESPERSON);
	}

	/* Hiring and Firing */

	/**
	 *
	 * @return Returns list of hired employees.
	 */
	public PropertyChangeSupportList<Employee> getHired() {
		return hired;
	}

	/**
	 *
	 * @return Returns list of fired employees.
	 */
	public PropertyChangeSupportList<Employee> getFired() {
		return fired;
	}

	/**
	 * Hire the employee. Add the employee to the corresponding team list.
	 *
	 * @param employee The employee to hire.
	 * @return Returns the cost of hiring. Returns 0, when employee was not added.
	 */
	public double hire(Employee employee) {
		if (employee == null) {
			throw new NullPointerException("Employee must be specified!");
		} else if (canHireEmployee(employee.getEmployeeType())) {
			// add employee to a team and add him to the hired list if successful.
			if (addEmployeeToTeam(employee)) {
				hired.add(employee);
				// TODO hiring cost should also be dependent on the skill level
				return getHiringCost();
			}
		}
		return 0;
	}

	/**
	 *
	 * @param employee The employee to add to the respective team by type.
	 * @return Returns true if employee was added successfully to a team. Return false otherwise.
	 */
	private boolean addEmployeeToTeam(Employee employee) {
		boolean isAdded = true;
		if (employee instanceof Engineer) {
			teams.get(EmployeeType.ENGINEER).addEmployee(employee);
		} else if (employee instanceof SalesPerson) {
			teams.get(EmployeeType.SALESPERSON).addEmployee(employee);

		} else if (employee instanceof HRWorker)  {
			/* HR Worker capacity is fixed. */
			if (hrCapacity > teams.get(EmployeeType.HR_WORKER).getTeam().size()) {
				teams.get(EmployeeType.HR_WORKER).addEmployee(employee);
			} else {
				isAdded = false;
			}
		} else if (employee instanceof ProductionWorker) {
			teams.get(EmployeeType.HR_WORKER).addEmployee(employee);
		} else {
			isAdded = false;

			String warnMessage = "Could not assign " + employee + " to any team!";
			logger.warn(warnMessage);
		}
		return isAdded;
	}

	/**
	 * Fires the employee.
	 *
	 * @param employee the employee to fire.
	 * @return Returns the cost of the firing.
	 */
	public double fire(Employee employee) {
		if (employee == null) {
			throw new NullPointerException("Employee must be specified!");
		}

		if(removeEmployeeFromTeam(employee)) {
			// TODO firing cost should be also dependent on skill level
			fired.add(employee);
			return getFiringCost();
		}

		return 0;
	}

	/**
	 *
	 * @param employee The employee to remove from the respective team.
	 * @return Returns true when the employee was removed from the respective team. Returns false otherwise.
	 */
	private boolean removeEmployeeFromTeam(Employee employee) {
		boolean isRemoved = true;

		if (employee instanceof Engineer) {
			teams.get(EmployeeType.ENGINEER).removeEmployee(employee);
		} else if (employee instanceof SalesPerson) {
			teams.get(EmployeeType.SALESPERSON).removeEmployee(employee);
		} else if (employee instanceof  HRWorker) {
			teams.get(EmployeeType.HR_WORKER).removeEmployee(employee);
		} else if (employee instanceof ProductionWorker) {
			teams.get(EmployeeType.PRODUCTION_WORKER).removeEmployee(employee);
		} else {
			isRemoved = false;

			String warnMessage = "Could not remove " + employee + " from any team!";
			logger.warn(warnMessage);
		}
		return isRemoved;
	}

	/**
	 * See p.24
	 *
	 * @return Returns the actual hiring cost per employee.
	 */
	public double getHiringCost() {
		double jss = getTotalJSS() / 100;
		return baseCost + baseCost * (1 - jss);
	}

	/**
	 * See p.24
	 *
	 * @return Returns the actual firing cost per employee.
	 */
	public double getFiringCost() {
		return baseCost;
	}

	/* Benefits */

	public void changeBenefitSetting(Benefit benefit) {
		benefitSettings.getBenefits().put(benefit.getType(), benefit);
		calculateAndUpdateEmployeesMeta();
	}

	public BenefitSettings getBenefitSettings() {
		return benefitSettings;
	}

	public void setBenefitSettings(BenefitSettings benefitSettings) {
		this.benefitSettings = benefitSettings;
		calculateAndUpdateEmployeesMeta();
	}

	/* Trainings */

	/**
	 *
	 * @return Returns all possible Trainings.
	 */
	public Training[] getAllTrainings() {
		return Training.values();
	}

	/**
	 *
	 * @param employee The employee to train.
	 * @param training The type of training for the employee.
	 * @return Returns the cost of the training.
	 */
	public double trainEmployee(Employee employee, Training training) {
		return EmployeeTraining.getInstance().trainEmployee(employee, training);
	}

	/**
	 *
	 * @author sdupper
	 */
	public double calculateTotalSalaries() {
		double totalSalaries = 0.0;
		for (Map.Entry<EmployeeType, Team> entry : teams.entrySet()) {
			totalSalaries += entry.getValue().calculateTotalSalaries();
		}
		return totalSalaries;
	}

	/**
	 *
	 * @author sdupper
	 */
	public double calculateTotalTrainingCosts() {
		double totalTrainingCosts = 0.0;
		for (Map.Entry<EmployeeType, Team> entry : teams.entrySet()) {
			totalTrainingCosts += entry.getValue().calculateTotalTrainingCosts();
		}
		return totalTrainingCosts;
	}

	public static void setInstance(HRDepartment instance) {
		HRDepartment.instance = instance;
	}

	/**
	 * Register the propertychangelistener to all propertychangesupport objects.
	 *
	 * @param listener The PropertyChangeListener to register to listen to all PropertyChangeSupport objects.
	 */
	public void registerPropertyChangeListener(PropertyChangeListener listener) {
		for (Map.Entry<EmployeeType, Team> entry : teams.entrySet()) {
			entry.getValue().addPropertyChangeListener(listener);
		}
	}

	/**
	 * Iterates through all HR Workers and sum up the capacity of each HR Worker.
	 *
	 * @return Returns the total capacity of the company for employees.
	 */
	public int getTotalEmployeeCapacity() {
		int eCapacity = 0;

		Team hrTeam = teams.get(EmployeeType.HR_WORKER);
		List<Employee> hrTeamList = hrTeam.getTeam();

		for (Employee worker : hrTeamList) {
			eCapacity += ((HRWorker) worker).getCapacity();
		}

		return eCapacity;
	}

	/**
	 * Iterates through all teams and sums up the number of {@link Employee}s for
	 * each {@link Team}.
	 *
	 * @return Total number of {@link Employee}s.
	 */
	public int getTotalNumberOfEmployees() {
		int numOfEmployees = 0;
		for (Entry<EmployeeType, Team> entry : teams.entrySet()) {
			numOfEmployees += entry.getValue().getTeam().size();
		}
		return numOfEmployees;
	}

	/**
	 * Iterates through all teams and sums up number of {@link Employee}s for
	 *  each {@link Team} excluding HR Workers.
	 * @return Returns the total number of {@link Employee}s excluding HR employees.
	 */
	private int getTotalNumberOfEmployeesNotHR() {
		int numOfEmployees = 0;
		for (Entry<EmployeeType, Team> entry : teams.entrySet()) {
			if(!entry.getKey().equals(EmployeeType.HR_WORKER)) {
				numOfEmployees += entry.getValue().getTeam().size();
			}
		}
		return numOfEmployees;
	}

	/**
	 *
	 * @return Returns true if still capacity to hire more employee. Return false otherwise.
	 */
	public boolean canHireEmployee(EmployeeType type) {
		if(!type.equals(EmployeeType.HR_WORKER)) {
			return  getTotalEmployeeCapacity() > getTotalNumberOfEmployeesNotHR();
		} else {
			return hrCapacity > teams.get(EmployeeType.HR_WORKER).getTeam().size();
		}
	}

	/**
	 * This returns the distribution with the current department level skill.
	 * @return Returns the current skill distribution for random employee generation.
	 */
	public Map<String, Double> getCurrentEmployeeDistribution() {
		return ((HRSkill) skillMap.get(getLevel())).getSkillDistribution();
	}

	/**
	 *
	 * @return Returns the total number of HR Worker you can have.
	 */
	public int getHrCapacity() {
		return hrCapacity;
	}

	/**
	 * Adds to the history the current number of employees.
	 * @param currentDate The current date of the game.
	 */
	public void updateEmployeeHistory(LocalDate currentDate) {
		employeeNumberHistory.put(currentDate, getTotalNumberOfEmployees());
		cleanEmployeeHistory(currentDate);
	}

	/**
	 * Removes from history entries that are older than 30 days.
	 * @param currentDate The current game date.
	 */
	private void cleanEmployeeHistory(LocalDate currentDate) {
		for(Entry<LocalDate, Integer> entry : employeeNumberHistory.entrySet()) {
			if(entry.getKey().plusDays(30).isBefore(currentDate)) {
				employeeNumberHistory.remove(entry.getKey());
			}
		}
	}

	/**
	 * If there is no entry of 30 days in the past, then take the oldest entry for comparison.
	 * @param currentDate The current date of the game.
	 * @return Returns the difference in number of employees from today and 30 days ago.
	 */
	public int getEmployeeDifference(LocalDate currentDate) {
		Integer oldValue = employeeNumberHistory.get(currentDate.minusDays(30));

		if(oldValue == null) {
			oldValue = employeeNumberHistory.get(getOldestEmployeeHistoryEntry());
		}
		return getTotalNumberOfEmployees() - oldValue;
	}

	/**
	 * Gets the key that is the oldest {@link LocalDate}.
	 * @return Returns from the employeeNumberHistory the oldest date.
	 */
	private LocalDate getOldestEmployeeHistoryEntry() {
		Set<LocalDate> dates = employeeNumberHistory.keySet();

		LocalDate tmp = null;
		for(LocalDate date : dates) {
			if(tmp == null || date.isBefore(tmp)) {
				tmp = date;
			}
		}
		return tmp;
	}

	/**
	 *
	 * @return Returns the map that contains the history number of employees.
	 */
	public Map<LocalDate, Integer> getEmployeeNumberHistory() {
		return employeeNumberHistory;
	}
}
