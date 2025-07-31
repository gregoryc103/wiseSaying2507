package com.ll;

import com.ll.util.Rq;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class App {
    private final Scanner sc = new Scanner(System.in);
    private int nextId = 1;
    private final List<WiseInput> wiseList = new ArrayList<>();

    void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine().trim();

            Rq rq = new Rq(cmd);

            switch (rq.getActionName()) {
                case "종료" -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                case "등록" -> actionWrite();
                case "목록" -> actionList();
                case "삭제" -> actionDelete(rq);
                case "수정" -> actionEdit(rq);
                default -> System.out.println("알 수 없는 명령입니다.");
            }
        }
    }

    private void actionWrite() {
        System.out.print("명언 : ");
        String content = sc.nextLine().trim();

        System.out.print("작가 : ");
        String author = sc.nextLine().trim();

        WiseInput wiseInput = new WiseInput(nextId++, author, content);
        wiseList.add(wiseInput);

        System.out.printf("%d번 명언이 등록되었습니다.%n", wiseInput.getId());
    }

    private void actionList() {
        if (wiseList.isEmpty()) {
            System.out.println("등록된 명언이 없습니다.");
            return;
        }

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        wiseList.stream()
                .sorted(Comparator.comparingInt(WiseInput::getId).reversed())
                .forEach(wise -> System.out.printf("%d / %s / %s%n",
                        wise.getId(), wise.getAuthor(), wise.getContent()));
    }

    private void actionDelete(Rq rq) {
        int deleteId = rq.getParamAsInt("id", -1);

        if (deleteId == -1) {
            System.out.println("삭제할 명언 번호를 입력해주세요.");
            return;
        }

        boolean removed = wiseList.removeIf(wise -> wise.getId() == deleteId);

        if (removed) {
            System.out.printf("%d번 명언이 삭제되었습니다.%n", deleteId);
        } else {
            System.out.printf("%d번 명언은 존재하지 않습니다.%n", deleteId);
        }
    }

    private void actionEdit(Rq rq) {
        int editId = rq.getParamAsInt("id", -1);

        if (editId == -1) {
            System.out.println("수정할 명언 번호를 입력해주세요.");
            return;
        }

        WiseInput editTarget = findById(editId);

        if (editTarget == null) {
            System.out.printf("%d번 명언은 존재하지 않습니다.%n", editId);
            return;
        }

        System.out.printf("명언(기존) : %s%n", editTarget.getContent());
        System.out.print("명언 : ");
        String newContent = sc.nextLine().trim();

        System.out.printf("작가(기존) : %s%n", editTarget.getAuthor());
        System.out.print("작가 : ");
        String newAuthor = sc.nextLine().trim();

        editTarget.setContent(newContent);
        editTarget.setAuthor(newAuthor);

        System.out.printf("%d번 명언이 수정되었습니다.%n", editId);
    }

    private WiseInput findById(int id) {
        return wiseList.stream()
                .filter(wise -> wise.getId() == id)
                .findFirst()
                .orElse(null);
    }
}