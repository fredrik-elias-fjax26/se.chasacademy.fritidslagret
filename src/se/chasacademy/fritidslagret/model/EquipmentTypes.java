package se.chasacademy.fritidslagret.model;

public enum EquipmentTypes {
    BIKE("Bike", Bike::new),
    SKISET("SkiSet", SkiSet::new),
    TENT("Tent",  Tent::new),;

    private final String displayName;
    private final EquipmentCreator creator;

    // Constructor
    EquipmentTypes(String displayName, EquipmentCreator creator) {
        this.displayName = displayName;
        this.creator = creator;
    }

    //Methods
    Equipment create(String name, boolean available, int maxLoanDays) {
        return creator.create(name, available, maxLoanDays);
    }

    //Getters
    public String getDisplayName() {
        return displayName;
    }
}
