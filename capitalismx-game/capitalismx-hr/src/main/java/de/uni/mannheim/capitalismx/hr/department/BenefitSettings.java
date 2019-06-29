package de.uni.mannheim.capitalismx.hr.department;

import de.uni.mannheim.capitalismx.hr.domain.Benefit;
import de.uni.mannheim.capitalismx.hr.domain.BenefitTypes;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * This class should save and load settings of benefits.
 * @author duly
 */
public class BenefitSettings implements Serializable {

    private Map<BenefitTypes, Benefit> benefits;

    public BenefitSettings () {
        initBenefitSettings();
    }

    public BenefitSettings (Map<BenefitTypes, Benefit> benefits) {
        this.benefits = benefits;
    }

    /**
     * Initialize with tier 0 benefits
     */
    public void initBenefitSettings () {
        this.benefits = new EnumMap<>(BenefitTypes.class);

        // assume each benefit type has only one benefit for each tier!
        Benefit[] bfs = Benefit.values();

        for (Benefit b : bfs) {
            if (b.getTier() == 0) {
                this.benefits.put(b.getType(), b);
            }
        }
    }

    public void loadSettings () {
        //TODO load exsisting settings, if available
    }

    public Map<BenefitTypes, Benefit> getBenefits() {
        return benefits;
    }

    public void setBenefits(Map<BenefitTypes, Benefit> benefits) {
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
