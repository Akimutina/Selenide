package ru.netology;

import com.codeborne.selenide.Condition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    public void openPage() {
        open("http://localhost:9999/");
    }

    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    @Test
    void shouldAcceptValidValues() {
        $("[data-test-id=city] input").setValue("Новосибирск");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        String dateDelivery = generateDate(3, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateDelivery);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79139130000");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + dateDelivery), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldReturnFailAfterIncorrectCity() {
        $("[data-test-id=city] input").setValue("Барабинск");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        String dateDelivery = generateDate(3, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateDelivery);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79139130000");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=city] .input__sub").shouldBe(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldReturnFailAfterSymbolCity() {
        $("[data-test-id=city] input").setValue("!@#$$%");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        String dateDelivery = generateDate(3, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateDelivery);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79139130000");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=city] .input__sub").shouldBe(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldReturnFailAfterEmptyCity() {
        $("[data-test-id=city] input").setValue("");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        String dateDelivery = generateDate(3, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateDelivery);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79139130000");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=city] .input__sub").shouldBe(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldReturnFailAfterLatinCity() {
        $("[data-test-id=city] input").setValue("Novgorod");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        String dateDelivery = generateDate(3, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateDelivery);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79139130000");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=city] .input__sub").shouldBe(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldReturnFailAfterEmptyName() {
        $("[data-test-id=city] input").setValue("Новосибирск");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        String dateDelivery = generateDate(3, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateDelivery);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79139130000");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=name] .input__sub").shouldBe(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldReturnFailAfterLatinName() {
        $("[data-test-id=city] input").setValue("Новосибирск");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        String dateDelivery = generateDate(3, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateDelivery);
        $("[data-test-id=name] input").setValue("Ivanov Ivan");
        $("[data-test-id=phone] input").setValue("+79139130000");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=name] .input__sub").shouldBe(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldReturnFailAfterSymbolName() {
        $("[data-test-id=city] input").setValue("Новосибирск");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        String dateDelivery = generateDate(3, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateDelivery);
        $("[data-test-id=name] input").setValue("!@#$%");
        $("[data-test-id=phone] input").setValue("+79139130000");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=name] .input__sub").shouldBe(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldReturnFailAfterIncorrectDate() {
        $("[data-test-id=city] input").setValue("Новосибирск");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        String dateDelivery = generateDate(2, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateDelivery);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79139130000");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=date] .input__sub").shouldBe(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldReturnFailAfterEmptyDate() {
        $("[data-test-id=city] input").setValue("Новосибирск");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue("");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79139130000");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=date] .input__sub").shouldBe(text("Неверно введена дата"));
    }

    @Test
    void shouldReturnFailAfterEmptyCheckbox() {
        $("[data-test-id=city] input").setValue("Новосибирск");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        String dateDelivery = generateDate(3, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateDelivery);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79139130000");
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=agreement] .checkbox__text").shouldBe(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
