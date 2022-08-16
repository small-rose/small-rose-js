package cn.xiaocai.js.data.persistence.repostory;

import cn.xiaocai.js.data.persistence.entity.JUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface JUserInfoRepostory extends JpaRepository<JUserInfo,Long> {

    @Query(value = "select * from JS_USER_INFO where slug = ?1 ", nativeQuery = true )
    JUserInfo findBySlug(String slug);

    @Query(value = "select * from JS_USER_INFO where precommender = 1 ", nativeQuery = true )
    List<JUserInfo> findCommenderList();
}
