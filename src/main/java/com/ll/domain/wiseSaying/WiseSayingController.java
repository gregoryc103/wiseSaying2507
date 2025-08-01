package com.ll.domain.wiseSaying;

import com.ll.Rq;
import com.ll.WiseInput;
import java.util.Comparator;
import java.util.Scanner;

public class WiseSayingController {
    private final Scanner sc;
    private final WiseSayingService service = new WiseSayingService();

    public WiseSayingController(Scanner sc) {
        this.sc = sc;
    }

    public void actionWrite() {
        System.out.print("명언 : ");
        String content = sc.nextLine().trim();

        System.out.print("작가 : ");
        String author = sc.nextLine().trim();

        WiseInput saved = service.write(content, author);
        System.out.printf("%d번 명언이 등록되었습니다.%n", saved.getId());
    }

    public void actionList() {
        var wiseList = service.findAll();

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

    public void actionDelete(Rq rq) {
        int deleteId = rq.getParamAsInt("id", -1);

        if (deleteId == -1) {
            System.out.println("삭제할 명언 번호를 입력해주세요.");
            return;
        }

        if (service.delete(deleteId)) {
            System.out.printf("%d번 명언이 삭제되었습니다.%n", deleteId);
        } else {
            System.out.printf("%d번 명언은 존재하지 않습니다.%n", deleteId);
        }
    }

    public void actionEdit(Rq rq) {
        int editId = rq.getParamAsInt("id", -1);

        if (editId == -1) {
            System.out.println("수정할 명언 번호를 입력해주세요.");
            return;
        }

        var optionalWise = service.findById(editId);

        if (optionalWise.isEmpty()) {
            System.out.printf("%d번 명언은 존재하지 않습니다.%n", editId);
            return;
        }

        WiseInput editTarget = optionalWise.get();

        System.out.printf("명언(기존) : %s%n", editTarget.getContent());
        System.out.print("명언 : ");
        String newContent = sc.nextLine().trim();

        System.out.printf("작가(기존) : %s%n", editTarget.getAuthor());
        System.out.print("작가 : ");
        String newAuthor = sc.nextLine().trim();

        if (service.edit(editId, newContent, newAuthor)) {
            System.out.printf("%d번 명언이 수정되었습니다.%n", editId);
        }
    }
}