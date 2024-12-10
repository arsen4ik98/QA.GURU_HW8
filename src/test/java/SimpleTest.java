import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SimpleTest {

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        Configuration.holdBrowserOpen = false;
    }

    @ValueSource(strings = {
            "Сапоги", "Куртки", "Подарки"
    })
    @ParameterizedTest(name = "Тест {0}")
    @DisplayName("Тест на поиск Я.Маркет")
    public void practiceFormTest(String search) {
        open("https://www.avito.ru/");
        $("[data-marker='search-form/suggest/input']").setValue(search).pressEnter();
        $("h1").shouldHave(text("«" + search + "»: объявления в Краснодаре"));
    }

    static Stream<Arguments> selenideDisplayCorrectButtons() {
        return Stream.of(
                Arguments.of(List.of("Путешествия","Скидки", "Мастера красоты", "Гараж" ,"Авито Молл"))
        );
    }

    @MethodSource
    @ParameterizedTest
    void selenideDisplayCorrectButtons(List<String> expectedButtons) {
        open("https://www.avito.ru/");
        $$(".styles-module-root-LIfwD .styles-module-root-fTGxT").filter(visible).shouldHave(texts(expectedButtons));
    }

    @CsvSource(value = {
            "Сапоги,«Сапоги»: объявления в Краснодаре",
            "Куртки, «Куртки»: объявления в Краснодаре",
            "Подарки, «Подарки»: объявления в Краснодаре"
    })
    @ParameterizedTest(name = "Тест {0}")
    @DisplayName("Тест на поиск Я.Маркет")
    public void practiceFormTestCsv(String search, String expectedText) {
        open("https://www.avito.ru/");
        $("[data-marker='search-form/suggest/input']").setValue(search).pressEnter();
        $("h1").shouldHave(text(expectedText));
    }

    @CsvFileSource(resources = "/test_data/searchFileAvito.csv")
    @ParameterizedTest(name = "Тест {0}")
    @DisplayName("Тест на поиск Я.Маркет")
    public void practiceFormTestCsvFile(String search, String expectedText) {
        open("https://www.avito.ru/");
        $("[data-marker='search-form/suggest/input']").setValue(search).pressEnter();
        $("h1").shouldHave(text(expectedText));
    }
}
