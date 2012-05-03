package org.wv.stepsovc.commcare;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wv.stepsovc.commcare.domain.User;
import org.wv.stepsovc.commcare.repository.AllGroups;
import org.wv.stepsovc.commcare.repository.AllUsers;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext-stepsovc-commcare-api.xml")
public class CommcareGroupIntegrationTest {
    @Autowired
    AllGroups allGroups;
    @Autowired
    AllUsers allUsers;

    private User newUser1;
    private User newUser2;
    private String userId1;
    private String userId2;
    private String groupName = "group1";

    @Test
    public void shouldCreateGroupWithUsers() {

        newUser1 = createUser("newUser1");
        newUser2 = createUser("newUser2");

        allUsers.add(newUser1);
        allUsers.add(newUser2);

        userId1 = allUsers.getIdByName(newUser1.getUsername());
        userId2 = allUsers.getIdByName(newUser2.getUsername());

        String domainName = "stepsovc";

        allGroups.createGroup(groupName, new String[]{userId1, userId2}, domainName);
        assertNotNull(allGroups.getIdByName(groupName));

        assertFalse(allGroups.createGroup(groupName,new String[]{newUser1.getId(),newUser2.getId()}, domainName));
    }

    private User createUser(String userName) {
        User newUser = new User();
        newUser.setBase_doc("CouchUser");
        newUser.setDomain("stepsovc");
        newUser.setUsername(userName);

        return newUser;
    }

    @After
    public void tearDown() {
        allGroups.removeByName(groupName);
        allUsers.removeByName(newUser1.getUsername());
        allUsers.removeByName(newUser2.getUsername());
    }
}
