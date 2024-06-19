package ru.iteco.fmhandroid.ui.utils;

import static ru.iteco.fmhandroid.ui.test.TestData.VALID_LOGIN;
import static ru.iteco.fmhandroid.ui.test.TestData.VALID_PASSWORD;

import android.os.SystemClock;

import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.ui.page.LoginPage;
import ru.iteco.fmhandroid.ui.page.MainPage;
import ru.iteco.fmhandroid.ui.page.NavBarActions;

public class TestHelper {

    public static void waitFor(long millis) {
        SystemClock.sleep(millis);
    }

    public static void logOut() {
        LoginPage loginPage = new LoginPage();
        NavBarActions navBarActions = new NavBarActions();
        MainPage mainPage = new MainPage();
        try {
            loginPage.waitingPageToLoad();
            loginPage.checkPageLoaded();
        } catch (Exception e) {
            mainPage.waitingPageToLoad();
            navBarActions.logOut();
        }
    }
    @Step("Предусловие: Пользователь должен быть авторизован")
    public static void logIn() {
        LoginPage loginPage = new LoginPage();
        MainPage mainPage = new MainPage();
        try {
            waitFor(1000);
            mainPage.waitingPageToLoad();
            mainPage.checkPageLoaded();
        } catch (Exception e) {
            loginPage.waitingPageToLoad();
            loginPage.inputLogin(VALID_LOGIN);
            loginPage.inputPassword(VALID_PASSWORD);
            loginPage.signIn();
            waitFor(1000);
        }
    }
}
