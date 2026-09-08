package se.chasacademy.fritidslagret.model;

public abstract class Equipment {
    private static int idCounter = 1;
    private String name;
    private boolean available;
    private int maxLoanDays;
    private final String type;
    private final int id;

    // Constructor
    public Equipment(String name, boolean available, int maxLoanDays) {
        this.name = name;
        this.available = available;
        this.maxLoanDays = maxLoanDays;
        this.type = this.getClass().getSimpleName();
        this.id = idCounter;
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

    public static int getIdCounter() {
        return idCounter;
    }

    // Setters
    // No Setters Needed //
}
