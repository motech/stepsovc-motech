package org.wv.stepsovc.tools.mapper;

import org.junit.Test;
import org.wv.stepsovc.commcare.vo.CareGiverInformation;
import org.wv.stepsovc.core.domain.Caregiver;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CaregiverMapperTest {


    @Test
    public void shouldMapCareGiver() throws Exception {
        String id = "123";
        String code = "code";
        String fName = "fname";
        String mName = "mName";
        String lName = "lName";
        String gender = "male";
        String phoneNumber = "456";

        CareGiverInformation caregiverInfo = new CareGiverInformation();
        caregiverInfo.setCaregiverId(id);
        caregiverInfo.setCaregiverCode(code);
        caregiverInfo.setFirstName(fName);
        caregiverInfo.setMiddleName(mName);
        caregiverInfo.setLastName(lName);
        caregiverInfo.setGender(gender);
        caregiverInfo.setPhoneNumber(phoneNumber);

        Caregiver caregiver = new CaregiverMapper().map(caregiverInfo);
        assertThat(caregiver.getId(), is(id));
        assertThat(caregiver.getCode(), is(code));
        assertThat(caregiver.getFirstName(), is(fName));
        assertThat(caregiver.getMiddleName(), is(mName));
        assertThat(caregiver.getLastName(), is(lName));
        assertThat(caregiver.getGender(), is(gender));
        assertThat(caregiver.getPhoneNumber(), is(phoneNumber));
    }
}
