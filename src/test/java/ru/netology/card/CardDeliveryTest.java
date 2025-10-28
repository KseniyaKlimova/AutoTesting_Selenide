package ru.netology.card;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryTest {

    @BeforeEach
    void setUp() { Selenide.open("http://localhost:9999"); }

    private String generationDate(int days, String pattern){
        return LocalDate.now()
                .plusDays(days)
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldBeSuccessfullyCompleted() {
        $("[data-test-id='city'] input").setValue("Москва");
        String planningDate = generationDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Андрей Петров");
        $("[data-test-id='phone'] input").setValue("+79101234567");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='notification']")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Успешно!"))
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
}




