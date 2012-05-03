package org.wv.stepsovc.commcare.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@TypeDiscriminator("doc.doc_type == 'Group'")
public class Group extends MotechBaseDataObject {
    @JsonProperty
    private String domain;
    @JsonProperty
    private String name;
    @JsonProperty
    private String[] users;
    @JsonProperty
    private boolean case_sharing;
    @JsonProperty
    private boolean reporting;
    @JsonProperty
    private String[] path;

    public String getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    @JsonProperty
    private String doc_type;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }

    public boolean isCase_sharing() {
        return case_sharing;
    }

    public void setCase_sharing(boolean case_sharing) {
        this.case_sharing = case_sharing;
    }

    public boolean isReporting() {
        return reporting;
    }

    public void setReporting(boolean reporting) {
        this.reporting = reporting;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }
}
