package cn.xiaocai.js.data.persistence.repostory;

import cn.xiaocai.js.data.persistence.entity.SubjectDataInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SubjectDataInfoRepostory extends JpaRepository<SubjectDataInfoEntity,Long> {

    @Query(value = "select t.* from JS_SUBJECT_DATA_INFO t where t.wen_id = ?1  ",nativeQuery = true )
    SubjectDataInfoEntity findByWenId(String id);
}
