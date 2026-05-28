package com.example.ayurmitra.models;

import java.io.Serializable;

public class Yoga implements Serializable {
    private String name;
    private int imageResId;
    private String description;
    private String benefits;
    private String steps;
    private String precautions;
    private String youtubeQuery;
    private String category; // Asana, Pranayama, Mudra, Philosophy
    private String suitableDosha; // Vata, Pitta, Kapha, All

    public Yoga(String name, int imageResId, String description, String benefits, String steps, String precautions, String youtubeQuery, String category, String suitableDosha) {
        this.name = name;
        this.imageResId = imageResId;
        this.description = description;
        this.benefits = benefits;
        this.steps = steps;
        this.precautions = precautions;
        this.youtubeQuery = youtubeQuery;
        this.category = category;
        this.suitableDosha = suitableDosha;
    }

    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
    public String getDescription() { return description; }
    public String getBenefits() { return benefits; }
    public String getSteps() { return steps; }
    public String getPrecautions() { return precautions; }
    public String getYoutubeQuery() { return youtubeQuery; }
    public String getCategory() { return category; }
    public String getSuitableDosha() { return suitableDosha; }
}
