package cn.xiaocai.js.data.persistence.service;

import cn.xiaocai.js.data.persistence.entity.SubjectDataInfoEntity;
import cn.xiaocai.js.data.persistence.repostory.SubjectDataInfoRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/8/14 15:54
 * @version: v1.0
 */
@Service
public class SubjectDataInfoService {

    @Autowired
    private SubjectDataInfoRepostory subjectDataInfoRepostory ;

    public void saveData(SubjectDataInfoEntity data){

        SubjectDataInfoEntity ztData = findByWenId(data.getWenId());
        if (ztData==null){
            subjectDataInfoRepostory.save(data);
        }else{
            ztData.setRewards(data.getRewards());
            ztData.setComments(data.getComments());
            ztData.setNickName(data.getNickName());
            ztData.setRecommender(data.getRecommender());
            ztData.setRecommenderSlug(data.getRecommenderSlug());
            subjectDataInfoRepostory.saveAndFlush(ztData);
        }
    }

    public SubjectDataInfoEntity findByWenId(String noteId){

        return  subjectDataInfoRepostory.findByWenId(noteId);
    }


}
