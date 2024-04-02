package org.example;

import org.example.container.Container;
import org.example.controller.ArticleController;
import org.example.controller.Controller;
import org.example.controller.ExportController;
import org.example.controller.MemberController;
import org.example.db.DBConnection;

import java.util.Scanner;

public class App {
    public App() {
        DBConnection.DB_NAME = "sbs_proj";
        DBConnection.DB_USER = "sbsst";
        DBConnection.DB_PASSWORD = "sbs123414";
        DBConnection.DB_PORT = 3306;

        Container.getDBConnection().connect();

        // 현재 게시판을 1번 게시판(공지)으로 선택
        Container.getSession().setCurrentBoard(Container.articleService.getBoard(1));
    }

    public void start() {
        System.out.println("== 프로그램 시작 ==");
        System.out.println("= 명령어 모음 =");
        System.out.println("1. 회원가입 : member join");
        System.out.println("2. 로그인/로그아웃 : member login/logout");
        System.out.println("3. 현재 게시판 버전 확인 : article currBoard");
        System.out.println("4. 게시판 버전 변경 : article chgBoard");
        System.out.println("5. 게시물 전체 조회 : article list");
        System.out.println("6. 게시물 상세 보기 : article detail [게시물 번호]");
        System.out.println("7. 게시물 작성(로그인 후 이용가능) : article write");
        System.out.println("8. 게시물 수정/삭제 : article modify/delete [게시물 번호]");

        Scanner sc = new Scanner(System.in);

        MemberController memberController = new MemberController(sc);
        ArticleController articleController = new ArticleController(sc);
        ExportController exportController = new ExportController(sc);

        while(true) {
            System.out.printf("명령어) ");
            String cmd = sc.nextLine();
            cmd = cmd.trim();

            if(cmd.length() == 0) {
                continue;
            }

            if(cmd.equals("exit")) {
                break;
            }

            String[] cmdBits = cmd.split(" ");

            if(cmdBits.length == 1) {
                System.out.println("존재하지 않는 명령어 입니다.");
                continue;
            }

            String controllerName = cmdBits[0];
            String actionMethodName = cmdBits[1];

            Controller controller = null;

            if(controllerName.equals("article")) {
                controller = articleController;
            }
            else if(controllerName.equals("member")) {
                controller = memberController;
            }
            else if(controllerName.equals("export")) {
                controller = exportController;
            }
            else {
                System.out.println("존재하지 않는 명령어 입니다.");
                continue;
            }

            String actionName = controllerName + "/" + actionMethodName;

            switch(actionName) {
                case "article/write" :
                case "article/modify" :
                case "article/delete" :
                case "member/logout" :
                    if(Container.getSession().isLogined() == false) {
                        System.out.println("로그인 후 이용해주세요.");
                        continue;
                    }
                    break;
            }

            switch(actionName) {
                case "member/login" :
                case "member/join" :
                    if(Container.getSession().isLogined()) {
                        System.out.println("로그아웃 후 이용해주세요.");
                        continue;
                    }
                    break;
            }

            controller.doAction(cmd, actionMethodName);
        }

        sc.close();
        System.out.println("== 프로그램 끝 ==");
    }
}