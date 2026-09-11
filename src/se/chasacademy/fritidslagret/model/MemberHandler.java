package se.chasacademy.fritidslagret.model;

import se.chasacademy.fritidslagret.db.MembersDB;

import java.util.Map;

public class MemberHandler {

    //Metoder
    public static void addMember(String name, MemberStatus memberStatus) {
        MembersDB.addMemberToDB(new Member(name, memberStatus));
    }

    public static Map<Integer, Member> getMembers() {
        Map<Integer, Member> membersDB = MembersDB.getMembers();
        return membersDB;
    }
}
