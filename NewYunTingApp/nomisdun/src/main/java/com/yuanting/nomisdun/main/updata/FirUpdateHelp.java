package com.yuanting.nomisdun.main.updata;

/**
 * Created on 2018/7/23 10:05
 * Created by 薛立民
 * TEL 13262933389
 */
public class FirUpdateHelp {
//     Log.e(TAG, "执行至--doInBackground");
//
//    URL url;
//    HttpURLConnection connection = null;
//    InputStream in = null;
//    FileOutputStream out = null;
//            try {
//        url = new URL(apk_path);
//        connection = (HttpURLConnection) url.openConnection();
//
//        in = connection.getInputStream();
//        long fileLength = connection.getContentLength();
//        File file_path = new File(FILE_PATH);
//        if (!file_path.exists()) {
//            file_path.mkdir();
//        }
//
//        out = new FileOutputStream(new File(FILE_NAME));//为指定的文件路径创建文件输出流
//        byte[] buffer = new byte[1024 * 1024];
//        int len = 0;
//        long readLength = 0;
//
//        Log.e(TAG, "执行至--readLength = 0");
//
//        while ((len = in.read(buffer)) != -1) {
//
//            out.write(buffer, 0, len);//从buffer的第0位开始读取len长度的字节到输出流
//            readLength += len;
//
//            int curProgress = (int) (((float) readLength / fileLength) * 100);
//
//            Log.e(TAG, "当前下载进度：" + curProgress);
//
//            publishProgress(curProgress);
//
//            if (readLength >= fileLength) {
//
//                Log.e(TAG, "执行至--readLength >= fileLength");
//                break;
//            }
//        }
//
//        out.flush();
//        return INSTALL_TOKEN;
//
//    } catch (Exception e) {
//        e.printStackTrace();
//    } finally {
//        if (out != null) {
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (in != null) {
//            try {
//                in.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (connection != null) {
//            connection.disconnect();
//        }
//    }
//            return null;
//    private static final String BASE_URL = (String) Latte.getConfigurations().get(ConfigKeys.API_HOST);
//    private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(OKHttpHolder.OK_HTTP_CLIENT)
//            .addConverterFactory(ScalarsConverterFactory.create())
//            .build();
}
