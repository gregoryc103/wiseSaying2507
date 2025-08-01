package com.ll.domain.wiseSaying;

import com.ll.WiseInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WiseSayingRepository {
    private int lastId = 0;
    private final List<WiseInput> wiseSayings = new ArrayList<>();

    // 저장 (등록)
    public WiseInput save(WiseInput wiseInput) {
        if (wiseInput.getId() == 0) {
            wiseInput.setId(++lastId);
        }
        wiseSayings.add(wiseInput);
        return wiseInput;
    }

    // 전체 조회
    public List<WiseInput> findAll() {
        return new ArrayList<>(wiseSayings);  // 방어적 복사
    }

    // ID로 조회
    public Optional<WiseInput> findById(int id) {
        return wiseSayings.stream()
                .filter(wise -> wise.getId() == id)
                .findFirst();
    }

    //업데이트
    public void update(WiseInput wiseInput) {

    }

    // 삭제
    public boolean deleteById(int id) {
        return wiseSayings.removeIf(wise -> wise.getId() == id);
    }

    // 다음 ID 생성 (필요시 사용)
    public int getNextId() {
        return lastId + 1;
    }
}
