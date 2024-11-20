package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.util.Comparator;

public class EventByPriorityComparator implements Comparator<Event<?>> {

    @Override
    public int compare(Event o1, Event o2) {
        if (o1.getPriority() == o2.getPriority()) {
            return o1.getTimestamp().compareTo(o2.getTimestamp());
        }

        return Integer.compare(o1.getPriority(), o2.getPriority());
    }

}
