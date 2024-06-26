package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.utils.TestHelper.waitFor;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;

import java.util.Random;

import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.utils.WaitForViewAction;

public class ControlPanelPage {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    ViewInteraction createNewButton = onView(withId(R.id.add_news_image_view));
    ViewInteraction newsList = onView(withId(R.id.news_list_recycler_view));
    ViewInteraction okSystemButton = onView(
            allOf(withId(android.R.id.button1), withText("OK")));

    @Step("Проверка загрузки экрана")
    public void checkPageLoaded() {
        newsList.check(matches(isDisplayed()));
    }

    @Step("Ожидание загрузки экрана")
    public void waitingPageToLoad() {
        Espresso.onView(ViewMatchers.isRoot()).perform(WaitForViewAction.waitForView(
                ViewMatchers.withId(R.id.add_news_image_view), 10000));
    }

    @Step("Выполняем поиск новости по тексту")
    public ViewInteraction findNewsByText(String text) {
        return onView(withText(text));
    }

    @Step("Тап по кнопке создания новости")
    public void tapCreateButton() {
        createNewButton.check(matches(isDisplayed()));
        createNewButton.perform(click());
    }

    @Step("Тап по кнопке редактирования новости")
    public void tapEditingNewsButton(String titleOfNews) {
        ViewInteraction editNewsButton = onView(
                allOf(
                        withId(R.id.edit_news_item_image_view),
                        hasSibling(withText(titleOfNews)),
                        withContentDescription("News editing button")
                )
        );
        editNewsButton.check(matches(isDisplayed()));
        editNewsButton.perform(click());
    }

    @Step("Тап ко кнопке удаления новости")
    public void deleteNews(String titleOfNews) {
        waitFor(1000);
        onView(withId(R.id.news_list_recycler_view)).check(matches(isDisplayed()));
        onView(allOf(
                withId(R.id.delete_news_item_image_view),
                withContentDescription("News delete button"),
                hasSibling(withText(titleOfNews))
        ))
                .perform(click());
        tapOkSystemButton();
    }

    @Step("Тап ОК в системном окне")
    public void tapOkSystemButton() {
        okSystemButton.check(matches(isDisplayed()));
        okSystemButton.perform(click());
    }

    @Step("Генерируем рандомное 4х значное число")
    public static String generateRandomFourDigitString() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000); // Генерируем случайное число от 0 до 9999
        return String.format("%04d", randomNumber); // Форматируем число как строку с 4 цифрами
    }

}
