package com.androidtest.gdxgame.EventManager;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private List<BaseEvent> eventList;
    private  List<Object> dataList;

    public EventManager() {
        eventList = new ArrayList<>();
        dataList = new ArrayList<>();
    }

    public void addEvent(BaseEvent event){
        eventList.add(event);
        if (event instanceof StringByChar) {
            dataList.add("");
        }
        if (event instanceof StringBySpace) {
            dataList.add("");
        }
    }

    public void step() {
        for (int i=0; i<eventList.size(); i++){
            if (eventList.get(i).step()){
                if (eventList.get(i) instanceof StringByChar){
                    StringByChar tmpEvent = (StringByChar) eventList.get(i);
                    dataList.set(i, tmpEvent.event());
                }
                if (eventList.get(i) instanceof StringBySpace){
                    StringBySpace tmpEvent = (StringBySpace) eventList.get(i);
                    dataList.set(i, tmpEvent.event());
                }
            }
        }
    }

    public Object getData(int i){return dataList.get(i);}
    public int size() {return eventList.size();}
}
