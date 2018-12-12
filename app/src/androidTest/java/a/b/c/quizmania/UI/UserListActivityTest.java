package a.b.c.quizmania.UI;

import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import a.b.c.quizmania.Entities.UserListItem;

public class UserListActivityTest {

    @Before
    public void setVariables() {
        List<UserListItem> users = new ArrayList<>();
        users.add(new UserListItem("test@email.com","test1"));
    }

}