package com.example.ayurmitra.models;

import java.io.Serializable;

public class Expert implements Serializable {
    private String name;
    private String specialization;
    private String experience;
    private int imageResId;
    private String about;
    private String qualifications;
    private String consultationFee;
    private String availability;
    private String rating;
    private String website;
    private String phone;
    private String email;
    private String expertiseDosha; // "Vata", "Pitta", "Kapha", or "All"

    public Expert(String name, String specialization, String experience, int imageResId, 
                  String about, String qualifications, String consultationFee, 
                  String availability, String rating, String website, 
                  String phone, String email, String expertiseDosha) {
        this.name = name;
        this.specialization = specialization;
        this.experience = experience;
        this.imageResId = imageResId;
        this.about = about;
        this.qualifications = qualifications;
        this.consultationFee = consultationFee;
        this.availability = availability;
        this.rating = rating;
        this.website = website;
        this.phone = phone;
        this.email = email;
        this.expertiseDosha = expertiseDosha;
    }

    // Getters
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public String getExperience() { return experience; }
    public int getImageResId() { return imageResId; }
    public String getAbout() { return about; }
    public String getQualifications() { return qualifications; }
    public String getConsultationFee() { return consultationFee; }
    public String getAvailability() { return availability; }
    public String getRating() { return rating; }
    public String getWebsite() { return website; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getExpertiseDosha() { return expertiseDosha; }
}
