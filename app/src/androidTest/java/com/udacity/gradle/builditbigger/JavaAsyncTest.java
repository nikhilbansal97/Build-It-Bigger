package com.udacity.gradle.builditbigger;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by NIKHIL on 27-12-2017.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class JavaAsyncTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private IdlingResource resource;
    private String joke = "";

    @Before
    public void registerIdlingResource() {
        resource = activityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(resource);
    }

    @Test
    public void testJokesAsyncTaskGCE() {
        onView(withId(R.id.jokeButton)).perform(click());
        joke = activityTestRule.getActivity().jokeReceived;
        assert joke.length() > 0;
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(resource);
    }
}
