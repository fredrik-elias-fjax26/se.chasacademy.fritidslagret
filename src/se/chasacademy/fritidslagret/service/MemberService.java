package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.model.member.MemberStatus;
import se.chasacademy.fritidslagret.repository.MemberRepository;

import java.util.Map;

public class MemberService {
    private final MemberRepository memberRepository = MemberRepository.getInstance();


    //Metoder
    public Member addMember(String name, MemberStatus memberStatus) {
        Member newMember = new Member(name, memberStatus);
        memberRepository.addMember(newMember);
        return newMember;
    }

    public Map<Integer, Member> getMembers() {
        return memberRepository.getDatabaseMembers();
    }

    public MemberStatus getMemberStatus(int id) {
        return memberRepository.getDatabaseMembers().get(id).getMemberStatus();
    }

    public String getMemberStatusFriendlyName(int id) {
        return memberRepository.getDatabaseMembers().get(id).getMemberStatus().getFriendlyName();
    }

    public Member getMember(int memberId) {
        return memberRepository.getDatabaseMembers().get(memberId);
    }

    public void addToActiveLoans(Member member) {
        member.addOneActiveLoan();
    }

    public void removeFromActiveLoans(Member member) {
        member.removeOneActiveLoan();
    }

    public int getActiveLoans(Member member) {
        return member.getActiveLoans();
    }

}
