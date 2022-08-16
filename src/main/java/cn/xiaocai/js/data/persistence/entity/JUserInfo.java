package cn.xiaocai.js.data.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/8/14 14:50
 * @version: v1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "JS_USER_INFO")
public class JUserInfo {

    @Id
    @GenericGenerator(name = "id", strategy = "cn.xiaocai.js.data.persistence.id.MyIdGenerator" )
    @GeneratedValue(generator = "id")
    private Long id ;
    //用户id

    private String slug ;
    private String slug_url ;
    private String nick_name ;
    private String nick_name_py;
    private int precommender; //候选推荐人


    @Override
    public String toString() {
        return "JUserInfo{" +
                "id=" + id +
                ", slug='" + slug + '\'' +
                ", slug_url='" + slug_url + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", nick_name_py='" + nick_name_py + '\'' +
                ", precommender=" + precommender +
                '}';
    }
}
