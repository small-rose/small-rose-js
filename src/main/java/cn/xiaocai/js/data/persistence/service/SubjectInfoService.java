package cn.xiaocai.js.data.persistence.service;

import cn.xiaocai.js.data.persistence.entity.SubjectInfoEntity;
import cn.xiaocai.js.data.persistence.repostory.SubjectInfoRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/8/14 20:25
 * @version: v1.0
 */
@Service
public class SubjectInfoService {

    @Autowired
    private SubjectInfoRepostory subjectInfoRepostory ;

    @Transactional
    public void save(SubjectInfoEntity subjectInfoEntity){

        Optional<SubjectInfoEntity> subjectInfo = subjectInfoRepostory.findBySubjectSlug(subjectInfoEntity.getSubjectSlug());
        if (!subjectInfo.isPresent()){
            subjectInfoRepostory.save(subjectInfoEntity);
        }
    }

    @Transactional
    public void saveAll(List<SubjectInfoEntity> subjectInfoEntityList){
        subjectInfoRepostory.saveAll(subjectInfoEntityList);
    }
}
