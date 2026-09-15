package se.chasacademy.fritidslagret.model;

public enum MemberStatus {
    ACTIVE("Aktiv"),
    INACTIVE("Inaktiv"),
    REVOKED("Återkallat");

    private String friendlyName;

    MemberStatus(String friendlyName) {
    this.friendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
