package de.uni.mannheim.capitalismx.hr.department;

import de.uni.mannheim.capitalismx.hr.domain.Benefit;
import de.uni.mannheim.capitalismx.hr.domain.BenefitTypes;

import java.util.EnumMap;
import java.util.Map;

/**
 * This class should save and load settings of benefits.
 * @author duly
 */
public class BenefitSettings {

    private Map<BenefitTypes, Benefit> benefits;

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

}
