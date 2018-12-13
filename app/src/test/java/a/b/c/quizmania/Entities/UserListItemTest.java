package a.b.c.quizmania.Entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserListItemTest {

    UserListItem mUser;
    UserListItem mEmptyUser;

    @Before
    public void initVariables() {
        mEmptyUser = new UserListItem();
        mUser = new UserListItem("Fiona@gmail.com", "Fiona");
        mUser.setPushToken("some:token");
    }

    @Test
    public void setDisplayName() {
        mEmptyUser.setDisplayName("TestDisplayName");
        assertEquals("TestDisplayName", mEmptyUser.getDisplayName());
    }

    @Test
    public void setEmail() {
        mEmptyUser.setEmail("Test@set.mail");
        assertEquals("Test@set.mail", mEmptyUser.getEmail());
    }

    @Test
    public void setPushToken() {
        mEmptyUser.setPushToken("test:token");
        assertEquals("test:token", mEmptyUser.getPushToken());
    }

    @Test
    public void getDisplayName() {
        assertEquals("Fiona", mUser.getDisplayName());
    }

    @Test
    public void getEmail() {
        assertEquals("Fiona@gmail.com", mUser.getEmail());
    }

    @Test
    public void getPushToken() {
        assertEquals("some:token", mUser.getPushToken());
    }
}