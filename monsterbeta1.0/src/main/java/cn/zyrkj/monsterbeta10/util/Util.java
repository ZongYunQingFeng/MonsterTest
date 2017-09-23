package cn.zyrkj.monsterbeta10.util;

import android.content.Context;
import android.widget.Toast;

public class Util {

	public static void t(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

}
