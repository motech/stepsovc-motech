package org.wv.stepsovc.commcare.factories;

import org.wv.stepsovc.commcare.domain.Group;

public class GroupFactory {

    public static Group createGroup(String groupName, String[] commcareUserIds, String domain) {
        Group newGroup = new Group();
        newGroup.setName(groupName);
        newGroup.setReporting(true);
        newGroup.setUsers(commcareUserIds);
        newGroup.setPath(new String[]{});
        newGroup.setDoc_type("Group");
        newGroup.setCase_sharing(true);
        newGroup.setDomain(domain);
        return newGroup;
    }
}