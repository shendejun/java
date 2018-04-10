package com.taotao.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;

@Controller
public class PictureController {
	@Autowired
	private PictureService pictureService;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	/*这里的返回值String代表逻辑视图，但是如果加上ResponseBody就相当于
	 * 直接使用Response对象调它的write方法直接写回一个字符串去*/
	public String pictureUpload(MultipartFile uploadFile){
		Map result= pictureService.uploadPicture(uploadFile);
		//为了保证功能的兼容性，需要把Result转成json格式的字符串。
		String json=JsonUtils.objectToJson(result);
		return json;
	}
	
}
