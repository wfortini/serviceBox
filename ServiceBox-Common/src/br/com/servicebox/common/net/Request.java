package br.com.servicebox.common.net;

import java.io.Serializable;

public class Request implements Serializable{

	
	private static final long serialVersionUID = 1018315777078309117L;
	
	private Long nodeId;

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	
	

}
