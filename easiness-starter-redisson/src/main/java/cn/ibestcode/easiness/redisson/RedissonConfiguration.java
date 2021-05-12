package cn.ibestcode.easiness.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedissonConfiguration {
  private static final String REDIS_PROTOCOL_PREFIX = "redis://";
  private static final String REDISS_PROTOCOL_PREFIX = "rediss://";
  @Autowired
  private RedisProperties redisProperties;

  @Bean(destroyMethod = "shutdown")
  @ConditionalOnMissingBean(RedissonClient.class)
  public RedissonClient redisson() {
    Config config = null;
    Long timeoutLong = redisProperties.getTimeout().toMillis();
    int timeout;
    if (null == timeoutLong) {
      timeout = 10000;
    } else {
      timeout = timeoutLong.intValue();
    }
    if (redisProperties.getSentinel() != null) {
      List<String> nodes = redisProperties.getSentinel().getNodes();
      config = new Config();
      config.useSentinelServers()
        .setMasterName(redisProperties.getSentinel().getMaster())
        .addSentinelAddress(convert(nodes))
        .setDatabase(redisProperties.getDatabase())
        .setConnectTimeout(timeout)
        .setPassword(redisProperties.getPassword());
    } else if (redisProperties.getCluster() != null) {
      RedisProperties.Cluster cluster = redisProperties.getCluster();
      List<String> nodes = cluster.getNodes();
      config = new Config();
      config.useClusterServers()
        .addNodeAddress(convert(nodes))
        .setConnectTimeout(timeout)
        .setPassword(redisProperties.getPassword());
    } else {
      config = new Config();
      String prefix = REDIS_PROTOCOL_PREFIX;
      if (redisProperties.isSsl()) {
        prefix = REDISS_PROTOCOL_PREFIX;
      }
      config.useSingleServer()
        .setAddress(prefix + redisProperties.getHost()
          + ":" + redisProperties.getPort())
        .setConnectTimeout(timeout)
        .setDatabase(redisProperties.getDatabase())
        .setPassword(redisProperties.getPassword());
    }
    return Redisson.create(config);
  }

  private String[] convert(List<String> nodes) {
    List<String> nodeList = new ArrayList<String>(nodes.size());
    for (String node : nodes) {
      if (!node.startsWith(REDIS_PROTOCOL_PREFIX)
        && !node.startsWith(REDISS_PROTOCOL_PREFIX)) {
        nodeList.add(REDIS_PROTOCOL_PREFIX + node);
      } else {
        nodeList.add(node);
      }
    }
    return nodeList.toArray(new String[nodeList.size()]);
  }
}