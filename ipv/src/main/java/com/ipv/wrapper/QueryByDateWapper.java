package com.ipv.wrapper;

import java.util.Date;

public class QueryByDateWapper {
	int id;
	Date start;
	Date end;
	
	public QueryByDateWapper(int id) {
		super();
		this.id = id;
	}
	
	public QueryByDateWapper(int id, Date start, Date end) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "QueryByDateWapper [id=" + id + ", start=" + start + ", end=" + end + "]";
	}
	
	
}
