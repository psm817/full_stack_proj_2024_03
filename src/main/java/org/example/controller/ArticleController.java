package org.example.controller;

import org.example.dto.Article;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller {
    private Scanner sc;
    private List<Article> articles;
    private String cmd;
    private String actionMethodName;

    public ArticleController(Scanner sc) {
        this.sc = sc;
        articles = new ArrayList<>();
    }

    public void doAction(String cmd, String actionMethodName) {
        // cmd를 매개변수로 받으면 전역변수에 cmd 명령어를 저장하고
        // doAction() 메서드가 실행하면 cmd에는 사용자가 입력한 명령어 cmd에 저장된다.
        // 그렇게 되면 cmd를 doModify()나 doDelete() 메서드를 호출할 때 이미 전역변수 cmd에 해당 명령어가 있으니
        // 인자와 메서드 매개변수에 아무것도 입력하지 않아도 된다.
        this.cmd = cmd;
        this.actionMethodName = actionMethodName;

        // App 클래스에서 인자로 넘겨받은 actionMethodName을 switch문을 사용하여 각 기능 메서드를 호출한다.
        switch (actionMethodName) {
            case "write":
                doWrite();
                break;
            case "list":
                showList();
                break;
            case "detail":
                showDetail();
                break;
            case "modify":
                doModify();
                break;
            case "delete":
                doDelete();
                break;
        }
    }

    // 테스트 데이터 만들기
    public void makeTestData() {
        System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");

        articles.add(new Article(1, "test1", "Hello", Util.getNowDateStr(), 103));
        articles.add(new Article(2, "test2", "World", Util.getNowDateStr(), 15));
        articles.add(new Article(3, "test3", "!!!!!", Util.getNowDateStr(), 62));
    }

    public void doWrite() {
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

    public void showList() {
        if(articles.size() == 0) {
            System.out.println("게시물이 없습니다.");
            // continue 대신 return을 한다.
            return;
        }

        String searchKeyword = cmd.substring("article list".length()).trim();

        List<Article> forListArticles = articles;

        if(searchKeyword.length() > 0) {
            forListArticles = new ArrayList<>();

            for(Article article : articles) {
                if(article.title.contains(searchKeyword)) {
                    forListArticles.add(article);
                }
            }

            if(forListArticles.size() == 0) {
                System.out.printf("검색어 : %s(이)가 포함된 게시물이 존재하지 않습니다.\n", searchKeyword);
                return;
            }
        }

        System.out.println("번호 | 조회 | 제목");
        for(int i = forListArticles.size() - 1; i >= 0 ; i--) {
            Article article = forListArticles.get(i);
            System.out.printf("%4d | %4d | %s\n", article.id, article.hit, article.title);
        }

    }

    public void showDetail() {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        Article foundArticle = getArticleById(id);

        if(foundArticle == null) {
            System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        foundArticle.increaseHit();

        System.out.printf("번호 : %d\n", foundArticle.id);
        System.out.printf("날짜 : %s\n", foundArticle.regDate);
        System.out.printf("제목 : %s\n", foundArticle.title);
        System.out.printf("내용 : %s\n", foundArticle.body);
        System.out.printf("조회 : %d\n", foundArticle.hit);
    }

    public void doModify() {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        Article foundArticle = getArticleById(id);

        if(foundArticle == null) {
            System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

        foundArticle.title = title;
        foundArticle.body = body;
        System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
    }

    public void doDelete() {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        int foundIndex = getArticleIndexById(id);

        if(foundIndex == -1) {
            System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        articles.remove(foundIndex);
        System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
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
}
