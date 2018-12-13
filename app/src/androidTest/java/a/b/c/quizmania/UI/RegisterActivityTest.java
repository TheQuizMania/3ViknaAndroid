package a.b.c.quizmania.UI;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import a.b.c.quizmania.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(MockitoJUnitRunner.class)
public class RegisterActivityTest {

    private ViewInteraction userNameBox;
    private ViewInteraction emailBox;
    private ViewInteraction passWBox;
    private ViewInteraction passWConfirmBox;
    private ViewInteraction registerBtn;
    private RegisterActivity activity;

    @Rule
    public ActivityTestRule<RegisterActivity> mActivity
            = new ActivityTestRule<>(RegisterActivity.class,
            false, false);



    @Mock
    private FirebaseDatabase mDb;

    @Mock
    private DatabaseReference mRef;

    @Mock
    private DataSnapshot mSnap;

    @Mock
    private FirebaseAuth mAuth;

    @Mock
    private OnCompleteListener<AuthResult> mListener;

    @Mock
    private Task<AuthResult> mTask;

    @Before
    public void setUp() {

        RegisterActivity.setInstance(mDb);
        mActivity.launchActivity(new Intent());
        activity = mActivity.getActivity();
        userNameBox = onView(withId(R.id.username_signUp));
        emailBox = onView(withId(R.id.email_signUp));
        passWBox = onView(withId(R.id.passwd_signUp));
        passWConfirmBox = onView(withId(R.id.passwd_confirm));
        registerBtn = onView(withId(R.id.sign_Up));

    }

    @After
    public void tearDown() {
    }

    @Test
    public void noInputTest() {
        registerBtn.perform(closeSoftKeyboard(), click());
        userNameBox.check(matches(hasFocus()));
        userNameBox.check(matches(hasErrorText(activity.getString(R.string.username_needed))));
    }

    @Test
    public void onlyOneFieldFilledTest(){
        userNameBox.perform(typeText("Test"), closeSoftKeyboard());
        registerBtn.perform(click());
        emailBox.check(matches(hasFocus()));
        emailBox.check(matches(hasErrorText(activity.getString(R.string.email_needed))));
        userNameBox.perform(clearText());
        emailBox.perform(typeText("test@testdomain.com"), closeSoftKeyboard());
        registerBtn.perform(click());
        userNameBox.check(matches(hasFocus()));
        userNameBox.check(matches(hasErrorText(activity.getString(R.string.username_needed))));
        emailBox.perform(clearText());
        passWBox.perform(clearText(), typeText("Password123"), closeSoftKeyboard());
        registerBtn.perform(click());
        userNameBox.check(matches(hasFocus()));
        userNameBox.check(matches(hasErrorText(activity.getString(R.string.username_needed))));
    }

    @Test
    public void emailValidityTest(){
        userNameBox.perform(typeText("Test"), closeSoftKeyboard());
        emailBox.perform(typeText("test@testdomain.com"), closeSoftKeyboard());
        registerBtn.perform(click());
        passWBox.check(matches(hasFocus()));
        passWBox.check(matches(hasErrorText(activity.getString(R.string.password_needed))));
        emailBox.perform(clearText(), typeText( "testtestdomain.com"), closeSoftKeyboard());
        registerBtn.perform(click());
        emailBox.check(matches(hasFocus()));
        emailBox.check(matches(hasErrorText(activity.getString(R.string.email_not_valid))));
        emailBox.perform(clearText(), typeText("test@testdomaincom"), closeSoftKeyboard());
        registerBtn.perform(click());
        emailBox.check(matches(hasFocus()));
        emailBox.check(matches(hasErrorText(activity.getString(R.string.email_not_valid))));
        emailBox.perform(clearText(), typeText("testtestdomaincom"), closeSoftKeyboard());
        registerBtn.perform(click());
        emailBox.check(matches(hasFocus()));
        emailBox.check(matches(hasErrorText(activity.getString(R.string.email_not_valid))));
    }

    @Test
    public void passwordValidityTest(){
        userNameBox.perform(typeText("Test"), closeSoftKeyboard());
        emailBox.perform(typeText("test@testdomain.tst"), closeSoftKeyboard());
        passWBox.perform(typeText(""), closeSoftKeyboard());
        passWBox.perform(clearText(), typeText(""), closeSoftKeyboard());
//      passWBox.perform(typeText("Test123"), closeSoftKeyboard());
        registerBtn.perform(click());
    }

    @Test
    public void confirmPasswordTest() {
        userNameBox.perform(typeText("Test"), closeSoftKeyboard());
        emailBox.perform(typeText("test@testdomain.tst"), closeSoftKeyboard());
        passWBox.perform(typeText("YoloSwag6969"), closeSoftKeyboard());
        passWConfirmBox.perform(typeText("Testing123"), closeSoftKeyboard());
        registerBtn.perform(click());
        passWConfirmBox.check(matches(hasFocus()));
        passWConfirmBox.check(matches(hasErrorText(activity.getString(R.string.password_mismatch))));
    }
}