package org.wv.stepsovc.web.mapper;

import org.junit.Test;
import org.wv.stepsovc.web.domain.Beneficiary;
import org.wv.stepsovc.web.request.StepsovcCase;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BeneficiaryMapperTest {

    @Test
    public void shouldMapBeneficiaryCaseToBeneficiary() {
        StepsovcCase stepsovcCase = new StepsovcCase();

        stepsovcCase.setCaregiver_code("some ID");
        stepsovcCase.setCaregiver_name("Some CareGiver name");
        stepsovcCase.setTitle("Ms");
        stepsovcCase.setSex("F");
        stepsovcCase.setBeneficiary_dob("12-12-1980");
        stepsovcCase.setBeneficiary_name("Some name");
        Beneficiary beneficiary = new BeneficiaryMapper().map(stepsovcCase);

        assertThat(beneficiary.getCaregiverCode(), is(stepsovcCase.getCaregiver_code()));
        assertThat(beneficiary.getDateOfBirth(), is(stepsovcCase.getBeneficiary_dob()));
        assertThat(beneficiary.getCode(), is(stepsovcCase.getBeneficiary_code()));
        assertThat(beneficiary.getSex(), is(stepsovcCase.getSex()));
        assertThat(beneficiary.getTitle(), is(stepsovcCase.getTitle()));
        assertThat(beneficiary.getName(), is(stepsovcCase.getBeneficiary_name()));

    }


}
