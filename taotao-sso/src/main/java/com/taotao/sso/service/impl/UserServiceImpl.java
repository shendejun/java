package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.UserService;

/*
 * 用户管理Service
 * */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Override
	public TaotaoResult checkData(String content, Integer type) {
		//创建查询条件
		TbUserExample example=new TbUserExample();
		Criteria criteria= example.createCriteria();
		//对数据进行校验：1、2、3分别代表username、phone、email
		//用户名校验
		if(1==type) {
			criteria.andUsernameEqualTo(content);
		}else if(2==type) {
			//电话校验
			criteria.andPhoneEqualTo(content);
		}else {
			//email校验
			criteria.andEmailEqualTo(content);
		}
		List<TbUser> list= userMapper.selectByExample(example);
		if(list==null||list.size()==0) {
			return TaotaoResult.ok(true);
		}
		return TaotaoResult.ok(false);
	}
	
	@Override
	public TaotaoResult createUser(TbUser user) {
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//md5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult userLogin(String username, String password) {
		TbUserExample example=new TbUserExample();
		
		return null;
	}

}
