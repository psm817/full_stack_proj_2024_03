package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 프로그램 시작 ==");

        // 스캐너는 키보드로 입력 값을 받는 클래스이다.
        // nextLine()을 하게 되면 키보드로 입력하고 enter를 치면 콘솔의 다음 라인으로 넘어간다.
        // next는 띄어쓰기를 허용하지 않고, nextLine은 띄어쓰기를 허용한다.
        // next에서 띄어쓰기를 하고 입력을 추가로 한다면 해당 입력 값은 다음 값으로 출력된다.
        Scanner sc = new Scanner(System.in);

        // 프로그램에 명령어를 입력하라는 안내문 -> 사용자를 위한 편의성
        System.out.printf("명령어) ");
        String cmd = sc.nextLine();
        System.out.printf("입력된 명령어 : %s\n", cmd);

        // nextInt는 오직 숫자만 입력할 수 있다.
        // nextInt는 enter가 잘 작동을 안할 수도 있기 때문에 항상 다음 줄에 nextLine을 작성한다.
        System.out.printf("명령어) ");
        int num = sc.nextInt();
        System.out.printf("입력된 명령어 : %d\n", num);

        // 스캐너를 생성하면 close로 항상 닫아줘야한다.
        sc.close();

        System.out.println("== 프로그램 끝 ==");
    }
}