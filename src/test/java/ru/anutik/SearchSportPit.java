package ru.anutik;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.anutik.data.Products;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

@DisplayName("Парметризованные тесты для поиска товаров в мазаине спортпита.")
public class SearchSportPit extends TestBase{

    @BeforeEach
    void setUp() {
        open("https://expert-sport.by/");
    }

    @ValueSource(strings = {
            "Animal Flex", "BCAA"
    })
    @ParameterizedTest(name = "Для поискового запроса {0} должен отдавать не пустой список карточек")
    @Tag("Critical")
    void searchResultsSportPit(String searchQuery) {
        $("#mod_virtuemart_search").setValue(searchQuery).pressEnter();
        $$("[id = products-container] [class = spacer]")
                .shouldBe(sizeGreaterThan(0));
    }

    @CsvSource(value = {
            "Animal Flex, Animal Flex (381 гр)",
            "BCAA 221, BCAA Flavored™ от RULE 1 ( 221 гр )",
    })
    @ParameterizedTest(name = "Для поискового запроса {0} в первой карточке должен быть результат: {1}")
    @DisplayName("Проверка поиска первого поискового результата")
    void siteShouldSearch(String searchQuery, String expectedLink) {
        $("#mod_virtuemart_search").setValue(searchQuery).pressEnter();

        $(".item-title a").shouldHave(text(expectedLink));
    }

    @CsvFileSource(resources = "/test_data/siteShouldSearchFromFile.csv")
    @ParameterizedTest(name = "Для поискового запроса {0} в первой карточке должен быть результат: {1}")
    @DisplayName("Проверка поиска первого поискового результата, когда поисковый данные хранятся в файле")
    void siteShouldSearchFromFile(String searchQuery, String expectedLink) {
        $("#mod_virtuemart_search").setValue(searchQuery).pressEnter();

        $(".item-title a").shouldHave(text(expectedLink));
    }

    @EnumSource(Products.class)
    @ParameterizedTest(name = "Для поискового запроса {0} в первой карточке должен быть результат: {1}")
    @DisplayName("Проверка поискового результата с использованием enum")
    void siteShouldSearchWithEnum(Products products) {
        $("#mod_virtuemart_search").setValue(String.valueOf(products)).pressEnter();
        $(".item-title a").shouldHave(text(String.valueOf(products)));
    }


}
