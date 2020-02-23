package de.uni.mannheim.capitalismx.domain.exception;

/**
 * Throw this exception if the levelUp requirement in {@link de.uni.mannheim.capitalismx.domain.department.LevelingMechanism}.
 * @author duly
 *
 * @since 0.0.5
 */
public class LevelingRequirementNotFulFilledException extends Exception {

    public LevelingRequirementNotFulFilledException(String message) {
        super(message);
    }
}
