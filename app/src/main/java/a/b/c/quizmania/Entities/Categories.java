package a.b.c.quizmania.Entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity that keeps track of available categories
 */
public class Categories {
    private static List<String> categories;

    public Categories() {
        List<String> catNumbers = new ArrayList<>();
        categories = new ArrayList<>();
        categories.add("Random");
        catNumbers.add("");
        categories.add("Film");
        catNumbers.add("11");
        categories.add("Science & nature");
        catNumbers.add("17");
        categories.add("General Knowledge");
        catNumbers.add("9");
        categories.add("Sports");
        catNumbers.add("21");
        categories.add("Mythology");
        catNumbers.add("20");
        categories.add("Politics");
        catNumbers.add("24");
        categories.add("Geography");
        catNumbers.add("22");
        categories.add("Video Games");
        catNumbers.add("15");
        categories.add("Television");
        catNumbers.add("14");
        categories.add("Music");
        catNumbers.add("12");
    }

    public  List<String> getCategories() {
        return categories;
    }
}
