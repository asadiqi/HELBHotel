package com.example.helbhotel.model;

public class Reservation {
    // Attributes
    private String fullName;
    private int numberOfPeople;
    private boolean isSmoker;
    private StayPurpose stayPurpose;
    private int numberOfChildren;

    // Enumeration for the purpose of stay
    public enum StayPurpose {
        TOURISM,
        BUSINESS,
        OTHER
    }

    // Constructor
    public Reservation(String fullName, int numberOfPeople, boolean isSmoker,
                       StayPurpose stayPurpose, int numberOfChildren) {
        // Validation of constraints
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }

        if (numberOfPeople < 1 || numberOfPeople > 4) {
            throw new IllegalArgumentException("Number of people must be between 1 and 4");
        }

        if (numberOfChildren < 0) {
            throw new IllegalArgumentException("Number of children cannot be negative");
        }

        if (numberOfChildren >= numberOfPeople) {
            throw new IllegalArgumentException("Number of children cannot be greater than or equal to the total number of people");
        }

        this.fullName = fullName;
        this.numberOfPeople = numberOfPeople;
        this.isSmoker = isSmoker;
        this.stayPurpose = stayPurpose;
        this.numberOfChildren = numberOfChildren;
    }

    // Getters
    public String getFullName() {
        return fullName;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public boolean isSmoker() {
        return isSmoker;
    }

    public StayPurpose getStayPurpose() {
        return stayPurpose;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    // Setters
    public void setFullName(String fullName) {
        if (fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        this.fullName = fullName;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        if (numberOfPeople < 1 || numberOfPeople > 4) {
            throw new IllegalArgumentException("Number of people must be between 1 and 4");
        }
        if (this.numberOfChildren >= numberOfPeople) {
            throw new IllegalArgumentException("Number of children cannot be greater than or equal to the total number of people");
        }
        this.numberOfPeople = numberOfPeople;
    }

    public void setIsSmoker(boolean isSmoker) {
        this.isSmoker = isSmoker;
    }

    public void setStayPurpose(StayPurpose stayPurpose) {
        this.stayPurpose = stayPurpose;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        if (numberOfChildren < 0) {
            throw new IllegalArgumentException("Number of children cannot be negative");
        }
        if (numberOfChildren >= this.numberOfPeople) {
            throw new IllegalArgumentException("Number of children cannot be greater than or equal to the total number of people");
        }
        this.numberOfChildren = numberOfChildren;
    }


}