## Описание процедуры запуска тестов и создание отчета

1. Склонировать репозиторий с проектом.
2. Открыть проект в Android Studio.
3. Собрать проект с помощью команды `./gradlew build`
4. Запустить эмулятор Pixel 7a API 29.
5. Запустить тесты с помощью команды `./gradlew connectedAndroidTest`
6. Для сбора и создания отчета напишите поочередно в терминале Android Studio команды:
- `adb pull /sdcard/googletest/test_outputfiles/build/allure-results .\app\build`
- `allure serve .\app\build\allure-results`
