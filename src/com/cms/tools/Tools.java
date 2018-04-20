package com.cms.tools;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Tools {
    private static MessageDigest md;
    private static Random rand=new Random();
    private static char[] charArrays;
    private static File identifyingCode=null;
    private static BASE64Encoder encoder = new BASE64Encoder();


    static{
        StringBuilder stringBuilder=new StringBuilder();
        for(char i='0';i<='9';stringBuilder.append(i++));
        for(char i='a';i<='z';stringBuilder.append(i++));
        for(char i='A';i<='Z';stringBuilder.append(i++));
        charArrays=stringBuilder.toString().toCharArray();
        try {
            identifyingCode=File.createTempFile("icImg",".png");
            identifyingCode.deleteOnExit();
            md= MessageDigest.getInstance("MD5");
        } catch (IOException|NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String cnToUnicode(String text){
        StringBuilder sb=new StringBuilder();
        char[] chars = text.toCharArray();
        for (char c:chars) {
            if(c>=0x4E00){
                sb.append("\\u");
                sb.append(Integer.toString(c, 16));
            }else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String MD5(String str){
        try {
            md.update(str.getBytes("utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String num=new BigInteger(1, md.digest()).toString(16);
        while(num.length()<32){
            num="0"+num;
        }
        return num;
    }

    /**
     * 将传入的数据给转换为JSONObject
     * @param request
     * @return
     */
    public static JSONObject dataToJSONObject(HttpServletRequest request){
        if(request.getContentType()==null){
            return null;
        }
        try {
            if ("application/json".equals(request.getContentType().split(";")[0].toLowerCase())) {
                return JSONObject.fromObject(getResultString(request));
            } else {
                return null;
            }
        }catch(IOException |JSONException e){
            return null;
        }
    }

    /**
     * 将post过来的数据文本全部加载出来
     * @param request
     * @return
     * @throws IOException
     */
    public static String getResultString(HttpServletRequest request) throws IOException{
        String charset=request.getCharacterEncoding();
        charset=(charset==null?"UTF-8":charset);
        return IOUtils.toString(request.getInputStream(), charset);
    }

    /**
     * 生成验证码
     * 返回数组的第一个参数是验证码答案
     * 第二个是验证码的base64
     * @return
     */
    public static String[] createIdentifyingCode(){
        BufferedImage bufferedImage=new BufferedImage(240,60, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics2D= bufferedImage.createGraphics();
        graphics2D.setFont(new Font("",rand.nextInt(4),40+rand.nextInt(8)));
        char result[]=new char[4];
        for(int i=0;i<=180;i+=60){
            result[i/60]=charArrays[rand.nextInt(36)];
            graphics2D.setColor(new Color(100+rand.nextInt(155),100+rand.nextInt(155),100+rand.nextInt(155)));
            graphics2D.drawString(Character.toString(result[i/60]),i+rand.nextInt(10),rand.nextInt(15)+35);
        }
        for(int i=0;i<2;i++) {
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawLine(rand.nextInt(20), rand.nextInt(60), rand.nextInt(20) + 200, rand.nextInt(60));
        }
        try {
            ImageIO.write(bufferedImage, "png", identifyingCode);
            InputStream inputStream = new FileInputStream(identifyingCode);
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
            return new String[]{String.valueOf(result),encoder.encode(data)};
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    void print(Object a){
        System.out.println(a.toString());
    }

}
