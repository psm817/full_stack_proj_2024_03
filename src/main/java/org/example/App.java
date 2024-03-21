package org.example;

import org.example.dto.Article;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
            else if(cmd.startsWith("article list")) {
                if(articles.size() == 0) {
                    System.out.println("게시물이 없습니다.");
                    continue;
                }

                // searchKeyword에는 article list 뒤에 적은 검색어를 담는다.
                // split을 사용할 수도 있지만 substring을 통해 시작 인덱스번호 부터 그 뒤 문자열을 모두 가져온다.
                // 이 때 시작 인덱스번호는 article list의 길이가 13이기 때문에 13을 써도 되지만,
                // article list라는 문자열을 다른 방식으로 사용자가 커스텀할 수도 있기 때문에,
                // "article list"의 길이 자체를 시작 인덱스번호로 인자를 넘겨준다.
                // trim을 붙혀준 이유는 "article list"의 양옆 공백에 상관없이 가져오게 끔 하려고 해준 것이다.
                String searchKeyword = cmd.substring("article list".length()).trim();


                // articles에 연결된 리모콘을 forListArticles에게 넘겨준다는 뜻
                // forListArticles은 검색어가 포함된 게시물을 담는 새로운 articles 창고다.
                List<Article> forListArticles = articles;

                // 만약에 article list 다음에 검색어가 존재한다면 새로운 forListArticles에 arraylist를 생성한다.
                // 검색어가 없다면 그냥 articles가 가진 정보를 그대로 가지고 있는것이다.
                if(searchKeyword.length() > 0) {
                    forListArticles = new ArrayList<>();

                    // articles에 있는 게시물을 하나씩 차례대로 반복하면서 article 제목에 입력한 검색어가 포함된다면,
                    // 해당 검색어가 포함된 게시물을 forListArticles에 add한다.
                    // contains()는 인자에 있는 검색어가 포함되었냐를 확인하는 메서드다.
                    for(Article article : articles) {
                        if(article.title.contains(searchKeyword)) {
                            forListArticles.add(article);
                        }
                    }

                    // 만약 forListArticles에 add된게 없다면(검색어 조회가 안될 때) 게시물이 없다고 출력
                    if(forListArticles.size() == 0) {
                        System.out.printf("검색어 : %s(이)가 포함된 게시물이 존재하지 않습니다.\n", searchKeyword);
                        continue;
                    }
                }

                // 기존에는 articles을 보여줬지만, 검색어가 담긴 새로운 forListArticles을 보여줘야한다.
                // 처음에 forListArticles에 articles의 리모콘을 넘겨줬기 때문에 반복문의 보폭을 forListArticles로 변경한다.
                // 검색어가 없다면 articles을 그냥 옮겨놓은 forListArticles가 출력되는 거고,
                // 검색어가 있다면 제목에 검색어가 포함된 게시물인 forListArticles가 출력된다.
                System.out.println("번호 | 조회 | 제목");
                for(int i = forListArticles.size() - 1; i >= 0 ; i--) {
                    Article article = forListArticles.get(i);
                    System.out.printf("%4d | %4d | %s\n", article.id, article.hit, article.title);
                }
            }

            // ===== 게시물 상세 조회 =====
            else if(cmd.startsWith("article detail ")) {
                String[] cmdBits = cmd.split(" ");

                int id = Integer.parseInt(cmdBits[2]);

                Article foundArticle = getArticleById(id);

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

                Article foundArticle = getArticleById(id);

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

                int foundIndex = getArticleIndexById(id);

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

    private int getArticleIndexById(int id) {
        int i = 0;

        for(Article article : articles) {
            if(article.id == id) {
                return i;
            }
            i++;
        }

        return -1;
    }

    private Article getArticleById(int id) {
        int index = getArticleIndexById(id);

        if(index != -1) {
            return articles.get(index);
        }

        return null;
    }

    // 테스트 데이터 만들기
    private void makeTestData() {
        System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");

        articles.add(new Article(1, "test1", "Hello", Util.getNowDateStr(), 103));
        articles.add(new Article(2, "test2", "World", Util.getNowDateStr(), 15));
        articles.add(new Article(3, "test3", "!!!!!", Util.getNowDateStr(), 62));

    }
}
