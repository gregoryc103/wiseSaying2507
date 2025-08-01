package com.ll;

public class Main {

    public static void main(String[] args) {
        App app = new App();
        app.run();

    }
    // Rq 클래스 테스트용
    private static void testRq1() {
        Rq rq = new Rq("목록?searchKeywordType=content&searchKeyword=자바");
        System.out.println("actionName : " + rq.getActionName());
        System.out.println("params searchKeywordType : " + rq.getParam("searchKeywordType", ""));
        System.out.println("params searchKeyword : " + rq.getParam("searchKeyword", ""));
        System.out.println("=== 테스트 완료 ===\n");
    }
}
