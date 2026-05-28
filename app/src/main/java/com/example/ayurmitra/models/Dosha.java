package com.example.ayurmitra.models;

import java.io.Serializable;

public class Dosha implements Serializable {
    private String name;
    private String tagline;
    private String description;
    private int imageResId;
    private String characteristics;
    private String symptoms;
    private String diet;
    private String lifestyle;

    public Dosha(String name, String tagline, String description, int imageResId, String characteristics, String symptoms, String diet, String lifestyle) {
        this.name = name;
        this.tagline = tagline;
        this.description = description;
        this.imageResId = imageResId;
        this.characteristics = characteristics;
        this.symptoms = symptoms;
        this.diet = diet;
        this.lifestyle = lifestyle;
    }

    public String getName() { return name; }
    public String getTagline() { return tagline; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }
    public String getCharacteristics() { return characteristics; }
    public String getSymptoms() { return symptoms; }
    public String getDiet() { return diet; }
    public String getLifestyle() { return lifestyle; }
}
