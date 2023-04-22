
-- auto-generated definition
create table js_article_rank
(
    id                 bigint       not null
        primary key comment '主键ID',
    author_avatar      varchar(255) null comment '头像地址',
    author_fp          varchar(255) null comment '作者收益',
    author_nickname    varchar(255) null comment '作者昵称',
    author_nickname_py varchar(255) null comment '作者昵称拼音',
    fp                 varchar(255) null comment '总收益',
    rank_date          varchar(255) null comment '上榜日期',
    rank_no            int          null comment '上榜排名',
    slug               varchar(255) null comment '用户slug',
    title              varchar(255) null comment '文章标题',
    voter_fp           varchar(255) null comment '点赞收益'
)ENGINE=InnoDB  comment '上榜数据';

create table js_spider_log
(
    id            bigint       not null
        primary key,
    exec_time     datetime     null comment '执行时间',
    rank_date     varchar(255) null comment '排名日期',
    rank_type     varchar(255) null comment '排名类型',
    result        varchar(255) null comment '结果',
    status        varchar(255) null comment '执行状态',
    update_result varchar(255) null  comment '执行结果'
)ENGINE=InnoDB comment '上榜抓取日志';


-- auto-generated definition
create table js_user_info
(
    id           bigint       not null comment '主键ID'
        primary key ,
    nick_name    varchar(255) null comment '用户昵称',
    nick_name_py varchar(255) null comment '用户昵称拼音',
    precommender int          not null comment '是否推荐人1-是0-否',
    slug         varchar(255) null comment '用户slug',
    slug_url     varchar(255) null comment '主页地址'
)ENGINE=InnoDB comment '简书用户表';

-- 专题表
create table js_subject_info
(
    id           bigint       not null comment '主键ID'
        primary key,
    subject_slug varchar(255) null comment '专题slug',
    title        varchar(255) null comment '专题名称',
    title_py     varchar(255) null comment '专题名称拼音'
)ENGINE=InnoDB comment '简书专题信息表';

-- auto-generated definition
drop table  js_subject_data_info ;
create table js_subject_data_info
(
    id               bigint       not null comment '主键ID'
        primary key,
    comments         longtext     null comment '评论数据',
    lp_reward        int   default  0  not null comment '理事会赞赏',
    nick_name        varchar(255) null comment '作者昵称',
    recommender      varchar(255) null comment '推荐人',
    recommender_slug varchar(255) null comment '推荐人slug',
    rewards          longtext     null comment '赞赏数据',
    shou_date        varchar(10)     null comment '收录日期',
    update_time      datetime     null comment '更新时间',
    subject_id       varchar(255) null comment '专题slug',
    title            varchar(255) null comment '文章标题',
    user_slug        varchar(255) null comment '用户slug',
    wen_id           varchar(255) null comment '文章id',
    wen_slug         varchar(255) null comment '文章slug',
    wen_url          varchar(255) null comment '文章地址'
)ENGINE=InnoDB comment '简书专题数据表';

