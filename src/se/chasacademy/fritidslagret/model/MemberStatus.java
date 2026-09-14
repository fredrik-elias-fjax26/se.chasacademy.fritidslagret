package se.chasacademy.fritidslagret.model;

public enum MemberStatus {
    ACTIVE("Aktiv"),
    INACTIVE("Inaktiv"),
    REVOKED("Återkallat");

    private String friendlyname;

    MemberStatus(String friendlyname) {
    this.friendlyname = friendlyname;
    }

    public String getFriendlyname() {
        return friendlyname;
    }
}
