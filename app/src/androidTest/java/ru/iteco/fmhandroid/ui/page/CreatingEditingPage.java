package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;


import static ru.iteco.fmhandroid.ui.test.TestData.CATEGORY_ADVERTISEMENT;
import static ru.iteco.fmhandroid.ui.test.TestData.DESCRIPTION;
import static ru.iteco.fmhandroid.ui.test.TestData.TITLE;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.utils.WaitForViewAction;

public class CreatingEditingPage {
    ViewInteraction categoryField = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
    ViewInteraction titleField = onView(withId(R.id.news_item_title_text_input_edit_text));
    ViewInteraction dateField = onView(withId(R.id.news_item_publish_date_text_input_edit_text));
    ViewInteraction timeField = onView(withId(R.id.news_item_publish_time_text_input_edit_text));
    ViewInteraction descriptionField = onView(withId(R.id.news_item_description_text_input_edit_text));
    ViewInteraction switchActive = onView(withId(R.id.switcher));
    ViewInteraction saveButton = onView(withId(R.id.save_button));
    ViewInteraction cancelButton = onView(withId(R.id.cancel_button));
    ViewInteraction okButton = onView(
            allOf(withId(android.R.id.button1), withText("OK")));

    public void checkPageLoaded() {
        categoryField.check(matches(isDisplayed()));
    }

    public void waitingPageToLoad() {
        Espresso.onView(ViewMatchers.isRoot()).perform(WaitForViewAction.waitForView(
                ViewMatchers.withId(
                        R.id.news_item_category_text_auto_complete_text_view), 10000));
    }

    public void waitAndCheckPage() {
        waitingPageToLoad();
        checkPageLoaded();
    }

    public void editCategory(String text) {
        categoryField.check(matches(isDisplayed()));
        categoryField.perform(replaceText(text));
    }
    public void editTitle(String text) {
        titleField.check(matches(isDisplayed()));
        titleField.perform(replaceText(text), closeSoftKeyboard());
    }
    public void editDate() {
        dateField.check(matches(isDisplayed()));
        dateField.perform(click());
        okButton.perform(scrollTo(), click());
    }
    public void editTime() {
        timeField.check(matches(isDisplayed()));
        timeField.perform(click());
        okButton.perform(scrollTo(), click());
    }
    public void editDescription(String description) {
        descriptionField.check(matches(isDisplayed()));
        descriptionField.perform(replaceText(description), closeSoftKeyboard());
    }
    public void switchOnOff() {
        switchActive.check(matches(isDisplayed()));
        switchActive.perform(click());
    }
    public void tapToSaveButton() {
        saveButton.check(matches(isDisplayed()));
        saveButton.perform(click());
    }
    public void tapToCancelButton() {
        cancelButton.check(matches(isDisplayed()));
        cancelButton.perform(click());
    }

    public void creatingNewNews() {
        editCategory(CATEGORY_ADVERTISEMENT);
        editTitle(TITLE);
        editDate();
        editTime();
        editDescription(DESCRIPTION);
        tapToSaveButton();
    }

}
