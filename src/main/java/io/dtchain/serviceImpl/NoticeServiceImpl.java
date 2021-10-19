package io.dtchain.serviceImpl;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import io.dtchain.dao.NoticeDao;
import io.dtchain.entity.Notice;
import io.dtchain.service.NoticeService;
import io.dtchain.utils.Result;

@Service("NoticeService")
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeDao noticeDao;

	@Override
	public String uploadImage(MultipartFile file) throws Exception {

		String path = ResourceUtils.getURL("src\\main\\resources\\static\\images").getPath();
		path = path.substring(1).replace('/', '\\');

		String oldName = file.getOriginalFilename();
		String newName = changeName(oldName);
		String fileName = path + newName;

		File file1 = new File(fileName);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("code", 0);
		map.put("src", "http://localhost:8080/images/" + newName);
		map.put("title", newName);
		map1.put("data", map);
		
		// 上传文件
		file.transferTo(file1);
		
		return new JSONObject(map1).toString();
	}

	/*
	 * 改变文件名字
	 */
	public static String changeName(String oldName) {
		String newName = oldName.substring(oldName.indexOf('.'));
		newName = new Date().getTime() + newName;
		return newName;
	}

	@Override
	public String noticeContent(Notice notice) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		String id = UUID.randomUUID().toString().replace("-", "");
		notice.setId(id);
		notice.setTime(time);
		noticeDao.saveNotice(notice);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "保存通知内容成功");
		return new JSONObject(map).toString();
	}

	@Override
	public Result<List<Notice>> queryNoticeTitle(Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();
		int n = (page <= 0 ? 1 : (page - 1) * 10);
		map.put("page", n);
		List<Notice> list = noticeDao.queryNoticeTitle(map);
		Result<List<Notice>> result = new Result<List<Notice>>();
		if (list != null && list.size() > 0) {
			result.setData(list);
			result.setMsg("查询通知标题成功");
			result.setState(1);
		} else {
			result.setMsg("没有通知标题");
			result.setState(0);
		}
		return result;
	}

	@Override
	public Result<Notice> queryNoticeContent(String id) {
		Result<Notice> result = new Result<Notice>();
		Notice notice = noticeDao.queryNoticeContent(id);
		if (notice != null) {
			result.setData(notice);
			result.setState(1);
			result.setMsg("查询通知详情成功");
		} else {
			result.setState(0);
			result.setMsg("查询通知详情失败");
		}
		return result;
	}

	@Override
	public Result<Object> queryNoticeCount() {
		Result<Object> result = new Result<Object>();
		int n = noticeDao.queryNoticeCount();
		if (n > 0) {
			result.setData(n);
			result.setState(1);
			result.setMsg("查询总数成功");
		} else {
			result.setState(0);
			result.setMsg("查询总数失败或者没有通知");
		}
		return result;
	}

	@Override
	public Result<Object> delNotice(String id) {
		Result<Object> result=new Result<Object>();
		int n=noticeDao.delNotice(id.substring(id.indexOf("=")+1));
		if(n>0) {
			result.setMsg("请求删除成功");
			result.setState(1);
		}else {
			result.setMsg("请求删除失败");
			result.setState(0);
		}
		return result;
	}

	@Override
	public Result<Object> updateNotice(Notice notice) {
		Result<Object> result=new Result<Object>();
		int n=noticeDao.updataNotice(notice);
		if(n>0) {
			result.setMsg("请求修改公告详情成功");
			result.setState(1);
		}else {
			result.setMsg("请求修改公告详情失败");
			result.setState(0);
		}
		return result;
	}

}
