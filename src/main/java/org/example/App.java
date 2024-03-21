package org.example;

import org.example.dto.Article;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// App은 기존 Main에 있던 내용을 가지고 있고, static을 사용하지 않기 때문에 static을 제거한다.
public class App {
    private List<Article> articles;

    App() {
        articles = new ArrayList<>();
    }

    public void start() {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        makeTestData();

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

            // ===== 게시물 작성 =====
            if(cmd.equals("article write")) {
                int id = articles.size() + 1;
                String regDate = Util.getNowDateStr();
                System.out.printf("제목 : ");
                String title = sc.nextLine();
                System.out.printf("내용 : ");
                String body = sc.nextLine();

                Article article = new Article(id, title, body, regDate);

                articles.add(article);

                System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
            }

            // ===== 전체 게시물 조회 =====
            else if(cmd.equals("article list")) {
                if(articles.size() == 0) {
                    System.out.println("게시물이 없습니다.");
                    continue;
                }

                System.out.println("번호 | 조회 | 제목");
                for(int i = articles.size() - 1; i >= 0 ; i--) {
                    Article article = articles.get(i);
                    System.out.printf("%4d | %4d | %s\n", article.id, article.hit, article.title);
                }
            }

            // ===== 게시물 상세 조회 =====
            else if(cmd.startsWith("article detail ")) {
                String[] cmdBits = cmd.split(" ");

                int id = Integer.parseInt(cmdBits[2]);

                Article foundArticle = null;

                for(int i = 0; i < articles.size(); i++) {
                    Article article = articles.get(i);

                    if(article.id == id) {
                        foundArticle = article;
                        break;
                    }
                }

                if(foundArticle == null) {
                    System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
                    continue;
                }

                foundArticle.increaseHit();

                System.out.printf("번호 : %d\n", foundArticle.id);
                System.out.printf("날짜 : %s\n", foundArticle.regDate);
                System.out.printf("제목 : %s\n", foundArticle.title);
                System.out.printf("내용 : %s\n", foundArticle.body);
                System.out.printf("조회 : %d\n", foundArticle.hit);
            }

            // ===== 게시물 수정 =====
            else if(cmd.startsWith("article modify ")) {
                String[] cmdBits = cmd.split(" ");

                int id = Integer.parseInt(cmdBits[2]);

                Article foundArticle = null;

                for(int i = 0; i < articles.size(); i++) {
                    Article article = articles.get(i);

                    if(article.id == id) {
                        foundArticle = article;
                        break;
                    }
                }

                if(foundArticle == null) {
                    System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
                    continue;
                }

                System.out.printf("제목 : ");
                String title = sc.nextLine();
                System.out.printf("내용 : ");
                String body = sc.nextLine();

                foundArticle.title = title;
                foundArticle.body = body;
                System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
            }

            // ===== 게시물 삭제 =====
            else if(cmd.startsWith("article delete ")) {
                String[] cmdBits = cmd.split(" ");

                int id = Integer.parseInt(cmdBits[2]);

                int foundIndex = -1;

                for(int i = 0; i < articles.size(); i++) {
                    Article article = articles.get(i);

                    if(article.id == id) {
                        foundIndex = i;
                        break;
                    }
                }

                if(foundIndex == -1) {
                    System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
                    continue;
                }

                articles.remove(foundIndex);
                System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
            }

            else {
                System.out.printf("%s(은)는 존재하지 않는 명령어 입니다.\n", cmd);
            }
        }

        sc.close();
        System.out.println("== 프로그램 끝 ==");
    }

    // 테스트 데이터 만들기
    private void makeTestData() {
        System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");

        articles.add(new Article(1, "test1", "Hello", Util.getNowDateStr(), 103));
        articles.add(new Article(2, "test2", "World", Util.getNowDateStr(), 15));
        articles.add(new Article(3, "test3", "!!!!!", Util.getNowDateStr(), 62));

    }
}
