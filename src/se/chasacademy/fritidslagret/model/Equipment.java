package se.chasacademy.fritidslagret.model;

public abstract class Equipment {
    private static int idCounter = 1;
    private final int id;
    private String name;
    private boolean available;
    private int maxLoanDays;
    private final String type;

    // Constructor
    public Equipment(String name, boolean available, int maxLoanDays, String type) {
        this.id = idCounter;
        this.name = name;
        this.available = available;
        this.maxLoanDays = maxLoanDays;
        this.type = type;
        idCounter++;
    }

    @Override
    public String toString() {
        return "Equipment{id=" + id + ",name=" + name + ",type=" + type + ",available=" + available + ",maxLoanDays=" + maxLoanDays + "}";
    }

    //Getters

    public String getName() {
        return name;
    }

    // Setters
    // No Setters Needed //
}
