package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.utils.WaitForViewAction;

public class OurMissionPage {
    ViewInteraction title = onView(withId(R.id.our_mission_title_text_view));
    ViewInteraction description = onView(withId(R.id.our_mission_item_description_text_view));
    ViewInteraction quotesList = onView(withId(R.id.our_mission_item_list_recycler_view));
    @Step("Проверка загрузки экрана")
    public void checkPageLoaded() {
        title.check(matches(isDisplayed()));
        quotesList.check(matches(isDisplayed()));
    }

    @Step("Ожидание загрузки экрана")
    public void waitingPageToLoad() {
        Espresso.onView(ViewMatchers.isRoot()).perform(
                WaitForViewAction.waitForView(
                        ViewMatchers.withId(
                                R.id.our_mission_item_list_recycler_view), 10000));
    }
    @Step("Раскрытие элемента с цитатой")
    public void quotesItemOpenClose(int numberOfItem) {
        quotesList.check(matches(isDisplayed()));
        quotesList.perform(actionOnItemAtPosition(numberOfItem, click()));
    }
    @Step("Проверка что цитата раскрылась и видно описание")
    public void checkDescriptionItem() {
        onView(
                allOf(
                withId(R.id.our_mission_item_description_text_view),
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)
                )
        ).check(matches(isDisplayed()));
    }
}
