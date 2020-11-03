/**
 * ファイル名: HttpUtils.java
 * バージョン: 1.0.0
 * 作成日付: Jan 11, 2017
 * 最終更新日付: Jan 11, 2017
 * 作成者: KCS
 * 更新履歴: Jan 11, 2017 : 新規作成
 */
package jp.co.kcs_grp.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpUtils
{
    public static String sendGet(String urlStr, String authJson) throws Exception {
        BufferedReader br = null;
        HttpURLConnection conn = null;
        String result = "";
        try {

            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            // ヘッダーの設定	
            conn.setRequestProperty("AUTH-DATA", authJson == null ? "" : authJson);
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String output = "";
            
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                result = result + output ;
            }
            
          } catch (MalformedURLException e) {

            e.printStackTrace();
            throw e;
          } catch (IOException e) {

            e.printStackTrace();
            throw e;
          } catch (Exception e) {
              e.printStackTrace();
              throw e;
        } finally {
              if(conn != null) {
                  conn.disconnect();
              }
            if(br!=null) {
                br.close();
            }
        }
        
        return result;
    }
    
    public static String sendPost(String urlStr, String urlParameters, String authJson) throws Exception {
        BufferedReader br = null;
        HttpURLConnection conn = null;
        String result = "";
        try {
            
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            URL    url            = new URL( urlStr );
            conn= (HttpURLConnection) url.openConnection();           
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"); 
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            // ヘッダーの設定	
            conn.setRequestProperty("AUTH-DATA", authJson == null ? "" : authJson);
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
               wr.write( postData );
            }
            
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String output = "";
            
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                result = result + output ;
            }
            
          } catch (MalformedURLException e) {

            e.printStackTrace();
            throw e;
          } catch (IOException e) {

            e.printStackTrace();
            throw e;
          } finally {
              if(conn != null) {
                  conn.disconnect();
              }
            if(br!=null) {
                br.close();
            }
        }
        
        return result;
    }
    
    public static String sendPut(String urlStr, String urlParameters, String authJson) throws Exception {
        BufferedReader br = null;
        HttpURLConnection conn = null;
        String result = "";
        try {
            
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            URL    url            = new URL( urlStr );
            conn= (HttpURLConnection) url.openConnection();           
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "PUT" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"); 
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            // ヘッダーの設定	
            conn.setRequestProperty("AUTH-DATA", authJson == null ? "" : authJson);

            conn.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
               wr.write( postData );
            }
            
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String output = "";
            
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                result = result + output ;
            }
            
          } catch (MalformedURLException e) {
            e.printStackTrace();
            throw e;
          } catch (IOException e) {
            e.printStackTrace();
            throw e;
          } finally {
              if(conn != null) {
                  conn.disconnect();
              }
            if(br!=null) {
                br.close();
            }
        }
        return result;
    }
}
