package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        // 게시물의 ID 번호 세팅
        int lastArticleId = 0;

        // while(true)는 무한루프다.
        while(true) {
            System.out.printf("명령어) ");
            String cmd = sc.nextLine();

            // 양 옆 공백은 trim으로 제거
            cmd = cmd.trim();

            // 아무것도 입력하지 않으면(입력된 cmd의 길이가 0이면) 다음 줄로 넘어감.
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

                System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
            }

            else if(cmd.equals("article list")) {
                System.out.println("게시물이 없습니다.");
            }
            else {
                System.out.printf("%s(은)는 존재하지 않는 명령어 입니다.\n", cmd);
            }
        }

        sc.close();
        System.out.println("== 프로그램 끝 ==");
    }
}