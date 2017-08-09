package com.geping.etl.entity;

public class CommentNeo4j extends Neo4jNode {
	public Long cnt;

	public CommentNeo4j(int masterId, int passiveId, Long cnt) {
		this.masterId = masterId;
		this.passiveId = passiveId;
		this.cnt = cnt;
	}

	public Long getCnt() {
		return cnt;
	}

	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}
	
	@Override
	public String toString(){
		return String.format("CommentNeo4j[masterId=%d,passiveId=%d,cnt=%d]", masterId,passiveId,cnt);
	}
}