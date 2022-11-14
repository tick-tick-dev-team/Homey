package com.ticktack.homey.auth;

enum Role {
    ROLE_USER("사용자"),
    ROLE_ADMIN("관리자"),
	ROLE_MANAGER("매니저");

    private String label;

    private Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}