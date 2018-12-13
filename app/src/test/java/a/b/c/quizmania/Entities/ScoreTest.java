package a.b.c.quizmania.Entities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScoreTest {

    Score mScore;
    Score mHalfScore;
    Score mEmptyScore;

    @Before
    public void initVariables() {
        List<QuestionStats> stats = new ArrayList<>();
        stats.add(new QuestionStats());
        mScore = new Score("Difficulty", "Category", 1, stats);
        mHalfScore = new Score("Difficulty2", "Category2");
        mEmptyScore = new Score();
    }

    @Test
    public void setters() {
        List<QuestionStats> testStats = new ArrayList<>();
        testStats.add(new QuestionStats());
        testStats.add(new QuestionStats());
        mEmptyScore.setCategory("setCat");
        mEmptyScore.setCorrectAnswers(13);
        mEmptyScore.setDifficulty("setDif");
        mEmptyScore.setQuestionStats(testStats);

        assertEquals("setCat", mEmptyScore.getCategory());
        assertEquals("setDif", mEmptyScore.getDifficulty());
        assertEquals(13, mEmptyScore.getCorrectAnswers());
        assertEquals(2, mEmptyScore.getQuestionStats().size());
    }

}