package a.b.c.quizmania.Entities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserTest {

    User mEmptyUser;
    User mUser;

    @Before
    public void initVariables() {
        mEmptyUser = new User();
        List<Score> scores = new ArrayList<>();
        scores.add(new Score());
        scores.add(new Score());
        mUser = new User("Estelle", scores, 15, 12);
    }

    @Test
    public void setUserName() {
        mEmptyUser.setUserName("Jezebel");
        assertEquals("Jezebel", mEmptyUser.getUserName());
    }

    @Test
    public void setScores() {
        List<Score> testScore = new ArrayList<>();
        testScore.add(new Score());
        mEmptyUser.setScores(testScore);
        assertEquals(1, mEmptyUser.getScores().size());
    }

    @Test
    public void setWins() {
        mEmptyUser.setWins(2);
        assertEquals(2, mEmptyUser.getWins());
    }

    @Test
    public void setLosses() {
        mEmptyUser.setLosses(3);
        assertEquals(3, mEmptyUser.getLosses());
    }

    @Test
    public void getUserName() {
        assertEquals("Estelle", mUser.getUserName());
    }

    @Test
    public void getScores() {
        assertEquals(2, mUser.getScores().size());
    }

    @Test
    public void getWins() {
        assertEquals(15, mUser.getWins());
    }

    @Test
    public void getLosses() {
        assertEquals(12, mUser.getLosses());
    }
}