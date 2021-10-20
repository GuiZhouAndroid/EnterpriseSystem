package io.dtchain.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.dtchain.entity.RecordTable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UpLoadService {
	
	/**
	 * 上传excel文件
	 * 
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	public void upLoad(MultipartFile file,HttpServletRequest req, HttpServletResponse res) throws Exception;


}
