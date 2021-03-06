package cn.zyrkj.monsterbeta10.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2017/9/21.
 */

public class GetConfigUtil {
    public String serverUrl;
    public long cacheTime = 1L;

    public GetConfigUtil(Context context) {
        AssetManager am = context.getAssets();
        InputStream is = null;
        try {
            is = am.open("serverConfig.properties");
            Properties props = new Properties();
            props.load(is);

            serverUrl = props.getProperty("serverUrl");
            String str = props.getProperty("cacheTime");

            String[] split = str.trim().split("\\*");
            for (String s : split) {
                cacheTime *= Long.parseLong(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
