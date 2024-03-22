package org.example;

import org.example.controller.ArticleController;
import org.example.controller.MemberController;
import org.example.dto.Article;
import org.example.util.Util;

import java.util.Scanner;

public class App {
    public void start() {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        MemberController memberContoller = new MemberController(sc);
        ArticleController articleController = new ArticleController(sc);

        articleController.makeTestData();

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

            // ===== 회원가입 =====
            if(cmd.equals("member join")) {
                memberContoller.doJoin();
            }

            // ===== 게시물 작성 =====
            else if(cmd.equals("article write")) {
                articleController.doWrite();
            }

            // ===== 전체 게시물 조회 =====
            else if(cmd.startsWith("article list")) {
                articleController.showList(cmd);
            }

            // ===== 게시물 상세 조회 =====
            else if(cmd.startsWith("article detail ")) {
                articleController.showDetail(cmd);
            }

            // ===== 게시물 수정 =====
            else if(cmd.startsWith("article modify ")) {
                articleController.doModify(cmd);
            }

            // ===== 게시물 삭제 =====
            else if(cmd.startsWith("article delete ")) {
                articleController.doDelete(cmd);
            }

            else {
                System.out.printf("%s(은)는 존재하지 않는 명령어 입니다.\n", cmd);
            }
        }

        sc.close();
        System.out.println("== 프로그램 끝 ==");
    }
}