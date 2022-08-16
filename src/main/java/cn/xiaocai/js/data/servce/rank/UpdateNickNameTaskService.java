package cn.xiaocai.js.data.servce.rank;

import cn.xiaocai.js.data.constans.JSConstants;
import cn.xiaocai.js.data.persistence.entity.ArticleRank;
import cn.xiaocai.js.data.persistence.entity.SpiderLog;
import cn.xiaocai.js.data.persistence.repostory.ArticleRankRepository;
import cn.xiaocai.js.data.persistence.repostory.SpiderLogRepository;
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
public class UpdateNickNameTaskService {

    @Autowired
    private ArticleRankRepository articleRankRepository ;
    @Autowired
    private SpiderLogRepository spiderLogRepository ;


    public String update(String start, String end) throws UnirestException {

        String yyyyMMdd = start ;
        if (StringUtils.hasText(end)){
            LocalDate yyyyMMdd1 = LocalDate.parse(start, DateTimeFormatter.BASIC_ISO_DATE);
            LocalDate yyyyMMdd2 = LocalDate.parse(end, DateTimeFormatter.BASIC_ISO_DATE);
            List<LocalDate> localDates = DateUtil.getsAllDatesInTheDateRange(yyyyMMdd1, yyyyMMdd2);
            log.info("抓取更新日期区间开始 " + yyyyMMdd1.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            log.info("抓取更新日期区间结束 " + yyyyMMdd2.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            for (LocalDate billDate : localDates){
                catchAndSaveRankData(billDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            }
            log.info("日期区间{} 到 {} 的数据更新完成！", yyyyMMdd1, yyyyMMdd2);
        }else{
            yyyyMMdd = start ;
            catchAndSaveRankData(yyyyMMdd);
            //list.stream().forEach(System.out::println);
        }

        return  "SUCCESS";
    }


    public String updateNickName(String date) throws UnirestException {

        log.info("开始准备更新昵称的排名数据！");
        List<SpiderLog> list = spiderLogRepository.selectByRankTypeUpdateResult("AR");

        if (!CollectionUtils.isEmpty(list)){

            for (SpiderLog log : list) {
                catchAndSaveRankData(log.getRank_date());
            }
            log.info("更新昵称的排名数据完成！");
        }
        //list.stream().forEach(System.out::println);
        return  "SUCCESS";
    }


    private String catchAndSaveRankData(String yyyyMMdd) throws UnirestException {

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
            String newNickName = articleRank.getAuthor_nickname();
            if (!StringUtils.hasText(articleRank.getSlug())){
                continue;
            }
            List<ArticleRank> slugList = null ;

            try {
                slugList = articleRankRepository.findBySlug(articleRank.getSlug());

            }catch (Exception e){
                System.out.println(" error " + articleRank.getSlug());
            }

            if (!CollectionUtils.isEmpty(slugList)){
                for (ArticleRank rank : slugList) {

                    if (rank.getAuthor_nickname().equals(newNickName)){
                        continue;
                    }
                    log.info("发现新的昵称变化 newNickName: " + newNickName +" , oldNickName : "+rank.getAuthor_nickname());

                    //rank.setAuthor_nickname(newNickName);
                    String pinyinString = PinYinSmallUtil.getPinYin(newNickName);
                    //rank.setAuthor_nickname_py(pinyinString);
                    articleRankRepository.updateNickNameBySlug(rank.getSlug(), newNickName, pinyinString);
                }

            }

        }

        log.info(yyyyMMdd+"排名数据更新成功！");
        return "SUCCESS";
    }

}
