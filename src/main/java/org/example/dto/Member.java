package org.example.dto;

// 데이터와 관련된 모든 관리는 dto 패키지에서 한다.
public class Member extends Dto {
    public String loginId;
    public String loginPw;
    public String name;

    public Member(int id, String regDate, String loginId, String loginPw, String name) {
        this.id = id;
        this.regDate = regDate;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
    }
}
