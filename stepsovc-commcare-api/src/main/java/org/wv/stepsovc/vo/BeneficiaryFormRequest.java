package org.wv.stepsovc.vo;

public class BeneficiaryFormRequest {

    private BeneficiaryInformation beneficiaryInformation;

    private CareGiverInformation caregiverInformation;

    private CaseInformation caseInformation;

    private MetaInformation metaInformation;


    public BeneficiaryInformation getBeneficiaryInformation() {
        return beneficiaryInformation;
    }

    public void setBeneficiaryInformation(BeneficiaryInformation beneficiaryInformation) {
        this.beneficiaryInformation = beneficiaryInformation;
    }

    public CareGiverInformation getCaregiverInformation() {
        return caregiverInformation;
    }

    public void setCaregiverInformation(CareGiverInformation caregiverInformation) {
        this.caregiverInformation = caregiverInformation;
    }

    public CaseInformation getCaseInformation() {
        return caseInformation;
    }

    public void setCaseInformation(CaseInformation caseInformation) {
        this.caseInformation = caseInformation;
    }

    public MetaInformation getMetaInformation() {
        return metaInformation;
    }

    public void setMetaInformation(MetaInformation metaInformation) {
        this.metaInformation = metaInformation;
    }
}
