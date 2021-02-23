package com.auxiliary;

import org.jetbrains.annotations.TestOnly;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Util {


    public static String downloadFile(HttpServletResponse response, String fileName) {/**/
        File path = null;
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(path + "/doc/" + fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (FileNotFoundException e1) {
            //e1.getMessage()+"系统找不到指定的文件";
            return "系统找不到指定的文件";
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }


    /**
     * 读取文件内容
     *
     * @param path 文件地址
     * @return String类型
     */
    public static String readFile(String path) {
        StringBuffer stringBuffer = new StringBuffer();
        ClassPathResource classPathResource = new ClassPathResource(path);
        try {
            InputStream stream = classPathResource.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(stringBuffer);
    }


    /**
     * 删除单个文件
     *
     * @param file 被删除文件
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(File file) {
        boolean flag = false;
//        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }


    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param dirFile 被删除目录
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(File dirFile) {
//    public static  boolean deleteDirectory(String sPath) {
//        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
//        if (!sPath.endsWith(File.separator)) {
//            sPath = sPath + File.separator;
//        }
//        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(new File(files[i].getAbsolutePath()));
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(new File(files[i].getAbsolutePath()));
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前时间
     * @return
     */
    @TestOnly
    public static Timestamp getCurrentTime() {
        Date date = new Date();

//        String  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//        calendar.setTime(date);


//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//        String BeijingTime = dateFormat.format(date);
//
//        System.out.println("时间"+BeijingTime);
//        System.out.println("时间"+ Timestamp.valueOf(BeijingTime));
//        return Timestamp.valueOf(BeijingTime);

        return new Timestamp(date.getTime());

    }
}
