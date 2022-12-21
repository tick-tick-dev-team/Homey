package com.ticktack.homey.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import lombok.ToString;


@Entity
@JsonIgnoreType
@Table(name = "tb_bgm")
@ToString
public class TbBgm {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="bgm_name")
	private String bgm_name;
	
    @Column(name="bgm_id")
	private String bgm_id;
	
    @Column(name="bgm_path")
	private String bgm_path;
	


	public String getBgm_name() {
		return this.bgm_name;
	}

	public void setBgm_name(String bgm_name) {
		this.bgm_name = bgm_name;
	}



	public String getBgm_id() {
		return this.bgm_id;
	}

	public void setBgm_id(String bgm_id) {
		this.bgm_id = bgm_id;
	}



	public String getBgm_path() {
		return this.bgm_path;
	}

	public void setBgm_path(String bgm_path) {
		this.bgm_path = bgm_path;
	}


	
	// popdo 결과값 테스트용 추가
	@Override
	public String toString() {
		return "Home [homeid=" + homeid + ", userid=" + userid + ", homename=" + homename + ", homeinst=" + homeinst
				+ ", homeuse=" + homeuse + ", homethema=" + homethema + "]";
	}
	
	//Home을 HomeForm 객체로 변환해서 반환, myHome 조회를 위해..
	public HomeForm getFormFromHome() {
		HomeForm form = new HomeForm();
		
		form.setHomeid(this.getHomeid());
		form.setUserid(this.getUserid());
		form.setHomename(this.getHomename());
		form.setHomeinst(this.getHomeinst());
		form.setHomeuse(this.getHomeuse());
		form.setHomethema(this.getHomethema());
		form.setAttfid(this.getAttfid());
		form.setHomebgm(this.getBgmid());
		return form;
	}
	
}