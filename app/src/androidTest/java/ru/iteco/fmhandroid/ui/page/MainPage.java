package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.utils.WaitForViewAction;

public class MainPage {

    ViewInteraction newsListOpener = onView(withId(R.id.expand_material_button));
    ViewInteraction newsItem = onView(withId(R.id.all_news_cards_block_constraint_layout));

    public void checkPageLoaded() {
        newsListOpener.check(matches(isDisplayed()));
    }

    public void waitingPageToLoad() {
        Espresso.onView(ViewMatchers.isRoot()).perform(WaitForViewAction.waitForView(
                ViewMatchers.withId(R.id.expand_material_button), 10000));
    }
    public void newsListOpenClose() {
        newsListOpener.check(matches(isDisplayed()));
        newsListOpener.perform(click());
    }
}
