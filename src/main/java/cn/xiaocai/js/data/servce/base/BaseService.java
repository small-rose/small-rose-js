package cn.xiaocai.js.data.servce.base;

import cn.xiaocai.js.data.constans.JSConstants;
import cn.xiaocai.js.data.uitl.NetUtil;
import kong.unirest.UnirestException;
import lombok.Data;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/8/14 16:04
 * @version: v1.0
 */
@Data
public abstract class BaseService {

    protected String subjectId ;

    protected Map<String, String> headers ;
    protected Map<String,String> cookies;


    protected void loadHeaderCookies(){
        Connection.Response response = NetUtil.getResponse(JSConstants.REFERURL);
        headers = response.headers();
        cookies = response.cookies();

    }
    /**
     * 取专题URL
     * @return
     */
    protected String getSubjectUrl(){
        return JSConstants.URL_ZHUAN_TI_REG.replace(JSConstants.REPLACE_ZT_KEY, subjectId);
    }

    protected Document getSubjectPage(String url){
        Document document = NetUtil.getDocument(url, JSConstants.REFERURL, headers, cookies);
        return document ;
    }


    protected abstract void start(String subjectId, int start, int end) throws UnirestException;

    protected abstract <T> List<T> parseData(Document zhuanTi, String subjectId) throws UnirestException;


    /**
     * 取 评论 URL
     * @return
     */
    protected String getCommentUrl(String noteId){
        return JSConstants.URL_COMMENTS_REG.replace(JSConstants.REPLACE_NOTE_KEY,  noteId);
    }
    /**
     * 取 评论2 URL
     * @return
     */
    protected String getComment2Url(String noteId, String commentCount){
        String replace = JSConstants.URL_COMMENTS_2_REG.replace(JSConstants.REPLACE_NOTE_KEY, noteId);
        replace = replace.replace(JSConstants.REPLACE_COUNT_KEY, commentCount);
        return replace;
    }
    /**
     * 取 赞赏 URL
     * @return
     */
    protected String getRewardUrl(String noteId){
        return JSConstants.URL_REWARD_REG.replace(JSConstants.REPLACE_NOTE_KEY, noteId);
    }
}
