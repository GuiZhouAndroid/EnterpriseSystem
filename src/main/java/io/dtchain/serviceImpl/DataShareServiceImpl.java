package io.dtchain.serviceImpl;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.ConnectException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import io.dtchain.dao.DataShareDao;
import io.dtchain.entity.DataShare;
import io.dtchain.service.DataShareService;
import io.dtchain.utils.Doc2PdfUtil;
import io.dtchain.utils.FtpUtil;
import io.dtchain.utils.Result;
import io.dtchain.utils.Utils;

@Service("DataShareService")
public class DataShareServiceImpl implements DataShareService {

	@Value("${openoffice.host}")
	private String OpenOfficeHost;
	@Value("${openoffice.port}")
	private Integer OpenOfficePort;
	
	@Autowired
	private DataShareDao dataShareDao;
	@Autowired
    private FtpUtil ftpUtil;
	@Override
	public Result<Object> uploadDataFile(MultipartFile file, String docTitle, String docContent) throws Exception {
		String id=Utils.createId();
		Result<Object> result=new Result<Object>();
		String fileName = file.getOriginalFilename();
		String name=fileName.substring(fileName.lastIndexOf("."));
		InputStream inputStream = file.getInputStream();
		String filePath = null;
        Boolean flag = ftpUtil.uploadFile(id+name, inputStream);
        if (flag == true) {
            
            filePath = ftpUtil.FTP_BASEPATH + fileName;
            System.out.println("文件上传成功   "+fileName);
        }

        
		//String fileContent = path + fileName;
		//File file1 = new File(fileContent);
		// 上传文件
		//file.transferTo(file1);
		
		DataShare ds=new DataShare();
		ds.setUserId((String)SecurityUtils.getSubject().getSession().getAttribute("userSessionId"));
		ds.setId(id);
		ds.setDocTitle(fileName);
		ds.setRamark(docContent);
		Timestamp time=new Timestamp(System.currentTimeMillis());
		ds.setCreateTime(time);
		ds.setTitle(docTitle);
		ds.setSuffix(fileName.substring(fileName.lastIndexOf(".")));
		dataShareDao.uploadDataFile(ds);
		result.setState(1);
		result.setMsg("上传文档资料成功");
		pdfPreview(id+name);
		return result;

	}

	@Override
	public Result<Object> preview(String id,HttpServletRequest req,HttpServletResponse res)  {
		res.setHeader("Access-Control-Allow-Origin", "*"); 
		
		
		String fileName=dataShareDao.getDocumentNameById(id);
		Result<Object> result=new Result<Object>();

		//String file="";
		//try {
		//	file = ResourceUtils.getURL("src\\main\\resources\\static\\dataShare\\file\\").getPath().substring(1).replace('/', '\\')+fileName;
		//} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
		//	e1.printStackTrace();
	//	}
		//Doc2PdfUtil doc2PdfUtil = new Doc2PdfUtil(OpenOfficeHost, OpenOfficePort);

		//try {
			// doc转pdf，返回pdf文件
			// doc2PdfUtil.doc2Pdf(file);
			result.setState(1);
			result.setData(fileName.substring(0, fileName.lastIndexOf("."))+".pdf");
			result.setMsg("转换成功");
		//} catch (ConnectException e) {
			//System.out.println("****调用Doc2PdfUtil doc转pdf失败****");
			//e.printStackTrace();
			////result.setState(0);
		//	result.setMsg("转换失败");
	//	} 

	
	
	
		
		return result;
	}

	@Override
	public Result<List<DataShare>> getReleaseDocument(int page) {
		String userId=(String)SecurityUtils.getSubject().getSession().getAttribute("userSessionId");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("begin", (page-1)*10);
		List<DataShare> list=dataShareDao.getReleaseDocument(map);
		Result<List<DataShare>> result=new Result<List<DataShare>>();
		result.setData(list);
		result.setState(1);
		return result;
	}

	@Override
	public Result<Object> getReleaseDocumentCount() {
		String userId=(String)SecurityUtils.getSubject().getSession().getAttribute("userSessionId");
		Result<Object> result=new Result<Object>();
		result.setCount(dataShareDao.getReleaseDocumentCount(userId));
		result.setState(1);
		return result;
	}

	@Override
	public Result<DataShare> getDocumentContent(String id) {
		Result<DataShare> result=new Result<DataShare>();
		DataShare ds=dataShareDao.getDocumentContent(id);
		result.setData(ds);
		result.setState(1);
		return result;
	}

	@Override
	public Result<Object> getAllDocumentCount(String value,int state) {
		Result<Object> result=new Result<Object>();
		if(state==0) {
			result.setCount(dataShareDao.getAllDocumentCount());
			result.setState(1);
		}
		if(state==1) {
			result.setCount(dataShareDao.getSearshDocumentCount(value));
			result.setState(1);
		}
		return result;
	}

	@Override
	public Result<List<DataShare>> initDocument(int page,String value,int state) {
		Result<List<DataShare>> result=new Result<List<DataShare>>();
		Map<String,Object> map=new HashMap<String,Object>();
		if(state==0) {
			List<DataShare> list=dataShareDao.initDocument((page-1)*10);
			result.setData(list);
			result.setState(1);
		}
		if(state==1) {
			map.put("begin", (page-1)*10);
			map.put("value", value);
			List<DataShare> list=dataShareDao.getSearchDocument(map);
			result.setData(list);
			result.setState(1);
		}
		return result;
	}
	
	
	public void pdfPreview(String fileName) {
		System.out.println("fileName :"+fileName);

		
		//String file="";
		//try {
			//file = ResourceUtils.getURL("src\\main\\resources\\static\\dataShare\\file\\").getPath().substring(1).replace('/', '\\')+fileName;
		//} catch (FileNotFoundException e1) {
			
			//e1.printStackTrace();
		//}
		String file="D:/file/file/"+fileName;
		Doc2PdfUtil doc2PdfUtil = new Doc2PdfUtil(OpenOfficeHost, OpenOfficePort);

		try {
			// doc转pdf，返回pdf文件
			 doc2PdfUtil.doc2Pdf(file);
			
		} catch (ConnectException e) {
			System.out.println("****调用Doc2PdfUtil doc转pdf失败****");
			e.printStackTrace();
			
		} 
	}

	@Override
	public Result<Object> delDoc(String id) {
		Result<Object> result = new Result<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		String suffix=dataShareDao.getSuffixById(id.substring(id.indexOf("=")+1));
		ftpUtil.deleteFile(id.substring(id.indexOf("=")+1),suffix);
		map.put("id", id.substring(id.indexOf("=")+1));
		int n = dataShareDao.delDoc(map);
		if (n > 0) {
			result.setMsg("删除信息成功");
			result.setState(1);
		} else {
			result.setMsg("删除信息是失败");
			result.setState(0);
		}
		return result;
	}
}
