package org.example;

import org.example.dto.Article;
import org.example.dto.Member;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private List<Article> articles;
    private List<Member> members;

    App() {
        articles = new ArrayList<>();
        members = new ArrayList<>();
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

            // ===== 회원가입 =====
            if(cmd.equals("member join")) {
                int id = members.size() + 1;
                String regDate = Util.getNowDateStr();
                String loginId = null;
                String loginPw = null;

                // 아이디 중복체크를 하기 위해 isJoinableLoginId() 메서드를 쓰고
                while(true) {
                    System.out.printf("ID : ");
                    loginId = sc.nextLine();

                    if(isJoinableLoginId(loginId) == false) {
                        System.out.printf("%s는 이미 사용중입니다. 아이디를 다시 입력해주세요.\n", loginId);
                        continue;
                    }

                    break;
                }

                // PW 입력하고 ConfirmPW를 입력할 때 서로 다르면 다시 입력하도록, 무한루프를 돌린다.
                // 만약 입력한 두 값이 같다면 무한루프를 빠져나간다.
                while(true) {
                    System.out.printf("Password : ");
                    loginPw = sc.nextLine();
                    System.out.printf("Password 확인 : ");
                    String loginPwConfirm = sc.nextLine();

                    if(loginPw.equals(loginPwConfirm) == false) {
                        System.out.println("비밀번호 확인 결과 다르게 입력하셨습니다. 다시 입력해주세요");
                        continue;
                    }

                    break;
                }

                System.out.printf("이름 : ");
                String name = sc.nextLine();

                Member member = new Member(id, regDate, loginId, loginPw, name);
                members.add(member);

                System.out.printf("%d번 회원이 생성되었습니다. 환영합니다!\n", id);
            }

            // ===== 게시물 작성 =====
            else if(cmd.equals("article write")) {
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
                        continue;
                    }
                }

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

    private int getMemberIndexByLoginId(String loginId) {
        int i = 0;

        for(Member member : members) {
            if(member.loginId.equals(loginId)) {
                return i;
            }
            i++;
        }

        return -1;
    }

    private boolean isJoinableLoginId(String loginId) {
        int index = getMemberIndexByLoginId(loginId);

        if(index == -1) {
            return true;
        }

        return false;
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