package cn.xiaocai.js.data.controller;

import cn.xiaocai.js.data.persistence.entity.ArticleRank;
import cn.xiaocai.js.data.persistence.service.SearchService;
import cn.xiaocai.js.data.search.vo.NameResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/7/31 10:58
 * @version: v1.0
 */
@Controller
@Slf4j
public class SearchController {

    @Autowired
    private SearchService searchService ;

    @RequestMapping("/index.html")
    public String openSearch(Model model) {
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        model.addAttribute("lastModify", format);
        Long aLong = searchService.searchRankCount();
        model.addAttribute("count", aLong);
        model.addAttribute("addressUrl", "jianshu.com");
        return "SearchMain";
    }

    @RequestMapping("/")
    public String home() {
        return "forward:/index.html";
    }

    @RequestMapping("/search")
    public String doSearch(String keyword, Model model, HttpServletRequest request) {
        if (!StringUtils.hasText(keyword)) {
            throw new HttpClientErrorException(HttpStatus.NOT_ACCEPTABLE,"参数keyword不能为空");
        }
        List<NameResult> articleRanks = searchService.searchByKeyword(keyword);

        model.addAttribute("results", articleRanks);
        return "searchResultJson";
    }


    @RequestMapping("/rankList")
    public String getRankList(String nickName, String nickNamePy, Model model) {
        try {
            List<ArticleRank> articleRanks = searchService.searchArticleRankList(nickName);

            model.addAttribute("nickName", nickName);
            model.addAttribute("nickNamePy", nickNamePy);
            model.addAttribute("items", articleRanks);
            Long count = searchService.searchRankCountByName(nickName);
            model.addAttribute("count", count);


        } catch (Exception e) {
            throw new RuntimeException("排名查询失败", e);
        }
        return "rankList";
    }

}
