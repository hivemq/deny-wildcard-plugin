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

import com.hivemq.spi.callback.exception.InvalidSubscriptionException;
import com.hivemq.spi.message.QoS;
import com.hivemq.spi.message.SUBSCRIBE;
import com.hivemq.spi.message.Topic;
import com.hivemq.spi.security.ClientData;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * @author Florian Limpoeck
 */
public class DenyWildcardCallbackTest {

    private DenyWildcardCallback denyWildcardCallback;
    private SUBSCRIBE subscribe;
    private List<Topic> topicList;
    private ClientData clientData;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        denyWildcardCallback = new DenyWildcardCallback();
        subscribe = new SUBSCRIBE();
        topicList = new ArrayList<Topic>();
        clientData = mock(ClientData.class);
        subscribe.setTopics(topicList);
    }


    @Test
    public void test_no_wildcard() throws Exception {
        topicList.add(new Topic("muh", QoS.AT_LEAST_ONCE));
        topicList.add(new Topic("mäh/foo", QoS.AT_LEAST_ONCE));
        topicList.add(new Topic("pfft/#", QoS.AT_LEAST_ONCE));

        denyWildcardCallback.onSubscribe(subscribe, clientData);
    }

    @Test
    public void test_with_wildcard() throws Exception {
        exception.expect(InvalidSubscriptionException.class);

        topicList.add(new Topic("muh", QoS.AT_LEAST_ONCE));
        topicList.add(new Topic("mäh/foo", QoS.AT_LEAST_ONCE));
        topicList.add(new Topic("#", QoS.AT_LEAST_ONCE));

        denyWildcardCallback.onSubscribe(subscribe, clientData);
    }

}
