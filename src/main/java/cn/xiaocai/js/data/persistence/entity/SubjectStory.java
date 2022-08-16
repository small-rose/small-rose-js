package cn.xiaocai.js.data.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/8/13 10:16
 * @version: v1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "JS_SUBJECT_STORY")
public class SubjectStory {

    @Id
    @GenericGenerator(name = "id", strategy = "cn.xiaocai.js.data.persistence.id.MyIdGenerator" )
    @GeneratedValue(generator = "id")
    private Long id ;
    private String wenId ;
    private String title ;
    private String wenUrl ;
    private String nickName ;
    //private String abstractDesc ;

    @Column(columnDefinition = "longtext")
    private String comments ;
    @Column(columnDefinition = "longtext")
    private String rewards ;

    private Date shouDate ; //收录时间，以扫描的时间为准
    private int lpReward ;
}
