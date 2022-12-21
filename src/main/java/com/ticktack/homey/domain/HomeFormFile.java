package com.ticktack.homey.domain;

import org.springframework.web.multipart.MultipartFile;

//실제파일을 가지고있는 저장시 사용하는 도메인 
public class HomeFormFile {
	
	//home
	Long homeid;
	Long userid;
	String homename;
	String homeinst;
	String homeuse;
	String homethema;
	Long attfid;
	String bgmFileName;

	//file
	MultipartFile attf_obj;
	MultipartFile bgm_obj;
	
	//첨부파일 삭제여부
	boolean deleteAttach = false;
	
	public Long getHomeid() {
		return homeid;
	}
	public void setHomeid(Long homeid) {
		this.homeid = homeid;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getHomename() {
		return homename;
	}
	public void setHomename(String homename) {
		this.homename = homename;
	}
	public String getHomeinst() {
		return homeinst;
	}
	public void setHomeinst(String homeinst) {
		this.homeinst = homeinst;
	}
	public String getHomeuse() {
		return homeuse;
	}
	public void setHomeuse(String homeuse) {
		this.homeuse = homeuse;
	}
	public String getHomethema() {
		return homethema;
	}
	public void setHomethema(String homethema) {
		this.homethema = homethema;
	}
	public Long getAttfid() {
		return attfid;
	}
	public void setAttfid(Long attfid) {
		this.attfid = attfid;
	}

	public String getBgmFileName(){
		return this.bgmFileName;
	}
	public void setBgmFileName(String bgmFileName){
		this.bgmFileName = bgmFileName;
	}
	
	public MultipartFile getAttf_obj() {
		return attf_obj;
	}
	public void setAttf_obj(MultipartFile attf_obj) {
		this.attf_obj = attf_obj;
	}

	public MultipartFile getBgm_obj() {
		return this.bgm_obj;
	}
	public void setBgm_obj(MultipartFile bgm_obj) {
		this.bgm_obj = bgm_obj;
	}

	public boolean isDeleteAttach() {
		return deleteAttach;
	}
	public void setDeleteAttach(boolean deleteAttach) {
		this.deleteAttach = deleteAttach;
	}
	
	
	//MyHome에서 Home 객체 반환
	public Home getHomeFromMFHome() {
		Home home = new Home();
		
		home.setHomeid(this.getHomeid());
		home.setUserid(this.getUserid());
		home.setHomename(this.getHomename());
		home.setHomeinst(this.getHomeinst());
		home.setHomeuse(this.getHomeuse());
		home.setHomethema(this.getHomethema());
		home.setAttfid(this.getAttfid());
		
		return home;
	}

}
