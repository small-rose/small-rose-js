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
@Entity(name = "JS_SUBJECT_DATA_INFO")
public class SubjectDataInfoEntity {

    @Id
    @GenericGenerator(name = "id", strategy = "cn.xiaocai.js.data.persistence.id.MyIdGenerator" )
    @GeneratedValue(generator = "id")
    private Long id ;


    private String wenId ;
    private String title ;
    private String wenUrl ;

    @Column
    private String wenSlug ;

    @Column
    private String nickName ;
    private String userSlug ;

    @Column(columnDefinition = "longtext")
    private String comments ;
    @Column(columnDefinition = "longtext")
    private String rewards ;

    //收录时间，以扫描的时间为准
    private Date updateTime ;
    //收录时间，以扫描的时间为准
    private String shouDate ;
    private Date shouTime ;
    private int lpReward ;
    private String recommender ;
    private String recommenderSlug ;

    private String subjectId ;


}
