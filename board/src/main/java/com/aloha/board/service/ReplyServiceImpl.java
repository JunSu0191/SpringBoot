package com.aloha.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.board.dto.Reply;
import com.aloha.board.mapper.ReplyMapper;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ReplyServiceImpl implements ReplyService{

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public List<Reply> list() throws Exception {
        
        List<Reply> replyList = replyMapper.list();
        return replyList;

    }

    @Override
    public Reply select(int no) throws Exception {

        Reply reply = replyMapper.select(no);
        return reply;
    }

    @Override
    public int insert(Reply reply) throws Exception {

        int result = replyMapper.insert(reply);
        int parentNo = reply.getParentNo();
        
        //  댓글 등록
        // - 댓글 번호(no)와 부모 번호(parent_no) 를 똑같이 수정
        if ( result > 0 && parentNo == 0 ) {
            int no = replyMapper.max();
            reply.setNo(no);
            reply.setParentNo(no);
            replyMapper.update(reply);
        }
        // 답글 등록
        // - 부모 번호가 지정되서 등록
        

        return result;
    }

    @Override
    public int update(Reply reply) throws Exception {

        int result = replyMapper.update(reply);
        return result;
    }

    /**
     * 댓글 삭제
     */
    @Override
    public int delete(int no) throws Exception {

        // ⭐ 자식 답글 삭제
        int result = replyMapper.delete(no);
        if ( result > 0) {
            result += deleteByParentNo(no);
        }
        return result;
    }

    /**
     * 댓글 목록
     */
    @Override
    public List<Reply> listByBoardNo(int boardNo) throws Exception {
        
        List<Reply> replyList = replyMapper.listByBoardNo(boardNo);
        return replyList;
    }

    /**
     * 댓글 종속 삭제
     */
    @Override
    public int deleteByBoardNo(int boardNo) throws Exception {

        int result = replyMapper.deleteByBoardNo(boardNo);
        return result;
    }

    /**
     * 최댓값
     */
    @Override
    public int max() throws Exception {

        int max = replyMapper.max();
        return max;
    }

    @Override
    public int deleteByParentNo(int parentNo) throws Exception {

        int result = replyMapper.deleteByParentNo(parentNo);
        return result;
    }

}
