package com.taotao.rest.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.manipulation.Sortable;

public class SolrJTest {
	
	/*
	 * 新增
	 * 修改和新增一样，id一样就行
	 * */
	@Test
	public void addDocument() throws Exception {
		//创建一个连接
		SolrServer solrServer=new HttpSolrServer("http://localhost:8080/solr");
		//创建一个文档对象
		SolrInputDocument document=new SolrInputDocument();
		document.addField("id", "test001");
		document.addField("item_title", "测试商品1");
		document.addField("item_price", 123456);
		//把文档对象写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	public void deleteDocument() throws Exception{
		//创建一个连接
		SolrServer solrServer=new HttpSolrServer("http://localhost:8080/solr");
		//solrServer.deleteById("test001");
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
}
