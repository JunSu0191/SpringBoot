package com.aloha.ex1.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Reply {
    
    private int no;
    private int boardNo;
    private int parentNo;
    private String writer;
    private String content;
    private Date regDate;
    private Date updDate;
}
