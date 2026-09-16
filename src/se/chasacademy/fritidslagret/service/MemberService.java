package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.model.member.MemberStatus;

import java.util.HashMap;
import java.util.Map;

public class MemberService {
    private static final Map<Integer, Member> membersDatabase = new HashMap<>();
    private static int uniqueMemberId = 1000;


    //Methods
    public void addMember(String name, MemberStatus memberStatus) {
        Member newMember = new Member(uniqueMemberId, name, memberStatus);
        membersDatabase.put(uniqueMemberId, newMember);
        uniqueMemberId++;
    }

    public Map<Integer, Member> getMembers() {
        return Map.copyOf(membersDatabase);
    }

    public MemberStatus getMemberStatus(int id) {
        return membersDatabase.get(id).getMemberStatus();
    }

    public String getMemberStatusFriendlyName(int id) {
        return membersDatabase.get(id).getMemberStatus().getFriendlyName();
    }

    public Member getMember(int memberId) {
        return membersDatabase.get(memberId);
    }

    public int getActiveLoans(Member member) {
        return member.getActiveLoans();
    }

    // Setters
    public void addToActiveLoans(Member member) {
        member.addOneActiveLoan();
    }

    public void removeFromActiveLoans(Member member) {
        member.removeOneActiveLoan();
    }
}
