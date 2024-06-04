package com.aloha.ex1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.ex1.dto.Reply;
import com.aloha.ex1.mapper.ReplyMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public List<Reply> list() throws Exception {

        List<Reply> replyList = replyMapper.list();
        return replyList;
    }

    @Override
    public List<Reply> listByBoardNo(int BoardNo) throws Exception {

        List<Reply> replyList = replyMapper.listByBoardNo(BoardNo);
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

        if ( result > 0 && parentNo == 0 ) {
            int no = replyMapper.max();
            reply.setNo(no);
            reply.setParentNo(no);
            replyMapper.update(reply);
        }
        return result;
    }

    @Override
    public int update(Reply reply) throws Exception {

        int result = replyMapper.update(reply);
        return result;
    }

    @Override
    public int delete(int no) throws Exception {

        int result = replyMapper.delete(no);
        if ( result > 0) {
            result += deleteByParentNo(no);
        }
        return result;
    }

    @Override
    public int deleteByBoardNo(int boardNo) throws Exception {

        int result = replyMapper.deleteByBoardNo(boardNo);
        return result;
    }

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
