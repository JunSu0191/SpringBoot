package com.aloha.ex1.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aloha.ex1.dto.Files;
import com.aloha.ex1.service.FileService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/file")
public class FileController {
    
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private FileService fileService;

    @GetMapping("/{no}")
    public void FileDownload(@PathVariable("no") int no, HttpServletResponse response) throws Exception {
        // 파일 서비스를 통해 파일 다운로드 정보를 가져옵니다.
        Files downloadFile = fileService.download(no);
        
        // 가져온 파일이 없으면 다운로드를 종료합니다.
        if(downloadFile == null) {
            return;
        }
        
        // 다운로드할 파일의 이름과 경로를 가져옵니다.
        String fileName = downloadFile.getFileName();   // 파일 명
        String filePath = downloadFile.getFilePath();   // 파일 경로
        
        // 응답 헤더를 설정하여 파일 다운로드를 시작합니다.
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);  // 응답의 컨텐츠 타입 설정
        fileName = URLEncoder.encode(filePath, "UTF-8");  // 파일 이름을 UTF-8로 인코딩하여 한글 파일명을 처리합니다.
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");  // 다운로드할 파일의 이름을 헤더에 설정합니다.
    
        // 파일을 읽어와서 클라이언트에게 전송합니다.
        File file = new File(filePath);  // 다운로드할 파일 객체 생성
        FileInputStream fis = new FileInputStream(file);  // 파일을 읽어오기 위한 FileInputStream 생성
        ServletOutputStream sos = response.getOutputStream();  // 서블릿 응답의 OutputStream을 가져옵니다.
        FileCopyUtils.copy(fis, sos);  // 파일을 복사하여 클라이언트에게 전송합니다.
    
        // 사용한 자원을 닫습니다.
        fis.close();  // FileInputStream 닫기
        sos.close();  // ServletOutputStream 닫기
    }

    @DeleteMapping("/{no}")
    public ResponseEntity<String> deleteFile(@PathVariable("no") int no) throws Exception {
        log.info("[DELETE] - /file/" + no);

        // 파일 삭제 요청
        int result = fileService.delete(no);

        // 삭제 성공
        if ( result > 0) {
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        // 삭제 실패
        return new ResponseEntity<>("FAIL", HttpStatus.OK);
    }
    
  /**
     * 이미지 썸네일
     * @param param
     * @return
     */
    @GetMapping("/img/{no}")
    public ResponseEntity<byte[]> thumbnailImg(@PathVariable("no") int no) throws Exception {

        // 파일 번호로 파일 정보 조회
        Files file = fileService.select(no);

        // Null 체크
        if( file == null ) {
            String filePath = uploadPath + "/no-image.jpg";
            File noImageFile = new File(filePath);
            byte[] noImageFileData = FileCopyUtils.copyToByteArray(noImageFile);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(noImageFileData, headers, HttpStatus.OK);
        }

        // 파일 정보 중에서 파일 경로 가져오기
        String filePath =  file.getFilePath();

        // 파일 객체 생성
        File f = new File(filePath);

        // 파일 데이터 
        byte[] fileData = FileCopyUtils.copyToByteArray(f);

        // 이미지 컨텐츠 타입 지정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        // new ResponseEntity<>(데이터, 헤더, 상태코드)
        return new ResponseEntity<>( fileData, headers, HttpStatus.OK );
    
    }
}