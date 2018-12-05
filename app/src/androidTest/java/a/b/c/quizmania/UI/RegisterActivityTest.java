package a.b.c.quizmania.UI;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegisterActivityTest {

    private ViewInteraction userNameBox;
    private ViewInteraction emailBox;
    private ViewInteraction passWBox;
    private ViewInteraction registerBtn;
    private RegisterActivity activity;

    @Rule
    public ActivityTestRule<RegisterActivity> mActivity
            = new ActivityTestRule<>(RegisterActivity.class,
            false, false);



//    @Mock
//    private FirebaseDatabase mDb;
//
//    @Mock
//    private DatabaseReference mRef;
//
//    @Mock
//    private DataSnapshot mSnap;

    @Before
    public void setUp() {
//        String uId = Objects.requireNonNull(FirebaseAuth
//                                  .getInstance()
//                                  .getCurrentUser()).getUid();
//        when(mDb.getReference("root//Users//" + uId)).thenReturn(mRef);
//        when(mRef.setValue(any(String.class))).thenReturn(null);

//        RegisterActivity.setInstance(mDb);
        mActivity.launchActivity(new Intent());
//        activity = mActivity.getActivity();
//        userNameBox = onView(withId(R.id.username_signUp));
//        emailBox = onView(withId(R.id.email_signUp));
//        passWBox = onView(withId(R.id.passwd_signUp));
//        registerBtn = onView(withId(R.id.sign_Up));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void noInputTest() {
//        registerBtn.perform(click());
//        userNameBox.check(matches(hasFocus()));
//        userNameBox.check(matches(hasErrorText(activity.getString(R.string.username_needed))));
    }
}