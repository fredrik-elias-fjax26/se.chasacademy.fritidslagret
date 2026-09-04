package se.chasacademy.fritidslagret.model;

public enum EquipmentTypes {
    BIKE("Bike"),
    SKISET("SkiSet"),
    TENT("Tent");

    private final String displayName;

    // Constructor
    EquipmentTypes(String displayName) {
        this.displayName = displayName;
    }

    //Getters
    public String getDisplayName() {
        return displayName;
    }
}
