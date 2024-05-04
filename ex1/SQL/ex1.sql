-- Active: 1714446741761@@127.0.0.1@3306@joeun
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
