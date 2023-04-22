package cn.xiaocai.js.data.servce.subject;

import cn.xiaocai.js.data.persistence.entity.JUserInfo;
import cn.xiaocai.js.data.persistence.entity.SubjectDataInfoEntity;
import cn.xiaocai.js.data.persistence.service.JUserInfoService;
import cn.xiaocai.js.data.persistence.service.SubjectDataInfoService;
import cn.xiaocai.js.data.search.parsevo.CommentVO;
import cn.xiaocai.js.data.search.parsevo.JsUserVO;
import cn.xiaocai.js.data.servce.base.BaseService;
import cn.xiaocai.js.data.uitl.PinYinSmallUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/8/14 15:08
 * @version: v1.0
 */
@Slf4j
@Service
public class SubjectDataInfoTaskService extends BaseService {
    @Autowired
    private JUserInfoService jUserInfoService;
    @Autowired
    private SubjectDataInfoService subjectDataInfoService;

    private List<String> RECOMMENDER_slug = new ArrayList<>();
    private List<String> RECOMMENDER_name = new ArrayList<>();

    public void start(String subjectId, int start, int end) throws UnirestException {
        setSubjectId(subjectId);
        loadCommenderList();
        loadHeaderCookies();


        List<SubjectDataInfoEntity> listTotal = new ArrayList<>(200);
        Document document ;
        for (int i = start; i <= end; i++) {
            String tmpUrl = getSubjectUrl()+i ;
            document = getSubjectPage(tmpUrl);
            List<SubjectDataInfoEntity> list2 = parseData(document, subjectId);
            listTotal.addAll(list2);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("==========================WYH TASK GAME OVER ======================================");
        System.out.println(listTotal.size());

        clearCommenderList();
    }


    @Override
    protected List<SubjectDataInfoEntity> parseData(Document document, String subjectId) throws UnirestException {
        List<SubjectDataInfoEntity> list = new ArrayList<>(20);
        Elements cList = document.select("#list-container > ul > li");
        System.out.println(" clist " + cList.size());
        for (Element c :cList){
            String noteId = c.attr("data-note-id");
            String title = c.select(".title").text();
            String addr = c.select(".title").attr("abs:href");
            String nickname = c.select(".nickname").text();
            String userMain = c.select(".nickname").attr("abs:href");


            String abstractDesc = c.select(".abstract").text();
            String commentCount = c.select(".meta > a").get(1).text();

            //String commentUrl = getCommentUrl(noteId);//评论只取精彩评论10条
            //GetRequest getRequest = Unirest.get(commentUrl);
            //HttpResponse<String> stringResult = getRequest.asString();
            //int status = stringResult.getStatus();
            //System.out.println("Status: " + status);
            //System.out.println("StatusText: " +stringResult.getStatusText());
            //System.out.println("Headers: " +stringResult.getHeaders());
            //String body = stringResult.getBody();
            //JSONObject responseJson = JSONObject.parseObject(body);

            System.out.println("=========================================");
            System.out.println("id  = " + noteId);
            System.out.println("title = " + title);
            System.out.println("nickname = " + nickname);
            System.out.println("abstractDesc = " + abstractDesc);
            String commentJson = null ;
            JSONObject responseJson = null ;
            if (!"0".equals(commentCount) ){
                String commentUrl2 = getComment2Url(noteId, commentCount);//评论只取精彩评论10条
                GetRequest getRequest2 = Unirest.get(commentUrl2);
                HttpResponse<String> stringResult2 = getRequest2.asString();
                String body2 = stringResult2.getBody();
                responseJson = JSONObject.parseObject(body2);
                commentJson = responseJson.toJSONString();
                System.out.println("commentJson = " + commentJson);
            }

            System.out.println("commentJson = " + commentJson);
            String _reward = getRewardUrl(noteId);
            GetRequest request2 = Unirest.get(_reward);
            HttpResponse<String> response2 = request2.asString();
            JSONObject responseJson2 = JSONObject.parseObject(response2.getBody());
            System.out.println("rewardJson = " + responseJson2.toJSONString());
            System.out.println("=========================================");
            SubjectDataInfoEntity wenStory = new SubjectDataInfoEntity();
            wenStory.setWenId(noteId);
            wenStory.setWenUrl(addr);
            wenStory.setWenSlug(addr.substring(addr.lastIndexOf("/")+1, addr.length()-1));
            wenStory.setTitle(title);
            wenStory.setNickName(nickname);
            wenStory.setComments(commentJson);
            wenStory.setRewards(responseJson2.toJSONString());

            wenStory.setUserSlug(userMain.substring(userMain.lastIndexOf("/")+1,userMain.length()-1));
            JsUserVO jsUserVO = getReCommender(responseJson);
            if (jsUserVO!=null) {
                wenStory.setRecommender(jsUserVO.getNickname());
                wenStory.setRecommenderSlug(jsUserVO.getSlug());
            }
            wenStory.setUpdateTime(new Date());
            wenStory.setShouDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            wenStory.setSubjectId(subjectId);
            wenStory.setShouTime(new Date());
            subjectDataInfoService.saveData(wenStory);

            JUserInfo user = new JUserInfo();

            user.setSlug_url(userMain);
            user.setSlug(userMain.substring(userMain.lastIndexOf("/")+1,userMain.length()-1));
            user.setNick_name(nickname);
            user.setNick_name_py(PinYinSmallUtil.getPinYin(nickname));
            jUserInfoService.addCheckUser(user);

            list.add(wenStory);
        }
        return list;
    }

    void  loadCommenderList(){
        List<JUserInfo> commenderList = jUserInfoService.findCommenderList();
        for (JUserInfo user : commenderList) {
            RECOMMENDER_slug.add(user.getSlug());
            RECOMMENDER_name.add(user.getNick_name());
        }
        log.info("加载候选推荐人："+RECOMMENDER_name.size() +" - "+ RECOMMENDER_slug.size());
    }
    void  clearCommenderList(){
        RECOMMENDER_slug.clear();
        RECOMMENDER_name.clear();
    }

    private JsUserVO getReCommender(JSONObject comments) {

        if (StringUtils.isEmpty(comments) || comments.toJSONString().contains("error")){
            //没有精彩评论
            return null;
        }
        List<String> keys1 = Arrays.asList("伯乐","编辑","理事会");
        List<String> keys2 = Arrays.asList("推荐","特别推荐","推荐语");
        JSONArray jsonArray = (JSONArray) comments.get("comments");
        List<CommentVO> list = JSONObject.parseArray(jsonArray.toString(),  CommentVO.class);
        String compiledContent = "";
        for (CommentVO commentVO : list){
            compiledContent = commentVO.getCompiled_content();
            final String finalCompiledContent = StringUtils.hasText(compiledContent)? compiledContent:"";


            System.out.println(finalCompiledContent);
            boolean b0 = RECOMMENDER_name.stream().anyMatch(n -> finalCompiledContent.contains(n));
            boolean b1 = RECOMMENDER_name.stream().anyMatch(n -> n.contains(commentVO.getUser().getNickname()));
            boolean b2 = keys1.stream().anyMatch(t -> finalCompiledContent.contains(t));
            boolean b3 = keys2.stream().anyMatch(t -> finalCompiledContent.contains(t));
            if (b0 || ( b1 && b2 && b3)){
                System.out.println(" (评论有推荐人)b0 = " + b0 +", (评论人是推荐人)b1 = " +b1 +", (评论喊伯乐、编辑、理事会)b2 = " +b2 +", (评论含推荐)b3 = " +b3 +" , 评论人 ："+commentVO.getUser().getNickname());
                boolean containsSlug = RECOMMENDER_slug.contains(commentVO.getUser().getSlug());
                log.info(" containsSlug = "+containsSlug);
                if (!containsSlug){
                    boolean containsName = RECOMMENDER_name.contains(commentVO.getUser().getNickname());
                    log.info(" containsName = "+containsName);

                    if (containsName){
                        log.info("名字确认检测出推荐人："+commentVO.getUser().getNickname());
                        return commentVO.getUser() ;
                    }
                }else{
                    log.info("slug 检测出推荐人："+commentVO.getUser().getNickname());
                    return commentVO.getUser() ;
                }
            }
        }
        return null;
    }
}
