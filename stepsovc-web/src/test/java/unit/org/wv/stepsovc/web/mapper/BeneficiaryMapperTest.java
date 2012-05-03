package org.wv.stepsovc.web.mapper;

import org.junit.Test;
import org.wv.stepsovc.web.domain.Beneficiary;
import org.wv.stepsovc.web.request.BeneficiaryCase;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BeneficiaryMapperTest {

    @Test
    public void shouldMapBeneficiaryCaseToBeneficiary() {
        BeneficiaryCase beneficiaryCase = new BeneficiaryCase();

        beneficiaryCase.setCaregiver_id("some ID");
        beneficiaryCase.setCaregiver_name("Some CareGiver name");
        beneficiaryCase.setTitle("Ms");
        beneficiaryCase.setSex("F");
        beneficiaryCase.setBeneficiary_dob("12-12-1980");
        beneficiaryCase.setBeneficiary_name("Some name");
        Beneficiary beneficiary = new BeneficiaryMapper().map(beneficiaryCase);

        assertThat(beneficiary.getCaregiverId(), is(beneficiaryCase.getCaregiver_id()));
        assertThat(beneficiary.getDateOfBirth(), is(beneficiaryCase.getBeneficiary_dob()));
        assertThat(beneficiary.getCode(), is(beneficiaryCase.getBeneficiary_code()));
        assertThat(beneficiary.getSex(), is(beneficiaryCase.getSex()));
        assertThat(beneficiary.getTitle(), is(beneficiaryCase.getTitle()));
        assertThat(beneficiary.getName(), is(beneficiaryCase.getBeneficiary_name()));

    }


}
