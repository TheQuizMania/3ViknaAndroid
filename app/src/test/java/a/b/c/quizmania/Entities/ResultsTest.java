package a.b.c.quizmania.Entities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ResultsTest {

    private Results mEmptyResults = new Results();
    private Results mResults;

    @Before
    public void initVariables() {
        List<String> answers = new ArrayList<>();
        answers.add("Some answers");
        mResults = new Results("Cat", "Diff", "Question", "CorrectAnswer", answers);

    }

    @Test
    public void tests() {
        List<String> testAnswers = new ArrayList<>();
        testAnswers.add("test");
        mEmptyResults.setCorrectAnswer("TestCorrect");
        mEmptyResults.setCategory("testCat");
        mEmptyResults.setDifficulty("testDiff");
        mEmptyResults.setQuestion("testQuestion");
        mEmptyResults.setIncorrectAnswers(testAnswers);

        assertEquals("TestCorrect", mEmptyResults.getCorrectAnswer());
        assertEquals("testCat", mEmptyResults.getCategory());
        assertEquals("testDiff", mEmptyResults.getDifficulty());
        assertEquals("testQuestion", mEmptyResults.getQuestion());
        assertEquals(1, mEmptyResults.getIncorrectAnswers().size());

    }



}