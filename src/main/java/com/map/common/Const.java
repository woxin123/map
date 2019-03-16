package com.map.common;

/**
 * @author mengchen
 * @time 19-3-15 下午3:56
 */
public interface Const {

    interface UserStaus {
        int LOCK = 1;
        int UNLOCK = 0;
    }

    interface SEX {
        int WOMAN = 0;
        int MAN = 1;
        int SECRET = 2;
        String WOMAN_STR = "女";
        String MAN_STR = "男";
        String SECRET_STR = "秘密";
    }


}
