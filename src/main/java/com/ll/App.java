package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    Scanner sc = new Scanner(System.in);
    int idx = 0;
    List<WiseInput> wiseList = new ArrayList<>();
    void run() {
        System.out.println("명언 앱");

        while (true) {
            System.out.print("명령: ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit") || cmd.equals("종료")) {
                System.out.println("스캐너 종료");
                sc.close();
                break;
            }
            else if (cmd.contains("등록")) {
                System.out.println(cmd);
                WiseInput wiseItem = actionWrite();
                actionAddToList(wiseItem);
                actionList();
            }
            else if (cmd.startsWith("삭제")) {
                actionDelete(cmd);
            }
            else if(cmd.contains("목록")){
                actionList();
            }
            else if(cmd.startsWith("수정")){
                actionEdit(cmd);
            }


        }
    }

    WiseInput actionWrite(){
        System.out.print("명언: ");
        String wiseContent = sc.nextLine().trim();
        System.out.println(wiseContent);

        System.out.print("작가: ");
        String wiseAuthor = sc.nextLine().trim();
        System.out.println(wiseAuthor);

        return new WiseInput(++idx, wiseAuthor, wiseContent);
    }

    void actionAddToList(WiseInput wiseItem){
        wiseList.add(wiseItem);
        //System.out.println(String.format("%d번 명언이 등록되었습니다.", idx));
        //System.out.println("%d번 명언이 등록되었습니다.".formatted(idx)); //Language level 15이상에서 사용 가능
        System.out.printf("%d번 명언이 등록되었습니다.%n", wiseItem.getId());
    }

    void actionList(){

        if(wiseList.isEmpty()){
            System.out.println("목록이 비어있습니다.");
        } else{
            System.out.println("-------------------------------------------");
            wiseList.stream()
                    .sorted((a, b) -> b.getId() - a.getId())
                    .forEach(wise -> System.out.printf("%d / %s / %s%n",
                            wise.getId(), wise.getAuthor(), wise.getContent()));
        }
    }

    void actionDelete(String cmd){
        System.out.println(cmd);
        String numStr = cmd.replaceAll("[^0-9]","");
        if(numStr.isEmpty()){
            System.out.println("삭제할 명언 ID를 입력하세요.");
            return;
        }
        else {
            int deleteId = Integer.parseInt(numStr);
            boolean exists = wiseList.removeIf(wise -> wise.getId() == deleteId);

            if (exists) {
                System.out.printf("%d번 명언이 삭제되었습니다.%n", deleteId);
            }
            else {
                System.out.printf("%d번 명언은 존재하지 않습니다.%n", deleteId);
            }
        }
    }

   /* void actionEdit(String cmd, List<WiseInput> wiseList){
        System.out.println(cmd);
        String numStr = cmd.replaceAll("[^0-9]","");
        if(numStr.isEmpty()){
            System.out.println("수정할 명언 ID를 입력하세요.");
            return;
        }
        else{
            int editId = Integer.parseInt(numStr);

            *//*boolean exists = wiseList.stream()
                    .anyMatch(x -> x.getId() == editId);*//*

            WiseInput editTarget = wiseList.stream()
                    .filter(x -> x.getId() == editId)
                    .findFirst()
                    .orElse(null);

            if(editTarget != null){

                System.out.printf("명언(기존) : %s%n", editTarget.getContent());
                System.out.print("수정할 명언 내용을 입력하세요: ");
                String editContent = sc.nextLine().trim();
                editTarget.setContent(editContent);

                System.out.printf("작가(기존) : %s%n", editTarget.getAuthor());
                System.out.print("수정할 작가 이름을 입력하세요: ");
                String editAuthor = sc.nextLine().trim();
                editTarget.setAuthor(editAuthor);

                System.out.printf("%d번 명언이 수정되었습니다.%n", editId);
                System.out.println(String.format("ID: %d / 수정된 명언: %s / 수정된 작가명: %s", editTarget.getId(), editContent, editAuthor));
            }
            else{
                System.out.printf("%d번 명언은 존재하지 않습니다.%n", editId);
            }
    }     */
   // 1. ID 파싱 메서드 분리
   private int parseId(String cmd) {
       String numStr = cmd.replaceAll("[^0-9]", "");
       return numStr.isEmpty() ? -1 : Integer.parseInt(numStr);
   }

    // 2. ID로 명언 찾기 메서드 분리
    private WiseInput findById(int id) {
        return wiseList.stream()
                .filter(wise -> wise.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // 3. 수정 입력 받기 메서드
    private void updateWiseInput(WiseInput wise) {
        // 기존 값 표시와 새 값 입력을 일관된 형식으로
        System.out.printf("명언(기존) : %s%n", wise.getContent());
        System.out.print("명언 : ");  // actionWrite()와 동일한 형식
        String newContent = sc.nextLine().trim();

        System.out.printf("작가(기존) : %s%n", wise.getAuthor());
        System.out.print("작가 : ");  // actionWrite()와 동일한 형식
        String newAuthor = sc.nextLine().trim();

        wise.setContent(newContent);
        wise.setAuthor(newAuthor);
    }

    void actionEdit(String cmd) {

        int editId = parseId(cmd);
        if (editId == -1) {
            System.out.println("수정할 명언 ID를 입력하세요.");
            return;
        }

        WiseInput editTarget = findById(editId);
        if (editTarget == null) {
            System.out.printf("%d번 명언은 존재하지 않습니다.%n", editId);
            return;
        }

        updateWiseInput(editTarget);
        System.out.printf("%d / %s / %s%n",
        editTarget.getId(), editTarget.getAuthor(), editTarget.getContent());
    }
}
