package org.example.controller;

public abstract class Controller {
    // ArticleController, MemberController가 Controller를 상속받고,
    // 두 클래스에서 doAction() 메서드를 사용하기 위해서는 동작버튼이라도 Controller에 포함되어야 한다.
    public abstract void doAction(String cmd, String actionMethodName);
}
