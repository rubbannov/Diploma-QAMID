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
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.page.NavBarActions;
import ru.iteco.fmhandroid.ui.page.NewsPage;


@Epic("Экран 'News'")
@Feature("Проверка раздела с новостями")
@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class NewsTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE, "screenshot");

    @Before
    public void precondition() {
        logIn();
    }

    NavBarActions navBarActions = new NavBarActions();
    NewsPage newsPage = new NewsPage();

    @Test
    @Story("Проверка отображения экрана с новостями")
    @Description("Проверяем что страница с новостями загружена и элементы отображаются")
    public void newsListTest() {
        Allure.step("Переходим на страницу новостей", () -> {
            navBarActions.clickBurger();
            navBarActions.goToNewsPage();
            newsPage.waitingPageToLoad();
        });
        Allure.step("Проверяем что страница загружена и отображаются элементы страницы", () -> {
            newsPage.checkPageLoaded();
        });
    }

    @Test
    @Story("Проверка элементов страницы")
    @Description("Проверка взаимодействия с пользователем элементов новостей")
    public void newsListItemsTest() {
        int ITEM_NUMBER = 1;
        Allure.step("Переходим на страницу новостей", () -> {
            navBarActions.clickBurger();
            navBarActions.goToNewsPage();
            newsPage.waitingPageToLoad();
        });
        Allure.step("Проверяем что элемент раскрывается и отображается описание", () -> {
            newsPage.newsItemOpenClose(ITEM_NUMBER);
            newsPage.checkNewsDescriptionItemExist();
        });
    }
}
