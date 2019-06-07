package com.harar.khalil.quraan.models;

public class BookMark {

    private int id;
    private String arabic;
    private String translation;
    private int sura;

    public BookMark(int id, String arabic, String translation, int sura) {
        this.id = id;
        this.arabic = arabic;
        this.translation = translation;
        this.sura = sura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArabic() {
        return arabic;
    }

    public void setArabic(String arabic) {
        this.arabic = arabic;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public int getSura() {
        return sura;
    }

    public void setSura(int sura) {
        this.sura = sura;
    }
}
