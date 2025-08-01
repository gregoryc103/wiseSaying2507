package com.ll;

import com.ll.domain.system.SystemContoller;
import com.ll.domain.wiseSaying.WiseSayingController;
import java.util.Scanner;

public class App {
    private final Scanner sc = new Scanner(System.in);
    private final SystemContoller systemContoller = new SystemContoller();
    private final WiseSayingController wiseSayingController = new WiseSayingController(sc);

    void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine().trim();

            Rq rq = new Rq(cmd);

            switch (rq.getActionName()) {
                case "종료" -> {
                    systemContoller.actionExit();
                    return;
                }
                case "등록" -> wiseSayingController.actionWrite();
                case "목록" -> wiseSayingController.actionList();
                case "삭제" -> wiseSayingController.actionDelete(rq);
                case "수정" -> wiseSayingController.actionEdit(rq);
                default -> System.out.println("알 수 없는 명령입니다.");
            }
        }
    }
}