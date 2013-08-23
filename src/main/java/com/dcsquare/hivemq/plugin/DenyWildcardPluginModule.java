package com.dcsquare.hivemq.plugin;

import com.dcsquare.hivemq.spi.HiveMQPluginModule;
import com.dcsquare.hivemq.spi.PluginEntryPoint;
import com.dcsquare.hivemq.spi.plugin.meta.Information;
import com.google.inject.Provider;
import org.apache.commons.configuration.AbstractConfiguration;

import static com.dcsquare.hivemq.spi.config.Configurations.*;


@Information(name = "HiveMQ DenyWildcard Plugin", author = "Florian Limpoeck", version = "1.0")
public class DenyWildcardPluginModule extends HiveMQPluginModule {


    @Override
    public Provider<Iterable<? extends AbstractConfiguration>> getConfigurations() {
        return noConfigurationNeeded();
    }

    @Override
    protected void configurePlugin() {
        // no further configuration needed
    }

    @Override
    protected Class<? extends PluginEntryPoint> entryPointClass() {
        return DenyWildcardPlugin.class;
    }
}
