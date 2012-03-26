package com.pjaol.ESB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.util.NamedList;
import org.carrot2.clustering.lingo.LingoClusteringAlgorithm;
import org.carrot2.core.Cluster;
import org.carrot2.core.Controller;
import org.carrot2.core.ControllerFactory;
import org.carrot2.core.Document;
import org.carrot2.core.ProcessingResult;

import com.pjaol.ESB.core.Module;

public class RSSClusterModule extends Module {
	
	String[] modules = null; 
	String key = null;
	String[] terms = null;
	final Controller controller = ControllerFactory.createPooling(4);
	
	@Override
	public void init(Map<String, String> args) {
		String fields = (String)args.get("modules");
		modules = fields.split(",");
		key = (String)args.get("key");
		Object fields2 = args.get("removeTerms");
		if (fields2 != null){
			terms = ((String)fields2).split(",");
			
		}
	}

	@Override
	public void initializeMonitor() {

	}

	@Override
	public NamedList process(NamedList input) throws Exception {

		//System.out.println(input);
		NamedList clustered = new NamedList();
		
		
		System.out.println("*********************");

		
		
		
		final ArrayList<Document> documents = new ArrayList<Document>();
		
		for(String module : modules){
			NamedList result = (NamedList)input.get(module);
			List<RSSItem> items =(List<RSSItem>) result.get(key);
			
			for(RSSItem item: items){
				
				String title = item.getTitle();
				if (terms != null){
					
					for(String t: terms){
						title = title.replaceAll(t, "");
					}
				}
				
				Document d = new Document(title, null, item.getLink());
				documents.add(d);
			}
			
		}
		
		
		
		final ProcessingResult byTopicClusters = controller.process(documents,
				null, LingoClusteringAlgorithm.class);
		
		final List<Cluster> clustersByTopic = byTopicClusters.getClusters();

		for(Cluster cluster: clustersByTopic){
			Map i = new HashMap();
			String label = cluster.getLabel();
			List<Document> docs = cluster.getAllDocuments();
			i.put("label", label);
			
			StringBuilder headlines = new StringBuilder();
			for(Document d : docs){
				headlines.append(d.getTitle()+" | ");
			}
			i.put("headlines", headlines.toString());
			clustered.add("cluster", i);
			
		}
		

		return clustered;
	}

}
