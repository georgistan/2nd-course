package bg.sofia.uni.fmi.mjt.eventbus;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventBusImpl implements EventBus {
    private Map<Class<?>, Set<Subscriber<?>>> subscribersOfEventType;
    private Map<Class<?>, Set<Event<?>>> eventsOfEventType;

    {
        subscribersOfEventType = new HashMap<>();
        eventsOfEventType = new HashMap<>();
    }

    @Override
    public <T extends Event<?>> void subscribe(Class<T> eventType, Subscriber<? super T> subscriber) {
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null.");
        }

        if (subscriber == null) {
            throw new IllegalArgumentException("Subscriber cannot be null.");
        }

        if (!subscribersOfEventType.containsKey(eventType)) {
            subscribersOfEventType.put(eventType, new HashSet<>());

            subscribersOfEventType.get(eventType).add(subscriber);
        }

        subscribersOfEventType.get(eventType).add(subscriber);
    }

    @Override
    public <T extends Event<?>> void unsubscribe(Class<T> eventType, Subscriber<? super T> subscriber)
        throws MissingSubscriptionException {
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null.");
        }

        if (subscriber == null) {
            throw new IllegalArgumentException("Subscriber cannot be null.");
        }

        if (!subscribersOfEventType.containsKey(eventType)) {
            throw new MissingSubscriptionException("Cannot unsubscribe user from non-existing event type.");
        }

        if (!subscribersOfEventType.get(eventType).contains(subscriber)) {
            throw new MissingSubscriptionException("Cannot unsubscribe user that is not present in the event type.");
        }

        subscribersOfEventType.get(eventType).remove(subscriber);
    }

    @Override
    public <T extends Event<?>> void publish(T event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null.");
        }

        if (!eventsOfEventType.containsKey(event.getClass())) {
            eventsOfEventType.put(event.getClass(), new HashSet<>());
        }

        eventsOfEventType.get(event.getClass()).add(event);

        if (!subscribersOfEventType.containsKey(event.getClass())) {
            subscribersOfEventType.put(event.getClass(), new HashSet<>());
            return;
        }

        Set<Subscriber<?>> subs = subscribersOfEventType.get(event.getClass());
        if (subs != null) {
            for (Subscriber<?> subscriber : subs) {
                Subscriber<T> typedSubscriber = (Subscriber<T>) subscriber;
                typedSubscriber.onEvent(event);
            }
        }
    }

    @Override
    public void clear() {
        subscribersOfEventType.clear();
        eventsOfEventType.clear();
    }

    @Override
    public Collection<? extends Event<?>> getEventLogs(Class<? extends Event<?>> eventType, Instant from, Instant to) {
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null.");
        }

        if (from == null) {
            throw new IllegalArgumentException("From cannot be null.");
        }

        if (to == null) {
            throw new IllegalArgumentException("To cannot be null.");
        }

        if (from.equals(to) || eventsOfEventType.isEmpty()) {
            return Collections.emptyList();
        }

        if (!eventsOfEventType.containsKey(eventType)) {
            return Collections.emptyList();
        }

        Collection<Event<?>> result = new HashSet<>();
        for (Event<?> event : eventsOfEventType.get(eventType)) {
            if (!event.getTimestamp().isBefore(from) && event.getTimestamp().isBefore(to)) {
                result.add(event);
            }
        }

        return Collections.unmodifiableCollection(result);
    }

    @Override
    public <T extends Event<?>> Collection<Subscriber<?>> getSubscribersForEvent(Class<T> eventType) {
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null.");
        }

        if (!subscribersOfEventType.containsKey(eventType)) {
            return Collections.unmodifiableCollection(new HashSet<>());
        }

        return Set.copyOf(subscribersOfEventType.get(eventType));
    }
}
