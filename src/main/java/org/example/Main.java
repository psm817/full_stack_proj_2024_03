package org.example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        int lastArticleId = 0;

        List<Article> articles = new ArrayList<>();

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
                int id = lastArticleId + 1;
                lastArticleId = id;
                System.out.printf("제목 : ");
                String title = sc.nextLine();
                System.out.printf("내용 : ");
                String body = sc.nextLine();

                // 게시물 작성했을 때 현재날짜 가져오기
                SimpleDateFormat regDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

                System.out.println("번호 | 제목");
                for(int i = articles.size() - 1; i >= 0 ; i--) {
                    Article article = articles.get(i);

                    System.out.printf("%d    | %s\n", article.id, article.title);
                }
            }

            // ===== 게시물 상세 조회 =====
            // startsWith는 시작된 문자열이 같은지 확인
            else if(cmd.startsWith("article detail ")) {
                // split(" ")을 사용해서 공백을 기준으로 cmd를 쪼갠다는 뜻이다.
                // 게시물 번호를 빼내기 위해서 사용한다.
                String[] cmdBits = cmd.split(" ");

                // 문자열로 되어있는 숫자를 int화 시키는 작업
                int id = Integer.parseInt(cmdBits[2]);

                // 게시물이 있는지 없는지 확인하는 게시물 생성
                Article foundArticle = null;

                for(int i = 0; i < articles.size(); i++) {
                    Article article = articles.get(i);

                    if(article.id == id) {
                        foundArticle = article;
                    }
                }

                // 만약 게시물이 없다면 존재하지 않는다는 출력문 출력한다.
                // continue를 사용하면 다시 위로 올라가서 명령어를 입력하게 된다.
                if(foundArticle == null) {
                    System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
                    continue;
                }

                System.out.printf("번호 : %d\n", foundArticle.id);
                System.out.printf("날짜 : %s\n", foundArticle.regDate);
                System.out.printf("제목 : %s\n", foundArticle.title);
                System.out.printf("내용 : %s\n", foundArticle.body);
            }

            else {
                System.out.printf("%s(은)는 존재하지 않는 명령어 입니다.\n", cmd);
            }
        }

        sc.close();
        System.out.println("== 프로그램 끝 ==");
    }
}

class Article {
    int id;
    String title;
    String body;
    String regDate;

    public Article(int id, String title, String body, SimpleDateFormat regDate) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.regDate = regDate.format(System.currentTimeMillis());
    }
}