package org.wv.stepsovc.tools.domain;

import org.motechproject.export.annotation.ExportValue;

public class ReferralData {

    private String referralId;
    private String beneficiaryId;
    private String caregiverId;
    private boolean followupRequired;
    private String serviceDetails;
    private String serviceDate;
    private String facilityCode;
    private boolean signed;
    private String signedDate;

    private boolean artReceived;
    private boolean artReferred;
    private boolean condomsReceived;
    private boolean condomsReferred;
    private boolean counsellingTestingReceived;
    private boolean counsellingTestingReferred;
    private boolean diagnosisReceived;
    private boolean diagnosisReferred;
    private boolean familyPlanningReceived;
    private boolean familyPlanningReferred;
    private boolean hospitalAdmissionReceived;
    private boolean hospitalAdmissionReferred;
    private boolean otherServiceReferred;
    private boolean otherServiceReceived;
    private boolean painManagementReceived;
    private boolean painManagementReferred;
    private boolean pmtctReceived;
    private boolean pmtctReferred;
    private boolean sexTransInfectionReferred;
    private boolean sexTransInfectionReceived;

    @ExportValue(index = 0)
    public String getReferralId() {
        return referralId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

    @ExportValue(index = 1)
    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    @ExportValue(index = 2)
    public String getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(String caregiverId) {
        this.caregiverId = caregiverId;
    }

    @ExportValue(index = 3)
    public boolean getFollowupRequired() {
        return followupRequired;
    }

    public void setFollowupRequired(boolean followupRequired) {
        this.followupRequired = followupRequired;
    }

    @ExportValue(index = 4)
    public String getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(String serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    @ExportValue(index = 5)
    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    @ExportValue(index = 6)
    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    @ExportValue(index = 7)
    public boolean getSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    @ExportValue(index = 8)
    public String getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(String signedDate) {
        this.signedDate = signedDate;
    }

    @ExportValue(index = 9)
    public boolean getArtReceived() {
        return artReceived;
    }

    public void setArtReceived(boolean artReceived) {
        this.artReceived = artReceived;
    }

    @ExportValue(index = 10)
    public boolean getArtReferred() {
        return artReferred;
    }

    public void setArtReferred(boolean artReferred) {
        this.artReferred = artReferred;
    }

    @ExportValue(index = 11)
    public boolean getCondomsReceived() {
        return condomsReceived;
    }

    public void setCondomsReceived(boolean condomsReceived) {
        this.condomsReceived = condomsReceived;
    }

    @ExportValue(index = 12)
    public boolean getCondomsReferred() {
        return condomsReferred;
    }

    public void setCondomsReferred(boolean condomsReferred) {
        this.condomsReferred = condomsReferred;
    }

    @ExportValue(index = 13)
    public boolean getCounsellingTestingReceived() {
        return counsellingTestingReceived;
    }

    public void setCounsellingTestingReceived(boolean counsellingTestingReceived) {
        this.counsellingTestingReceived = counsellingTestingReceived;
    }

    @ExportValue(index = 14)
    public boolean getCounsellingTestingReferred() {
        return counsellingTestingReferred;
    }

    public void setCounsellingTestingReferred(boolean counsellingTestingReferred) {
        this.counsellingTestingReferred = counsellingTestingReferred;
    }

    @ExportValue(index = 15)
    public boolean getDiagnosisReceived() {
        return diagnosisReceived;
    }

    public void setDiagnosisReceived(boolean diagnosisReceived) {
        this.diagnosisReceived = diagnosisReceived;
    }

    @ExportValue(index = 16)
    public boolean getDiagnosisReferred() {
        return diagnosisReferred;
    }

    public void setDiagnosisReferred(boolean diagnosisReferred) {
        this.diagnosisReferred = diagnosisReferred;
    }

    @ExportValue(index = 17)
    public boolean getFamilyPlanningReceived() {
        return familyPlanningReceived;
    }

    public void setFamilyPlanningReceived(boolean familyPlanningReceived) {
        this.familyPlanningReceived = familyPlanningReceived;
    }

    @ExportValue(index = 18)
    public boolean getFamilyPlanningReferred() {
        return familyPlanningReferred;
    }

    public void setFamilyPlanningReferred(boolean familyPlanningReferred) {
        this.familyPlanningReferred = familyPlanningReferred;
    }

    @ExportValue(index = 19)
    public boolean getHospitalAdmissionReceived() {
        return hospitalAdmissionReceived;
    }

    public void setHospitalAdmissionReceived(boolean hospitalAdmissionReceived) {
        this.hospitalAdmissionReceived = hospitalAdmissionReceived;
    }

    @ExportValue(index = 20)
    public boolean getHospitalAdmissionReferred() {
        return hospitalAdmissionReferred;
    }

    public void setHospitalAdmissionReferred(boolean hospitalAdmissionReferred) {
        this.hospitalAdmissionReferred = hospitalAdmissionReferred;
    }

    @ExportValue(index = 21)
    public boolean getOtherServiceReferred() {
        return otherServiceReferred;
    }

    public void setOtherServiceReferred(boolean otherServiceReferred) {
        this.otherServiceReferred = otherServiceReferred;
    }

    @ExportValue(index = 22)
    public boolean getOtherServiceReceived() {
        return otherServiceReceived;
    }

    public void setOtherServiceReceived(boolean otherServiceReceived) {
        this.otherServiceReceived = otherServiceReceived;
    }

    @ExportValue(index = 23)
    public boolean getPainManagementReceived() {
        return painManagementReceived;
    }

    public void setPainManagementReceived(boolean painManagementReceived) {
        this.painManagementReceived = painManagementReceived;
    }

    @ExportValue(index = 24)
    public boolean getPainManagementReferred() {
        return painManagementReferred;
    }

    public void setPainManagementReferred(boolean painManagementReferred) {
        this.painManagementReferred = painManagementReferred;
    }

    @ExportValue(index = 25)
    public boolean getPmtctReceived() {
        return pmtctReceived;
    }

    public void setPmtctReceived(boolean pmtctReceived) {
        this.pmtctReceived = pmtctReceived;
    }

    @ExportValue(index = 26)
    public boolean getPmtctReferred() {
        return pmtctReferred;
    }

    public void setPmtctReferred(boolean pmtctReferred) {
        this.pmtctReferred = pmtctReferred;
    }

    @ExportValue(index = 27)
    public boolean getSexTransInfectionReferred() {
        return sexTransInfectionReferred;
    }

    public void setSexTransInfectionReferred(boolean sexTransInfectionReferred) {
        this.sexTransInfectionReferred = sexTransInfectionReferred;
    }

    @ExportValue(index = 28)
    public boolean getSexTransInfectionReceived() {
        return sexTransInfectionReceived;
    }

    public void setSexTransInfectionReceived(boolean sexTransInfectionReceived) {
        this.sexTransInfectionReceived = sexTransInfectionReceived;
    }
}
