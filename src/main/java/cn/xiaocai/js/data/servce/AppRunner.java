package cn.xiaocai.js.data.servce;

import cn.xiaocai.js.data.persistence.service.JUserInfoService;
import cn.xiaocai.js.data.persistence.service.SubjectInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/7/31 16:05
 * @version: v1.0
 */
@Slf4j
@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    SubjectInfoService subjectInfoService ;
    @Autowired
    JUserInfoService jUserInfoService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        List<SubjectInfoEntity> list = new ArrayList<>(10);

//        list.add(new SubjectInfoEntity(1L,"0527ee052b5b","我与简书的故事 ","woyujianshudegushi"));
//        list.add(new SubjectInfoEntity(2L,"f61832508891","理事会点赞汇总","lishihuidianzanhuizong"));
//        list.add(new SubjectInfoEntity(3L,"57cc936b828b","简书文友汇推文","wenyouhuituiwen"));

//        list.stream().forEach(zt->subjectInfoService.save(zt));
//        subjectInfoService.saveAll(list);
        log.info("简书专题数据初始化成功！");

/*

        String fileName = this.getClass().getClassLoader().getResource("file/1.txt").getPath();//获取文件路径
        List<String> cvsList = CvsUtils.getCsvDataMethod2(new File(fileName));
        cvsList.stream().forEach(System.out::println);
*/

        /*
        String fileName = this.getClass().getClassLoader().getResource("user.cvs").getPath();//获取文件路径
        System.out.println("fileName " +fileName);
        //String fileUtl = this.getClass().getResource("user.cvs").getFile();
        //System.out.println("fileName " +fileUtl);

        List<JUserInfo> userList = jUserInfoService.findCommenderList();
        if (CollectionUtils.isEmpty(userList)){
            List<JUserInfo> cvsList = CvsUtils.getCsvDataMethod1(new File(fileName));
            if (!CollectionUtils.isEmpty(cvsList)){
                //cvsList.stream().forEach(juser-> jUserInfoService.save(juser));
                jUserInfoService.saveAll(cvsList);
            }
            log.info("推荐人初始化完毕");
        }
    */



    }
}
