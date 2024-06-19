package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

import android.widget.EditText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.utils.WaitForViewAction;

public class LoginPage {

    ViewInteraction loginInput = onView(
            allOf(
                    isDescendantOfA(withId(R.id.login_text_input_layout)),
                    isAssignableFrom(EditText.class)
            )
    );
    ViewInteraction passwordInput = onView(
            allOf(
                isDescendantOfA(withId(R.id.password_text_input_layout)),
                isAssignableFrom(EditText.class)
            )
    );
    ViewInteraction enterButton = onView(withId(R.id.enter_button));

    public void checkPageLoaded() {
        loginInput.check(matches(isDisplayed()));
        enterButton.check(matches(isDisplayed()));
    }
    public void inputLogin(String login) {
        loginInput.perform(replaceText(login), ViewActions.closeSoftKeyboard());
    }
    public void inputPassword(String password) {
        passwordInput.perform(replaceText(password), ViewActions.closeSoftKeyboard());
    }
    public void signIn() {
        enterButton.perform(click());
    }
    public void waitingPageToLoad() {
        try {
            Espresso.onView(ViewMatchers.isRoot()).perform(
                    WaitForViewAction.waitForView(
                            ViewMatchers.withId(R.id.login_text_input_layout), 10000));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Page is not load");
        }
    }

}
