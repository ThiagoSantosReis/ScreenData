package br.com.thisantos.screendata.models.enums;

public enum Category {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    HORROR("Horror"),
    TERROR("Terror"),
    THRILLER("Thriller"),
    SCI_FI("Sci-fi"),
    MYSTERY("Mistery"),
    FANTASY("Fantasy"),
    ROMANCE("Romance"),
    ANIMATION("Animation"),
    MUSICAL("Musical");

    private String omdbCategory;

    Category(String omdbCategory){
        this.omdbCategory = omdbCategory;
    }

    public static Category fromString(String text){
        for(Category ctg : Category.values()){
            if(ctg.omdbCategory.equalsIgnoreCase(text)){
                return ctg;
            }
        }
        throw new IllegalArgumentException("Category Error: category not found");
    }

}
