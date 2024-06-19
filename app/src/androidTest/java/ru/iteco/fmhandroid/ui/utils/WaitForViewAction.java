package ru.iteco.fmhandroid.ui.utils;

import android.view.View;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;
import org.hamcrest.Matcher;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
public class WaitForViewAction implements ViewAction {
    private final Matcher<View> viewMatcher;
    private final long timeout;

    public WaitForViewAction(Matcher<View> viewMatcher, long timeout) {
        this.viewMatcher = viewMatcher;
        this.timeout = timeout;
    }

    @Override
    public Matcher<View> getConstraints() {
        return isRoot();
    }

    @Override
    public String getDescription() {
        return "wait for a specific view with matcher <" + viewMatcher + "> during " + timeout + " millis.";
    }

    @Override
    public void perform(UiController uiController, View view) {
        uiController.loopMainThreadUntilIdle();
        final long startTime = System.currentTimeMillis();
        final long endTime = startTime + timeout;

        do {
            for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                if (viewMatcher.matches(child)) {
                    return;
                }
            }

            uiController.loopMainThreadForAtLeast(50);
        } while (System.currentTimeMillis() < endTime);

        throw new PerformException.Builder()
                .withActionDescription(this.getDescription())
                .withViewDescription(HumanReadables.describe(view))
                .build();
    }

    public static ViewAction waitForView(Matcher<View> viewMatcher, long timeout) {
        return new WaitForViewAction(viewMatcher, timeout);
    }
}
