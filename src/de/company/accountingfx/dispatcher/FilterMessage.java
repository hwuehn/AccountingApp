package de.company.accountingfx.dispatcher;

public class FilterMessage implements IMsg {
    private String msgType;

    @Override
    public String getMsgType() {
        return msgType;
    }
}
