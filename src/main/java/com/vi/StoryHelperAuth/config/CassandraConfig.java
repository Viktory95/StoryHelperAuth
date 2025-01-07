package com.vi.StoryHelperAuth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@PropertySource(value = { "classpath:application.yml" })
@EnableCassandraRepositories(basePackages = "com.vi.StoryHelperAuth.repository")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "story_helper_auth";
    }

    @Override
    public String getContactPoints() {
        return "172.17.0.2:9042";
    }

    @Override
    protected String getLocalDataCenter() {
        return "datacenter1";
    }
}
