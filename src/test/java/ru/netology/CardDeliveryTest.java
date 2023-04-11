package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeAll
    static void setUpAll() {
        Configuration.headless = true; //безголовый режим
    }

    @BeforeEach
    public void openPage() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldAcceptValidValues() {
        $("[data-test-id=city] input").setValue("Новосибирск");
        $("[data-test-id=date] input").doubleClick().sendKeys("DELETE");
        String dateDelivery = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateDelivery);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79139130000");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $(withText("Встреча успешно забронирована на")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldReturnFailAfterIncorrectCity() {
        $("[data-test-id=city] input").setValue("Барабинск");
        $("[data-test-id=date] input").doubleClick().sendKeys("DELETE");
        String dateDelivery = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
        $("[data-test-id=date] input").doubleClick().sendKeys("DELETE");
        String dateDelivery = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
        $("[data-test-id=date] input").doubleClick().sendKeys("DELETE");
        String dateDelivery = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
        $("[data-test-id=date] input").doubleClick().sendKeys("DELETE");
        String dateDelivery = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
        $("[data-test-id=date] input").doubleClick().sendKeys("DELETE");
        String dateDelivery = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
        $("[data-test-id=date] input").doubleClick().sendKeys("DELETE");
        String dateDelivery = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
        $("[data-test-id=date] input").doubleClick().sendKeys("DELETE");
        String dateDelivery = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
        $("[data-test-id=date] input").doubleClick().sendKeys("DELETE");
        String dateDelivery = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
        $("[data-test-id=date] input").doubleClick().sendKeys("DELETE");
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
        $("[data-test-id=date] input").doubleClick().sendKeys("DELETE");
        String dateDelivery = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateDelivery);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79139130000");
        //$("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=agreement] .checkbox__text").shouldBe(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
