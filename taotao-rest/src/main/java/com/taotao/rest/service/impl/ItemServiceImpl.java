package com.taotao.rest.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private JedisClient JedisClient;
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;
	
	@Override
	public TaotaoResult getItemBaseInfo(long itemId) {
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息			
			String json= JedisClient.get(REDIS_ITEM_KEY+":"+itemId+":base");
			//判断是否有值
			if(!StringUtils.isBlank(json)) {
				TbItem item=JsonUtils.jsonToPojo(json, TbItem.class);
				return TaotaoResult.ok(item); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//根据商品Id查询商品信息
		TbItem item=itemMapper.selectByPrimaryKey(itemId);
		//添加缓存不能影响正常的业务逻辑
		try {
			//把商品信息写入缓存
			JedisClient.set(REDIS_ITEM_KEY+":"+itemId+":base", JsonUtils.objectToJson(item));
			//设置key过期时间
			JedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return TaotaoResult.ok(item);
	}
}
