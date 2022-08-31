package com.ticktack.homey.domain;

import java.util.Date;

public class Post {
	Long POST_ID;
	String POST_CONT;
	Long POST_WRITER;
	Date POST_DATE;
	Long POST_UWRITER;
	Date POST_UPDATE;
	Long POST_HOME;
	
	
	
	
	public Long getPOST_ID() {
		return POST_ID;
	}
	public void setPOST_ID(Long pOST_ID) {
		POST_ID = pOST_ID;
	}
	public String getPOST_CONT() {
		return POST_CONT;
	}
	public void setPOST_CONT(String pOST_CONT) {
		POST_CONT = pOST_CONT;
	}
	public Long getPOST_WRITER() {
		return POST_WRITER;
	}
	public void setPOST_WRITER(Long pOST_WRITER) {
		POST_WRITER = pOST_WRITER;
	}
	public Date getPOST_DATE() {
		return POST_DATE;
	}
	public void setPOST_DATE(Date pOST_DATE) {
		POST_DATE = pOST_DATE;
	}
	public Long getPOST_UWRITER() {
		return POST_UWRITER;
	}
	public void setPOST_UWRITER(Long pOST_UWRITER) {
		POST_UWRITER = pOST_UWRITER;
	}
	public Date getPOST_UPDATE() {
		return POST_UPDATE;
	}
	public void setPOST_UPDATE(Date pOST_UPDATE) {
		POST_UPDATE = pOST_UPDATE;
	}
	public Long getPOST_HOME() {
		return POST_HOME;
	}
	public void setPOST_HOME(Long pOST_HOME) {
		POST_HOME = pOST_HOME;
	}
	
	
}
