-- --------------------------------------------------------
-- 호스트:                          52.231.77.73
-- 서버 버전:                        10.3.14-MariaDB-1:10.3.14+maria~bionic - mariadb.org binary distribution
-- 서버 OS:                        debian-linux-gnu
-- HeidiSQL 버전:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- clipping 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `clipping` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `clipping`;

-- 테이블 clipping.category 구조 내보내기
CREATE TABLE IF NOT EXISTS `category` (
  `category_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deleted` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `order_no` int(11) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  KEY `FKpfk8djhv5natgshmxiav6xkpu` (`user_id`),
  CONSTRAINT `FKpfk8djhv5natgshmxiav6xkpu` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 clipping.category:~11 rows (대략적) 내보내기
DELETE FROM `category`;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (`category_id`, `deleted`, `name`, `order_no`, `user_id`) VALUES
	(1, b'0', 'Cloud Computing', 2, 1),
	(2, b'0', 'Developer', 1, 1),
	(3, b'0', 'Design', 3, 1),
	(4, b'0', 'UX/Reaserch', 5, 1),
	(5, b'0', 'Design Tool', 4, 1),
	(16, b'0', '안녕', 6, 1),
	(17, b'0', 'test2', 7, 1),
	(18, b'0', 'test2', 8, 1),
	(19, b'0', 'test', 9, 1),
	(20, b'0', 'test', 10, 1),
	(21, b'0', 'rrrrrrrrrrrttttttttttttㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ', 11, 1),
	(22, b'0', '스포츠', 12, 1);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;

-- 테이블 clipping.post 구조 내보내기
CREATE TABLE IF NOT EXISTS `post` (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `is_bookmark` bit(1) NOT NULL,
  `source_of` varchar(255) DEFAULT NULL,
  `thumbnail_img_link` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `personal_title` varchar(50) NOT NULL,
  PRIMARY KEY (`post_id`),
  KEY `FKg6l1ydp1pwkmyj166teiuov1b` (`category_id`),
  KEY `FK72mt33dhhs48hf9gcqrq4fxte` (`user_id`),
  CONSTRAINT `FK72mt33dhhs48hf9gcqrq4fxte` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKg6l1ydp1pwkmyj166teiuov1b` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 clipping.post:~21 rows (대략적) 내보내기
DELETE FROM `post`;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` (`post_id`, `comment`, `created_date`, `deleted`, `is_bookmark`, `source_of`, `thumbnail_img_link`, `title`, `updated_date`, `url`, `user_id`, `category_id`, `personal_title`) VALUES
	(1, '오라클 클라우드', '2019-04-13 17:14:47', b'0', b'1', 'www.itworld.co.kr', 'http://files.idg.co.kr/itworld/image/avatar/article/2018/October/erin_hur@idg.co.kr/Post4.JPG', '개발자가 개발에만" 집중할 수 있는 오라클 클라우드만의 특징들 - ITWorld Korea', '2019-04-13 17:14:47', 'http://www.itworld.co.kr/t/34/%ED%81%B4%EB%9D%BC%EC%9A%B0%EB%93%9C/111222', 1, 1, ''),
	(2, '구글 클라우드', '2019-04-13 17:14:48', b'0', b'0', 'byline.network', 'https://byline.network/wp-content/uploads/2018/10/google-lee-vp.jpg', '구글의 뒤늦은 클라우드 행보, 무기는 머신러닝', '2019-04-13 17:14:48', 'https://byline.network/2018/10/26-28/', 1, 1, ''),
	(3, 'AWS', '2019-04-13 17:14:48', b'0', b'0', 'byline.network', 'https://byline.network/wp-content/uploads/2018/10/aws-kim-ilho.jpg', 'AWS가 생각하는 엣지컴퓨팅, 그리고 그린그래스(Greengrass) |', '2019-04-13 17:14:48', 'https://byline.network/2018/10/25-30/', 1, 1, ''),
	(4, '오라클 클라우드', '2019-04-13 17:14:48', b'0', b'0', 'www.itworld.co.kr', 'http://files.idg.co.kr/itworld/image/avatar/article/2018/October/erin_hur@idg.co.kr/Post4.JPG', '개발자가 개발에만" 집중할 수 있는 오라클 클라우드만의 특징들 - ITWorld Korea', '2019-04-13 17:14:48', 'http://www.itworld.co.kr/t/34/%ED%81%B4%EB%9D%BC%EC%9A%B0%EB%93%9C/111222', 1, 1, ''),
	(5, 'GCP', '2019-04-13 17:14:48', b'0', b'0', 'www.bloter.net', 'http://www.bloter.net/wp-content/uploads/2018/10/google-cloud-summit_1_crop.jpg', '구글 클라우드의 강점은 AI 가속기 ‘엣지 TPU', '2019-04-13 17:14:48', 'http://www.bloter.net/archives/322908', 1, 1, ''),
	(6, 'TDD', '2019-04-13 17:14:48', b'0', b'1', 'it.chosun.com', 'http://itimg.chosun.com/sitedata/image/201807/29/2018072900674_0.jpg', '[마소 393호] 자바스크립트와 TDD - IT조선 > 기술 > 과학·기술', '2019-04-13 17:14:48', 'http://it.chosun.com/site/data/html_dir/2018/07/29/2018072900674.html', 1, 1, ''),
	(7, 'TDD BDD', '2019-04-13 17:14:48', b'0', b'1', 'www.itworld.co.kr', 'http://files.idg.co.kr/itworld/image/avatar/article/2018/April/just2kill@gmail.com/0_citizen-developers-100694214-large.jpg', 'BDD부터 TDD까지" 다양한 애자일 기법의 장단점 - ITWorld Korea', '2019-04-13 17:14:48', 'http://www.itworld.co.kr/news/109011', 1, 2, ''),
	(8, 'TDD 공부법', '2019-04-13 17:14:48', b'0', b'1', 'medium.com', 'https://cdn-images-1.medium.com/max/1200/1*jFw7ZZMoVcsEYM_fS33DBA.gif', 'TDD(Test Driven Development) 를 연습하면서 참고하기 좋은 팁 10가지', '2019-04-13 17:14:48', 'https://medium.com/@rinae/tdd-test-driven-development-%EB%A5%BC-%EC%97%B0%EC%8A%B5%ED%95%98%EB%A9%B4%EC%84%9C-%EC%B0%B8%EA%B3%A0%ED%95%98%EA%B8%B0-%EC%A2%8B%EC%9D%80-%ED%8C%81-10%EA%B0%80%EC%A7%80-d8cf46ae1806', 1, 2, ''),
	(9, '테스트 주도 개발', '2019-04-13 17:14:48', b'0', b'1', 'medium.com', NULL, '단위 테스트, TDD, BDD의 차이점 – Jason Ryu – Medium', '2019-04-13 17:14:48', 'https://medium.com/@sryu99/%EB%8B%A8%EC%9C%84-%ED%85%8C%EC%8A%A4%ED%8A%B8-tdd-bdd%EC%9D%98-%EC%B0%A8%EC%9D%B4%EC%A0%90-3d25fab5ccb2', 1, 2, ''),
	(10, 'TDD', '2019-04-13 17:14:48', b'0', b'1', 'it.chosun.com', 'http://itimg.chosun.com/sitedata/image/201807/29/2018072900674_0.jpg', '[마소 393호] 자바스크립트와 TDD - IT조선 > 기술 > 과학·기술', '2019-04-13 17:14:48', 'http://it.chosun.com/site/data/html_dir/2018/07/29/2018072900674.html', 1, 2, ''),
	(11, '창문형 투명 TV', '2019-04-13 17:14:48', b'0', b'1', 'www.dt.co.kr', 'http://contents.dt.co.kr/images/201810/2018102602100932056001[1].jpg?var=0134', '창문같은 `투명TV` 나온다...삼성전자 놀라운 신기술 - 디지털타임스', '2019-04-13 17:14:48', 'http://www.dt.co.kr/contents.html?article_no=2018102602100932056001&naver=stand', 1, 2, ''),
	(12, '디자인적 구조', '2019-04-13 17:14:48', b'0', b'1', 'www.etnews.com', 'http://img.etnews.com/photonews/1810/1116026_20181005142131_107_0001.jpg', '[김태형의 디자인 싱킹]<7>디자인 마이 라이프 - 전자신문', '2019-04-13 17:14:48', 'http://www.etnews.com/20181005000119', 1, 3, ''),
	(13, NULL, '2019-04-13 17:14:48', b'0', b'1', 'www.edaily.co.kr', 'http://image.edaily.co.kr/images/Photo/files/NP/S/2018/10/PS18101601323.jpg', '알렉사 UX도 한번에 디자인"..어도비, 음성인식 프로토타이핑 공개', '2019-04-13 17:14:48', 'http://www.edaily.co.kr/news/read?newsId=04296806619373576&mediaCodeNo=257', 1, 3, ''),
	(14, '프로토파이', '2019-04-13 17:14:49', b'0', b'1', 'it.chosun.com', 'http://itimg.chosun.com/sitedata/image/201807/29/2018072900690_0.jpg', '[마소 393호] 일렉트론과 타입스크립트로 만든 프로토파이 - IT조선 > 기술 > 과학·기술', '2019-04-13 17:14:49', 'http://it.chosun.com/site/data/html_dir/2018/07/29/2018072900690.html', 1, 3, ''),
	(15, '프로토파이', '2019-04-13 17:14:49', b'0', b'1', 'www.ditoday.com', NULL, '디자이너를 위한 프로토타이핑 툴, 프로토파이 리뷰 ② | 디아이투데이', '2019-04-13 17:14:49', 'http://www.ditoday.com/articles/articles_view.html?idno=22203', 1, 4, ''),
	(16, 'design thinking', '2019-04-13 17:14:49', b'0', b'1', 'www.etnews.com', 'http://img.etnews.com/photonews/1810/1116026_20181005142131_107_0001.jpg', '[김태형의 디자인 싱킹]<7>디자인 마이 라이프 - 전자신문', '2019-04-13 17:14:49', 'http://www.etnews.com/20181005000119', 1, 4, ''),
	(17, 'User experience', '2019-04-13 17:14:49', b'0', b'1', 'www.edaily.co.kr', 'http://image.edaily.co.kr/images/Photo/files/NP/S/2018/10/PS18101601323.jpg', '알렉사 UX도 한번에 디자인"..어도비, 음성인식 프로토타이핑 공개', '2019-04-13 17:14:49', 'http://www.edaily.co.kr/news/read?newsId=04296806619373576&mediaCodeNo=257', 1, 4, ''),
	(18, 'Design Tool', '2019-04-13 17:14:49', b'0', b'1', 'it.chosun.com', 'http://itimg.chosun.com/sitedata/image/201807/29/2018072900690_0.jpg', '[마소 393호] 일렉트론과 타입스크립트로 만든 프로토파이 - IT조선 > 기술 > 과학·기술', '2019-04-13 17:14:49', 'http://it.chosun.com/site/data/html_dir/2018/07/29/2018072900690.html', 1, 4, ''),
	(19, '', '2019-04-15 13:41:28', b'0', b'0', 'www.google.com', '', 'Google', '2019-04-15 13:41:28', 'http://www.google.com', 1, 16, ''),
	(20, NULL, '2019-04-21 08:39:59', b'0', b'0', 'www.hankookilbo.com', 'http://image.hankookilbo.com/level/2019/04/21/f328d8d8-b426-48bf-b1f1-8a4d2df0b1ae.jpg', '판문점선언 1주년… ‘북한 빠진’ 행사 개최', '2019-04-21 08:39:59', 'https://www.hankookilbo.com/News/Read/201904211690767928?did=PA&dtype=3&dtypecode=1618', 1, 17, ''),
	(21, '', '2019-04-23 16:11:05', b'0', b'0', 'www.naver.com', 'https://s.pstatic.net/static/www/mobile/edit/2016/0705/mobile_212852414260.png', 'NAVER', '2019-04-23 16:11:05', 'http://www.naver.com', 1, 2, ''),
	(22, '', '2019-04-27 11:13:32', b'0', b'0', 'm.tf.co.kr', 'http://img.tf.co.kr/article/home/2019/04/27/201951201556354204.jpg', '트럼프 &quot러시아·중국 고맙다…김정은과는 훌륭한 관계&quot - 더팩트', '2019-04-27 11:13:32', 'http://m.tf.co.kr/read/ptoday/1751778.htm?ref=main_news', 1, 2, ''),
	(23, '', '2019-04-27 11:15:07', b'0', b'0', 'm.sports.naver.com', 'https://imgnews.pstatic.net/image/477/2019/04/27/0000179953_001_20190427195510130.jpg', '[대구 게임노트] \'윌슨 4승+2홈런\' LG, 4연승 질주…삼성 3연패 :: 네이버스포츠', '2019-04-27 11:15:07', 'https://m.sports.naver.com/kbaseball/news/read.nhn?oid=477&aid=0000179953', 1, 22, ''),
	(24, NULL, '2019-04-28 12:02:37', b'0', b'0', 'www.skkuw.com', 'http://www.skkuw.com/image/logo/snslogo_20180212032513.jpg', '선배 기자의 생생한 고백, 그 현장을 담다 - 성대신문', '2019-04-28 12:02:37', 'http://www.skkuw.com/news/articleView.html?idxno=11412', 1, 1, '');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;

-- 테이블 clipping.user 구조 내보내기
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_key` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ipt3vyu673wxeem2v5huhi2mh` (`device_key`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- 테이블 데이터 clipping.user:~1 rows (대략적) 내보내기
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `device_key`) VALUES
	(1, '#$9981');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
