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

    public MemberStatus getMemberStatus() {
        return memberStatus;
    }

    // Setters

    public void setMemberStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }

    public void addOneActiveLoan() {
        this.activeLoans++;
    }

    public void removeOneActiveLoan() {
        this.activeLoans--;
    }
}