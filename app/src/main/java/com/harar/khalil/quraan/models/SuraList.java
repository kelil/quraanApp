package com.harar.khalil.quraan.models;

public class SuraList {

    private int id;
    private String juz;
    private String arabic;
    private String oromo;
    private int position;

    public SuraList(int id, String juz, String arabic, String oromo, int position) {
        this.id = id;
        this.juz = juz;
        this.arabic = arabic;
        this.oromo = oromo;
        this.position = position;

    }

    public int getId() {
        return id;
    }

    public String getJuz() {
        return juz;
    }

    public String getArabic() {
        return arabic;
    }

    public String getOromo() {
        return oromo;
    }

    public int getPosition() {
        return position;
    }
}