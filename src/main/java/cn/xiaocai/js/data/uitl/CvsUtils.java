package cn.xiaocai.js.data.uitl;

import cn.xiaocai.js.data.persistence.entity.JUserInfo;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2022/8/14 20:31
 * @version: v1.0
 */
public class CvsUtils {

    //	析csv文件并转成bean（方法一）
    public static List<JUserInfo> getCsvDataMethod1(File file) {
        ArrayList<JUserInfo> csvFileList = new ArrayList<>();

        InputStreamReader in = null;
        String s = null;
        try {
            in = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(in);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (!StringUtils.hasText(line)){
                    continue;
                }
                String[] split = line.split(",");
                //System.out.println(line);
                //System.out.println(split.length);
                JUserInfo csvFile = new JUserInfo();
                csvFile.setId(Long.valueOf(split[0]));
                csvFile.setNick_name(splitResult(split[1]));
                csvFile.setNick_name_py(PinYinSmallUtil.getPinYin(splitResult(split[1])));
                csvFile.setPrecommender(Integer.parseInt(split[3]));
                if (split.length > 4 && StringUtils.hasText(splitResult(split[4]))){
                    csvFile.setSlug(splitResult(split[4]));
                }else {
                    csvFile.setSlug(null);
                }

                if (split.length > 5 && StringUtils.hasText(splitResult(split[5]))){
                    csvFile.setSlug_url("https://www.jianshu.com/u/"+splitResult(split[5]));
                }else {
                    csvFile.setSlug_url(null);
                }
                csvFileList.add(csvFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFileList;
    }

    private static String splitResult(String once) {
        String result = "";
        if (!StringUtils.hasText(once)){
            return result ;
        }

        for (int i = 0; i < once.length(); i++) {
            if (once.charAt(i) != '"') {
                result += once.charAt(i);
            }
        }
        return result;
    }
}
