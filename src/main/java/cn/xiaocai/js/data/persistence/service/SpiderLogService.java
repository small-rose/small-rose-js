package cn.xiaocai.js.data.persistence.service;

import cn.xiaocai.js.data.persistence.entity.SpiderLog;
import cn.xiaocai.js.data.persistence.repostory.SpiderLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/8/14 15:55
 * @version: v1.0
 */
@Service
public class SpiderLogService {

    @Autowired
    private SpiderLogRepository spiderLogRepository ;

    public SpiderLog saveLog(String yyyyMMdd, String result, String rankType){
        //保存结果

        List<SpiderLog> list = spiderLogRepository.selectByRankDateRankType(yyyyMMdd, rankType);
        SpiderLog spiderLog = null;
        if (CollectionUtils.isEmpty(list)){
            spiderLog = new SpiderLog();
            spiderLog.setRank_date(yyyyMMdd);
            spiderLog.setRank_type(rankType);
        }else{
            spiderLog = list.get(0);

        }
        spiderLog.setExec_time(new Date());
        if (result.startsWith("SUCCESS")){
            spiderLog.setResult("SUCCESS");
            spiderLog.setStatus("S");
        }else{
            spiderLog.setResult(result);
            spiderLog.setStatus("F");
        }
        spiderLog =  spiderLogRepository.save(spiderLog);
        return spiderLog ;
    }
}
