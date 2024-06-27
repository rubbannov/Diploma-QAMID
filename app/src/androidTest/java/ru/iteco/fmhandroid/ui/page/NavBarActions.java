package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static io.qameta.allure.kotlin.Allure.step;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class NavBarActions {
    ViewInteraction mainMenuBurger = onView(withId(R.id.main_menu_image_button));
    ViewInteraction profileIconButton = onView(withId(R.id.authorization_image_button));
    ViewInteraction ourMissionButton = onView(withId(R.id.our_mission_image_button));
    ViewInteraction newsButton = onView(withText("News"));
    ViewInteraction aboutButton = onView(withText("About"));
    ViewInteraction logOutButton = onView(withText("Log out"));
    ViewInteraction mainMenuButton = onView(withText("Main"));

    public void logOut() {
        step("Выход из учетной записи:");
        profileIconButton.check(matches(isDisplayed()));
        profileIconButton.perform(click());
        step("Тап по иконке пользователя");
        logOutButton.check(matches(isDisplayed()));
        logOutButton.perform(click());
        step("Тап по кнопке Выйти");
    }

    public void clickBurger() {
        step("Тап по бургеру");
        mainMenuBurger.check(matches(isDisplayed()));
        mainMenuBurger.perform(click());
    }

    public void goToOurMissionPage() {
        step("Переход на экран с цитатами");
        ourMissionButton.check(matches(isDisplayed()));
        ourMissionButton.perform(click());
    }

    public void goToNewsPage() {
        step("Переход на экран с новостями");
        newsButton.check(matches(isDisplayed()));
        newsButton.perform(click());
    }

    public void goToAboutPage() {
        step("Переход на экран с информацией о разработчике");
        aboutButton.check(matches(isDisplayed()));
        aboutButton.perform(click());
    }

    public void goToMainPage() {
        step("Переход на главный экран");
        mainMenuButton.check(matches(isDisplayed()));
        mainMenuButton.perform(click());
    }
}
