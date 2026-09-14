package se.chasacademy.fritidslagret.db;

import se.chasacademy.fritidslagret.model.Member;

import java.util.HashMap;
import java.util.Map;

public class MembersDB {
    private static final Map<Integer, Member> membersDatabase = new HashMap<>();
    private static int uniqueMemberId = 1000;

    //Getters
    public static Member getMember(int id) {
        return membersDatabase.get(id);
    }

    public static Map<Integer, Member> getMembers() {
        Map<Integer, Member> readOnlyDB = Map.copyOf(membersDatabase);
        return readOnlyDB;
    }

    // Setters

    public static void addMemberToDB(Member member) {
        int assignedUniqueId = uniqueMemberId;
        membersDatabase.put(assignedUniqueId, member);
        uniqueMemberId++;
    }
}
