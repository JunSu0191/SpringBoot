package com.aloha.ex1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Option {
    
    String keyword;
    int code;

    public Option() {
        this.keyword = "";
    }
}
