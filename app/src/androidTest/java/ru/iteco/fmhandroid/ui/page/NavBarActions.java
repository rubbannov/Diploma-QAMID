package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;

public class NavBarActions {
    ViewInteraction mainMenuBurger = onView(withId(R.id.main_menu_image_button));
    ViewInteraction profileIconButton = onView(withId(R.id.authorization_image_button));
    ViewInteraction ourMissionButton = onView(withId(R.id.our_mission_image_button));
    ViewInteraction newsButton = onView(withText("News"));
    ViewInteraction aboutButton = onView(withText("About"));
    ViewInteraction logOutButton = onView(withText("Log out"));
    ViewInteraction mainMenuButton = onView(withText("Main"));

    @Step("Выходим из учетной записи")
    public void logOut() {
        profileIconButton.check(matches(isDisplayed()));
        profileIconButton.perform(click());

        logOutButton.check(matches(isDisplayed()));
        logOutButton.perform(click());
    }

    @Step("Тап по бургеру")
    public void clickBurger() {
        mainMenuBurger.check(matches(isDisplayed()));
        mainMenuBurger.perform(click());
    }

    @Step("Переход на экран с цитатами")
    public void goToOurMissionPage() {
        ourMissionButton.check(matches(isDisplayed()));
        ourMissionButton.perform(click());
    }

    @Step("Переход на экран с новостями")
    public void goToNewsPage() {
        newsButton.check(matches(isDisplayed()));
        newsButton.perform(click());
    }

    @Step("Переход на экран с информацией о разработчике")
    public void goToAboutPage() {
        aboutButton.check(matches(isDisplayed()));
        aboutButton.perform(click());
    }

    @Step("Переход на главный экран")
    public void goToMainPage() {
        mainMenuButton.check(matches(isDisplayed()));
        mainMenuButton.perform(click());
    }
}
