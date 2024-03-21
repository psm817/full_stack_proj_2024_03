package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // main 메서드 안에 있던 articles을 Main 클래스의 인스턴스 변수로 꺼내준다.
    // Main 메서드와 makeTestData에서 같은 articles을 사용하기 때문이다.
    // articles은 Main 클래스 밖에서 쓸 일이 없기 때문에 private을 써준다.
    private static List<Article> articles;

    static {
         articles = new ArrayList<>();
    }

    public static void main(String[] args) {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        // 테스트 데이터 생성 메서드 호출
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
                // 최초에 3개의 테스트 데이터를 만들기 때문에 lastArticleId라는 게 필요없고,
                // size()를 받아서 번호를 증가시킨다.
                int id = articles.size() + 1;

                // Util.java에서 만든 getNowDateStr()을 호출하여 regDate에 저장
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

                    // %4d는 최소 4칸을 확보하고 간다라는 뜻
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

                // 조회수 1씩 증가시키기
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
    public static void makeTestData() {
        System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");

        // hit를 추가한 생성자를 오버로딩하여 인자로 테스트 데이터를 넣어준다.
        // 각각 articles에 저장한다.
        articles.add(new Article(1, "test1", "Hello", Util.getNowDateStr(), 103));
        articles.add(new Article(2, "test2", "World", Util.getNowDateStr(), 15));
        articles.add(new Article(3, "test3", "!!!!!", Util.getNowDateStr(), 62));

    }
}

class Article {
    int id;
    String title;
    String body;
    String regDate;
    int hit;

    public Article(int id, String title, String body, String regDate, int hit) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.regDate = regDate;
        this.hit = hit;
    }

    public Article(int id, String title, String body, String regDate) {
        // 오버로딩된 Article을 호출하는 this 메서드를 사용해서 hit 값을 0을 준다.
        this(id, title, body, regDate, 0);
    }

    public void increaseHit() {
        hit++;
    }
}