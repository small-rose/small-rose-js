package cn.xiaocai.js.data.search.parsevo;

import lombok.Data;

import java.util.List;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/8/14 17:16
 * @version: v1.0
 */
@Data
public class CommentVO {

    private Long score ;
    private Long  id ;
    private String created_at ;
    private String compiled_content	;
    private Long floor;
    private Long parent_id ;
    private Long  images_count	;
    private List images	;
    private Long  likes_count	;
    private Long  children_count	;
    private boolean  liked	;
    private JsUserVO user ;
    private List<CommentVO> children ;


}
