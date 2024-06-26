package ru.iteco.fmhandroid.ui.test;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.test.TestData.EMPTY_STRING;
import static ru.iteco.fmhandroid.ui.test.TestData.INCORRECT_VALUE;
import static ru.iteco.fmhandroid.ui.test.TestData.INVALID_LOGIN;
import static ru.iteco.fmhandroid.ui.test.TestData.INVALID_PASSWORD;
import static ru.iteco.fmhandroid.ui.test.TestData.SQL_INJECTION;
import static ru.iteco.fmhandroid.ui.test.TestData.TOAST_MSG_CANT_BE_EMPTY;
import static ru.iteco.fmhandroid.ui.test.TestData.TOAST_MSG_SOMETHING_WENT_WRONG;
import static ru.iteco.fmhandroid.ui.test.TestData.VALID_LOGIN;
import static ru.iteco.fmhandroid.ui.test.TestData.VALID_PASSWORD;
import static ru.iteco.fmhandroid.ui.test.TestData.XSS_INJECTION;
import static ru.iteco.fmhandroid.ui.utils.TestHelper.logOut;
import static ru.iteco.fmhandroid.ui.utils.TestHelper.waitFor;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.page.LoginPage;
import ru.iteco.fmhandroid.ui.page.MainPage;

@Epic("Форма авторизации")
@Feature("Проверка формы авторизации")
@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE, "screenshot");

    private View decorView;

    @Before
    public void setUp() {
        activityRule.getScenario().onActivity(activity -> {
            decorView = activity.getWindow().getDecorView();
        });
        logOut();
    }

    LoginPage loginPage = new LoginPage();

    @Test
    @Story("Авторизация с правильными данными")
    @Description("Проверка успешного входа в систему с корректными данными")
    public void validAuthTest() {
        Allure.step("Открыть приложение", () -> {
            loginPage.waitingPageToLoad();
            loginPage.checkPageLoaded();
        });

        Allure.step("Ввести правильные логин и пароль", () -> {
            loginPage.inputLogin(VALID_LOGIN);
            loginPage.inputPassword(VALID_PASSWORD);
        });

        Allure.step("Тап по кнопке 'Войти'", () -> {
            waitFor(1000);
            loginPage.signIn();
        });

        MainPage mainPage = new MainPage();
        Allure.step("Проверить успешный вход в систему", () -> {
            mainPage.waitingPageToLoad();
            mainPage.checkPageLoaded();
        });
    }

    @Test
    @Story("Авторизация с неправильными данными")
    @Description("Проверка обработки ошибки при неверных данных авторизации")
    public void invalidAuthTest() {
        Allure.step("Открыть приложение", () -> {
            loginPage.waitingPageToLoad();
            loginPage.checkPageLoaded();
        });

        Allure.step("Ввести неправильные логин и пароль", () -> {
            loginPage.inputLogin(INVALID_LOGIN);
            loginPage.inputPassword(INVALID_PASSWORD);
        });

        Allure.step("Тап по кнопке 'Войти'", () -> {
            waitFor(1000);
            loginPage.signIn();
        });

        Allure.step("Проверка что появилось сообщение об ошибке", () -> {
            onView(withText(TOAST_MSG_SOMETHING_WENT_WRONG))
                    .inRoot(withDecorView(Matchers.not(decorView)))
                    .check(matches(isDisplayed()));
        });
    }

    @Test
    @Story("Авторизация с неправильными данными")
    @Description("Проверка обработки ошибки при пустом логине и некорректном пароле")
    public void incorrectAuthTest() {
        Allure.step("Открыть приложение", () -> {
            loginPage.waitingPageToLoad();
            loginPage.checkPageLoaded();
        });

        Allure.step("Ввести в поле 'Логин' пустое значение и в поле 'Пароль' некорректное значение", () -> {
            loginPage.inputLogin(EMPTY_STRING);
            loginPage.inputPassword(INCORRECT_VALUE);
        });

        Allure.step("Тап по кнопке 'Войти'", () -> {
            waitFor(1000);
            loginPage.signIn();
        });

        Allure.step("Проверка что появилось сообщение об ошибке", () -> {
            onView(withText(TOAST_MSG_CANT_BE_EMPTY))
                    .inRoot(withDecorView(Matchers.not(decorView)))
                    .check(matches(isDisplayed()));
        });
    }

    @Test
    @Story("Проверка на наличие SQL инъекций")
    @Description("Проверить, что поле логина защищено от SQL-инъекций")
    public void injectionSQLAuthTest() {
        Allure.step("Открыть приложение", () -> {
            loginPage.waitingPageToLoad();
            loginPage.checkPageLoaded();
        });

        Allure.step("Ввести в поле 'Логин' SQL инъекцию и в поле 'Пароль' любое значение", () -> {
            loginPage.inputLogin(SQL_INJECTION);
            loginPage.inputPassword(VALID_PASSWORD);
        });

        Allure.step("Тап по кнопке 'Войти'", () -> {
            waitFor(1000);
            loginPage.signIn();
        });

        Allure.step("Проверка что появилось сообщение об ошибке", () -> {
            onView(withText(TOAST_MSG_SOMETHING_WENT_WRONG))
                    .inRoot(withDecorView(Matchers.not(decorView)))
                    .check(matches(isDisplayed()));
        });
    }

    @Test
    @Story("Проверка на наличие XSS инъекций")
    @Description("Проверить, что поле логина защищено от XSS-инъекций")
    public void injectionXSSAuthTest() {
        Allure.step("Открыть приложение", () -> {
            loginPage.waitingPageToLoad();
            loginPage.checkPageLoaded();
        });

        Allure.step("Ввести в поле 'Логин' XSS инъекцию и в поле 'Пароль' любое значение", () -> {
            loginPage.inputLogin(XSS_INJECTION);
            loginPage.inputPassword(VALID_PASSWORD);
        });

        Allure.step("Тап по кнопке 'Войти'", () -> {
            waitFor(1000);
            loginPage.signIn();
        });

        Allure.step("Проверка что появилось сообщение об ошибке", () -> {
            onView(withText(TOAST_MSG_SOMETHING_WENT_WRONG))
                    .inRoot(withDecorView(Matchers.not(decorView)))
                    .check(matches(isDisplayed()));
        });
    }
}

