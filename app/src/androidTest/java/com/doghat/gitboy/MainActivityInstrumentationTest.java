package com.doghat.gitboy;

/**
 * Created by saul on 9/14/17.
 */

import android.support.test.rule.ActivityTestRule;

import com.doghat.gitboy.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void validateEditText() {
        onView(withId(R.id.searchRepoEditText)).perform(typeText("Angular"))
                .check(matches(withText("Angular")));
    }
}
