package io.dtchain.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

@Component
public class FtpUtil {
	 
    //ftp服务器ip地址
    private static final String FTP_ADDRESS = "192.168.119.1";
    //端口号
    private static final int FTP_PORT = 21;
    //用户名
    private static final String FTP_USERNAME = "Engine";
    //密码
    private static final String FTP_PASSWORD = "lvguanshao1314";
    //图片路径
    public final String FTP_BASEPATH = "/file/";
    private static FTPClient ftpClient = null;
    
    /**
	 * 初始化ftp服务器
	 */
	private static void initFtpClient() {
		ftpClient = new FTPClient();
		ftpClient.setControlEncoding("utf-8");
		try {
			ftpClient.connect(FTP_ADDRESS, FTP_PORT); // 连接ftp服务器
			ftpClient.login(FTP_USERNAME, FTP_PASSWORD);// 登录ftp服务器
			ftpClient.getReplyCode(); // 是否成功登录服务器
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    public boolean uploadFile(String originFileName, InputStream input) {
    	initFtpClient();
        boolean success = false;
      //  FTPClient ftp = new FTPClient();
       // ftpClient.setControlEncoding("utf-8");
        try {
            int reply;
           // ftpClient.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
          //  ftp.login("anonymous", "");// 登录
           // ftpClient.login(FTP_USERNAME, FTP_PASSWORD);// 登录
           // reply = ftpClient.getReplyCode();
            //if (!FTPReply.isPositiveCompletion(reply)) {
            ////	ftpClient.disconnect();
             //   return success;
           // }
			
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.makeDirectory(FTP_BASEPATH);
            ftpClient.changeWorkingDirectory(FTP_BASEPATH);
            ftpClient.enterLocalPassiveMode();
            boolean fff= ftpClient.storeFile(originFileName, input);
            input.close();
            ftpClient.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                	ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }
    
	public boolean deleteFile(String filename,String suffix) {
		boolean flag = false;
		try {
			initFtpClient();
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(FTP_BASEPATH);
			ftpClient.deleteFile(filename+suffix);
			if(!suffix.equals(".pdf")) {
				ftpClient.deleteFile(filename+".pdf");
			}
			ftpClient.logout();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
}
