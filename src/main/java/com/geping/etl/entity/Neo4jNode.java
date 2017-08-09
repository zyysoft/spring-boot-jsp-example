package com.geping.etl.entity;

public class Neo4jNode {
	
	// 邀请人
	public int masterId;
	// 被邀请人
	public int passiveId;
	
	public int getMasterId() {
		return masterId;
	}

	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}

	public int getPassiveId() {
		return passiveId;
	}

	public void setPassiveId(int passiveId) {
		this.passiveId = passiveId;
	}
}