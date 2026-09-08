package se.chasacademy.fritidslagret.model;

public enum EquipmentTypes {
    BIKE("Bike", Bike::new),
    SKI_SET("SkiSet", SkiSet::new),
    TENT("Tent",  Tent::new),;

    private final String displayName;
    private final EquipmentCreator creator;

    // Constructor
    EquipmentTypes(String displayName, EquipmentCreator creator) {
        this.displayName = displayName;
        this.creator = creator;
    }

    //Getters
    public String getDisplayName() {
        return displayName;
    }
}
