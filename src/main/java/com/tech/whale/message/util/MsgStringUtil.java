package com.tech.whale.message.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MsgStringUtil {
    public String formatMessageTime(Timestamp msgTime) {
        // 한국어 로케일을 사용하여 SimpleDateFormat 생성
        SimpleDateFormat sdf = new SimpleDateFormat("a h:mm", Locale.KOREAN);
        String formattedTime = sdf.format(msgTime);
        // 오전/오후에 따라 한국어로 변환
        return formattedTime;
    }
}
