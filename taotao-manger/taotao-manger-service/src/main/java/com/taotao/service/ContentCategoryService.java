package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {
	List<EUTreeNode> getCategoryList(Long parentId);
	TaotaoResult insertContentCategory(Long parentId,String name);
}
