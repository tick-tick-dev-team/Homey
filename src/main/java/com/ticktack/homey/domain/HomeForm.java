package com.ticktack.homey.domain;

//파일정보를 가지고 있는 html에 뿌리기용 도메인
public class HomeForm {
	
	//home
		private Long homeid;
		private Long userid;
		private String homename;
		private String homeinst;
		private String homeuse;
		private String homethema;
		private Long attfid;
		
		//홈프로필 파일 정보객체
		private Attach attf_obj;
		
		//홈프로필 파일 삭제여부
		private boolean deleteAttach = false;
		
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

		

		public Attach getAttf_obj() {
			return attf_obj;
		}
		public void setAttf_obj(Attach attf_obj) {
			this.attf_obj = attf_obj;
		}
		public boolean isDeleteAttach() {
			return deleteAttach;
		}
		public void setDeleteAttach(boolean deleteAttach) {
			this.deleteAttach = deleteAttach;
		}
		
		//MyHome에서 Home 객체 반환
		public Home getHomeFromHomeForm() {
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
