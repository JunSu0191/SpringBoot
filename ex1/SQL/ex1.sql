-- Active: 1714459978310@@127.0.0.1@3306@joeun
CREATE TABLE `ex1` (
  `no` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `writer` varchar(100) NOT NULL,
  `content` text,
  `reg_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `views` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`no`)
) COMMENT='게시판';


-- 게시글 목록
SELECT *
FROM ex1

-- 게시글 등록
INSERT INTO ex1 (title, writer, content)
VALUES("안녕", "안녕", "안녕")

-- 게시글 조회
SELECT title, writer, content
FROM ex1

-- 게시글 수정
UPDATE ex1
SET title = "ㅎㅇ", writer = "안녕", content = "ㅋㅋ"

-- 게시글 삭제
DELETE FROM ex1
WHERE no;

CREATE TABLE `file1` (
  `no` int NOT NULL AUTO_INCREMENT,
  `parent_table` varchar(45) NOT NULL,
  `parent_no` int NOT NULL,
  `file_name` text NOT NULL,
  `origin_name` text,
  `file_path` text NOT NULL,
  `file_size` int NOT NULL DEFAULT '0',
  `reg_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `file_code` int NOT NULL DEFAULT '0',         
  PRIMARY KEY (`no`)
) COMMENT='파일';

SELECT *
FROM file1

SELECT *
FROM ex1


TRUNCATE file1;

TRUNCATE ex1;

-- 샘플 데이터 등록
INSERT INTO ex1( title, writer, content )
VALUES ('제목01', '작성자01', '내용01')
      ,('제목02', '작성자02', '내용02')
      ,('제목03', '작성자03', '내용03')
      ,('제목04', '작성자04', '내용04')
      ,('제목05', '작성자05', '내용05')
;

CREATE TABLE reply1 (
    `no`              INT NOT NULL AUTO_INCREMENT PRIMARY KEY,        -- 댓글번호
    `board_no`        INT NOT NULL,                                   -- 글번호
    `parent_no`       INT NOT NULL,                                   -- 부모번호
    `writer`          VARCHAR(100) NOT NULL,                          -- 작성자
    `content`         TEXT NOT NULL,                                  -- 내용
    `reg_date`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,   -- 등록일자
    `upd_date`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,   -- 수정일자
    -- ⭐ 외래키 지정 : 게시글 삭제 시, 종속된 댓글 삭제
    FOREIGN KEY (`board_no`) REFERENCES ex1(`no`) 
                                                ON DELETE CASCADE   -- CASCADE, RESTRICT, SET NULL
                                                ON UPDATE CASCADE   
);

SELECT *
FROM reply1 

DROP TABLE reply1