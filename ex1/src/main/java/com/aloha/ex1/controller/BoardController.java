package com.aloha.ex1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.ex1.dto.Board;
import com.aloha.ex1.dto.Files;
import com.aloha.ex1.dto.Option;
import com.aloha.ex1.dto.Page;
import com.aloha.ex1.service.BoardService;
import com.aloha.ex1.service.FileService;

import lombok.extern.slf4j.Slf4j;





@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileService fileService;
    


    /**
     * 게시글 목록
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public String list(Model model, Option option, Page page) throws Exception {
        
        List<Board> boardList = boardService.list(option, page);

        log.info("keyword" + option);
        
        model.addAttribute("boardList", boardList);
        model.addAttribute("page", page);
        model.addAttribute("option", option);

        // 동적으로 옵션값을 가져오는 경우
        List<Option> optionList = new ArrayList<Option>();
        optionList.add(new Option("전체", 0));
        optionList.add(new Option("제목", 1));
        optionList.add(new Option("내용",2));
        optionList.add(new Option("제목+내용", 3));
        optionList.add(new Option("작성자", 4));
        model.addAttribute("optionList", optionList);

        return "/board/list";

    }

    /**
     * 게시글 조회
     * @param no
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/read")
    public String read(@RequestParam("no") int no, Model model, Files file) throws Exception {
        
        Board board = boardService.select(no);
        
        // 파일 목록 요청
        file.setParentTable("ex1");
        file.setParentNo(no);
        List<Files> fileList = fileService.listByParent(file);   

        model.addAttribute("board", board);
        model.addAttribute("fileList", fileList);

        return "/board/read";
    }

    /**
     * 게시글 등록 화면
     * @return
     * @throws Exception
     */
    @GetMapping("/insert")
    public String insert() {
        return "/board/insert";
    }

    /**
     * 게시글 등록 처리
     * @param board
     * @return
     * @throws Exception
     */
    @PostMapping("/insert")
    public String insertPro(Board board) throws Exception {
        int result = boardService.insert(board);
        
        if( result > 0 ) {

            return "redirect:/board/list";
        }
        return "redirect:/board/insert?error";
    }
    
    /**
     * 게시글 수정 화면
     * @param no
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/update")
    public String update(@RequestParam("no") int no, Model model, Files file) throws Exception {
        
        Board board = boardService.select(no);
        
        // 파일 목록 요청
        file.setParentTable("ex1");
        file.setParentNo(no);
        List<Files> fileList = fileService.listByParent(file);   

        model.addAttribute("board", board);
        model.addAttribute("fileList", fileList);

        return "/board/update";
    }
    /**
     * 게시글 수정 처리
     * @param board
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    public String updatePro(Board board) throws Exception {
        
        int result = boardService.update(board);

        if( result > 0) {
            return "redirect:/board/list";
        }
        int no = board.getNo();
        return "redirect:/board/update?no=" + no + "&error";
    }
    
    /**
     * 게시글 삭제 처리
     * @param no
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    public String delete(@RequestParam("no") int no) throws Exception {
        log.info("게시글 번호 : " + no);
        int result = boardService.delete(no);
        
        if( result > 0) {
            Files file = new Files();
            file.setParentTable("ex1");
            file.setParentNo(no);
            fileService.deleteByParent(file);
            return "redirect:/board/list";
        }
        return "redirect:/board/delete?no=" + no + "&error";
    }
    
    
}
