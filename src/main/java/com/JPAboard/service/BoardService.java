package com.JPAboard.service;

import com.JPAboard.domain.entity.BoardEntity;
import com.JPAboard.domain.repository.BoardRepository;
import com.JPAboard.dto.BoardDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {
    private BoardRepository boardRepository;

    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 5; // 한 페이지에 존재하는 게시글 수

    @Transactional
    public List<BoardDTO> getBoardlist(Integer pageNum) {
        Page<BoardEntity> page = boardRepository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "createdDate")));

        List<BoardEntity> boardEntities = page.getContent();
        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (BoardEntity boardEntity : boardEntities) {
            boardDTOList.add(this.convertEntityToDto(boardEntity));
        }

        return boardDTOList;
    }

    // 페이징 설정
    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }

    public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getBoardCount());

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        // 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
    }

    @Transactional
    public BoardDTO getPost(Long id) {
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityWrapper.get();

        BoardDTO boardDTO = BoardDTO.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreatedDate())
                .build();

        return boardDTO;
    }

    @Transactional
    public Long savePost(BoardDTO boardDTO) {
        return boardRepository.save(boardDTO.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long no) {
        boardRepository.deleteById(no);
    }
    
    /* 유저 데이터 정보 포함 게시글 삭제 구현 필요
    @Transactional
    public void deletePost(Long no, Long uno) {
        boardRepository = boardRepository.findById(no);
        if(boardRepository.belongsToUser(uno) {
            boardRepository.deleteById(no);
        }
    }
    */

    // 글제목 기준 검색 설정
    @Transactional
    public List<BoardDTO> searchPosts(String keyword) {
        List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword);
        List<BoardDTO> boardDTOList = new ArrayList<>();

        if (boardEntities.isEmpty()) return boardDTOList;

        for (BoardEntity boardEntity : boardEntities) {
            boardDTOList.add(this.convertEntityToDto(boardEntity));
        }

        return boardDTOList;
    }

    private BoardDTO convertEntityToDto(BoardEntity boardEntity) {
        return BoardDTO.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }
}