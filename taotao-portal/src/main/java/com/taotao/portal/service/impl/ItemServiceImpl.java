package com.taotao.portal.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.Item;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;

/*
 * 商品信息管理
 * */
@Service
public class ItemServiceImpl implements ItemService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITME_INFO_URL}")
	private String ITME_INFO_URL;
	
	@Override
	public ItemInfo getItemById(Long itemId) {
		try {
			//调用rest 服务
			String json= HttpClientUtil.doGet(REST_BASE_URL+ITME_INFO_URL+itemId);
			if(!StringUtils.isBlank(json)) {
				TaotaoResult taotaoResult= TaotaoResult.formatToPojo(json, ItemInfo.class);
				if(taotaoResult.getStatus()==200) {
					ItemInfo item=(ItemInfo)taotaoResult.getData();
					return item;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
