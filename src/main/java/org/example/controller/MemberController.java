package org.example.controller;

import org.example.container.Container;
import org.example.dto.Member;
import org.example.service.MemberService;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController extends Controller {
    private Scanner sc;
    private String cmd;
    private String actionMethodName;
    private MemberService memberService;

    public MemberController(Scanner sc) {
        this.sc = sc;
        memberService = Container.memberService;
    }

    public void makeTestData() {
        System.out.println("테스트를 위한 회원 데이터를 생성합니다.");

        memberService.join(new Member(memberService.getNewId(), Util.getNowDateStr(), "admin", "admin", "관리자"));
        memberService.join(new Member(memberService.getNewId(), Util.getNowDateStr(), "user1", "user1", "홍길동"));
        memberService.join(new Member(memberService.getNewId(), Util.getNowDateStr(), "user2", "user2", "홍길순"));
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
            case "logout":
                doLogout();
                break;
            default:
                System.out.println("존재하지 않는 명령어입니다.");
                break;
        }
    }

    // ===== 회원가입 =====
    public void doJoin() {
        int id = memberService.getNewId();
        String regDate = Util.getNowDateStr();
        String loginId = null;
        String loginPw = null;

        while (true) {
            System.out.printf("ID : ");
            loginId = sc.nextLine();

            if (isJoinableLoginId(loginId) == false) {
                System.out.printf("%s는 이미 사용중입니다. 아이디를 다시 입력해주세요.\n", loginId);
                continue;
            }

            break;
        }

        while (true) {
            System.out.printf("Password : ");
            loginPw = sc.nextLine();
            System.out.printf("Password 확인 : ");
            String loginPwConfirm = sc.nextLine();

            if (loginPw.equals(loginPwConfirm) == false) {
                System.out.println("비밀번호 확인 결과 다르게 입력하셨습니다. 다시 입력해주세요");
                continue;
            }

            break;
        }

        System.out.printf("이름 : ");
        String name = sc.nextLine();

        Member member = new Member(id, regDate, loginId, loginPw, name);

        memberService.join(member);

        System.out.printf("%d번째 회원이 생성되었습니다. 환영합니다!\n", id);
    }

    // ===== 로그인 =====
    public void doLogin() {
        System.out.printf("ID : ");
        String loginId = sc.nextLine();
        System.out.printf("Password : ");
        String loginPw = sc.nextLine();

        Member member = memberService.getMemberByLoginId(loginId);

        if (member == null) {
            System.out.println("입력하신 ID는 등록되지 않은 ID입니다. 다시 시도해주세요");
            return;
        }

        if (member.loginPw.equals(loginPw) == false) {
            System.out.println("비밀번호가 일치하지 않습니다. 다시 시도해주세요.");
            return;
        }

        loginedMember = member;
        System.out.printf("로그인 성공!! %s 님 환영합니다!\n", loginedMember.name);
    }

    // ===== 로그아웃 =====
    private void doLogout() {
        loginedMember = null;
        System.out.println("로그아웃 되었습니다.");
    }

    private boolean isJoinableLoginId(String loginId) {
        int index = memberService.getMemberIndexByLoginId(loginId);

        if (index == -1) {
            return true;
        }

        return false;
    }
}
