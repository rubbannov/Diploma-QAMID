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
import static io.qameta.allure.kotlin.Allure.step;
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

    public void checkPageLoaded() {
        step("Проверка загрузки экрана контрольной панели");
        newsList.check(matches(isDisplayed()));
    }

    public void waitingPageToLoad() {
        step("Ожидание загрузки экрана контрольной панели");
        Espresso.onView(ViewMatchers.isRoot()).perform(WaitForViewAction.waitForView(
                ViewMatchers.withId(R.id.add_news_image_view), 10000));
    }

    public ViewInteraction findNewsByText(String text) {
        step("Выполняем поиск новости по тексту '" + text + "'");
        return onView(withText(text));
    }

    public void tapCreateButton() {
        step("Тап по кнопке создания новости");
        createNewButton.check(matches(isDisplayed()));
        createNewButton.perform(click());
    }

    public void tapEditingNewsButton(String titleOfNews) {
        step("Тап по кнопке редактирования новости '" + titleOfNews + "'");
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

    public void deleteNews(String titleOfNews) {
        step("Тап ко кнопке удаления новости '" + titleOfNews + "'");
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

    public void tapOkSystemButton() {
        step("Тап ОК в системном окне");
        okSystemButton.check(matches(isDisplayed()));
        okSystemButton.perform(click());
    }

    public static String generateRandomFourDigitString() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000); // Генерируем случайное число от 0 до 9999
        return String.format("%04d", randomNumber); // Форматируем число как строку с 4 цифрами
    }

}
