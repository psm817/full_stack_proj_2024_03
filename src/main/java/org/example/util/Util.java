// util 패키지를 만들어 해당 패키지로 파일 이동
// 앞으로 유틸과 관련된 클래스는 모두 util 패키지에 모아둘 것
package org.example.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static String getNowDateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return formatter.format(now);
    }
}
