package com.and.blf.baking_app;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.and.blf.baking_app.ui.MainRecipeListActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;

@RunWith(AndroidJUnit4.class)
public class MainRecipeListActivityTest {


    @Rule
    public ActivityTestRule<MainRecipeListActivity> mActivityTestRule = new ActivityTestRule<>(MainRecipeListActivity.class);

    @Test
    public void clickItem_OpensRecipeHostActivity(){
        //click the main recipe_RV item
        onView(withId(R.id.rv_main_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click())
        );

        //check if the toolbar title is equal to the selected recipe's title
        onView(withId(R.id.activity_recipe_host_toolbar)).check(matches(withToolbarTitle("Nutella Pie")));

        //just checking if there's a textView with text matching "Steps:"
        onView(withId(R.id.textView)).check(matches(withText("Steps:")));
    }

    public static Matcher<View> withToolbarTitle(CharSequence title) {
        return withToolbarTitle(is(title));
    }

    public static Matcher<View> withToolbarTitle(final Matcher<CharSequence> textMatcher) {
            return new BoundedMatcher<View, Toolbar>(Toolbar.class) {
                @Override
                public boolean matchesSafely(Toolbar toolbar) {
                    return textMatcher.matches(toolbar.getTitle());
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText("with toolbar title: ");
                    textMatcher.describeTo(description);
                }
            };
        }


}
