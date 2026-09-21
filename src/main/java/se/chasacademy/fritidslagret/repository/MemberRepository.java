package se.chasacademy.fritidslagret.repository;

import se.chasacademy.fritidslagret.model.member.Member;

import java.util.HashMap;
import java.util.Map;

public class MemberRepository {
    private final Map<Integer, Member> membersDatabase = new HashMap<>();
    private static MemberRepository instance = null;

    private MemberRepository() {}

    public static MemberRepository getInstance() {
        if (instance == null) {
            instance = new MemberRepository();
        }
        return instance;
    }

    public Map<Integer, Member> getDatabaseMembers() {
        return Map.copyOf(membersDatabase);
    }

    public Member addMember(Member member) {
        int memberId = member.getMemberId();
        membersDatabase.put(memberId, member);
        return member;
    }
}
