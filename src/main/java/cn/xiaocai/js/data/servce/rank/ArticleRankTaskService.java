package cn.xiaocai.js.data.servce.rank;

import cn.xiaocai.js.data.constans.JSConstants;
import cn.xiaocai.js.data.persistence.entity.ArticleRank;
import cn.xiaocai.js.data.persistence.entity.SpiderLog;
import cn.xiaocai.js.data.persistence.repostory.ArticleRankRepository;
import cn.xiaocai.js.data.persistence.repostory.SpiderLogRepository;
import cn.xiaocai.js.data.persistence.service.SpiderLogService;
import cn.xiaocai.js.data.uitl.DateUtil;
import cn.xiaocai.js.data.uitl.PinYinSmallUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/7/30 23:37
 * @version: v1.0
 */
@Slf4j
@Service
public class ArticleRankTaskService {

    @Autowired
    private SpiderLogService spiderLogService;
    @Autowired
    private ArticleRankRepository articleRankRepository ;
    @Autowired
    private SpiderLogRepository spiderLogRepository ;


    public String catchRankData(String start, String end) throws UnirestException {

        String yyyyMMdd = start ;
        if (StringUtils.hasText(end)){
            LocalDate yyyyMMdd1 = LocalDate.parse(start, DateTimeFormatter.BASIC_ISO_DATE);
            LocalDate yyyyMMdd2 = LocalDate.parse(end, DateTimeFormatter.BASIC_ISO_DATE);
            List<LocalDate> localDates = DateUtil.getsAllDatesInTheDateRange(yyyyMMdd1, yyyyMMdd2);
            log.info("抓取日期区间开始 " + yyyyMMdd1.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            log.info("抓取日期区间结束 " + yyyyMMdd2.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            for (LocalDate billDate : localDates){
                catchRankData(billDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            }
            log.info("日期区间{} 到 {} 的数据抓取完成！", yyyyMMdd1, yyyyMMdd2);
        }else{
            yyyyMMdd = start ;
            List<SpiderLog> list = spiderLogRepository.selectByRankDateRankType(yyyyMMdd, "AR");

            if (!CollectionUtils.isEmpty(list)){
                SpiderLog spiderLog = list.get(0);
                if ("S".equals(spiderLog.getStatus())) {
                    log.info("日期 " + yyyyMMdd + " 的排名已经抓取");
                    return "ALREADY";
                }else {
                    log.info("日期 " + yyyyMMdd + " 的排名上次抓取失败");
                }
            }
            String result = catchAndSaveRankData(yyyyMMdd);

            spiderLogService.saveLog(yyyyMMdd, result,"AR");
            //list.stream().forEach(System.out::println);
        }

        return  "SUCCESS";
    }


    public String catchRankData(String date) throws UnirestException {
        String yyyyMMdd = date;
        if(!StringUtils.hasText(yyyyMMdd)){
            LocalDate localDate = LocalDate.now().minusDays(1);
            yyyyMMdd = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }

        Long dateCount = articleRankRepository.countByDate(yyyyMMdd);
        log.info(" 本次抓取  yyyyMMdd = " +yyyyMMdd +"数据");
        if (dateCount> 0 && dateCount > 100){
            Integer delCount = articleRankRepository.deleteByDate(yyyyMMdd);
            log.info("本次删除无效数据：{} " +delCount, yyyyMMdd);
            SpiderLog spiderLog = new SpiderLog();
            spiderLog.setRank_type("AR");
            spiderLog.setRank_date(yyyyMMdd);
            spiderLogRepository.delete(spiderLog);
            log.info("本次删除日志数据：{} " , yyyyMMdd);
        }

        log.info("本次抓取的上榜日期为" + yyyyMMdd);
        List<SpiderLog> list = spiderLogRepository.selectByRankDateRankType(yyyyMMdd, "AR");

        if (!CollectionUtils.isEmpty(list)){
            SpiderLog spiderLog = list.get(0);
            if ("S".equals(spiderLog.getStatus())) {
                log.info("日期 " + yyyyMMdd + " 的排名已经抓取");
                return "ALREADY";
            }else {
                log.info("日期 " + yyyyMMdd + " 的排名抓取失败");
            }
        }
        String result = catchAndSaveRankData(yyyyMMdd);

        spiderLogService.saveLog(yyyyMMdd, result,"AR");
        //list.stream().forEach(System.out::println);
        return  "SUCCESS";
    }


    public String catchAndSaveRankData(String yyyyMMdd) throws UnirestException {
        log.info("开始抓取 " + yyyyMMdd +" 的文章排名数据！");
        GetRequest getRequest = Unirest.get(JSConstants.RANK_URL_PRE +yyyyMMdd);
        HttpResponse<String> stringResult = getRequest.asString();
        int status = stringResult.getStatus();
        if (status!=200 ){
            log.info("---抓取排名接口异常");
            return "Fail|"+status ;
        }
        String body = stringResult.getBody();
        JSONObject responseJson = JSONObject.parseObject(body);
        String respDate = (String) responseJson.get("date");
        if (!yyyyMMdd.equals(respDate)){
            log.info("---请求日期与响应日期不符！！");
            return "Fail";
        }
        JSONArray notes = (JSONArray) responseJson.get("notes");
        List<ArticleRank> list = JSONObject.parseArray(notes.toString(),  ArticleRank.class);
        long size = list.size() ;
        if (size ==0){
            return "Fail|没有数据";
        }
        ArticleRank articleRank = null ;
        for(int index = 0 ; index < size ; index ++) {
            articleRank = list.get(index);
            articleRank.setRank_no(index+1);
            articleRank.setRank_date(yyyyMMdd);
            if (articleRank.getTitle()==null){
                articleRank.setTitle("该文章不可访问");
            }
            if (articleRank.getAuthor_nickname()==null){
                articleRank.setAuthor_nickname("该用户不在简书");
            }

            articleRank.setAuthor_nickname_py(PinYinSmallUtil.getPinYin(articleRank.getAuthor_nickname()));
            //System.out.println(articleRank.toString());
        }

        articleRankRepository.saveAll(list);
        log.info("排名数据保存成功！");
        return "SUCCESS";
    }





}
