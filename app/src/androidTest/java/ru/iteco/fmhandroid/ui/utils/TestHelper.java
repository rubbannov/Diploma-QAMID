package ru.iteco.fmhandroid.ui.utils;

import static io.qameta.allure.kotlin.Allure.step;
import static ru.iteco.fmhandroid.ui.test.TestData.VALID_LOGIN;
import static ru.iteco.fmhandroid.ui.test.TestData.VALID_PASSWORD;

import android.os.SystemClock;

import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.ui.page.LoginPage;
import ru.iteco.fmhandroid.ui.page.MainPage;
import ru.iteco.fmhandroid.ui.page.NavBarActions;

public class TestHelper {

    public static void waitFor(long millis) {
        step("Ждем " + millis/1000 + " сек");
        SystemClock.sleep(millis);
    }

    public static void logOut() {
        step("Предусловие: Выход из учетной записи");
        LoginPage loginPage = new LoginPage();
        NavBarActions navBarActions = new NavBarActions();
        MainPage mainPage = new MainPage();
        try {
            step("Предусловие: ожидаем экран авторизации");
            loginPage.waitingPageToLoad();
            loginPage.checkPageLoaded();
        } catch (AssertionError e) {
            step("Предусловие: экрана авторизации не видно, выходим из учетной записи");
            mainPage.waitingPageToLoad();
            navBarActions.logOut();
        }
    }

    public static void logIn() {
        step("Предусловие: Пользователь должен быть авторизован");
        LoginPage loginPage = new LoginPage();
        MainPage mainPage = new MainPage();
        try {
            step("Предусловие: ожидаем начальный экран, убеждаемся что мы авторизованы в сиситеме");
            waitFor(1000);
            mainPage.waitingPageToLoad();
            mainPage.checkPageLoaded();
        } catch (Exception e) {
            step("Предусловие: авторизация с валидными данными");
            loginPage.waitingPageToLoad();
            loginPage.inputLogin(VALID_LOGIN);
            loginPage.inputPassword(VALID_PASSWORD);
            loginPage.signIn();
            waitFor(1000);
        }
    }
}
