package a.b.c.quizmania.Entities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuestionStatsTest {

    private QuestionStats mStats;
    private QuestionStats mEmptyStats = new QuestionStats();

    @Before
    public void initVariables() {
        List<String> answers = new ArrayList<>();
        answers.add("Answer1");
        mStats = new QuestionStats(12, "Question", "Diff", "Cat", "answer", answers, true);
    }

    @Test
    public void questionStatTest() {
        mEmptyStats.setQuestionCategory("Cat");
        mEmptyStats.setQuestionDifficulty("Diff");
        mEmptyStats.setRightAnswer("RAnswer");
        mEmptyStats.setStatsQuestion("Question");
        mEmptyStats.setTimeToAnswer(13);
        mEmptyStats.setWasCorrect(false);
        List<String> strings = new ArrayList<>();
        strings.add("String");
        mEmptyStats.setWrongAnswers(strings);

        assertEquals("Cat", mEmptyStats.getQuestionCategory());
        assertEquals("Diff", mEmptyStats.getQuestionDifficulty());
        assertEquals("RAnswer", mEmptyStats.getRightAnswer());
        assertEquals("Question", mEmptyStats.getStatsQuestion());
        assertEquals(13, mEmptyStats.getTimeToAnswer());
        assertEquals(false, mEmptyStats.isWasCorrect());
        assertEquals(1, mEmptyStats.getWrongAnswers().size());


    }

}