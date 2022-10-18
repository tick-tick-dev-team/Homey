package com.ticktack.homey.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class UHList {

	//사용자
	Long U_ID;
	String U_NAME;
	String U_NICK;
	Date U_JOIN;
	String U_POWER;
	Date U_BIRTH;
	Long U_ATTF_ID;
	
	//집
	Long H_ID;
	String H_NAME;
	String H_INST;
	String H_USE;
	String H_THEMA;
	Long H_ATTF_ID;
	
	// 첨부파일 ID
	Long ATTF_ID;
	// 첨부파일 객체
	MultipartFile ATTF_OBJ;
	// 첨부파일 삭제 여부
	boolean deleteAttach = false;
	
	
	public Long getU_ID() {
		return U_ID;
	}
	public void setU_ID(Long u_ID) {
		U_ID = u_ID;
	}
	public String getU_NAME() {
		return U_NAME;
	}
	public void setU_NAME(String u_NAME) {
		U_NAME = u_NAME;
	}
	public String getU_NICK() {
		return U_NICK;
	}
	public void setU_NICK(String u_NICK) {
		U_NICK = u_NICK;
	}
	public Date getU_JOIN() {
		return U_JOIN;
	}
	public void setU_JOIN(Date u_JOIN) {
		U_JOIN = u_JOIN;
	}
	public String getU_POWER() {
		return U_POWER;
	}
	public void setU_POWER(String u_POWER) {
		U_POWER = u_POWER;
	}
	public Date getU_BIRTH() {
		return U_BIRTH;
	}
	public void setU_BIRTH(Date u_BIRTH) {
		U_BIRTH = u_BIRTH;
	}
	public Long getU_ATTF_ID() {
		return U_ATTF_ID;
	}
	public void setU_ATTF_ID(Long u_ATTF_ID) {
		U_ATTF_ID = u_ATTF_ID;
	}
	public Long getH_ID() {
		return H_ID;
	}
	public void setH_ID(Long h_ID) {
		H_ID = h_ID;
	}
	public String getH_NAME() {
		return H_NAME;
	}
	public void setH_NAME(String h_NAME) {
		H_NAME = h_NAME;
	}
	public String getH_INST() {
		return H_INST;
	}
	public void setH_INST(String h_INST) {
		H_INST = h_INST;
	}
	public String getH_USE() {
		return H_USE;
	}
	public void setH_USE(String h_USE) {
		H_USE = h_USE;
	}
	public String getH_THEMA() {
		return H_THEMA;
	}
	public void setH_THEMA(String h_THEMA) {
		H_THEMA = h_THEMA;
	}
	public Long getH_ATTF_ID() {
		return H_ATTF_ID;
	}
	public void setH_ATTF_ID(Long h_ATTF_ID) {
		H_ATTF_ID = h_ATTF_ID;
	}
	public Long getATTF_ID() {
		return ATTF_ID;
	}
	public void setATTF_ID(Long aTTF_ID) {
		ATTF_ID = aTTF_ID;
	}
	public MultipartFile getATTF_OBJ() {
		return ATTF_OBJ;
	}
	public void setATTF_OBJ(MultipartFile aTTF_OBJ) {
		ATTF_OBJ = aTTF_OBJ;
	}
	public boolean isDeleteAttach() {
		return deleteAttach;
	}
	public void setDeleteAttach(boolean deleteAttach) {
		this.deleteAttach = deleteAttach;
	}
	
	//이거를 User 객체로 반환
	public User getUserFromUHList() {
		User user = new User();
		
		user.setUser_id(this.getU_ID());
		user.setUsernick(this.getU_NICK());
		user.setUserbirth(this.getU_BIRTH());
		user.setUserjoin(this.getU_JOIN());
		user.setUserpower(this.getU_POWER());
		user.setAttf_id(this.getU_ATTF_ID());
		
		return user;
	}
	
	//이거를 Home 객체로 반환
	public Home getHomeFromUHList() {
		Home home = new Home();
		
		home.setHomeid(this.getH_ID());
		home.setHomename(this.getH_NAME());
		home.setHomeinst(this.getH_INST());
		home.setHomeuse(this.getH_USE());
		home.setHomethema(this.getH_THEMA());
		home.setAttfid(this.getH_ATTF_ID());
		
		return home;
	}
}
