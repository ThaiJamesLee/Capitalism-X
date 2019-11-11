package de.uni.mannheim.capitalismx.domain.employee;

import de.uni.mannheim.capitalismx.domain.employee.impl.Engineer;
import de.uni.mannheim.capitalismx.domain.employee.impl.HRWorker;
import de.uni.mannheim.capitalismx.domain.employee.impl.ProductionWorker;
import de.uni.mannheim.capitalismx.domain.employee.impl.SalesPerson;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;

/**
 * The employee factory to generate Employee objects.
 * @author duly
 */
public class EmployeeFactory {

    private EmployeeFactory() {}

    /**
     *
     * @param type The Employee Type that is to be generated.
     * @param meta The {@link PersonMeta} that contains meta data of the employee.
     * @return Returns an Employee object of the type engineer.
     */
    public static Employee getEmployee(EmployeeType type, PersonMeta meta) {
        switch (type) {
            case ENGINEER: return new Engineer(meta).setEmployeeType(type);
            case SALESPERSON: return new SalesPerson(meta).setEmployeeType(type);
            case PRODUCTION_WORKER: return new ProductionWorker(meta).setEmployeeType(type);
            case HR_WORKER: return new HRWorker(meta).setEmployeeType(type);
            default: return new Engineer(meta);
        }
    }
}
