package org.wv.stepsovc.tools.domain;

import org.motechproject.export.annotation.ExportValue;

public class ReferralData {

    private String referralId;
    private String facilityCode;
    private String serviceDate;
    private byte followupRequired;
    private byte signed;
    private String signedDate;
    private byte artReceived;
    private byte artReferred;
    private byte condomsReceived;
    private byte condomsReferred;
    private byte counsellingTestingReceived;
    private byte counsellingTestingReferred;
    private byte diagnosisReceived;
    private byte diagnosisReferred;
    private byte familyPlanningReceived;
    private byte familyPlanningReferred;
    private byte hospitalAdmissionReceived;
    private byte hospitalAdmissionReferred;
    private String serviceDetails;
    private byte otherServiceReceived;
    private byte otherServiceReferred;
    private byte painManagementReceived;
    private byte painManagementReferred;
    private byte pmtctReceived;
    private byte pmtctReferred;
    private byte sexTransInfectionReceived;
    private byte sexTransInfectionReferred;
    private String beneficiaryId;
    private String caregiverId;

    @ExportValue(column = "rr_id", index = 0)
    public String getReferralId() {
        return this.referralId;
    }

    @ExportValue(column = "rr_referrer_name", index = 1)
    public String getFacilityCode() {
        return this.facilityCode;
    }

    @ExportValue(column = "rr_referral_date", index = 2)
    public String getServiceDate() {
        return this.serviceDate;
    }

    @ExportValue(column = "rr_follow_up", index = 3)
    public byte getFollowupRequired() {
        return this.followupRequired;
    }

    @ExportValue(column = "rr_signed", index = 4)
    public byte getSigned() {
        return this.signed;
    }

    @ExportValue(column = "rr_signed_date", index = 5)
    public String getSignedDate() {
        return this.signedDate;
    }

    @ExportValue(column = "rr_hlt_art_rec", index = 6)
    public byte getArtReceived() {
        return this.artReceived;
    }

    @ExportValue(column = "rr_hlt_art_ref", index = 7)
    public byte getArtReferred() {
        return this.artReferred;
    }

    @ExportValue(column = "rr_hlt_condoms_rec", index = 8)
    public byte getCondomsReceived() {
        return this.condomsReceived;
    }

    @ExportValue(column = "rr_hlt_condoms_ref", index = 9)
    public byte getCondomsReferred() {
        return this.condomsReferred;
    }

    @ExportValue(column = "rr_hlt_ct_rec", index = 10)
    public byte getCounsellingTestingReceived() {
        return this.counsellingTestingReceived;
    }

    @ExportValue(column = "rr_hlt_ct_ref", index = 11)
    public byte getCounsellingTestingReferred() {
        return this.counsellingTestingReferred;
    }

    @ExportValue(column = "rr_hlt_diag_rec", index = 12)
    public byte getDiagnosisReceived() {
        return this.diagnosisReceived;
    }

    @ExportValue(column = "rr_hlt_diag_ref", index = 13)
    public byte getDiagnosisReferred() {
        return this.diagnosisReferred;
    }

    @ExportValue(column = "rr_hlt_fp_rec", index = 14)
    public byte getFamilyPlanningReceived() {
        return this.familyPlanningReceived;
    }

    @ExportValue(column = "rr_hlt_fp_ref", index = 15)
    public byte getFamilyPlanningReferred() {
        return this.familyPlanningReferred;
    }

    @ExportValue(column = "rr_hlt_hosp_rec", index = 16)
    public byte getHospitalAdmissionReceived() {
        return this.hospitalAdmissionReceived;
    }

    @ExportValue(column = "rr_hlt_hosp_ref", index = 17)
    public byte getHospitalAdmissionReferred() {
        return this.hospitalAdmissionReferred;
    }

    @ExportValue(column = "rr_hlt_other_hlt", index = 18)
    public String getServiceDetails() {
        return this.serviceDetails;
    }

    @ExportValue(column = "rr_hlt_other_hlt_rec", index = 19)
    public byte getOtherServiceReceived() {
        return this.otherServiceReceived;
    }

    @ExportValue(column = "rr_hlt_other_hlt_ref", index = 20)
    public byte getOtherServiceReferred() {
        return this.otherServiceReferred;
    }

    @ExportValue(column = "rr_hlt_pain_rec", index = 21)
    public byte getPainManagementReceived() {
        return this.painManagementReceived;
    }

    @ExportValue(column = "rr_hlt_pain_ref", index = 22)
    public byte getPainManagementReferred() {
        return this.painManagementReferred;
    }

    @ExportValue(column = "rr_hlt_pmtct_rec", index = 23)
    public byte getPmtctReceived() {
        return this.pmtctReceived;
    }

    @ExportValue(column = "rr_hlt_pmtct_ref", index = 24)
    public byte getPmtctReferred() {
        return this.pmtctReferred;
    }

    @ExportValue(column = "rr_hlt_sex_trans_rec", index = 25)
    public byte getSexTransInfectionReceived() {
        return this.sexTransInfectionReceived;
    }

    @ExportValue(column = "rr_hlt_sex_trans_ref", index = 26)
    public byte getSexTransInfectionReferred() {
        return this.sexTransInfectionReferred;
    }

    @ExportValue(column = "ben_id", index = 27)
    public String getBeneficiaryId() {
        return this.beneficiaryId;
    }

    @ExportValue(column = "cg_id", index = 28)
    public String getCaregiverId() {
        return this.caregiverId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public void setFollowupRequired(byte followupRequired) {
        this.followupRequired = followupRequired;
    }

    public void setSigned(byte signed) {
        this.signed = signed;
    }

    public void setSignedDate(String signedDate) {
        this.signedDate = signedDate;
    }

    public void setArtReceived(byte artReceived) {
        this.artReceived = artReceived;
    }

    public void setArtReferred(byte artReferred) {
        this.artReferred = artReferred;
    }

    public void setCondomsReceived(byte condomsReceived) {
        this.condomsReceived = condomsReceived;
    }

    public void setCondomsReferred(byte condomsReferred) {
        this.condomsReferred = condomsReferred;
    }

    public void setCounsellingTestingReceived(byte counsellingTestingReceived) {
        this.counsellingTestingReceived = counsellingTestingReceived;
    }

    public void setCounsellingTestingReferred(byte counsellingTestingReferred) {
        this.counsellingTestingReferred = counsellingTestingReferred;
    }

    public void setDiagnosisReceived(byte diagnosisReceived) {
        this.diagnosisReceived = diagnosisReceived;
    }

    public void setDiagnosisReferred(byte diagnosisReferred) {
        this.diagnosisReferred = diagnosisReferred;
    }

    public void setFamilyPlanningReceived(byte familyPlanningReceived) {
        this.familyPlanningReceived = familyPlanningReceived;
    }

    public void setFamilyPlanningReferred(byte familyPlanningReferred) {
        this.familyPlanningReferred = familyPlanningReferred;
    }

    public void setHospitalAdmissionReceived(byte hospitalAdmissionReceived) {
        this.hospitalAdmissionReceived = hospitalAdmissionReceived;
    }

    public void setHospitalAdmissionReferred(byte hospitalAdmissionReferred) {
        this.hospitalAdmissionReferred = hospitalAdmissionReferred;
    }

    public void setServiceDetails(String serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public void setOtherServiceReceived(byte otherServiceReceived) {
        this.otherServiceReceived = otherServiceReceived;
    }

    public void setOtherServiceReferred(byte otherServiceReferred) {
        this.otherServiceReferred = otherServiceReferred;
    }

    public void setPainManagementReceived(byte painManagementReceived) {
        this.painManagementReceived = painManagementReceived;
    }

    public void setPainManagementReferred(byte painManagementReferred) {
        this.painManagementReferred = painManagementReferred;
    }

    public void setPmtctReceived(byte pmtctReceived) {
        this.pmtctReceived = pmtctReceived;
    }

    public void setPmtctReferred(byte pmtctReferred) {
        this.pmtctReferred = pmtctReferred;
    }

    public void setSexTransInfectionReceived(byte sexTransInfectionReceived) {
        this.sexTransInfectionReceived = sexTransInfectionReceived;
    }

    public void setSexTransInfectionReferred(byte sexTransInfectionReferred) {
        this.sexTransInfectionReferred = sexTransInfectionReferred;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public void setCaregiverId(String caregiverId) {
        this.caregiverId = caregiverId;
    }
}
