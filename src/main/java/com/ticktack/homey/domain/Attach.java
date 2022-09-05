package com.ticktack.homey.domain;

public class Attach {
	
	// 첨부파일 ID (auto-increment)
	Long ATTF_ID;
	
	// 파일경로
	String ATTF_ROUTE;
	
	// 서버측파일명
	String  ATTF_SERNM;
	
	// 실제파일명
	String ATTF_REALNM;
	
	// 파일확장자
	String ATTF_EXE;
	
	// 파일크기
	Long ATTF_SIZE;

	public Long getATTF_ID() {
		return ATTF_ID;
	}

	public void setATTF_ID(Long aTTF_ID) {
		ATTF_ID = aTTF_ID;
	}

	public String getATTF_ROUTE() {
		return ATTF_ROUTE;
	}

	public void setATTF_ROUTE(String aTTF_ROUTE) {
		ATTF_ROUTE = aTTF_ROUTE;
	}

	public String getATTF_SERNM() {
		return ATTF_SERNM;
	}

	public void setATTF_SERNM(String aTTF_SERNM) {
		ATTF_SERNM = aTTF_SERNM;
	}

	public String getATTF_REALNM() {
		return ATTF_REALNM;
	}

	public void setATTF_REALNM(String aTTF_REALNM) {
		ATTF_REALNM = aTTF_REALNM;
	}

	public String getATTF_EXE() {
		return ATTF_EXE;
	}

	public void setATTF_EXE(String aTTF_EXE) {
		ATTF_EXE = aTTF_EXE;
	}

	public Long getATTF_SIZE() {
		return ATTF_SIZE;
	}

	public void setATTF_SIZE(Long aTTF_SIZE) {
		ATTF_SIZE = aTTF_SIZE;
	}
	
	

}
