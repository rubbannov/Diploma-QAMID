package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.utils.TestHelper.logIn;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.page.MainPage;

@Epic("Главный экран с новостями")
@Feature("Проверка главного экрана")
@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class MainScreenTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE, "screenshot");

    @Before
    public void precondition() {
        logIn();
    }

    MainPage mainPage = new MainPage();

    @Test
    @Story("Проверка отображения главного экрана приложения")
    @Description("Проверяем что главная страница загружена и элементы отображаются")
    public void newsListTest() {
        mainPage.waitingPageToLoad();
        mainPage.checkPageLoaded();

    }

}
