package org.wv.stepsovc.vo;


public class CareGiverInformation {

    /* primary key column cg_id - from DMIS (id)*/
    private String id;

    /* user name to login - from DMIS*/
    private String code;

    /* password to login - generated */
    private String password;

    /* name of the care giver - from DMIS */
    private String name;

    /* phone number of care giver - from DMIS */
    private String phoneNumber;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
