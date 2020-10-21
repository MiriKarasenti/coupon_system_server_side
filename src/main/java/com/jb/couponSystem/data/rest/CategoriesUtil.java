package com.jb.couponSystem.data.rest;

import java.util.ArrayList;
import java.util.List;

public class CategoriesUtil {
    public static class Category{
        int id;

        public Category(int id, String name) {
            this.id = id;
            this.name = name;
        }

        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    private static List<Category> categories = new ArrayList<>();
    static {
        categories.add(new Category(1, "Clothing"));
        categories.add(new Category(2, "Food"));
        categories.add(new Category(3, "Entertainment"));
        categories.add(new Category(4, "Kids"));
        categories.add(new Category(5, "Electronics"));
        categories.add(new Category(6, "Health & Beauty"));
        categories.add(new Category(7, "Home"));
    }

    public static List<Category> getCategories (){
        return categories;
    }
}
