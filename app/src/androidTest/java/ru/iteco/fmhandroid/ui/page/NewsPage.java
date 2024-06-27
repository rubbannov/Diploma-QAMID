package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static io.qameta.allure.kotlin.Allure.step;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.utils.WaitForViewAction;

public class NewsPage {

    ViewInteraction sortButton = onView(withId(R.id.sort_news_material_button));
    ViewInteraction filterButton = onView(withId(R.id.filter_news_material_button));
    ViewInteraction editButton = onView(withId(R.id.edit_news_material_button));
    //ViewInteraction expandButton = onView(withId(R.id.expand_material_button));
    ViewInteraction newsList = onView(withId(R.id.news_list_recycler_view));

    public void checkPageLoaded() {
        step("Проверка загрузки экрана");
        newsList.check(matches(isDisplayed()));
        sortButton.check(matches(isDisplayed()));
        filterButton.check(matches(isDisplayed()));
    }

    public void waitingPageToLoad() {
        step("Ожидание загрузки экрана");
        Espresso.onView(ViewMatchers.isRoot()).perform(WaitForViewAction.waitForView(
                ViewMatchers.withId(R.id.news_list_recycler_view), 10000));
    }

    public void editNews() {
        step("Тап по кнопке создания новости");
        editButton.check(matches(isDisplayed()));
        editButton.perform(click());
    }

    public void newsItemOpenClose(int numberOfItem) {
        step("Раскрытие элемента с новостью №" + numberOfItem);
        newsList.check(matches(isDisplayed()));
        newsList.perform(actionOnItemAtPosition(numberOfItem, click()));
    }

    public void checkNewsDescriptionItemExist() {
        step("Проверка что новость раскрылась и видно описание");
        onView(
                allOf(
                        withId(R.id.news_item_description_text_view),
                        withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)
                )
        ).check(matches(isDisplayed()));
    }

    public static void goToControlPage() {
        step("Переход на экран управления новостями");
        NavBarActions navBarActions = new NavBarActions();
        ControlPanelPage controlPanelPage = new ControlPanelPage();
        NewsPage newsPage = new NewsPage();

        navBarActions.clickBurger();
        navBarActions.goToNewsPage();
        newsPage.waitingPageToLoad();
        newsPage.editNews();
        controlPanelPage.waitingPageToLoad();
    }

    public static void goToCreateNewsPage() {
        step("Переход к экрану создания новости");
        goToControlPage();
        ControlPanelPage controlPanelPage = new ControlPanelPage();
        controlPanelPage.tapCreateButton();
    }

}
