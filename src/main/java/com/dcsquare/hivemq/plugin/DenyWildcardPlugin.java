package com.dcsquare.hivemq.plugin;

import com.dcsquare.hivemq.plugin.callbacks.*;
import com.dcsquare.hivemq.spi.PluginEntryPoint;
import com.dcsquare.hivemq.spi.callback.registry.CallbackRegistry;

import javax.annotation.PostConstruct;

public class DenyWildcardPlugin extends PluginEntryPoint {

    @PostConstruct
    public void postConstruct() {

        CallbackRegistry callbackRegistry = getCallbackRegistry();

        callbackRegistry.addCallback(new DenyWildcardCallback());

    }
}
