package org.example.controller;

import org.example.dto.Member;

public abstract class Controller {
    // 로그인한 회원이 있는지 없는지 체크하기 위해 static으로 loginedMember와 isLogined()를 컨트롤러로 이동
    // static을 붙이는 이유는 로그인된 회원은 하나이기 때문에 공공재로서 붙여줬다.
    public static Member loginedMember;

    public static boolean isLogined() {
        return loginedMember != null;
    }

    public abstract void doAction(String cmd, String actionMethodName);
    public abstract void makeTestData();
}
