package se.chasacademy.fritidslagret.model;

public abstract class Equipment {
    private String name;
    private boolean available;
    private int maxLoanDays;
    private final String type;

    // Constructor
    public Equipment(String name, boolean available, int maxLoanDays) {
        this.name = name;
        this.available = available;
        this.maxLoanDays = maxLoanDays;
        this.type = this.getClass().getSimpleName();

    }

    @Override
    public String toString() {
        return "Equipment{" + "name=" + name + ",type=" + type + ",available=" + available + ",maxLoanDays=" + maxLoanDays + "}";
    }

    //Getters

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getMaxLoanDays() {
        return maxLoanDays;
    }
    // Setters
    // No Setters Needed //
}
