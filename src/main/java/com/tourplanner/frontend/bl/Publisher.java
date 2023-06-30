package com.tourplanner.frontend.bl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Publisher {
    static List<Subscriber> subscribers = new ArrayList<>();

    public void subscribe(Subscriber s)
    {
        this.subscribers.add(s);
    }
    public void unsubscribe(Subscriber s)
    {
        this.subscribers.remove(s);
    }
    public static void notifySubscribers(UUID id){
        for(Subscriber s : subscribers){
            s.update(id);
        }
    }

}
