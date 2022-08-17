package cn.xiaocai.js.data.search.parsevo;

import lombok.Data;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/8/14 17:21
 * @version: v1.0
 */
@Data
public class JsUserVO {

    private long id ;
    private String nickname	;
    private String slug ;
    private MemberVO member ;
    private String avatar;
}
