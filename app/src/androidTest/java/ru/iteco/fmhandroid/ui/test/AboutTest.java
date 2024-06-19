package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.utils.TestHelper.logIn;
import static ru.iteco.fmhandroid.ui.utils.TestHelper.waitFor;

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
import ru.iteco.fmhandroid.ui.page.AboutPage;
import ru.iteco.fmhandroid.ui.page.NavBarActions;

@Epic("Экран с информацией о приложении и разработчике")
@Feature("Проверка раздела 'О нас'")
@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AboutTest {

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
    @Story("Проверка экрана информации о разработчике")
    @Description("Проверяем что информация о разработчике доступна и видна")
    public void aboutInformationTest() {
        NavBarActions navBarActions = new NavBarActions();
        AboutPage aboutPage = new AboutPage();
            waitFor(1000);
            navBarActions.clickBurger();
            navBarActions.goToAboutPage();
            aboutPage.waitingPageToLoad();

            aboutPage.checkPageLoaded();

    }
}
