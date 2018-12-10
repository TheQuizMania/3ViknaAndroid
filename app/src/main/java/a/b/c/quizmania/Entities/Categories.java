package a.b.c.quizmania.Entities;

import java.util.ArrayList;
import java.util.List;

public class Categories {
    private List<String> categories;

    public Categories() {
        categories = new ArrayList<>();
        categories.add("Random");
        categories.add("Film");
        categories.add("Science & nature");
        categories.add("General Knowledge");
        categories.add("Sports");
        categories.add("Mythology");
        categories.add("Politics");
        categories.add("Geography");
        categories.add("Video Games");
        categories.add("Television");
        categories.add("Music");

    };

    public List<String> getCategories() {
        return categories;
    }
}
