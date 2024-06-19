package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import static ru.iteco.fmhandroid.ui.utils.CustomMatchers.withIndex;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matchers;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.utils.WaitForViewAction;

public class NewsPage {
    int index = 0;
    ViewInteraction sortButton = onView(withId(R.id.sort_news_material_button));
    ViewInteraction filterButton = onView(withId(R.id.filter_news_material_button));
    ViewInteraction editButton = onView(withId(R.id.edit_news_material_button));
    ViewInteraction expandButton = onView(withId(R.id.expand_material_button));
    ViewInteraction newsList = onView(withId(R.id.news_list_recycler_view));
    ViewInteraction newsItemTitle = onView(withIndex(withId(R.id.news_item_title_text_view), index));
    ViewInteraction newsItemDescription = onView(withId(R.id.news_item_description_text_view));

    public void checkPageLoaded() {
        newsList.check(matches(isDisplayed()));
        sortButton.check(matches(isDisplayed()));
        filterButton.check(matches(isDisplayed()));
    }

    public void waitingPageToLoad() {
        Espresso.onView(ViewMatchers.isRoot()).perform(WaitForViewAction.waitForView(
                ViewMatchers.withId(R.id.news_list_recycler_view), 10000));
    }

    public void sortNews() {
        sortButton.check(matches(isDisplayed()));
        sortButton.perform(click());
    }

    public void filterNews() {
        filterButton.check(matches(isDisplayed()));
        filterButton.perform(click());
    }

    public void editNews() {
        editButton.check(matches(isDisplayed()));
        editButton.perform(click());
    }

    public void hideShowNewsList() {
        expandButton.check(matches(isDisplayed()));
        expandButton.perform(click());
    }

    public void newsItemOpenClose(int numberOfItem) {
        newsList.check(matches(isDisplayed()));
        newsList.perform(actionOnItemAtPosition(numberOfItem, click()));
    }

    public void checkNewsTitleExist(String title, int indexOfItem) {
        index = indexOfItem;
        newsItemTitle.check(matches(withText(title)));

    }

    public void checkNewsDescriptionWithTextExist(String description) {
        newsItemDescription.check(matches(withText(description)));
    }

    public void checkNewsDescriptionItemExist() {
        onView(
                allOf(
                    withId(R.id.news_item_description_text_view),
                    withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)
                )
        ).check(matches(isDisplayed()));
    }

    public void checkNewsTitleDoesNotExist(String title) {
        ViewInteraction doesNotExistTitle = onView(
                allOf(withId(R.id.news_item_title_text_view), withText(title)));
        doesNotExistTitle.check(doesNotExist());
    }

    public void checkNewsDescriptionDoesNotExist(String Description) {
        ViewInteraction doesNotExistDescription = onView(
                allOf(withId(R.id.news_item_description_text_view), withText(Description)));
        doesNotExistDescription.check(doesNotExist());
    }

    public static void goToControlPage() {
        NavBarActions navBarActions = new NavBarActions();
        ControlPanelPage controlPanelPage = new ControlPanelPage();
        NewsPage newsPage = new NewsPage();
        navBarActions.clickBurger();
        navBarActions.goToNewsPage();
        newsPage.waitingPageToLoad();
        newsPage.editNews();
        controlPanelPage.waitingPageToLoad();
        controlPanelPage.checkPageLoaded();
    }
    public static void goToCreateNewsPage() {
        goToControlPage();
        ControlPanelPage controlPanelPage = new ControlPanelPage();
        controlPanelPage.tapCreateButton();
    }

}
