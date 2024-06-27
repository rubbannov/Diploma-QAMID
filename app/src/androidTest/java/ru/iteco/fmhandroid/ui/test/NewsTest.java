package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.utils.TestHelper.logIn;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.page.NavBarActions;
import ru.iteco.fmhandroid.ui.page.NewsPage;


@Epic("Экран 'News'")
@Feature("Проверка раздела с новостями")
@DisplayName("Проверка экрана с новостями")
@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class NewsTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE, "screenshot");

    NavBarActions navBarActions = new NavBarActions();
    NewsPage newsPage = new NewsPage();

    int ITEM_NUMBER = 1;

    @Before
    public void precondition() {
        logIn();
        navBarActions.clickBurger();
        navBarActions.goToNewsPage();
        newsPage.waitingPageToLoad();
    }

    @Test
    @Story("Проверка отображения экрана с новостями")
    @Description("Проверяем что страница с новостями загружена и элементы отображаются")
    @DisplayName("Проверка экрана с новостями")
    public void newsListTest() {
        newsPage.checkPageLoaded();

    }

    @Test
    @Story("Проверка элементов страницы")
    @Description("Проверка взаимодействия с пользователем элементов новостей")
    @DisplayName("Проверка интерактивности элементов новостей")
    public void newsListItemsTest() {
        newsPage.newsItemOpenClose(ITEM_NUMBER);
        newsPage.checkNewsDescriptionItemExist();

    }
}
