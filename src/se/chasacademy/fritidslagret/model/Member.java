package se.chasacademy.fritidslagret.model;

public class Member {
    private String memberName;
    private int memberId;
    private MemberStatus memberStatus;
    private int activeLoans;

    // Constructor
    public Member(int memberId, String memberName, MemberStatus memberStatus) {
        this.memberName = memberName;
        this.memberId = memberId;
        this.memberStatus = memberStatus;
        this.activeLoans = 0;
    }

    //Getters
    public String getName() {
        return memberName;
    }

    public int getActiveLoans() {
        return activeLoans;
    }

    public MemberStatus getMemberStatus() {
        return memberStatus;
    }

    public int getMemberId() {
        return memberId;
    }

    // Setters

    public void setMemberStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }

    // Methods
    public void addOneActiveLoan() {
        this.activeLoans++;
    }

    public void removeOneActiveLoan() {
        this.activeLoans--;
    }
}