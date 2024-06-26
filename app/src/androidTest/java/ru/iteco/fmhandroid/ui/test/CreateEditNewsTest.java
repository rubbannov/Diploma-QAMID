package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static io.qameta.allure.kotlin.Allure.step;
import static ru.iteco.fmhandroid.ui.page.ControlPanelPage.generateRandomFourDigitString;
import static ru.iteco.fmhandroid.ui.page.NewsPage.goToControlPage;
import static ru.iteco.fmhandroid.ui.page.NewsPage.goToCreateNewsPage;
import static ru.iteco.fmhandroid.ui.test.TestData.CAEGORY_BIRTHDAY;
import static ru.iteco.fmhandroid.ui.test.TestData.CATEGORY_ADVERTISEMENT;
import static ru.iteco.fmhandroid.ui.test.TestData.DESCRIPTION;
import static ru.iteco.fmhandroid.ui.test.TestData.DESCRIPTION_UPD;
import static ru.iteco.fmhandroid.ui.test.TestData.EMPTY_STRING;
import static ru.iteco.fmhandroid.ui.test.TestData.OVER_100_CHARACTERS_STRING;
import static ru.iteco.fmhandroid.ui.test.TestData.OVER_500_CHARACTERS_STRING;
import static ru.iteco.fmhandroid.ui.test.TestData.SPECIAL_CHARACTERS_STRING;
import static ru.iteco.fmhandroid.ui.test.TestData.SQL_INJECTION;
import static ru.iteco.fmhandroid.ui.test.TestData.TITLE;
import static ru.iteco.fmhandroid.ui.test.TestData.TITLE_UPD;
import static ru.iteco.fmhandroid.ui.test.TestData.TOAST_MSG_EMPTY_FIELDS;
import static ru.iteco.fmhandroid.ui.test.TestData.TOAST_MSG_LONG_DESCRIPTION;
import static ru.iteco.fmhandroid.ui.test.TestData.TOAST_MSG_LONG_TITLE;
import static ru.iteco.fmhandroid.ui.test.TestData.TOAST_MSG_SPECIAL_CHARACTERS;
import static ru.iteco.fmhandroid.ui.test.TestData.XSS_INJECTION;
import static ru.iteco.fmhandroid.ui.utils.TestHelper.logIn;
import static ru.iteco.fmhandroid.ui.utils.TestHelper.waitFor;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import junit.framework.AssertionFailedError;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.page.ControlPanelPage;
import ru.iteco.fmhandroid.ui.page.CreatingEditingPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@Epic("Создание/редактирование новостей")
@Feature("Проверка реализации функционала создания и редактирования новостей")
@DisplayName("Создание/редактирование новостей")
public class CreateEditNewsTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE, "screenshot");

    private View decorView;

    @Before
    public void setUp() {
        activityRule.getScenario().onActivity(
                activity -> decorView = activity.getWindow().getDecorView()
        );
        logIn();
    }

    CreatingEditingPage creatingEditingPage = new CreatingEditingPage();
    ControlPanelPage controlPanelPage = new ControlPanelPage();


    @Test
    @Story("Проверка добавления новой новости")
    @Description("Проверка возможности создания новой новости")
    @DisplayName("Проверка создания новости")
    public void createNewsTest() {
        String testTitle = TITLE + generateRandomFourDigitString();
        try {
            goToCreateNewsPage();
            creatingEditingPage.waitAndCheckPage();
            creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
            creatingEditingPage.editTitle(testTitle);
            creatingEditingPage.editDate();
            creatingEditingPage.editTime();
            creatingEditingPage.editDescription(DESCRIPTION);
            creatingEditingPage.tapToSaveButton();
            waitFor(1000);
            step("Проверяем что новость с названием '" + testTitle + "' создана и отображается в списке");
            ViewInteraction result = controlPanelPage.findNewsByText(testTitle);
            result.check(matches(isDisplayed()));

        } finally {
            controlPanelPage.deleteNews(testTitle);
        }
    }

    @Test
    @Story("Редактирование существующей новости")
    @Description("Проверка возможности редактирования существующей новости")
    @DisplayName("Проверка редактирования новости")
    public void editingNewsTest() {
        String testTitle = TITLE_UPD + generateRandomFourDigitString();
        goToCreateNewsPage();
        creatingEditingPage.creatingNewNews();
        try {
            goToControlPage();
            controlPanelPage.tapEditingNewsButton(TITLE);
            creatingEditingPage.waitAndCheckPage();
            creatingEditingPage.editCategory(CAEGORY_BIRTHDAY);
            creatingEditingPage.editTitle(testTitle);
            creatingEditingPage.editDate();
            creatingEditingPage.editTime();
            creatingEditingPage.editDescription(DESCRIPTION_UPD);
            creatingEditingPage.tapToSaveButton();
            waitFor(1000);
            step("Проверяем что новость с новым названием '" + testTitle + "' создана и отображается в списке");
            ViewInteraction result = controlPanelPage.findNewsByText(testTitle);
            result.check(matches(isDisplayed()));
        } finally {
            controlPanelPage.deleteNews(testTitle);
        }

    }

    @Story("Удаление новости")
    @Description("Проверка возможности удаления новости")
    @DisplayName("Проверка удаления новости")
    @Test
    public void deleteNewsTest() {
        goToCreateNewsPage();
        creatingEditingPage.creatingNewNews();
        controlPanelPage.deleteNews(TITLE);
        waitFor(1000);
        step("Проверяем что новость с названием '" + TITLE + "' удалена и не отображается в списке");
        ViewInteraction result = controlPanelPage.findNewsByText(TITLE);
        waitFor(2000);
        result.check(doesNotExist());
    }

    @Story("Проверка обработки ошибок при некорректном вводе данных")
    @Description("Проверяем, что приложение корректно обрабатывает некорректные данные при редактировании новости")
    @DisplayName("Создание новости с слишком длинным заголовком")
    @Test
    public void wrongDataCreateNewsTest() {
        try {
            goToCreateNewsPage();
            creatingEditingPage.waitAndCheckPage();
            creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
            creatingEditingPage.editTitle(OVER_100_CHARACTERS_STRING);
            creatingEditingPage.editDate();
            creatingEditingPage.editTime();
            creatingEditingPage.editDescription(DESCRIPTION);
            creatingEditingPage.tapToSaveButton();

            step("Проверяем что появилось сообщение об ошибке: " + TOAST_MSG_LONG_TITLE);
            onView(withText(TOAST_MSG_LONG_TITLE))
                    .inRoot(withDecorView(Matchers.not(decorView)))
                    .check(matches(isDisplayed()));
        } catch (Exception e) {
            e.printStackTrace();
            waitFor(2000);
            try {
                ViewInteraction result = controlPanelPage.
                        findNewsByText(OVER_100_CHARACTERS_STRING);
                result.check(doesNotExist());
                waitFor(2000);
                throw new AssertionError("News is visible when it should not be.");
            } catch (AssertionFailedError q) {
                q.printStackTrace();
                controlPanelPage.deleteNews(OVER_100_CHARACTERS_STRING);
                throw new AssertionError("Toast with message '" + TOAST_MSG_LONG_TITLE + "' does not exist");
            }
        }
    }

    @Story("Сохранение пустого названия новости")
    @Description("Проверка обработки ошибок при попытке сохранить новость без названия")
    @DisplayName("Создание новости с пустым заголовком")
    @Test
    public void saveEmptyTitleTest() {
        goToCreateNewsPage();
        creatingEditingPage.waitAndCheckPage();
        creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
        creatingEditingPage.editTitle(EMPTY_STRING);
        creatingEditingPage.editDate();
        creatingEditingPage.editTime();
        creatingEditingPage.editDescription(DESCRIPTION);
        creatingEditingPage.tapToSaveButton();

        step("Проверяем что появилось сообщение об ошибке: " + TOAST_MSG_EMPTY_FIELDS);
        onView(withText(TOAST_MSG_EMPTY_FIELDS))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Story("Ввод слишком длинного описания новости")
    @Description("Проверка обработки ошибок при вводе описания, превышающего допустимую длину")
    @DisplayName("Создание новости с слишком длинным описанием")
    @Test
    public void createNewsWithLongDescription() {
        String testTitle = TITLE + generateRandomFourDigitString();
        try {
            goToCreateNewsPage();
            creatingEditingPage.waitAndCheckPage();
            creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
            creatingEditingPage.editTitle(testTitle);
            creatingEditingPage.editDate();
            creatingEditingPage.editTime();
            creatingEditingPage.editDescription(OVER_500_CHARACTERS_STRING);
            creatingEditingPage.tapToSaveButton();

            step("Проверяем что появилось сообщение об ошибке: " + TOAST_MSG_LONG_DESCRIPTION);
            onView(withText(TOAST_MSG_LONG_DESCRIPTION))
                    .inRoot(withDecorView(Matchers.not(decorView)))
                    .check(matches(isDisplayed()));
        } catch (Exception e) {
            e.printStackTrace();
            waitFor(2000);
            try {
                ViewInteraction result = controlPanelPage.
                        findNewsByText(testTitle);
                result.check(doesNotExist());
                waitFor(2000);
                throw new AssertionError("News is visible when it should not be.");
            } catch (AssertionFailedError q) {
                q.printStackTrace();
                controlPanelPage.deleteNews(testTitle);
                throw new AssertionError("Toast with message '" + TOAST_MSG_LONG_DESCRIPTION + "' does not exist");
            }
        }
    }

    @Story("Ввод спецсимволов в названии новости")
    @Description("Проверка обработки ошибок при вводе спецсимволов в названии")
    @DisplayName("Создание новости с спецсимволами в заголовке")
    @Test
    public void createNewsWithSpecialCharactersInTitleTest() {
        try {
            goToCreateNewsPage();
            creatingEditingPage.waitAndCheckPage();
            creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
            creatingEditingPage.editTitle(SPECIAL_CHARACTERS_STRING);
            creatingEditingPage.editDate();
            creatingEditingPage.editTime();
            creatingEditingPage.editDescription(OVER_500_CHARACTERS_STRING);
            creatingEditingPage.tapToSaveButton();

            step("Проверяем что появилось сообщение об ошибке: " + TOAST_MSG_SPECIAL_CHARACTERS);
            onView(withText(TOAST_MSG_SPECIAL_CHARACTERS))
                    .inRoot(withDecorView(Matchers.not(decorView)))
                    .check(matches(isDisplayed()));
        } catch (Exception e) {
            e.printStackTrace();
            waitFor(2000);
            try {
                ViewInteraction result = controlPanelPage.
                        findNewsByText(SPECIAL_CHARACTERS_STRING);
                result.check(doesNotExist());
                waitFor(2000);
                throw new AssertionError("News is visible when it should not be.");
            } catch (AssertionFailedError q) {
                q.printStackTrace();
                controlPanelPage.deleteNews(SPECIAL_CHARACTERS_STRING);
                throw new AssertionError("Toast with message '" + TOAST_MSG_SPECIAL_CHARACTERS + "' does not exist");
            }
        }
    }

    @Story("Проверка предотвращения SQL-инъекций в поле заголовка новости")
    @Description("Проверить, что поле заголовка защищено от SQL-инъекций ")
    @DisplayName("Создание новости с SQL-инъекцией в заголовке")
    @Test
    public void createNewsWithSQLInjectionInTitleTest() {
        try {
            goToCreateNewsPage();
            creatingEditingPage.waitAndCheckPage();
            creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
            creatingEditingPage.editTitle(SQL_INJECTION);
            creatingEditingPage.editDate();
            creatingEditingPage.editTime();
            creatingEditingPage.editDescription(OVER_100_CHARACTERS_STRING);
            creatingEditingPage.tapToSaveButton();

            step("Проверяем что SQL-инъекция '" + SQL_INJECTION
                    + "' не срабатывает, новость сохраняется с текстом инъекции в заголовке");
            ViewInteraction result = controlPanelPage.findNewsByText(SQL_INJECTION);
            result.check(matches(isDisplayed()));

        } finally {
            controlPanelPage.deleteNews(SQL_INJECTION);
        }
    }

    @Story("Проверка предотвращения SQL-инъекций в поле текста ")
    @Description("Проверить, что поле описания защищено от SQL-инъекций")
    @DisplayName("Создание новости с SQL-инъекцией в описании")
    @Test
    public void createNewsWithSQLInjectionInDescriptionTest() {
        String testTitle = TITLE + generateRandomFourDigitString();
        try {
            goToCreateNewsPage();
            creatingEditingPage.waitAndCheckPage();
            creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
            creatingEditingPage.editTitle(testTitle);
            creatingEditingPage.editDate();
            creatingEditingPage.editTime();
            creatingEditingPage.editDescription(SQL_INJECTION);
            creatingEditingPage.tapToSaveButton();

            step("Проверяем что SQL-инъекция '" + SQL_INJECTION
                    + "' не срабатывает, новость сохраняется с названием '"
                    + testTitle + "' и текстом инъекции в описании");
            ViewInteraction result = controlPanelPage.findNewsByText(testTitle);
            result.check(matches(isDisplayed()));
        } finally {
            controlPanelPage.deleteNews(testTitle);
        }
    }

    @Story("Проверка предотвращения XSS-инъекций в поле заголовка ")
    @Description("Проверить, что поле заголовка защищено от XSS-инъекций")
    @DisplayName("Создание новости с XSS-инъекцией в заголовке")
    @Test
    public void createNewsWithXSSInjectionInTitleTest() {
        try {
            goToCreateNewsPage();
            creatingEditingPage.waitAndCheckPage();
            creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
            creatingEditingPage.editTitle(XSS_INJECTION);
            creatingEditingPage.editDate();
            creatingEditingPage.editTime();
            creatingEditingPage.editDescription(OVER_100_CHARACTERS_STRING);
            creatingEditingPage.tapToSaveButton();

            step("Проверяем что XSS-инъекция '" + XSS_INJECTION
                    + "' не срабатывает, новость сохраняется с текстом инъекции в названии");
            ViewInteraction result = controlPanelPage.findNewsByText(XSS_INJECTION);
            result.check(matches(isDisplayed()));
        } finally {//Удаляем эту новость чтобы не было конфликтов при дальнейших тестах.
            controlPanelPage.deleteNews(XSS_INJECTION);
        }
    }

    @Test
    @Story("Проверка предотвращения XSS-инъекций в поле текста ")
    @Description("Проверить, что поле описания защищено от XSS-инъекций")
    @DisplayName("Создание новости с XSS-инъекцией в описании")
    public void createNewsWithXSSInjectionInDescriptionTest() {
        String testTitle = TITLE + generateRandomFourDigitString();
        try {
            goToCreateNewsPage();
            creatingEditingPage.waitAndCheckPage();
            creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
            creatingEditingPage.editTitle(testTitle);
            creatingEditingPage.editDate();
            creatingEditingPage.editTime();
            creatingEditingPage.editDescription(XSS_INJECTION);
            creatingEditingPage.tapToSaveButton();

            step("Проверяем что SQL-инъекция '" + XSS_INJECTION
                    + "' не срабатывает, новость сохраняется с названием '"
                    + testTitle + "' и текстом инъекции в описании");
            ViewInteraction result = controlPanelPage.findNewsByText(testTitle);
            result.check(matches(isDisplayed()));
        } finally {//Удаляем эту новость чтобы не было конфликтов при дальнейших тестах.
            controlPanelPage.deleteNews(testTitle);
        }
    }
}