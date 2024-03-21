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

                // getArticleById() 메서드는 사용자가 입력한 게시물 번호를 통해 게시물을 가져오는 역할이다.
                // 가져온 게시물을 foundArticle에 넣어준다.
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

                // getArticleById() 메서드는 사용자가 입력한 게시물 번호를 통해 게시물을 가져오는 역할이다.
                // 가져온 게시물을 foundArticle에 넣어준다.
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

                // getArticleIndexById() 메서드는 사용자가 입력한 게시물 번호를 통해 게시물의 인덱스 번호를 가져오는 역할이다.
                // 가져온 게시물을 foundIndex에 넣어준다.
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

        // 게시물 article을 articles가 있을 때까지 차례대로 가져오는 것을 반복한다.
        // 1번 게시물은 0번째 인덱스 번호와 같은 식이기 때문에 i++을 해준다.
        for(Article article : articles) {
            if(article.id == id) {
                return i;
            }
            i++;
        }

        // 찾는 게시물이 없다면 -1을 return한다.
        return -1;
    }

    private Article getArticleById(int id) {
        // getArticleIndexById()을 통해 인덱스 번호를 가져온다.
        int index = getArticleIndexById(id);

        // index가 -1이 아니면 게시물이 있다는 것이기 때문에 articles.get을 통해 게시물 정보를 리턴한다.
        // 이렇게 되면 밑에 있는 반복문은 사용할 필요가 없다.
        // 이미 인덱스를 가져올 때 순회를 했기 때문에
        if(index != -1) {
            return articles.get(index);
        }

//        // 아래 반복문을 향상된 for문으로 코드를 다이어트 시켜준다.
//        // 게시물 article을 articles가 있을 때까지 차례대로 가져오는 것을 반복한다.
//        for(Article article : articles) {
//            if(article.id == id) {
//                return article;
//            }
//        }
//        for(int i = 0; i < articles.size(); i++) {
//            Article article = articles.get(i);
//
//            // 게시물 번호를 통해 게시물을 찾았다면 article을 return한다.
//            if(article.id == id) {
//                return article;
//            }
//        }

        // 찾는 게시물이 없다면 null을 return한다.
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
