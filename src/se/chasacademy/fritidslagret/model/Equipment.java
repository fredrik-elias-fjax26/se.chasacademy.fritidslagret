package se.chasacademy.fritidslagret.model;

public abstract class Equipment {
    private final int equipmentId;
    private String name;
    private boolean available;
    private int maxLoanDays;
    private final Class<? extends Equipment> type;

    // Constructor
    public Equipment(int assignedEquipmentId, String name, boolean available, int maxLoanDays) {
        this.name = name;
        this.equipmentId = assignedEquipmentId;
        this.available = available;
        this.maxLoanDays = maxLoanDays;
        this.type = this.getClass();
    }

    @Override
    public String toString() {
        return "Equipment{" + "name=" + name + ",type=" + type.getSimpleName() + ",available=" + available + ",maxLoanDays=" + maxLoanDays + "}";
    }

    //Getters

    public String getEquipmentName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getMaxLoanDays() {
        return maxLoanDays;
    }

    public String getType() {
        return type.getSimpleName() ;
    }

    public int getEquipmentId() {
        return equipmentId;
    }
    // Setters
    public void setAvailable(boolean available) {
        this.available = available;
    }
}
