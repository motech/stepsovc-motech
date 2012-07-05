package org.wv.stepsovc.commcare.vo;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FacilityInformationTest {

    @Test
    public void shouldNotAddEmptyPhoneNumbers() {
        FacilityInformation facilityInformation = new FacilityInformation();
        facilityInformation.setMandatoryPhoneNumber("221312");
        facilityInformation.setOptionalPhoneNumber1("");
        facilityInformation.setOptionalPhoneNumber2(null);
        facilityInformation.setOptionalPhoneNumber3("444444");
        assertThat(facilityInformation.getPhoneNumbers().size(), is(2));

    }
}
