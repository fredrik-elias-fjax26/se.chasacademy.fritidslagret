package se.chasacademy.fritidslagret.model;

public class Member {
    // Bör vara final sen!!
    private int id;
    //
    private String name;
    private String memberStatus;

    // Constructor
    public Member(int id, String name, String memberStatus) {
        this.id = id;
        this.name = name;
        this.memberStatus = memberStatus;
    }
}