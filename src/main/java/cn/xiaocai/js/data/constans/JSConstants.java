package cn.xiaocai.js.data.constans;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/8/14 15:24
 * @version: v1.0
 */
public class JSConstants {

    public static String REFERURL = "https://www.jianshu.com/";

    /**
     * 排名接口
     */
    public static String RANK_URL_PRE = "https://www.jianshu.com/asimov/fp_rankings/voter_notes?date=";

    /**
     * （精彩）评论接口
     */
    public static String URL_COMMENTS_REG = "https://www.jianshu.com/shakespeare/notes/${noteId}/featured_comments?max_score=0&count=10";
    /**
     * (普通)评论接口
     */
    public static String URL_COMMENTS_2_REG = "https://www.jianshu.com/shakespeare/notes/${noteId}/comments?page=1&count=${countNum}&author_only=false&order_by=desc";

    /**
     * 赞赏接口
     */
    public static String URL_REWARD_REG = "https://www.jianshu.com/shakespeare/notes/${noteId}/reward_section";

    /**
     * 专题接口前缀
     */
    public static String URL_ZHUAN_TI_REG  = "https://www.jianshu.com/c/${ztId}?order_by=added_at&page=";

    public final static String  REPLACE_ZT_KEY = "${ztId}";
    public final static String  REPLACE_NOTE_KEY = "${noteId}";
    public final static String  REPLACE_COUNT_KEY = "${countNum}";


    /**
     * 专题接口前缀
     */
    public static String URL_SEARCH_REG  = "https://www.jianshu.com/search/do?q=${keyword}&type=note&page=1&order_by=default";
    public final static String  REPLACE_SEARCH_KEY = "${keyword}";

}
