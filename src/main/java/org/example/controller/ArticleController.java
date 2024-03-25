package org.example.controller;

import org.example.Container;
import org.example.dto.Article;
import org.example.util.Util;
import org.example.dto.Member;

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
        articles = Container.articleDao.articles;
    }

    // 테스트 데이터 만들기
    public void makeTestData() {
        System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");

        // 리스트 추가와 id번호를 모두 컨테이너를 통해서 가져온다.
        Container.articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 1, "test1", "Hello", 128));
        Container.articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 2, "test2", "World", 18));
        Container.articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 2, "test3", "Good", 53));
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;
        this.actionMethodName = actionMethodName;

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
            default:
                System.out.println("존재하지 않는 명령어입니다.");
                break;
        }
    }

    public void doWrite() {
        // 컨테이너의 articleDao를 타고 들어가서 getNewId()를 통해 id를 가져온다.
        int id = Container.articleDao.getNewId();
        String regDate = Util.getNowDateStr();
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

        Article article = new Article(id, regDate, loginedMember.id, title, body);

        // articles.add를 통해 바로 추가하는 것은 이제 Dao가 할 일이다.
        // 컨테이너를 통해 article를 add한다.
        Container.articleDao.add(article);

        System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
    }

    public void showList() {
        if(articles.size() == 0) {
            System.out.println("게시물이 없습니다.");
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

        System.out.println("번호 |   작성자 | 조회 | 제목");
        for(int i = forListArticles.size() - 1; i >= 0 ; i--) {
            Article article = forListArticles.get(i);
            String writerName = null;

            List<Member> members = Container.memberDao.members;

            for(Member member : members) {
                if(article.memberId == member.id) {
                    writerName = member.name;
                }
            }

            System.out.printf("%4d | %5s | %4d | %s\n", article.id, writerName, article.hit, article.title);
        }

    }

    public void showDetail() {
        String[] cmdBits = cmd.split(" ");

        if(cmdBits.length <= 2) {
            System.out.println("조회하고 싶은 게시물 번호를 입력해주세요.");
            return;
        }

        int id = Integer.parseInt(cmdBits[2]);

        Article foundArticle = getArticleById(id);

        if(foundArticle == null) {
            System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        foundArticle.increaseHit();

        System.out.printf("번호 : %d\n", foundArticle.id);
        System.out.printf("날짜 : %s\n", foundArticle.regDate);
        System.out.printf("작성자 : %d\n", foundArticle.memberId);
        System.out.printf("제목 : %s\n", foundArticle.title);
        System.out.printf("내용 : %s\n", foundArticle.body);
        System.out.printf("조회 : %d\n", foundArticle.hit);
    }

    public void doModify() {
        String[] cmdBits = cmd.split(" ");

        if(cmdBits.length <= 2) {
            System.out.println("수정하고 싶은 게시물 번호를 입력해주세요.");
            return;
        }

        int id = Integer.parseInt(cmdBits[2]);

        Article foundArticle = getArticleById(id);

        if(foundArticle == null) {
            System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        if(foundArticle.memberId != loginedMember.id) {
            System.out.println("권한이 없습니다.");
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

        if(cmdBits.length <= 2) {
            System.out.println("삭제하고 싶은 게시물 번호를 입력해주세요.");
            return;
        }

        int id = Integer.parseInt(cmdBits[2]);

        Article foundArticle = getArticleById(id);

        if(foundArticle == null) {
            System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        if(foundArticle.memberId != loginedMember.id) {
            System.out.println("권한이 없습니다.");
            return;
        }

        articles.remove(foundArticle);
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
