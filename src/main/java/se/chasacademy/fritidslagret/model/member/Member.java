package se.chasacademy.fritidslagret.model.member;

public class Member {
    private String memberName;
    private final int memberId;
    private MemberStatus memberStatus;
    private int activeLoans;
    private static int uniqueMemberId = 1000;


    // Constructor
    public Member(String memberName, MemberStatus memberStatus) {
        this.memberName = memberName;
        this.memberId = uniqueMemberId;
        this.memberStatus = memberStatus;
        this.activeLoans = 0;
        uniqueMemberId++;
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

    @Override
    public String toString() {
        return String.format("Medlems-Id: %d, Namn: %s, Status: %s",  memberId, memberName, memberStatus.getFriendlyName());
    }
}