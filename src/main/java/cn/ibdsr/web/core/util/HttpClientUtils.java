package cn.ibdsr.web.core.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 第三方库查询处理
 */
@Component
public class HttpClientUtils
{

    /**
     * 查询
     * 
     * @param url url
     * @param josnParam 参数
     * @return String
     */
    public static String jsonPost(String url, String josnParam)
    {
        String resData = null;
        try
        {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost method = new HttpPost(url);
            
            StringEntity entity = new StringEntity(josnParam, "utf-8"); // 解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            method.setEntity(entity);
            
            HttpResponse result = httpClient.execute(method);
            
            // 请求结束，返回结果
            resData = EntityUtils.toString(result.getEntity(), "utf-8");


        }
        catch (Exception e)
        {
        }
        return resData;
    }

    /**
     * 查询
     *
     * @param url url
     * @param formParams 参数
     * @return String
     */
    public static String formPost(String url, List<NameValuePair> formParams)
    {
        String resData = null;
        CloseableHttpClient httpClient = null;
        UrlEncodedFormEntity uefEntity;
        try
        {
            uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
            httpClient = HttpClients.createDefault();
            HttpPost method = new HttpPost(url);

            method.setEntity(uefEntity);

            HttpResponse result = httpClient.execute(method);

            // 请求结束，返回结果
            resData = EntityUtils.toString(result.getEntity(), "utf-8");

            httpClient.close();
        }
        catch (Exception e)
        {
        }
        return resData;
    }
    
    /**
     * get调用
     * 
     * @param url url
     * @param charset charset
     * @param pretty pretty
     * @return String
     */
    public static String doGet(String url, String charset, List<NameValuePair> formParams, boolean pretty)
    {
        StringBuilder response = new StringBuilder();
        HttpClient client = new HttpClient();
        HttpMethod method = null;
        try
        {
            String params = EntityUtils.toString(new UrlEncodedFormEntity(formParams,Consts.UTF_8));
            method = new GetMethod(url + "?" + params);
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK)
            {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(method.getResponseBodyAsStream(), charset));
                String line;
                while ((line = reader.readLine()) != null)
                {
                    if (pretty)
                    {
                        response.append(line).append(System.getProperty("line.separator"));
                    }
                    else
                    {
                        response.append(line);
                    }
                }
                reader.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            method.releaseConnection();
        }
        return response.toString();
    }
    
}