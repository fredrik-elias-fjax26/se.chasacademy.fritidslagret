package se.chasacademy.fritidslagret.model;

import se.chasacademy.fritidslagret.db.MembersDB;

import java.util.Map;

public class MemberService {

    //Metoder
    public static void addMember(String name, MemberStatus memberStatus) {
        MembersDB.addMemberToDB(new Member(name, memberStatus));
    }

    public static Map<Integer, Member> getMembers() {
        return MembersDB.getMembers();
    }

    public static MemberStatus getMemberStatus(int id) {
        return MembersDB.getMember(id).getMemberStatus();
    }

    public static String getMemberStatusFriendlyName(int id) {
        return getMemberStatus(id).getFriendlyname();
    }

    public static Member getMember(int memberId) {
        return MembersDB.getMember(memberId);
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
