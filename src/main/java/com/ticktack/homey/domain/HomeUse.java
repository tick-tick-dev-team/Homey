package com.ticktack.homey.domain;

enum HomeUse {
    Y("공개"),
    N("비공개"),
    B("강제폐쇄");

    private String label;

    private HomeUse(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
