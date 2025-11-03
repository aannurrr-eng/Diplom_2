package data;

import com.github.javafaker.Faker;

public class TestData {

    public static String genEmail()
    {
        Faker user = new Faker();
        return String.format("%s@yandex.ru", user.regexify("[a-z]{4}"));
    }

    public static String genPassword()
    {
        Faker user = new Faker();
        return user.regexify("[0-9]{4}");
    }

    public static String genName()
    {
        Faker user = new Faker();
        return user.name().firstName();
    }

    public static String genHash()
    {
        Faker hash = new Faker();
        return hash.regexify("[0-9]{24}");
    }
}
