package com.aloha.ex1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aloha.ex1.dto.Board;
import com.aloha.ex1.dto.Option;
import com.aloha.ex1.dto.Page;

public interface BoardService {

    // 게시글 목록
    public List<Board> list(Option option, Page page) throws Exception;
    
    // 게시글 조회
    public Board select(int no) throws Exception;

    // 게시글 등록
    public int insert(Board board) throws Exception;

    // 게시글 수정
    public int update(Board board) throws Exception;

    // 게시글 삭제
    public int delete(int no) throws Exception;

    // 게시글 목록 - [검색]
    // public List<Board> search(@Param("keyword") String keyword) throws Exception;
    public List<Board> search(@Param("option") Option option) throws Exception;
    
}