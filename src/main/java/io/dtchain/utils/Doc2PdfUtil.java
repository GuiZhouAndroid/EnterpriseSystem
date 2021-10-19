package io.dtchain.utils;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;



public class Doc2PdfUtil {
    private String OpenOfficeHost; //openOffice服务地址
    private Integer OpenOfficePort; //openOffice服务端口
    
    public Doc2PdfUtil(){
    }

    public Doc2PdfUtil(String OpenOfficeHost, Integer OpenOfficePort){
        this.OpenOfficeHost = OpenOfficeHost;
        this.OpenOfficePort = OpenOfficePort;
    }
    
    private Logger logger = LoggerFactory.getLogger(Doc2PdfUtil.class);
    
    /**
     * doc转pdf
     * @return pdf文件路径
     * @throws ConnectException
     */
    public void doc2Pdf(String fileName) throws ConnectException{
        File docFile = new File(fileName);  
        File pdfFile = new File(fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf");

        if (docFile.exists()) {
            if (!pdfFile.exists()) {
                OpenOfficeConnection connection = new SocketOpenOfficeConnection(OpenOfficeHost, OpenOfficePort);
                try {
                    connection.connect();
                    DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
                    //最核心的操作，doc转pdf
                    converter.convert(docFile, pdfFile);
                    connection.disconnect();
                    logger.info("****pdf转换成功，PDF输出：" + pdfFile.getPath() + "****");
                } catch (java.net.ConnectException e) {
                    logger.info("****pdf转换异常，openoffice服务未启动！****");
                    e.printStackTrace();
                    throw e;
                } catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
                    System.out.println("****pdf转换器异常，读取转换文件失败****");
                    e.printStackTrace();
                    throw e;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        } else {
            logger.info("****pdf转换异常，需要转换的doc文档不存在，无法转换****");
        }
    }
}