* 업무에서 사용하는데 아직 적용이 안된 코드 수정한뒤 블로그 하기

```java
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class ClusterConfigurationProperties {

    /*
     * spring.redis.cluster.nodes[0] = 127.0.0.1:7379
     * spring.redis.cluster.nodes[1] = 127.0.0.1:7380
     * ...
     */
    List<String> nodes;

    /**
     * Get initial collection of known cluster nodes in format {@code host:port}.
     *
     * @return
     */
    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }
}

@Configuration
public class AppConfig {

    /**
     * Type safe representation of application.properties
     */
    @Autowired ClusterConfigurationProperties clusterProperties;

    public @Bean RedisConnectionFactory connectionFactory() {

        return new JedisConnectionFactory(
            new RedisClusterConfiguration(clusterProperties.getNodes()));
    }
}
```

* 처음에 몇일동안 해당 방법으로 테스트 했는데, 웹서버에 멀티 노드 레디스가 붙지 않았다.

* 헷갈렸던 부분을 AWS 전문가 분께 질문해 보니 다음과 같은 Insight 를 얻을 수 있었다.
    * 기존에 헷갈리고 있었던 부분 
        * Q. 클러스터에 멀티 노드를 바라보고 있는데 웹서버에서 이를 각각 바라봐야 하는지 
        * A . No, 웹서버에서는 클러스터만 바라보고 AWS 내부적으로 각각의 노드를 사용하는 방식. AWS 를 사용하면 Application Layer 에서 클러스터를 직접할 필요가 없는 것.
        * 또, 하나의 노드가 죽었을 때(장애 상황), 새로운 노드를 만들어 클러스터에 등록해 줘야하는데, 새로 생긴 노드의 End point 는 기존과 같을 것이라 보장할 수 없다. 
        이러한 이유로 Application Layer 에서 이를 등록하는 것은 잘못된 것.

* 따라서, 각각의  node end point 가 아닌 cluster의 Configuration Endpoint 만을 바라보도록 설정.

* 코드 상으로는 Jedis 를 통해 redis client 을 구성하는 부분을 Lettuce 로 변경
    * Jedis는 쓰레드에 안전하지 않음, 성능도 Lettuce 가 뛰어남
    * 더 중요한건, 레퍼런스가 Lettuce 가 훨씬 많았음
 
* Configuration 추가   
```java
@Configuration
@PropertySource("classpath:xxx.properties")
public class RedisConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
        clusterConfiguration.clusterNode(redisHost, redisPort);

        return new LettuceConnectionFactory(clusterConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
```