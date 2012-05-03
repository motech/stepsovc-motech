package org.wv.stepsovc.commcare.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@TypeDiscriminator("doc.base_doc == 'CouchUser'")
public class User extends MotechBaseDataObject {
    @JsonProperty
    private String first_name;

    @JsonProperty
    private  boolean is_superuser;

    @JsonProperty
    private  String registering_device_id;
    @JsonProperty
    private String status;
    @JsonProperty
    private String last_login;

    @JsonProperty
    private String base_doc;

    @JsonProperty
    private boolean is_staff;
    @JsonProperty
    private String created_on;
    @JsonProperty
    private String username;

    @JsonProperty
    private String doc_type;
    @JsonProperty
    private String domain;
    @JsonProperty
    private String[] device_ids;
    @JsonProperty
    private String email;
    @JsonProperty
    private  boolean is_active;

    @JsonProperty
    private String last_name;

    @JsonProperty
    private UserData user_data;

    @JsonProperty
    private String[] domain_memberships;

    @JsonProperty
    private String[] phone_numbers;

    @JsonProperty
    private String password;

    @JsonProperty
    private String date_joined;

    public String getDate_joined() {
        return date_joined;
    }

    public void setDate_joined(String date_joined) {
        this.date_joined = date_joined;
    }

    public String[] getDomain_memberships() {
        return domain_memberships;
    }

    public void setDomain_memberships(String[] domain_memberships) {
        this.domain_memberships = domain_memberships;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean active) {
        this.is_active = active;
    }

    public UserData getUser_data() {
        return user_data;
    }

    public void setUser_data(UserData user_data) {
        this.user_data = user_data;
    }

    public String getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public boolean isIs_staff() {
        return is_staff;
    }

    public void setIs_staff(boolean is_staff) {
        this.is_staff = is_staff;
    }

    public String getBase_doc() {
        return base_doc;
    }

    public void setBase_doc(String base_doc) {
        this.base_doc = base_doc;
    }

    public boolean isIs_superuser() {
        return is_superuser;

    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String[] getDevice_ids() {
        return device_ids;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDevice_ids(String[] device_ids) {
        this.device_ids = device_ids;
    }

    public void setIs_superuser(boolean is_superuser) {
        this.is_superuser = is_superuser;
    }

    public String getRegistering_device_id() {
        return registering_device_id;
    }


    public void setRegistering_device_id(String registering_device_id) {
        this.registering_device_id = registering_device_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getPhone_numbers() {
        return phone_numbers;
    }

    public void setPhone_numbers(String[] phone_numbers) {
        this.phone_numbers = phone_numbers;
    }

    static class UserData {

    }
}
