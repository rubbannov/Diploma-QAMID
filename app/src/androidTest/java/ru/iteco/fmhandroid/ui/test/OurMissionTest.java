package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.utils.TestHelper.logIn;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

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
import io.qameta.allure.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.page.NavBarActions;
import ru.iteco.fmhandroid.ui.page.OurMissionPage;

@Epic("Экран с тематическими цитатами")
@Feature("Проверка раздела с цитатами")
@DisplayName("Экран с тематическими цитатами")
@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class OurMissionTest {
    //TODO папку с тестами коммитить и пушить в последнюю очередь
    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE, "screenshot");

    @Before
    public void precondition() {
        logIn();
    }

    @Test
    @Story("Проверка отображения экрана с цитатами")
    @Description("Проверяем что страница с цитатами загружена и элементы отображаются")
    @DisplayName("Проверка отображения экрана с цитатами")
    public void ourMissionScreenTest() {
        NavBarActions navBarActions = new NavBarActions();
        OurMissionPage ourMissionPage = new OurMissionPage();
        Allure.step("Переходим на страницу с цитатами", () -> {
            navBarActions.goToOurMissionPage();
            ourMissionPage.waitingPageToLoad();
        });
        Allure.step("Проверяем что страница с цитатами загружена и отображаются элементы страницы", () -> {
            ourMissionPage.checkPageLoaded();
        });
    }

    @Test
    @Story("Проверка взаимодействия элемента с цитататми")
    @Description("Проверяем как открывается элемент с цитатой")
    @DisplayName("Проверка взаимодействия элемента с цитататми")
    public void ourMissionQuotesTest() {
        NavBarActions navBarActions = new NavBarActions();
        OurMissionPage ourMissionPage = new OurMissionPage();
        Allure.step("Переходим на страницу с цитатами", () -> {
            navBarActions.goToOurMissionPage();
            ourMissionPage.waitingPageToLoad();
            ourMissionPage.checkPageLoaded();
        });
        Allure.step("Проверяем что элемент раскрывается и отображается описание", () -> {
            ourMissionPage.quotesItemOpenClose(2);
            ourMissionPage.checkDescriptionItem();
        });

    }
}
