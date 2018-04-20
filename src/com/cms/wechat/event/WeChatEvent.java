package com.cms.wechat.event;

import com.cms.infobeans.wechat.WCInfo;
import com.cms.wechat.WeChatIO;

public interface WeChatEvent<T extends WCInfo> {
    boolean filter(WeChatIO<T> wcInfo);
    void disposeMag(WeChatIO<T> weChatIO);
}
