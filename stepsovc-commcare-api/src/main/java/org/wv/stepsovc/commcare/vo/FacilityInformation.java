package org.wv.stepsovc.commcare.vo;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FacilityInformation {

    private String facilityCode;
    private String id;
    private String facilityName;
    private List<String> phoneNumbers;
    private String district;
    private String constituency;
    private String ward;
    private String village;
    private String mandatoryPhoneNumber;
    private String optionalPhoneNumber1;
    private String optionalPhoneNumber2;
    private String optionalPhoneNumber3;
    private String creationDate;

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getFacilityCode() {
        return facilityCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public List<String> getPhoneNumbers() {
        ArrayList<String> phoneNumberList = new ArrayList<>();
        if (StringUtils.isNotEmpty(getMandatoryPhoneNumber()))
            phoneNumberList.add(getMandatoryPhoneNumber());
        if (StringUtils.isNotEmpty(getOptionalPhoneNumber1()))
            phoneNumberList.add(getOptionalPhoneNumber1());
        if (StringUtils.isNotEmpty(getOptionalPhoneNumber2()))
            phoneNumberList.add(getOptionalPhoneNumber2());
        if (StringUtils.isNotEmpty(getOptionalPhoneNumber3()))
            phoneNumberList.add(getOptionalPhoneNumber3());

        return phoneNumberList;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getMandatoryPhoneNumber() {
        return mandatoryPhoneNumber;
    }

    public void setMandatoryPhoneNumber(String mandatoryPhoneNumber) {
        this.mandatoryPhoneNumber = mandatoryPhoneNumber;
    }

    public String getOptionalPhoneNumber1() {
        return optionalPhoneNumber1;
    }

    public void setOptionalPhoneNumber1(String optionalPhoneNumber1) {
        this.optionalPhoneNumber1 = optionalPhoneNumber1;
    }

    public String getOptionalPhoneNumber2() {
        return optionalPhoneNumber2;
    }

    public void setOptionalPhoneNumber2(String optionalPhoneNumber2) {
        this.optionalPhoneNumber2 = optionalPhoneNumber2;
    }

    public String getOptionalPhoneNumber3() {
        return optionalPhoneNumber3;
    }

    public void setOptionalPhoneNumber3(String optionalPhoneNumber3) {
        this.optionalPhoneNumber3 = optionalPhoneNumber3;
    }
}
