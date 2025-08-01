package com.ll.domain.wiseSaying;

import com.ll.WiseInput;

import java.util.List;
import java.util.Optional;

public class WiseSayingService {
    private final WiseSayingRepository repository = new WiseSayingRepository();

    // 명언 등록
    public WiseInput write(String content, String author) {
        WiseInput wiseInput = new WiseInput(0, author, content);
        return repository.save(wiseInput);
    }

    // 전체 목록 조회
    public List<WiseInput> findAll() {
        return repository.findAll();
    }

    // ID로 조회
    public Optional<WiseInput> findById(int id) {
        return repository.findById(id);
    }

    // 수정
    public boolean edit(int id, String content, String author) {
        Optional<WiseInput> optionalWise = repository.findById(id);

        if (optionalWise.isEmpty()) {
            return false;
        }

        WiseInput wiseInput = optionalWise.get();
        wiseInput.setContent(content);
        wiseInput.setAuthor(author);
        repository.update(wiseInput);

        return true;
    }

    // 삭제
    public boolean delete(int id) {
        return repository.deleteById(id);
    }
}