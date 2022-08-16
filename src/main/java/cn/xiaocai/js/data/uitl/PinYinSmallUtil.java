package cn.xiaocai.js.data.uitl;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/8/14 15:39
 * @version: v1.0
 */
@Slf4j
public class PinYinSmallUtil {

    public static String getPinYin(String newNickName){
        String pinyinString = "";
        if (!StringUtils.hasText(newNickName)){
            return pinyinString;
        }

        try {
            pinyinString = PinyinHelper.convertToPinyinString(newNickName, "", PinyinFormat.WITHOUT_TONE);
        } catch (PinyinException e) {
            log.info("昵称转拼音失败 ： " + newNickName);
            e.printStackTrace();
        }
        return pinyinString ;
    }
}
