package com.harar.khalil.quraan.models;

public class SuraContent {
    private int id;

    private int rowId;
    private String arabic;
    private String oromo;
    private String amharic;
    private String english;

    public SuraContent(int id, int rowId, String arabic, String oromo, String amharic, String english) {
        this.id = id;
        this.rowId = rowId;
        this.arabic = arabic;
        this.oromo = oromo;
        this.amharic = amharic;
        this.english = english;
    }

    public int getId() {
        return id;
    }

    public String getArabic() {
        return arabic;
    }

    public String getOromo() {
        return oromo;
    }

    public String getAmharic() {
        return amharic;
    }

    public String getEnglish() {
        return english;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }
}
