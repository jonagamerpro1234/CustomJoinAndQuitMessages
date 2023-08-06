package jss.customjoinandquitmessage.managers;

import jss.customjoinandquitmessage.managers.handlers.Group;
import jss.customjoinandquitmessage.managers.handlers.Single;

import java.util.ArrayList;
import java.util.List;

public class JoinQuitMessageHandlerFactory  {

    private static final JoinQuitMessageHandlerFactory instance = new JoinQuitMessageHandlerFactory();
    private final List<AbstractJoinQuitMessageHandler> handlers = new ArrayList<>();
    private AbstractJoinQuitMessageHandler activeHandler;

    private JoinQuitMessageHandlerFactory(){
        handlers.add(new Single());
        handlers.add(new Group());

        setActiveHandlerByType("single");
    }

    public void setActiveHandlerByType(String handlerType) {
        for (AbstractJoinQuitMessageHandler handler : handlers) {
            if (handlerType.equalsIgnoreCase("single") && handler instanceof Single) {
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

    public static JoinQuitMessageHandlerFactory getInstance() {
        return instance;
    }
}
