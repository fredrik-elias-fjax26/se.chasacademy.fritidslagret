package se.chasacademy.fritidslagret.model.member;

public enum MemberStatus {
    ACTIVE("Aktiv"),
    INACTIVE("Inaktiv"),
    REVOKED("Återkallat");

    private final String friendlyName;

    MemberStatus(String friendlyName) {
    this.friendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
