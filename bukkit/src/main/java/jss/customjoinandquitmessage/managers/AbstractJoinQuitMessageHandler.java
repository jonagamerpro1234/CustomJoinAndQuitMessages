package jss.customjoinandquitmessage.managers;

public abstract class AbstractJoinQuitMessageHandler {

    public abstract void handlerJoinAndQuitMessages(boolean isJoin);

    public abstract void welcome();

    public abstract void handlerJoinAndQuitTitle(boolean isJoin);

    public abstract void handlerJoinAndQuitActionbar(boolean isJoin);

    public abstract void handlerJoinAndQuitSound(boolean isJoin);

}
