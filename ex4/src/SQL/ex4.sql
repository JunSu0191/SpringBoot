-- Active: 1714446741761@@127.0.0.1@3306@joeun
CREATE TABLE `ex4` (
  `no` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `writer` varchar(100) NOT NULL,
  `content` text,
  `reg_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `views` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`no`)
) COMMENT='게시판';

SELECT *
FROM ex4

INSERT INTO ex4 (title, writer, content)
VALUES("제목01", "작성자01", "내용01")

