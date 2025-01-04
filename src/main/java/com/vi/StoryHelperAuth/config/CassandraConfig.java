package com.vi.StoryHelperAuth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "story_helper_auth";
    }

    @Override
    public String getContactPoints() {
        return "127.0.0.1:9042";
    }

    @Override
    protected String getLocalDataCenter() {
        return "datacenter1";
    }
}
