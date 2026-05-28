package com.example.ayurmitra.models;

import java.io.Serializable;

public class Herb implements Serializable {
    private String name;
    private int imageResId;
    private String description;
    private String tagline;
    private String benefits;
    private String usage;
    private String precautions;
    private String doshaType;

    public Herb(String name, int imageResId, String description, String tagline, String benefits, String usage, String precautions, String doshaType) {
        this.name = name;
        this.imageResId = imageResId;
        this.description = description;
        this.tagline = tagline;
        this.benefits = benefits;
        this.usage = usage;
        this.precautions = precautions;
        this.doshaType = doshaType;
    }

    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
    public String getDescription() { return description; }
    public String getTagline() { return tagline; }
    public String getBenefits() { return benefits; }
    public String getUsage() { return usage; }
    public String getPrecautions() { return precautions; }
    public String getDoshaType() { return doshaType; }
}