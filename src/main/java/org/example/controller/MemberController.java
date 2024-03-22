package org.example.controller;

import org.example.dto.Member;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController extends Controller {
    private Scanner sc;
    private List<Member> members;
    private String cmd;
    private String actionMethodName;
    private Member loginedMember;

    public MemberController(Scanner sc) {
        this.sc = sc;
        members = new ArrayList<>();
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;
        this.actionMethodName = actionMethodName;

        switch (actionMethodName) {
            case "join":
                doJoin();
                break;
            case "login":
                doLogin();
                break;
            default:
                System.out.println("존재하지 않는 명령어입니다.");
                break;
        }
    }

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

    public void doLogin() {
        System.out.printf("ID : ");
        String loginId = sc.nextLine();
        System.out.printf("Password : ");
        String loginPw = sc.nextLine();

        // 입력받은 아이디에 해당하는 회원이 존재하는지 확인
        Member member = getMemberByLoginId(loginId);

        // 회원이 존재하지 않으면 다시 시도하라는 출력문
        if(member == null) {
            System.out.println("입력하신 ID는 등록되지 않은 ID입니다. 다시 시도해주세요");
            return;
        }

        // 비밀번호 확인
        if(member.loginPw.equals(loginPw) == false) {
            System.out.println("비밀번호가 일치하지 않습니다. 다시 시도해주세요.");
            return;
        }

        // 회원이 있다면 전역변수인 loginedMember에 로그인한 member를 연결시켜주고,
        // 출력문으로 이름을 호출
        loginedMember = member;
        System.out.printf("로그인 성공!! %s 님 환영합니다!\n", loginedMember.name);
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

    private Member getMemberByLoginId(String loginId) {
        int index = getMemberIndexByLoginId(loginId);

        if(index == -1) {
            return null;
        }

        return members.get(index);
    }
}
