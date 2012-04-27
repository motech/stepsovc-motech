package org.wv.stepsovc.web.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@TypeDiscriminator("doc.type == 'HealthWorker'")
public class HealthWorker extends MotechBaseDataObject {
    @JsonProperty
    private String name;
    @JsonProperty
    private String id;
    @JsonProperty
    private String facilityId;
    @JsonProperty
    private String phoneNumber;
}

