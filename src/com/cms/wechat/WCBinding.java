package com.cms.wechat;

import com.cms.infobeans.wechat.WCText;
import com.cms.wechat.event.WCTextEvent;

public class WCBinding implements WCTextEvent {
    @Override
    public boolean filter(WeChatIO<WCText> wcInfo) {
        return false;
    }

    @Override
    public void disposeMag(WeChatIO<WCText> weChatIO) {

    }
}
