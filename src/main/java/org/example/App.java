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