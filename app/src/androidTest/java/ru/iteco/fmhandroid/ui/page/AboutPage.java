package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import io.qameta.allure.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.utils.WaitForViewAction;

public class AboutPage {
    ViewInteraction privacyPolicyLink = onView(withId(R.id.about_privacy_policy_value_text_view));
    ViewInteraction termsOfUseLink = onView(withId(R.id.about_terms_of_use_value_text_view));
    ViewInteraction developerMark = onView(withId(R.id.about_company_info_label_text_view));

    @Step("Проверка загрузки страницы")
    public void checkPageLoaded() {
        privacyPolicyLink.check(matches(isDisplayed()));
        termsOfUseLink.check(matches(isDisplayed()));
        developerMark.check(matches(isDisplayed()));
    }
    @Step("Ожидание загрузки страницы")
    public void waitingPageToLoad() {
        Espresso.onView(
                ViewMatchers.isRoot()).perform(
                        WaitForViewAction.waitForView(
                                ViewMatchers.withId(
                                        R.id.about_company_info_label_text_view), 10000));
    }
}
