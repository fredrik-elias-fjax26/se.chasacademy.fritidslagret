package se.chasacademy.fritidslagret.model.equipment;

public abstract class Equipment {
    private static int uniqueEquipmentId = 0;
    private final int equipmentId;
    private String name;
    private boolean available;
    private int maxLoanDays;
    private final Class<? extends Equipment> type;

    // Constructor
    public Equipment(String name, boolean available, int maxLoanDays) {
        this.name = name;
        this.equipmentId = uniqueEquipmentId;
        this.available = available;
        this.maxLoanDays = maxLoanDays;
        this.type = this.getClass();
        uniqueEquipmentId++;
    }

    @Override
    public String toString() {
        return "Equipment{" + "name=" + name + ",type=" + type.getSimpleName() + ",available=" + available + ",maxLoanDays=" + maxLoanDays + "}";
    }

    //Getters

    public static int getUniqueEquipmentId() {
        return uniqueEquipmentId;
    }

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
