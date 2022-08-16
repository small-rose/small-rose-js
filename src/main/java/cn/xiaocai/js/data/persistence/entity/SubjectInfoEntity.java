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
@Entity(name = "JS_SUBJECT_INFO")
public class SubjectInfoEntity {

    @Id
    @GenericGenerator(name = "id", strategy = "cn.xiaocai.js.data.persistence.id.MyIdGenerator" )
    @GeneratedValue(generator = "id")
    private Long id ;
    //专题id
    private String subjectSlug ;
    private String title ;
    private String titlePy;
}
