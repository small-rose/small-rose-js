package cn.xiaocai.js.data.persistence.service;

import cn.xiaocai.js.data.persistence.entity.JUserInfo;
import cn.xiaocai.js.data.persistence.repostory.JUserInfoRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/8/14 15:47
 * @version: v1.0
 */
@Service
public class JUserInfoService {

    @Autowired
    private JUserInfoRepostory jUserInfoRepostory ;

    public List<JUserInfo> findCommenderList(){

        return jUserInfoRepostory.findCommenderList();
    }
    /**
     * 保存简书用户
     * @param user
     * @return
     */
    @Transactional
    public boolean addCheckUser(JUserInfo user){

        JUserInfo jsUser = jUserInfoRepostory.findBySlug(user.getSlug());
        if (jsUser==null){
            jUserInfoRepostory.save(user);
        }else{
            jsUser.setSlug(user.getSlug());
            jsUser.setSlug_url(user.getSlug_url());
            jsUser.setNick_name(user.getNick_name());
            jsUser.setNick_name_py(user.getNick_name_py());
            jUserInfoRepostory.saveAndFlush(jsUser);
        }

        return true ;
    }

    public List<JUserInfo> findAll() {
        return jUserInfoRepostory.findAll();
    }

    @Transactional
    public void save(JUserInfo juser) {

        Optional<JUserInfo> user = jUserInfoRepostory.findById(juser.getId());
        if (!user.isPresent()){
            jUserInfoRepostory.save(juser);
        }
    }

    @Transactional
    public void saveAll(List<JUserInfo> userInfoList) {
        jUserInfoRepostory.saveAll(userInfoList);
    }
}
