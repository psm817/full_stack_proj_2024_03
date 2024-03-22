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

    public void makeTestData() {
        System.out.println("테스트를 위한 회원 데이터를 생성합니다.");

        members.add(new Member(1, Util.getNowDateStr(), "admin", "admin", "관리자"));
        members.add(new Member(2, Util.getNowDateStr(), "user1", "user1", "홍길동"));
        members.add(new Member(3, Util.getNowDateStr(), "user2", "user2", "홍길순"));
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

        System.out.printf("%d번째 회원이 생성되었습니다. 환영합니다!\n", id);
    }

    // ===== 로그인 =====
    public void doLogin() {
        if(isLogined()) {
            System.out.println("이미 로그인되어 있습니다.");
            return;
        }

        System.out.printf("ID : ");
        String loginId = sc.nextLine();
        System.out.printf("Password : ");
        String loginPw = sc.nextLine();

        Member member = getMemberByLoginId(loginId);

        if(member == null) {
            System.out.println("입력하신 ID는 등록되지 않은 ID입니다. 다시 시도해주세요");
            return;
        }

        // 비밀번호 확인
        if(member.loginPw.equals(loginPw) == false) {
            System.out.println("비밀번호가 일치하지 않습니다. 다시 시도해주세요.");
            return;
        }

        loginedMember = member;
        System.out.printf("로그인 성공!! %s 님 환영합니다!\n", loginedMember.name);
    }

    // 지금 프로그램에서 로그인이 되있는지 아닌지 확인
    private boolean isLogined() {
        return loginedMember != null;
    }

    // ===== 로그아웃 =====
    private void doLogout() {
        // 로그인할 때 loginedMember에 로그인한 회원 정보를 담아놨기 때문에,
        // 만약 isLogined() 메서드를 통해 loginedMember가 null이 아니라 어느 member가 들어있는지 확인하고,
        // loginedMember가 null이라면 로그인 상태가 아니기 때문에 로그아웃을 하지 못하도록 한다.
        if(isLogined() == false) {
            System.out.println("로그인 상태가 아닙니다.");
            return;
        }

        // 만약 loginedMemeber에 어떤 회원이 들어있다면 로그아웃하기 위해선 로그인 된 loginedMember를 다시 비워준다.
        loginedMember = null;
        System.out.println("로그아웃 되었습니다.");
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
