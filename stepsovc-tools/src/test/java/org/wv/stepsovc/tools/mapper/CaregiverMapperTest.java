package org.wv.stepsovc.tools.mapper;

import org.junit.Test;
import org.wv.stepsovc.commcare.vo.CareGiverInformation;
import org.wv.stepsovc.core.domain.Caregiver;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CaregiverMapperTest {


    @Test
    public void shouldMapCareGiver() throws Exception {
        String phoneNumber = "456";
        String id = "123";
        String code = "code";
        String name = "name";

        CareGiverInformation caregiverInfo = new CareGiverInformation();
        caregiverInfo.setPhoneNumber(phoneNumber);
        caregiverInfo.setId(id);
        caregiverInfo.setCode(code);
        caregiverInfo.setName(name);

        Caregiver caregiver = new CaregiverMapper().map(caregiverInfo);
        assertThat(caregiver.getId(), is(id));
        assertThat(caregiver.getName(), is(name));
        assertThat(caregiver.getCode(), is(code));
        assertThat(caregiver.getPhoneNumber(), is(phoneNumber));
    }
}
