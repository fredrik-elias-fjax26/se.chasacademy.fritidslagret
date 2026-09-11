package se.chasacademy.fritidslagret.model;

public class Member {
    private String name;
    private MemberStatus memberStatus;
    private int activeLoans;

    // Constructor
    public Member(String name, MemberStatus memberStatus) {
        this.name = name;
        this.memberStatus = memberStatus;
        this.activeLoans = 0;
    }

    //Getters
    public String getName() {
        return name;
    }

    public int getActiveLoans() {
        return activeLoans;
    }
}