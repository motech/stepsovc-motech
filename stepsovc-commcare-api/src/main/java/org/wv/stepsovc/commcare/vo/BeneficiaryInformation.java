package org.wv.stepsovc.commcare.vo;


public class BeneficiaryInformation {

    private String beneficiaryId;

    private String beneficiaryName;

    private String beneficiaryLastName;

    private String beneficiaryDob;

    private String beneficiarySex;

    private String beneficiaryCode;

    private String beneficiaryTitle;

    private String receivingOrganization;

    private String careGiverId;

    private String careGiverCode;

    private String careGiverName;

    private String ownerId;

    /* date of created of beneficiary case in commcare */
    private String dateModified;

    /* case type is Beneficiary */
    private String caseType;

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryDob() {
        return beneficiaryDob;
    }

    public void setBeneficiaryDob(String beneficiaryDob) {
        this.beneficiaryDob = beneficiaryDob;
    }

    public String getBeneficiarySex() {
        return beneficiarySex;
    }

    public void setBeneficiarySex(String beneficiarySex) {
        this.beneficiarySex = beneficiarySex;
    }

    public String getBeneficiaryCode() {
        return beneficiaryCode;
    }

    public void setBeneficiaryCode(String beneficiaryCode) {
        this.beneficiaryCode = beneficiaryCode;
    }

    public String getBeneficiaryTitle() {
        return beneficiaryTitle;
    }

    public void setBeneficiaryTitle(String beneficiaryTitle) {
        this.beneficiaryTitle = beneficiaryTitle;
    }

    public String getReceivingOrganization() {
        return receivingOrganization;
    }

    public void setReceivingOrganization(String receivingOrganization) {
        this.receivingOrganization = receivingOrganization;
    }

    public String getCareGiverId() {
        return careGiverId;
    }

    public void setCareGiverId(String careGiverId) {
        this.careGiverId = careGiverId;
    }

    public String getCareGiverCode() {
        return careGiverCode;
    }

    public void setCareGiverCode(String careGiverCode) {
        this.careGiverCode = careGiverCode;
    }

    public String getCareGiverName() {
        return careGiverName;
    }

    public void setCareGiverName(String careGiverName) {
        this.careGiverName = careGiverName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getBeneficiaryLastName() {
        return beneficiaryLastName;
    }

    public void setBeneficiaryLastName(String beneficiaryLastName) {
        this.beneficiaryLastName = beneficiaryLastName;
    }
}
