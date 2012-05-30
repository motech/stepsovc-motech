package org.wv.stepsovc.tools.seed.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.core.domain.Caregiver;
import org.wv.stepsovc.core.repository.AllCaregivers;
import org.wv.stepsovc.tools.seed.Seed;

@Component
public class CaregiverTemporarySeed extends Seed {

    @Autowired
    private AllCaregivers allCaregivers;

    @Override
    protected void preLoad() {
        super.preLoad();
    }

    @Override
    protected void load() {
        allCaregivers.removeAll();
        allCaregivers.add(new Caregiver("7ac0b33f0dac4a81c6d1fbf1bd9dfee0","CG1","Caregiver1","1234567890"));
        allCaregivers.add(new Caregiver("43995926c1c82fc37fb50804633e0432","CG2","Caregiver2","0987654321"));
    }
}
