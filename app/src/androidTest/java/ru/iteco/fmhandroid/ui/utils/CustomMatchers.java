package ru.iteco.fmhandroid.ui.utils;import android.view.View;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static org.hamcrest.Matchers.allOf;

public class CustomMatchers {

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(View item) {
                return matcher.matches(item) && currentIndex++ == index;
            }
        };
    }
}
