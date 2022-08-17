package cn.xiaocai.js.data.task;

import cn.xiaocai.js.data.servce.rank.ArticleRankTaskService;
import cn.xiaocai.js.data.servce.rank.UpdateNickNameTaskService;
import cn.xiaocai.js.data.servce.subject.SubjectDataInfoTaskService;
import kong.unirest.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/7/30 23:36
 * @version: v1.0
 */
@Service
@Slf4j
public class AppTask {

    @Autowired
    private ArticleRankTaskService articleRankTaskService ;
    @Autowired
    private UpdateNickNameTaskService updateNickNameTaskService ;
    @Autowired
    private SubjectDataInfoTaskService subjectDataInfoTaskService;


    @Scheduled(cron = "0 0/5 0-1 * * ? ")
    public void shangHaiPlatformTask(){

        log.info("定时抓取文章排名任务开始执行！！");
        try {
            articleRankTaskService.catchRankData(null);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        log.info("定时抓取文章排名任务执行完成！！");
    }

    //@Scheduled(cron = "0 0 0/1 * * ? ")
    @Scheduled(cron = "0 0/25 * * * ? ")
    public void updateNameTask(){

        log.info("定时更新昵称任务开始执行！！");
        try {
            updateNickNameTaskService.updateNickName(null);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        log.info("定时更新昵称任务执行完成！！");
    }


    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void jsLpTask(){

        log.info("简书专题[理事会点赞汇总]任务开始执行！！");
        try {
            //每天爬取100条记录
            subjectDataInfoTaskService.start("f61832508891",1,10);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        log.info("简书专题[理事会点赞汇总]任务执行完成！！");
    }
}
