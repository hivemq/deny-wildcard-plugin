package com.dcsquare.hivemq.plugin.callbacks;

import com.dcsquare.hivemq.spi.callback.CallbackPriority;
import com.dcsquare.hivemq.spi.callback.events.OnSubscribeCallback;
import com.dcsquare.hivemq.spi.callback.exception.InvalidSubscriptionException;
import com.dcsquare.hivemq.spi.message.SUBSCRIBE;
import com.dcsquare.hivemq.spi.message.Topic;
import com.dcsquare.hivemq.spi.security.ClientData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * DenyWildcard-Plugin is a plugin which denies a wildcard subscription
 * on top level for any Client. That means that you are not allowed to
 * subscribe for "#" only. Any sub level wildcard subscription like "/house/#"
 * is not affected and still possible.
 * Your Client disconnects after subscription on top level wildcard.
 *
 * @author Florian Limpoeck
 */
public class DenyWildcardCallback implements OnSubscribeCallback {


    public static final String WILDCARD = "#";
    private static Logger logger = LoggerFactory.getLogger(DenyWildcardCallback.class);

    @Override
    public void onSubscribe(final SUBSCRIBE subscribe, final ClientData clientData) throws InvalidSubscriptionException {
        final List<Topic> topics = subscribe.getTopics();

        for (Topic topic : topics) {
            if (WILDCARD.equals(topic.getTopic())) {
                logger.debug("Client {} disconnected because of an invalid subscription to '#'", clientData.getClientId());
                throw new InvalidSubscriptionException();
            }
        }
    }

    @Override
    public int priority() {
        return CallbackPriority.HIGH;
    }
}
