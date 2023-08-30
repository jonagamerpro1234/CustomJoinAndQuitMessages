package jss.customjoinandquitmessage.managers;

import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.managers.handlers.Group;
import jss.customjoinandquitmessage.managers.handlers.Single;

import java.util.ArrayList;
import java.util.List;

public class JoinQuitMessageHandlerFactory {

    private final List<AbstractJoinQuitMessageHandler> handlers = new ArrayList<>();
    private AbstractJoinQuitMessageHandler activeHandler;
    private static  JoinQuitMessageHandlerFactory instance;

    public JoinQuitMessageHandlerFactory() {
        instance = this;
        handlers.add(new Single());
        handlers.add(new Group());

        setActiveHandlerByType("normal");
    }

    public void setActiveHandlerByType(String handlerType) {
        for (AbstractJoinQuitMessageHandler handler : handlers) {
            if (handlerType.equalsIgnoreCase("normal") && handler instanceof Single) {
                activeHandler = handler;
                return;
            } else if (handlerType.equalsIgnoreCase("group") && handler instanceof Group) {
                activeHandler = handler;
                return;
            }
        }
    }

    public AbstractJoinQuitMessageHandler getActiveHandler() {
        return activeHandler;
    }

    public void updateActiveHandler() {
        String chatFormatType = Settings.chatformat_type;

        if (chatFormatType.equalsIgnoreCase("normal")) {
            setActiveHandlerByType("normal");
        } else if (chatFormatType.equalsIgnoreCase("group")) {
            setActiveHandlerByType("group");
        }
    }

    public static JoinQuitMessageHandlerFactory getInstance() {
        return instance;
    }
}
