package ru.iteco.fmhandroid.ui.test;
public class TestData {
    public static String EMPTY_STRING = "";
    public static String INCORRECT_VALUE = "!@#";
    public static String INVALID_LOGIN = "login222";
    public static String INVALID_PASSWORD = "password222";
    public static String VALID_LOGIN = "login2";
    public static String VALID_PASSWORD = "password2";
    public static String SQL_INJECTION = "admin' OR '1'='1";
    public static String XSS_INJECTION = "<script>alert('XSS1')</script>";
    public static String CATEGORY_ADVERTISEMENT = "Объявление";
    public static String CAEGORY_BIRTHDAY = "День рождения";
    public static String TITLE = "Test create";
    public static String DESCRIPTION = "Description for news";
    public static String TITLE_UPD = "Test update";
    public static String DESCRIPTION_UPD = "Update description for news";
    public static String OVER_100_CHARACTERS_STRING = "Lorem ipsum dolor sit amet, " +
            "consectetur adipiscing elit. Phasellus imperdiet, " +
            "nulla et dictum interdum, nisi lorem egestas odio.";
    public static String OVER_500_CHARACTERS_STRING = "Lorem ipsum dolor sit amet, consectetur adipiscing " +
            "elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. " +
            "Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. " +
            "Praesent mauris. Fusce nec tellus sed augue semper porta. " +
            "Mauris massa. Vestibulum lacinia arcu eget nulla. " +
            "Class aptent taciti sociosqu ad litora torquent per conubia nostra, " +
            "per inceptos himenaeos. Curabitur sodales ligula in libero. " +
            "Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. " +
            "Aenean quam. In scelerisque sem at dolor. Maecenas mattis. " +
            "Sed convallis tristique sem.";

    public static String SPECIAL_CHARACTERS_STRING = "<>&%$#@!";
}
