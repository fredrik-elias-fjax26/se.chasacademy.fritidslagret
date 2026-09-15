package se.chasacademy.fritidslagret.model;

import java.util.HashMap;
import java.util.Map;

public class MemberService {
    private static final Map<Integer, Member> membersDatabase = new HashMap<>();
    private static int uniqueMemberId = 1000;


    //Metoder
    public static void addMember(String name, MemberStatus memberStatus) {
        Member newMember = new Member(name, memberStatus);
        membersDatabase.put(uniqueMemberId, newMember);
    }

    public static Map<Integer, Member> getMembers() {
        return Map.copyOf(membersDatabase);
    }

    public static MemberStatus getMemberStatus(int id) {
        return membersDatabase.get(id).getMemberStatus();
    }

    public static String getMemberStatusFriendlyName(int id) {
        return membersDatabase.get(id).getMemberStatus().getFriendlyName();
    }

    public static Member getMember(int memberId) {
        return membersDatabase.get(memberId);
    }

    public static void addToActiveLoans(Member member) {
        member.addOneActiveLoan();
    }

    public static void removeFromActiveLoans(Member member) {
        member.removeOneActiveLoan();
    }

    public static int getActiveLoans(Member member) {
        return member.getActiveLoans();
    }

}
