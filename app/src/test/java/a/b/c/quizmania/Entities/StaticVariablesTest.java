package a.b.c.quizmania.Entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class StaticVariablesTest {

    StaticVariables mVariables;

    @Before
    public void initVariables() {
        mVariables = new StaticVariables();
        mVariables.setCurrScore(new Score());
        mVariables.pendingChallenge = new Challenge();
        mVariables.currChallenge = new Challenge();
        mVariables.question = new Question();
    }

    @After
    public void resetVariables() {
        mVariables = new StaticVariables();
        mVariables.setCurrScore(null);
        mVariables.pendingChallenge = null;
        mVariables.currChallenge = null;
        mVariables.question = null;
    }

    @Test
    public void testStaticVariables() {
        assertTrue(mVariables.getCurrScore() != null);
        assertTrue(mVariables.pendingChallenge != null);
        assertTrue(mVariables.currChallenge != null);
        assertTrue(mVariables.question != null);
    }

}