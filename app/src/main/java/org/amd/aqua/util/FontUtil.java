package org.amd.aqua.util;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Akira on 2018/04/10.
 */

public class FontUtil {
    public static final String FONT_AWESOME = "fontawesome-webfont.ttf";

    public static final String FONT_FLATICON = "flaticon.ttf";

    public static final String FONT_TYPICONS = "typicons.ttf";

    public static final String FONT_HGRKK = "HGRKK.TTC_1.ttf";

    private static Map<String, Typeface> fontCache = new HashMap<String, Typeface>();

    /**
     * フォントを assets から読み込みます。
     *
     * @param context コンテキスト。
     * @param path    フォント ファイルを示す assets フォルダからの相対パス。
     * @return 成功時は Typeface インスタンス。それ以外は null。
     */
    public static Typeface getTypefaceFromAssets(Context context, String font) {
        if (fontCache.containsKey(font)) {
            return fontCache.get(font);
        }
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), font);
        fontCache.put(font, typeface);
        return typeface;
    }
}
