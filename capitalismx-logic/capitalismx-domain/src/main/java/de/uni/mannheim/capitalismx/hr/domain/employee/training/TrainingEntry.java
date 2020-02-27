package de.uni.mannheim.capitalismx.hr.domain.employee.training;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * This class contains a training entry for an {@link de.uni.mannheim.capitalismx.hr.domain.employee.Employee}.
 * The employee maintains a list of {@link Training} with the date.
 * @author duly
 *
 * @since 0.0.5
 */
public class TrainingEntry implements Serializable {

    private Training training;
    private LocalDate date;

    /**
     * The constructor.
     * @param training The training the employee has done.
     * @param date The date the employee did the training.
     */
    public TrainingEntry(Training training, LocalDate date) {
        this.training = training;
        this.date = date;
    }

    /**
     *
     * @return The date the employee did the training.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     *
     * @return The training the employee has done.
     */
    public Training getTraining() {
        return training;
    }
}
