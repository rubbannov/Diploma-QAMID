package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.page.ControlPanelPage.generateRandomFourDigitString;
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

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import junit.framework.AssertionFailedError;

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
import ru.iteco.fmhandroid.ui.page.ControlPanelPage;
import ru.iteco.fmhandroid.ui.page.CreatingEditingPage;
import ru.iteco.fmhandroid.ui.page.NewsPage;

@Epic("Создание/редактирование новостей")
@Feature("Проверка реализации функционала создания и редактирования новостей")
@LargeTest
@RunWith(AllureAndroidJUnit4.class)
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
    public void createNewsTest() {
        String testTitle = TITLE + generateRandomFourDigitString();
        try {
            Allure.step("Идем на экран создания новости", () -> {
                goToCreateNewsPage();
                creatingEditingPage.waitAndCheckPage();
            });
            Allure.step("Заполняем поле категории", () -> {
                creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
            });
            Allure.step("Заполняем поле заголовка", () -> {
                creatingEditingPage.editTitle(testTitle);
            });
            Allure.step("Заполняем поле даты", () -> {
                creatingEditingPage.editDate();
            });
            Allure.step("Заполняем поле времени", () -> {
                creatingEditingPage.editTime();
            });
            Allure.step("Заполняем поле описания", () -> {
                creatingEditingPage.editDescription(DESCRIPTION);
            });
            Allure.step("Тап по кнопке 'Сохранить'", () -> {
                creatingEditingPage.tapToSaveButton();
            });
            Allure.step("Проверяем что новость создана и отображается в списке", () -> {
                ViewInteraction result = controlPanelPage.findNewsByText(testTitle);
                result.check(matches(isDisplayed()));
            });
        } finally {
            controlPanelPage.deleteNews(testTitle);
        }
    }

    @Test
    @Story("Редактирование существующей новости")
    @Description("Проверка возможности редактирования существующей новости")
    public void editingNewsTest() {
        String testTitle = TITLE_UPD + generateRandomFourDigitString();
        goToCreateNewsPage();
        creatingEditingPage.creatingNewNews();
        try {
            Allure.step("Идем на экран панели управления новостями", NewsPage::goToControlPage);
            Allure.step("Тап по кнопке редактирования созданной новости", () -> {
                controlPanelPage.tapEditingNewsButton(TITLE);
            });
            Allure.step("Ожидание и проверка загрузки экрана редактирования", () -> {
                creatingEditingPage.waitAndCheckPage();
            });
            Allure.step("Заполняем поле категории", () -> {
                creatingEditingPage.editCategory(CAEGORY_BIRTHDAY);
            });
            Allure.step("Заполняем поле заголовка", () -> {
                creatingEditingPage.editTitle(testTitle);
            });
            Allure.step("Заполняем поле даты", () -> {
                creatingEditingPage.editDate();
            });
            Allure.step("Заполняем поле времени", () -> {
                creatingEditingPage.editTime();
            });
            Allure.step("Заполняем поле описания", () -> {
                creatingEditingPage.editDescription(DESCRIPTION_UPD);
            });
            Allure.step("Тап по кнопке 'Сохранить'", () -> {
                creatingEditingPage.tapToSaveButton();
            });
            Allure.step("Проверяем что новость с обновленными данными отображается в списке", () -> {
                ViewInteraction result = controlPanelPage.findNewsByText(testTitle);
                result.check(matches(isDisplayed()));
            });
        } finally {
            controlPanelPage.deleteNews(testTitle);
        }

    }
    @Story("Удаление новости")
    @Description("Проверка возможности удаления новости")
    @Test
    public void deleteNewsTest() {

        Allure.step("Переходим на экран создания новости и создаем новость", () -> {
            goToCreateNewsPage();
            creatingEditingPage.creatingNewNews();
        });
        Allure.step("Возвращаемся на панель управления новостями и удаляем созданную новость", () -> {
            controlPanelPage.deleteNews(TITLE);
        });
        Allure.step("Проверяем что новость отсутствует в списке", () -> {
            ViewInteraction result = controlPanelPage.findNewsByText(TITLE);
            waitFor(2000);
            result.check(doesNotExist());
        });
    }
    @Story("Проверка обработки ошибок при некорректном вводе данных")
    @Description("Проверяем, что приложение корректно обрабатывает некорректные данные при редактировании новости")
    @Test
    public void wrongDataCreateNewsTest() {
        try {
            Allure.step("Переходим на страницу создания новости", () -> {
                goToCreateNewsPage();
                creatingEditingPage.waitAndCheckPage();
            });
            Allure.step("Заполняем поле категории", () -> {
                creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
            });
            Allure.step("Заполняем поле заголовка строкой с более 100 символов", () -> {
                creatingEditingPage.editTitle(OVER_100_CHARACTERS_STRING);
            });
            Allure.step("Заполняем поле даты", () -> {
                creatingEditingPage.editDate();
            });
            Allure.step("Заполняем поле времени", () -> {
                creatingEditingPage.editTime();
            });
            Allure.step("Заполняем поле описания", () -> {
                creatingEditingPage.editDescription(DESCRIPTION);
            });
            Allure.step("Тап по кнопке 'Сохранить'", () -> {
                creatingEditingPage.tapToSaveButton();
            });

            Allure.step("Проверяем что появилось сообщение об ошибке 'Название слишком длинное'", () -> {
                onView(withText(TOAST_MSG_LONG_TITLE))
                        .inRoot(withDecorView(Matchers.not(decorView)))
                        .check(matches(isDisplayed()));
            });
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
                throw new AssertionError("Toast with message 'Title is too long' does not exist");
            }
        }
    }
    @Story("Сохранение пустого названия новости")
    @Description("Проверка обработки ошибок при попытке сохранить новость без названия")
    @Test
    public void saveEmptyTitleTest() {
        Allure.step("Переходим на страницу создания новости", () -> {
            goToCreateNewsPage();
            creatingEditingPage.waitAndCheckPage();
        });
        Allure.step("Заполняем поле категории", () -> {
            creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
        });
        Allure.step("Оставляем поле заголовка пустым", () -> {
            creatingEditingPage.editTitle(EMPTY_STRING);
        });
        Allure.step("Заполняем поле даты", () -> {
            creatingEditingPage.editDate();
        });
        Allure.step("Заполняем поле времени", () -> {
            creatingEditingPage.editTime();
        });
        Allure.step("Заполняем поле описания", () -> {
            creatingEditingPage.editDescription(DESCRIPTION);
        });
        Allure.step("Тап по кнопке 'Сохранить'", () -> {
            creatingEditingPage.tapToSaveButton();
        });
        Allure.step("Проверяем что появилось сообщение об ошибке 'Заполните пустые поля'", () -> {
            onView(withText(TOAST_MSG_EMPTY_FIELDS))
                    .inRoot(withDecorView(Matchers.not(decorView)))
                    .check(matches(isDisplayed()));
        });

    }
    @Story("Ввод слишком длинного описания новости")
    @Description("Проверка обработки ошибок при вводе описания, превышающего допустимую длину")
    @Test
    public void createNewsWithLongDescription() {
        String testTitle = TITLE + generateRandomFourDigitString();
        try {
            Allure.step("Переходим на страницу создания новости", () -> {
                goToCreateNewsPage();
                creatingEditingPage.waitAndCheckPage();
            });
            Allure.step("Заполняем поле категории", () -> {
                creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
            });
            Allure.step("Заполняем поле заголовка", () -> {
                creatingEditingPage.editTitle(testTitle);
            });
            Allure.step("Заполняем поле даты", () -> {
                creatingEditingPage.editDate();
            });
            Allure.step("Заполняем поле времени", () -> {
                creatingEditingPage.editTime();
            });
            Allure.step("Заполняем поле описания строкой с более 500 символов", () -> {
                creatingEditingPage.editDescription(OVER_500_CHARACTERS_STRING);
            });
            Allure.step("Тап по кнопке 'Сохранить'", () -> {
                creatingEditingPage.tapToSaveButton();
            });
            Allure.step("Проверяем что появилось сообщение об ошибке 'Описание слишком длинное'", () -> {
                onView(withText(TOAST_MSG_LONG_DESCRIPTION))
                        .inRoot(withDecorView(Matchers.not(decorView)))
                        .check(matches(isDisplayed()));
            });
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
                throw new AssertionError("Toast with message 'Description is too long' does not exist");
            }
        }
    }
    @Story("Ввод спецсимволов в названии новости")
    @Description("Проверка обработки ошибок при вводе спецсимволов в названии")
    @Test
    public void createNewsWithSpecialCharactersInTitleTest() {
        try {
            Allure.step("Переходим на страницу создания новости", () -> {
                goToCreateNewsPage();
                creatingEditingPage.waitAndCheckPage();
            });
            Allure.step("Заполняем поле категории", () -> {
                creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
            });
            Allure.step("Заполняем поле заголовка строкой с спецсимволами", () -> {
                creatingEditingPage.editTitle(SPECIAL_CHARACTERS_STRING);
            });
            Allure.step("Заполняем поле даты", () -> {
                creatingEditingPage.editDate();
            });
            Allure.step("Заполняем поле времени", () -> {
                creatingEditingPage.editTime();
            });
            Allure.step("Заполняем поле описания строкой с более 500 символов", () -> {
                creatingEditingPage.editDescription(OVER_500_CHARACTERS_STRING);
            });
            Allure.step("Тап по кнопке 'Сохранить'", () -> {
                creatingEditingPage.tapToSaveButton();
            });
            Allure.step("Проверяем что появилось сообщение об ошибке 'Заголовок не должен состоять из спецсимволов'", () -> {
                onView(withText(TOAST_MSG_SPECIAL_CHARACTERS))
                        .inRoot(withDecorView(Matchers.not(decorView)))
                        .check(matches(isDisplayed()));
            });
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
                throw new AssertionError("Toast with message 'The title must not contain special characters' does not exist");
            }
        }
    }
    @Story("Проверка предотвращения SQL-инъекций в поле заголовка новости")
    @Description("Проверить, что поле заголовка защищено от SQL-инъекций ")
    @Test
    public void createNewsWithSQLInjectionInTitleTest() {
        try {
            Allure.step("Переходим на страницу создания новости", () -> {
                goToCreateNewsPage();
                creatingEditingPage.waitAndCheckPage();
            });
            Allure.step("Заполняем поле категории", () -> {
                creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
            });
            Allure.step("Заполняем поле заголовка строкой с SQL-инъекцией", () -> {
                creatingEditingPage.editTitle(SQL_INJECTION);
            });
            Allure.step("Заполняем поле даты", () -> {
                creatingEditingPage.editDate();
            });
            Allure.step("Заполняем поле времени", () -> {
                creatingEditingPage.editTime();
            });
            Allure.step("Заполняем поле описания", () -> {
                creatingEditingPage.editDescription(OVER_100_CHARACTERS_STRING);
            });
            Allure.step("Тап по кнопке 'Сохранить'", () -> {
                creatingEditingPage.tapToSaveButton();
            });
            Allure.step("Проверяем что SQL-инъекция не срабатывает, новость сохраняется с текстом инъекции", () -> {
                ViewInteraction result = controlPanelPage.findNewsByText(SQL_INJECTION);
                result.check(matches(isDisplayed()));
            });
        } finally {
            controlPanelPage.deleteNews(SQL_INJECTION);
        }
    }
    @Story("Проверка предотвращения SQL-инъекций в поле текста ")
    @Description("Проверить, что поле описания защищено от SQL-инъекций")
    @Test
    public void createNewsWithSQLInjectionInDescriptionTest() {
        String testTitle = TITLE + generateRandomFourDigitString();
        try {
            Allure.step("Переходим на страницу создания новости", () -> {
                goToCreateNewsPage();
                creatingEditingPage.waitAndCheckPage();
            });
            Allure.step("Заполняем поле категории", () -> {
                creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
            });
            Allure.step("Заполняем поле заголовка", () -> {
                creatingEditingPage.editTitle(testTitle);
            });
            Allure.step("Заполняем поле даты", () -> {
                creatingEditingPage.editDate();
            });
            Allure.step("Заполняем поле времени", () -> {
                creatingEditingPage.editTime();
            });
            Allure.step("Заполняем поле описания строкой с SQL-инъекцией", () -> {
                creatingEditingPage.editDescription(SQL_INJECTION);
            });

            Allure.step("Тап по кнопке 'Сохранить'", () -> {
                creatingEditingPage.tapToSaveButton();
            });
            Allure.step("Проверяем что SQL-инъекция не срабатывает, новость сохраняется с текстом инъекции", () -> {
                ViewInteraction result = controlPanelPage.findNewsByText(testTitle);
                result.check(matches(isDisplayed()));
            });
        } finally {
            controlPanelPage.deleteNews(testTitle);
        }
    }
    @Story("Проверка предотвращения XSS-инъекций в поле заголовка ")
    @Description("Проверить, что поле заголовка защищено от XSS-инъекций")
    @Test
    public void createNewsWithXSSInjectionInTitleTest() {
        try {
            Allure.step("Переходим на страницу создания новости", () -> {
                goToCreateNewsPage();
                creatingEditingPage.waitAndCheckPage();
            });
            Allure.step("Заполняем поле категории", () -> {
                creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
            });
            Allure.step("Заполняем поле заголовка строкой с XSS-инъекцией", () -> {
                creatingEditingPage.editTitle(XSS_INJECTION);
            });
            Allure.step("Заполняем поле даты", () -> {
                creatingEditingPage.editDate();
            });
            Allure.step("Заполняем поле времени", () -> {
                creatingEditingPage.editTime();
            });
            Allure.step("Заполняем поле описания", () -> {
                creatingEditingPage.editDescription(OVER_100_CHARACTERS_STRING);
            });
            Allure.step("Тап по кнопке 'Сохранить'", () -> {
                creatingEditingPage.tapToSaveButton();
            });
            Allure.step("Проверяем что XSS-инъекция не срабатывает, новость сохраняется с текстом инъекции", () -> {
                ViewInteraction result = controlPanelPage.findNewsByText(XSS_INJECTION);
                result.check(matches(isDisplayed()));
            });
        } finally {//Удаляем эту новость чтобы не было конфликтов при дальнейших тестах.
            controlPanelPage.deleteNews(XSS_INJECTION);
        }
    }
    @Story("Проверка предотвращения XSS-инъекций в поле текста ")
    @Description("Проверить, что поле описания защищено от XSS-инъекций")
    @Test
    public void createNewsWithXSSInjectionInDescriptionTest() {
        String testTitle = TITLE + generateRandomFourDigitString();
        try {
            Allure.step("Переходим на страницу создания новости", () -> {
                goToCreateNewsPage();
                creatingEditingPage.waitAndCheckPage();
            });
            Allure.step("Заполняем поле категории", () -> {
                creatingEditingPage.editCategory(CATEGORY_ADVERTISEMENT);
            });
            Allure.step("Заполняем поле заголовка", () -> {
                creatingEditingPage.editTitle(testTitle);
            });
            Allure.step("Заполняем поле даты", () -> {
                creatingEditingPage.editDate();
            });
            Allure.step("Заполняем поле времени", () -> {
                creatingEditingPage.editTime();
            });
            Allure.step("Заполняем поле описания строкой с XSS-инъекцией", () -> {
                creatingEditingPage.editDescription(XSS_INJECTION);
            });

            Allure.step("Тап по кнопке 'Сохранить'", () -> {
                creatingEditingPage.tapToSaveButton();
            });
            Allure.step("Проверяем что XSS-инъекция не срабатывает, новость сохраняется с текстом инъекции", () -> {
                ViewInteraction result = controlPanelPage.findNewsByText(testTitle);
                result.check(matches(isDisplayed()));
            });
        } finally {//Удаляем эту новость чтобы не было конфликтов при дальнейших тестах.
            controlPanelPage.deleteNews(testTitle);
        }
    }
}
