---------------------------김민님 작성---------------------------
-- 사용자 테이블 
create table tbl_member    
(userseq            number         not null                   -- 사용자번호
,userid             varchar2(40)   not null                   -- 아이디
,pwd                varchar2(200)  not null                   -- 비밀번호 (SHA-256 암호화 대상)
,busiNum            number         not null                   -- 사업자등록번호
,email              varchar2(200)  not null                   -- 이메일 (AES-256 암호화/복호화 대상)
,mobile             varchar2(200)  not null                   -- 연락처 (AES-256 암호화/복호화 대상) 
,postcode           number         not null                   -- 우편번호
,address            varchar2(200)  not null                   -- 주소
,detailaddress      varchar2(200)  not null                   -- 상세주소
,extraaddress       varchar2(200)                             -- 참고항목
,company            Nvarchar2(30)  not null                   -- 사명    
,registerday        date         default sysdate not null     -- 가입일자 
,lastpwdchangedate  date         default sysdate not null     -- 마지막으로 암호를 변경한 날짜  
,status             Nvarchar2(4) default '가입중' not null     -- 탈퇴유무    
,idle               Nvarchar2(4) default '활동중' not null     -- 휴면유무   
,ban                Nvarchar2(4) default '정상'   not null     -- 정지유무
,constraint PK_tbl_member_userid primary key(userid)
,constraint UQ_tbl_member_userseq unique(userseq)
,constraint UQ_tbl_member_email  unique(email)
,constraint UQ_tbl_member_busiNum  unique(busiNum)
,constraint CK_tbl_member_status check( status in('가입중','탈퇴') )
,constraint CK_tbl_member_idle check( idle in('활동중','휴면중') )
,constraint CK_tbl_member_ban check( ban in('정상','정지') )
);

create sequence seq_userseq;

-- 로그인기록 테이블
create table tbl_loginhistory
(historyno   number               not null         -- 로그인기록번호
,fk_userid   varchar2(40)         not null         -- 회원아이디
,logindate   date default sysdate not null         -- 로그인되어진 접속날짜및시간
,clientip    varchar2(20)         not null         -- 클라이언트ip
,constraint  PK_tbl_loginhistory_historyno primary key(historyno)
,constraint  FK_tbl_loginhistory_fk_userid foreign key(fk_userid) references tbl_member(userid)
);

create sequence seq_historyno;

-- 위시리스트 테이블
create table tbl_wishlist
(fk_userid   varchar2(40) not null   -- 회원아이디
,fk_pnum     number       not null   -- 판매번호
,constraint  PK_tbl_wishlist primary key(fk_userid, fk_pnum)
,constraint  FK_tbl_wishlist_fk_userid foreign key(fk_userid) references tbl_member(userid)
,constraint  FK_tbl_wishlist_fk_pnum foreign key(fk_pnum) references tbl_product(pnum) ON DELETE CASCADE
);

-- 문의사항 테이블
create table tbl_qna
(qnanum     number         not null         -- 문의번호
,fk_pnum    number         not null         -- 판매번호
,fk_userid  varchar2(40)   not null         -- 아이디
,qwritedate date           default sysdate not null  -- 작성일
,qcontents  Nvarchar2(600) not null         -- 내용
,islock     Nvarchar2(10)  default '비공개' not null  -- 잠금여부
,qanswer    Nvarchar2(600)                  -- 답글내용
,constraint PK_tbl_qna_qnanum primary key(qnanum)
,constraint FK_tbl_qna_fk_pnum foreign key(fk_pnum) references tbl_product(pnum) ON DELETE CASCADE
,constraint FK_tbl_qna_fk_userid foreign key(fk_userid) references tbl_member(userid)
,constraint CK_tbl_qna_islock check( islock in('비공개','공개')) 
);

create sequence seq_qnanum;

---------------------------정영준님 작성---------------------------
-- 주문 테이블
create table tbl_order
(onum            number            not null                    -- 주문번호
,fk_userid       VARCHAR2(40)      not null                       -- 아이디(foreign key)
,orderstatus     NVARCHAR2(10)     default '접수' not null         -- 주문 상태
,orderdate       DATE              default sysdate not null       -- 주문시간
,arrivaldate     DATE                                             -- 도착예정일
,constraint PK_tbl_order_onum primary key(onum)
,constraint FK_tbl_order_fk_userid foreign key(fk_userid) references tbl_member(userid) 
,constraint CK_tbl_order_orderstatus check (orderstatus IN ('취소', '접수', '상품 준비 중', '출고 완료'))     -- 주문상태는 "취소", "접수", "상품 준비 중", "출고 완료" 만 가능
);

create sequence seq_onum;

-- 주문 상세 테이블
create table tbl_orderdetail
(odnum           number             not null                        -- 주문상세번호
,fk_onum         number             not null                        -- 주문번호(foreign key)
,fk_snum         number             not null                        -- 제품번호(foreign key)
,oqty            number             not null                        -- 수량
,constraint PK_tbl_orderdetail_odnum primary key(odnum)
,constraint FK_tbl_orderdetail_fk_onum foreign key(fk_onum) references tbl_order(onum) 
,constraint FK_tbl_orderdetail_fk_snum foreign key(fk_snum) references tbl_stock(snum) 
);

create sequence seq_odnum;

-- 리뷰 테이블
create table tbl_review
(fk_odnum         number                not null                                      -- 주문상세번호(PK,foreign key)
,rating           number(1)             default 5  not null                           -- 별점
,rcontents        nvarchar2(300)                                                      -- 내용
,rwritedate       DATE                  default sysdate not null                      -- 작성일
,rimage           VARCHAR2(100)                                                       -- 이미지
,constraint PK_tbl_review_odnum primary key(fk_odnum)
,constraint fk_tbl_review_fk_odnum foreign key (fk_odnum) references tbl_orderdetail(odnum)
,constraint CK_tbl_review_rating check (rating IN (1, 2, 3, 4, 5))                   -- 별점은 1, 2, 3, 4, 5 만 가능
);

-- 장바구니 테이블
create table tbl_cart
(fk_userid         VARCHAR2(40)            not null                            -- 아이디(PK,foreign key)
,fk_snum           number                  not null                            -- 제품번호(PK,foreign key)
,cqty              number                  not null                            -- 수량
,constraint PK_tbl_cart_fk_userid_fk_snum primary key(fk_userid,fk_snum)
,constraint fk_tbl_cart_fk_userid foreign key (fk_userid) references tbl_member(userid)
,constraint fk_tbl_cart_fk_snum foreign key (fk_snum) references tbl_stock(snum)
,constraint CK_tbl_cart_cqty check (cqty >= 1)                                        -- 수량은 1개 이상
);




---------------------------김기찬님 작성---------------------------
show user;

show con_name;

-- 카테고리 테이블 
CREATE TABLE TBL_CATEGORY (
    CATENUM     NUMBER,
    CATENAME    NVARCHAR2(100) NOT NULL,
    CONSTRAINT PK_TBL_CATEGORY_CATENUM PRIMARY KEY(CATENUM)
);
  
CREATE SEQUENCE SEQ_CATENUM;
  
-- 카탈로그 테이블 
CREATE TABLE TBL_CATALOGUE (
    PNAME       NVARCHAR2(100),
    FK_CATENUM  NUMBER,
    PURPRICE    NUMBER NOT NULL,
    REGPRICE    NUMBER NOT NULL,
    SALEPRICE   NUMBER NOT NULL,
    BRAND       NVARCHAR2(20) NOT NULL,
    CONSTRAINT PK_TBL_CATALOGUE_PNAME PRIMARY KEY(PNAME),
    CONSTRAINT FK_TBL_CATALOGUE_FK_CATENUM FOREIGN KEY(FK_CATENUM) REFERENCES TBL_CATEGORY(CATENUM) ON DELETE SET NULL
);
SELECT * FROM tbl_catalogue;

-- 판매중 상품
CREATE TABLE TBL_PRODUCT (
    PNUM        NUMBER,
    FK_PNAME    NVARCHAR2(100) NOT NULL,
    PIMAGE1     VARCHAR2(100) NOT NULL,
    PIMAGE2     VARCHAR2(100) NOT NULL,
    PCONTENT    VARCHAR2(4000),
    DELIVERYFEE NUMBER NOT NULL,
    WARRANTY_SYSTEMFILENAME VARCHAR2(200) NOT NULL,
    WARRANTY_ORIGINFILENAME VARCHAR2(200) NOT NULL,
    CONSTRAINT PK_TBL_PRODUCT_PNUM PRIMARY KEY(PNUM),
    CONSTRAINT FK_TBL_PRODUCT_FK_PNAME FOREIGN KEY(FK_PNAME) REFERENCES TBL_CATALOGUE(PNAME) ON DELETE CASCADE
);

create sequence SEQ_PNUM;

-- 추가 이미지
CREATE TABLE TBL_PRODUCT_IMAGE (
    IMGNUM      NUMBER,
    FK_PNUM     NUMBER NOT NULL,
    IMAGE_NAME  VARCHAR2(100) NOT NULL,
    CONSTRAINT PK_TBL_PRODUCT_IMAGE_IMGNUM PRIMARY KEY(IMGNUM),
    CONSTRAINT FK_TBL_PRODUCT_IMAGE_FK_PNUM FOREIGN KEY(FK_PNUM) REFERENCES TBL_PRODUCT(PNUM) ON DELETE CASCADE
);
    
CREATE SEQUENCE SEQ_IMGNUM;

-- 재고
CREATE TABLE TBL_STOCK (
    SNUM        NUMBER,
    FK_PNAME    NVARCHAR2(100) NOT NULL,
    SSIZE       NUMBER NOT NULL,
    COLOR       NVARCHAR2(10) NOT NULL,
    SQTY        NUMBER NOT NULL,
    CONSTRAINT PK_TBL_STOCK_SNUM PRIMARY KEY(SNUM),
    CONSTRAINT FK_TBL_STOCK_FK_PNAME FOREIGN KEY(FK_PNAME) REFERENCES TBL_CATALOGUE(PNAME) ON DELETE CASCADE
);

CREATE SEQUENCE SEQ_SNUM;

-- 발주 상세   
CREATE TABLE TBL_PURDETAIL (
    PURDETAILNUM    NUMBER,
    FK_PURNUM       NUMBER NOT NULL,
    FK_SNUM         NUMBER NOT NULL,
    PURQTY          NUMBER NOT NULL,
    CONSTRAINT PK_TBL_PURDETAIL_PURDETAILNUM PRIMARY KEY(PURDETAILNUM),
    CONSTRAINT FK_TBL_PURDETAIL_FK_PURNUM FOREIGN KEY(FK_PURNUM) REFERENCES TBL_PURCHASE(PURNUM) ON DELETE CASCADE,
    CONSTRAINT FK_TBL_PURDETAIL_FK_SNUM FOREIGN KEY(FK_SNUM) REFERENCES TBL_STOCK(SNUM)    
);

CREATE SEQUENCE SEQ_PURDETAILNUM;

---------------------------육무군님 작성---------------------------
-- 공급업체
create table tbl_supplier
(supname     NVARCHAR2(100) not null
,sbusinum    number not null
,ceo         NVARCHAR2(10) not null
,smobile     VARCHAR(200) not null 
,semail      VARCHAR(200) not null   
,constraint PK_tbl_supplier_supname primary key(supname)
,constraint UQ_tbl_supplier_sbusinum unique(sbusinum)
,constraint UQ_tbl_supplier_smobile unique(smobile)
,constraint UQ_tbl_supplier_semail unique(semail)
);

-- 발주
create table tbl_purchase
(purnum      NUMBER not null
,fk_supname  NVARCHAR2(100) not null
,purtime     DATE default sysdate not null
,purdeadline DATE not null 
,instock     NVARCHAR2(10) default '미입고' not null   
,constraint  PK_tbl_purchase_purnum primary key(purnum)
,constraint  CK_tbl_purchase_instock check( instock in('입고','미입고') )
);

CREATE SEQUENCE SEQ_PURNUM;

-- 공지사항
create table tbl_notice
(nnum       NUMBER not null
,nsubject   NVARCHAR2(100) not null
,ncontents  NVARCHAR2(1000) not null
,nwritedate DATE default sysdate not null 
,nimage     VARCHAR2(100)  
,constraint PK_tbl_notice_nnum primary key(nnum)
);

CREATE SEQUENCE SEQ_NNUM;

-- FAQ
create table tbl_faq
(fnum       NUMBER not null
,fsubject   NVARCHAR2(100) not null
,fcontents  NVARCHAR2(1000) not null
,fcategory  NVARCHAR2(100) not null  
,constraint PK_tbl_faq_fnum primary key(fnum)
,constraint CK_tbl_faq_fcategory check(fcategory in ('가입/탈퇴', '정보변경', '결제', '주문', '취소', '상품정보', '배송'))
);

CREATE SEQUENCE SEQ_FNUM;


SELECT * FROM tbl_catalogue
WHERE pname='핸드볼 스페지알 로우 프로';


SELECT * FROM tbl_supplier;

SELECT P.purnum, to_char(P.purtime,'yyyy-mm-dd') AS purtime, 
       S.supname, S.sbusinum, S.ceo, S.smobile, S.semail 
FROM tbl_purchase P JOIN tbl_supplier S ON P.fk_supname = S.supname 
WHERE P.purnum = 1 ;

select P.purdetailnum, P.purqty, P.purqty, P.purdprice, 
		                   P.purqty * P.purdprice AS amount,
       S.color, S.ssize, S.fk_pname
from tbl_purdetail P INNER JOIN tbl_stock S
ON P.fk_snum = S.snum
where fk_purnum = 1 
order by purdetailnum 

SELECT * FROM tbl_purdetail