package com.pjaol.ESB;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.solr.common.util.NamedList;

import com.pjaol.ESB.core.Module;

public class RSSFetcher extends Module{

	private String url = null;
	private PoolingClientConnectionManager manager;
	@Override
	public void init(Map<String, String> args) {
		
		url = (String)args.get("url");
		manager = new PoolingClientConnectionManager();
		manager.setMaxTotal(100);
		
		//manager.setMaxTotal(20);
		
	}

	@Override
	public void initializeMonitor() {

		
	}

	@Override
	public NamedList process(NamedList input) throws Exception {

		HttpClient client = new DefaultHttpClient(manager);
		
		HttpResponse response  =client.execute(new HttpGet(url));
		String content = EntityUtils.toString(response.getEntity());
		
		EntityUtils.consume(response.getEntity());
		//manager.releaseConnection(conn, keepalive, tunit)
		//manager.shutdown();
		
		NamedList result = new NamedList();
		List<RSSItem> items = RSSDomParser.getRSSItems(content);
		result.add("content",items);
		
		return result;
	}

}
