package org.amd.aqua.task;

import android.app.Activity;
import android.os.AsyncTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Akira on 2018/04/21.
 */

public abstract class AquaApiTask extends AsyncTask<String, Void, String> {

    private Activity mainActivity;

    public static String BASE_URL = "http://hackathon.support-cloud-projects.com/LaundromatWebApi/api/";

    private static final String TOKEN = "Ad6xhPJRT4ZZwuQBIDGLsgnL8tI3ivPIQdp3Dfj_R3BFyppz8D9dUTDXScKTFLR0Bvt8hRPTSOtflPMowamQEgb1JjM14XhlJJnyIpTOqpsbP7-IjjYrutsGXnnoAA7ry_W95-Ior06hGKG1d2pvrpQrkU-1MVloxC5zXlHNEI_oyytddCZ3GUOw1eOwJnWi2tP_fi0JkQ7yfC0o9t9Xh5YXAlNnVvmeX1mbFJmOnw-D2sYM0TQz2lBR8rnrUOhYNcHlEgkA2kMYBg87MzRhCCvNtHKHP2j20j2gZJWAA9pdIYmmEmULe9oF1Y5zZHiHY_6O_hPjhM_xiWKoxCQu990ZuW0Il4SMsmxPlIRMhkiAhn3kdUELKdmHx2cY3a52Aftv4BGI6omzI9RBoJiQ9SPpEApRTFw5xnKiSINwVQfPVHUZItr2YZ4Ckg3p1dJpq_Au8Uq4P_9lUpfF6atTFFUlrKQuWHGpWMflefSoroxkHGYSgkY4ptBqzPBnEvA4";

    public AquaApiTask(Activity activity) {
        // 呼び出し元のアクティビティ
        this.mainActivity = activity;
    }


    @Override
    protected String doInBackground(String... params) {
        String endPoint = params[0];
        Request request = new Request.Builder()
                .url(BASE_URL + endPoint)
                .addHeader("Authorization", "Bearer " + TOKEN)
                .get()
                .build();

        OkHttpClient client = new OkHttpClient();

        try {
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            String strBody = body.string();
            System.err.println(strBody);
            return strBody;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}