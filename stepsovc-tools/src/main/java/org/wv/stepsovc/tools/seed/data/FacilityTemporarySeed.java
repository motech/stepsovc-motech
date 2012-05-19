package org.wv.stepsovc.tools.seed.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.core.domain.Facility;
import org.wv.stepsovc.core.domain.ServiceUnavailability;
import org.wv.stepsovc.core.repository.AllFacilities;
import org.wv.stepsovc.tools.seed.Seed;

import static java.util.Arrays.asList;

@Component
public class FacilityTemporarySeed extends Seed {

    @Autowired
    private AllFacilities allFacilities;

    @Override
    protected void preLoad() {
        super.preLoad();
    }

    @Override
    protected void load() {
        allFacilities.removeAll();
        allFacilities.add(new Facility("FAC001", "FAC001-Name",
                asList(new ServiceUnavailability("reason1", "2012-06-20", "2012-06-20"),
                        new ServiceUnavailability("reason2", "2012-06-26", "2012-06-26")
                ),
                asList("9999999999", "88888888")));
        allFacilities.add(new Facility("FAC002", "FAC002-Name", null, asList("0987654321", "12345890")));
    }
}
