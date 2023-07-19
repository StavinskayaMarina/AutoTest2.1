package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardWebTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void createChrome() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @Test
    void happyPathTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Петров Олег");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79809909898");

        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual.strip());
    }

    @Test
    void emptyNameTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79809909898");

        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actual.strip());
    }

    @Test
    void engNameTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Petrov Oleg");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79101234567");

        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.strip());
    }

    @Test
    void numberNameTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("1234 1234");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79101234567");

        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.strip());
    }

    @Test
    void specialCharactersNameTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("$$)) %%");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79101234567");

        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.strip());
    }

    @Test
    void empryPhoneTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Петров Олег");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("");

        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actual.strip());
    }

    @Test
    void engCharactersPhoneTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Петров Олег");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("Oleg");

        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.strip());
    }

    @Test
    void specialCharactersPhoneTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Петров Олег");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("%%%%%%% ))");

        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.strip());
    }

    @Test
    void longNumbersTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Петров Олег");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+7999990000099999");

        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.strip());
    }

    @Test
    void shortNumbersTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Петров Олег");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+799999");

        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.strip());
    }

    @Test
    void withoutPlusesNumbersTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Петров Олег");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("79009009898");

        driver.findElement(By.className("checkbox_size_m")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.strip());
    }

    @Test
    void noClickCeckboxTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Петров Олег");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79809909898");

        driver.findElement(By.className("button")).click();

        assertTrue(driver.findElement(By.className("input_invalid")).isEnabled());
    }
}
