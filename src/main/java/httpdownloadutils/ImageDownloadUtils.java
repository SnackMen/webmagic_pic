package httpdownloadutils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by laowang on 16-8-8.
 */
public class ImageDownloadUtils {
    private static Logger logger = Logger.getLogger(ImageDownloadUtils.class);

    public static synchronized void downLoadImage(String url,String filePath,String fileName,String picType) throws Exception{
        logger.info("----------文件开始下载-----------");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if(url==null || url.equals(""))
            return;
        //创建目标才存储目录
        File desPathFile = new File(filePath);
        System.out.println("filePath:"+filePath);
        if(!desPathFile.exists()){
            desPathFile.mkdirs();
            //创建此抽象路径指定的目录，包括所有必须但不存在的父目录
        }
        //得到文件绝对路径
        String fullPath = filePath+File.separator+fileName+"."+picType;
        System.out.println("完整路径:"+fullPath);
        System.out.println("下载url:"+url);
        logger.info("文件路径："+filePath);
        logger.info("文件名："+fileName);
        logger.info("源文件url"+url);
        //从源网址下载图片
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpget);
        HttpEntity entity = response.getEntity();
        InputStream in = entity.getContent();
        //设置下载地址
        File file = new File(fullPath);

        try {
            FileOutputStream fout = new FileOutputStream(file);
            int l = -1;
            byte[] tmp = new byte[1024];
            while ((l = in.read(tmp)) != -1) {
                fout.write(tmp,0,l);
            }
            fout.flush();
            fout.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } finally {

            in.close();
        }
        logger.info("-------------------文件下载结束---------------------");



    }
}
