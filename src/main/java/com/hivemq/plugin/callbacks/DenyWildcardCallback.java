/*
 * Copyright 2015 dc-square GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hivemq.plugin.callbacks;

import com.hivemq.spi.callback.CallbackPriority;
import com.hivemq.spi.callback.events.OnSubscribeCallback;
import com.hivemq.spi.callback.exception.InvalidSubscriptionException;
import com.hivemq.spi.message.SUBSCRIBE;
import com.hivemq.spi.message.Topic;
import com.hivemq.spi.security.ClientData;
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
