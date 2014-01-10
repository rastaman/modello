package org.codehaus.modello.plugin.jaxrs.metadata;

import java.util.Map;

import org.codehaus.modello.metadata.InterfaceMetadata;

public class JaxRSInterfaceMetadata implements InterfaceMetadata {

	private Map<String,String> data;

	public JaxRSInterfaceMetadata(Map<String,String> datas) {
		this.data = datas;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

}
