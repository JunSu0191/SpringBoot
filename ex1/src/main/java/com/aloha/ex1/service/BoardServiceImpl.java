package com.aloha.ex1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aloha.ex1.dto.Board;
import com.aloha.ex1.dto.Files;
import com.aloha.ex1.dto.Option;
import com.aloha.ex1.dto.Page;
import com.aloha.ex1.mapper.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private FileService fileService;

    @Override
    public List<Board> list(Option option, Page page) throws Exception {

        int total = boardMapper.count(option);
        page.setTotal(total);

        List<Board> boardList = boardMapper.list(option, page);
            return boardList;
    }

    @Override
    public int insert(Board board) throws Exception {

        int result = boardMapper.insert(board);

        // 파일 업로드
        String parentTable = "ex1";
        int parentNo = boardMapper.maxPk();

        // 썸네일 업로드
        // - 부모테이블, 부모번호, 멀티파트파일, 파일코드:1(썸네일)
        MultipartFile thMultipartFile = board.getThumbnail();

        if ( thMultipartFile != null && !thMultipartFile.isEmpty() ) {
            Files thumbnail = new Files();
            thumbnail.setFile(thMultipartFile);
            thumbnail.setParentTable(parentTable);
            thumbnail.setParentNo(parentNo);
            thumbnail.setFileCode(1);
            fileService.upload(thumbnail);
        }

        // 첨부파일 업로드
        List<MultipartFile> fileList = board.getFile();
        if ( !fileList.isEmpty() ) {
            for (MultipartFile file : fileList) {
                if ( file.isEmpty() ) continue;
                
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

    @Override
    public Board select(int no) throws Exception {
        
        Board board = boardMapper.select(no);
        return board;
        
    }

    @Override
    public int update(Board board) throws Exception {

        int result = boardMapper.update(board);
        return result;
    }

    @Override
    public int delete(int no) throws Exception {

        int result = boardMapper.delete(no);
        log.info("게시글 삭제" + result);
        return result;
    }

    @Override
    public List<Board> search(Option option) throws Exception {

        List<Board> boardList = boardMapper.search(option);
        return boardList;
    }
    
}
