package org.example;

import org.example.controller.ArticleController;
import org.example.controller.Controller;
import org.example.controller.MemberController;

import java.util.Scanner;

public class App {
    public void start() {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        MemberController memberController = new MemberController(sc);
        ArticleController articleController = new ArticleController(sc);

        articleController.makeTestData();

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

            String[] cmdBits = cmd.split(" ");
            // 사용자가 입력한 명령어를 쪼갰을 때 0번째에 article 또는 member 둘 중에 하나를 받는 controllerName 변수를 만든다.
            String controllerName = cmdBits[0]; // article, member

            // actionMethodName에는 각 article, member 뒤에 들어가는 명령어 이름이 저장된다.
            String actionMethodName = cmdBits[1]; // write, list, join ...

            Controller controller = null;

            // 입력한 명령어의 첫번째가 article이면 controller를 articleController와 연결한다.
            // 해당 코드가 적용될려면 두 클래스의 타입이 다르기 때문에,
            // articleController은 Controller에 상속되게 해야한다.
            if(controllerName.equals("article")) {
                controller = articleController;
            }
            // 입력한 명령어의 첫번째가 member이면 controller를 memberController와 연결한다.
            // 해당 코드가 적용될려면 두 클래스의 타입이 다르기 때문에,
            // articleController은 Controller에 상속되게 해야한다.
            else if(controllerName.equals("member")) {
                controller = memberController;
            }
            else {
                System.out.println("존재하지 않는 명령어 입니다.");
                continue;
            }

            // Controller에서 doAction()을 하면 각 article, member 관련 기능(actionMethodName)을 수행한다.
            // cmd를 인자로 넘기는 이유는 doAction 안에서 doJoin이나 showList를 호출할 때 cmd를 알아야하기 때문에
            controller.doAction(cmd, actionMethodName);
        }

        sc.close();
        System.out.println("== 프로그램 끝 ==");
    }
}