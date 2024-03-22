package org.example.controller;

import org.example.dto.Member;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController {
    // App에 있던 members 생성을 컨트롤러로 이전
    private Scanner sc;
    private List<Member> members;

    public MemberController(Scanner sc) {
        this.sc = sc;
        members = new ArrayList<>();
    }

    // App에 있던 member Join 기능을 모두 doJoin() 메서드 안으로 이동
    public void doJoin() {
        int id = members.size() + 1;
        String regDate = Util.getNowDateStr();
        String loginId = null;
        String loginPw = null;

        while(true) {
            System.out.printf("ID : ");
            loginId = sc.nextLine();

            if(isJoinableLoginId(loginId) == false) {
                System.out.printf("%s는 이미 사용중입니다. 아이디를 다시 입력해주세요.\n", loginId);
                continue;
            }

            break;
        }

        while(true) {
            System.out.printf("Password : ");
            loginPw = sc.nextLine();
            System.out.printf("Password 확인 : ");
            String loginPwConfirm = sc.nextLine();

            if(loginPw.equals(loginPwConfirm) == false) {
                System.out.println("비밀번호 확인 결과 다르게 입력하셨습니다. 다시 입력해주세요");
                continue;
            }

            break;
        }

        System.out.printf("이름 : ");
        String name = sc.nextLine();

        Member member = new Member(id, regDate, loginId, loginPw, name);
        members.add(member);

        System.out.printf("%d번 회원이 생성되었습니다. 환영합니다!\n", id);
    }

    private int getMemberIndexByLoginId(String loginId) {
        int i = 0;

        for(Member member : members) {
            if(member.loginId.equals(loginId)) {
                return i;
            }
            i++;
        }

        return -1;
    }

    private boolean isJoinableLoginId(String loginId) {
        int index = getMemberIndexByLoginId(loginId);

        if(index == -1) {
            return true;
        }

        return false;
    }
}
