package com.aloha.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aloha.board.dto.Board;
import com.aloha.board.dto.Files;
import com.aloha.board.dto.Option;
import com.aloha.board.dto.Page;
import com.aloha.board.mapper.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service    // 서비스 역할의 스프링 빈 등록
public class BoardServiceImpl implements BoardService {
    
    
    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private ReplyService replyService;
    

    /**
     * 게시글 목록 조회
     */
    @Override
    public List<Board> list(Page page, Option option) throws Exception {
        // 게시글 데이터 개수 조회
        int total = boardMapper.count(option);
        page.setTotal(total);

        List<Board> boardList = boardMapper.list(page, option);
        return boardList;
    }

    /**
     * 게시글 조회
     * - no 매개변수로 게시글 번호를 전달받아서
     *   데이터베이스에 조회 요청
     */
    @Override
    public Board select(int no) throws Exception {

        Board board = boardMapper.select(no);
        
        return board;
    }

    /**
     * 게시글 등록
     */

    @Override
    public int insert(Board board) throws Exception {

        
        int result = boardMapper.insert(board);
        
        // 파일 업로드
        String parentTable = "board";
        int parentNo = boardMapper.maxPk();
        
        // 썸네일 업로드
        // - 부모테이블, 부모번호, 멀티파트파일, 파일코드:1(썸네일)
        MultipartFile thMultipartFile = board.getThumbnail();

        if( thMultipartFile != null && !thMultipartFile.isEmpty() ) {
            Files thumbnail = new Files();
            thumbnail.setFile(thMultipartFile);
            thumbnail.setParentTable(parentTable);
            thumbnail.setParentNo(parentNo);
            thumbnail.setFileCode(1);       // 썸네일 파일 코드(1)
            fileService.upload(thumbnail);           // 썸네일 파일 업로드
        }

        // 첨부파일 업로드
        List<MultipartFile> fileList = board.getFile();
        if( !fileList.isEmpty() ) {
            for (MultipartFile file : fileList) {
                if( file.isEmpty() ) continue;

                // 파일 업로드 요청
                Files uploadFile = new Files();
                uploadFile.setParentTable(parentTable);
                uploadFile.setParentNo(parentNo);
                uploadFile.setFile(file);

                fileService.upload(uploadFile);

            }
        }
        return result;
    }
    
    /**
     * 게시글 수정
     */
    @Override
    public int update(Board board) throws Exception {

        int result = boardMapper.update(board);
        return result;
    }
    
    /**
     * 게시글 삭제
     */
    @Override
    public int delete(int no) throws Exception {
        
        int result = boardMapper.delete(no);
        if( result > 0) {
            result += replyService.deleteByBoardNo(no);
        }
        return result;
    }

    /**
     * 게시글 목록 검색
     */
    @Override
    // public List<Board> search(String keyword) throws Exception {
    public List<Board> search(Option option) throws Exception {

        // List<Board> boardList = boardMapper.search(keyword);
        List<Board> boardList = boardMapper.search(option);
        return boardList;
    }

    // 게시글 조회수 증가
    @Override
    public int views(int no) throws Exception {

        log.info(no + "번 글 조회수 증가...");
        
        return boardMapper.views(no);
    }

}
