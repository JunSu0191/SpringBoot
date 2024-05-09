package com.aloha.ex6.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aloha.ex6.dto.Board;

@Mapper
public interface BoardMapper {
    
    public List<Board> list() throws Exception;

    public Board select(int no) throws Exception;

    public int insert(Board board) throws Exception;

    public int update(Board board) throws Exception;

    public int delete(int no) throws Exception;
}
