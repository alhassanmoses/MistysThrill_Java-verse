package com.mistyinc.mistysthrill.constants;

public enum Gender {


    MALE ( 0),
    FEMALE ( 1),
    TRANSGENDER ( 2);

    private Gender(int type) {
        this.type = type;
    }

    private int type;

    public int getType() {
        return type;
    }
}
