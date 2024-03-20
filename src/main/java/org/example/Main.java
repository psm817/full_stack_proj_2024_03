package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        int lastArticleId = 0;

        // 게시물을 담을 배열 생성
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

            if(cmd.equals("article write")) {
                int id = lastArticleId + 1;
                lastArticleId = id;
                System.out.printf("제목 : ");
                String title = sc.nextLine();
                System.out.printf("내용 : ");
                String body = sc.nextLine();

                // articles 배열에 담을 개개인의 게시물 객체 생성
                // 생성자를 통해 입력한 제목, 내용과 고유 번호를 저장
                Article article = new Article(id, title, body);

                // 배열에 게시물 넣기
                articles.add(article);

                System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
            }
            else if(cmd.equals("article list")) {
                // 만약 articles가 비어있다면 게시물이 없다고 출력
                if(articles.size() == 0) {
                    System.out.println("게시물이 없습니다.");
                    continue;
                }

                System.out.println("번호 | 제목");
                // for문은 가장 최근에 작성된 게시물이 위로 올라가게 보폭 설정
                for(int i = articles.size() - 1; i >= 0 ; i--) {
                    // article 변수에 차레대로 가져오기
                    Article article = articles.get(i);

                    System.out.printf("%d    | %s\n", article.id, article.title);
                }
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

    public Article(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }
}