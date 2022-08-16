package cn.xiaocai.js.data.persistence.repostory;

import cn.xiaocai.js.data.persistence.entity.SubjectInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/8/14 20:27
 * @version: v1.0
 */
@Repository
public interface SubjectInfoRepostory extends JpaRepository<SubjectInfoEntity,Long> {

    @Query(value = "select t.* from JS_SUBJECT_INFO t where t.subject_slug = ?1  ",nativeQuery = true )
    Optional<SubjectInfoEntity> findBySubjectSlug(String subjectSlug);
}
