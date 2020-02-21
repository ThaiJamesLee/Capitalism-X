package de.uni.mannheim.capitalismx.hr.department;

import de.uni.mannheim.capitalismx.hr.domain.Benefit;
import de.uni.mannheim.capitalismx.hr.domain.BenefitType;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * This class should save and load settings of benefits.
 * For each {@link BenefitType} there can only be one {@link Benefit}.
 * @author duly
 *
 * @since 1.0.0
 */
public class BenefitSettings implements Serializable {

    /**
     * Maintains the setting in a map.
     * Therefore, for each {@link BenefitType} there can only be one {@link Benefit}.
     */
    private Map<BenefitType, Benefit> benefits;

    /* Constructor */
    public BenefitSettings () {
        initBenefitSettings();
    }

    /**
     *
     * @param benefits The benefit setting.
     */
    public BenefitSettings (Map<BenefitType, Benefit> benefits) {
        this.benefits = benefits;
    }

    /**
     * Initialize with tier 0 benefits
     */
    public void initBenefitSettings () {
        this.benefits = new EnumMap<>(BenefitType.class);

        // assume each benefit type has only one benefit for each tier!
        Benefit[] bfs = Benefit.values();

        for (Benefit b : bfs) {
            if (b.getTier() == 0) {
                this.benefits.put(b.getType(), b);
            }
        }
    }

    public Map<BenefitType, Benefit> getBenefits() {
        return benefits;
    }

    /**
     *
     * @param benefits Set the new BenefitSettings.
     */
    public void setBenefits(Map<BenefitType, Benefit> benefits) {
        if (benefits == null) {
            throw new NullPointerException("Benefits can not be null!");
        }
        this.benefits = benefits;
    }

    /**
     * Overwrite the benefit with the same benefit type with new benefit
     * @param benefit and the benefit
     */
    public void changeBenefit( Benefit benefit) {
        if (benefit == null) {
            throw new NullPointerException("Benefit must be non Null object!");
        }
        benefits.put(benefit.getType(), benefit);
    }

}
