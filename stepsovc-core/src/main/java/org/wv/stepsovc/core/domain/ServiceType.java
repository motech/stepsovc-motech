package org.wv.stepsovc.core.domain;

public enum ServiceType {
    NEW_DIAGNOSIS("001","New diagnosis / pre-ART enrollment, CD4 test"),
    END_OF_LIFE_HOSPICE("002","End-of-life hospice/hospital admission"),
    HIVE_COUNSELING("003","HIV counseling and testing"),
    FAMILY_PLANNING("004","Family Planning"),
    SEXUALLY_TRANSMITTED_INFEC("005","Sexually transmitted infection"),
    ART_ADHERENCE("006","ART adherence counseling"),
    PAIN_MANAGEMENT("007","Pain Management"),
    PMTCT("008","PMTCT"),
    CONDOMS("009","Condoms"),
    OTHER_HEALTH_SERVICES("010","Other Health Service or health education");

    private final String code;

    private final String desc;

    ServiceType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return code;
    }
}
