package com.dcsquare.hivemq.plugin.callbacks;

import com.dcsquare.hivemq.spi.callback.exception.InvalidSubscriptionException;
import com.dcsquare.hivemq.spi.message.QoS;
import com.dcsquare.hivemq.spi.message.SUBSCRIBE;
import com.dcsquare.hivemq.spi.message.Topic;
import com.dcsquare.hivemq.spi.security.ClientData;
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
