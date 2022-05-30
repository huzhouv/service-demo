package cn.bookingsmart.demo.web;

import cn.bookingsmart.demo.ServiceDemoApplicationTests;
import cn.bookingsmart.demo.domain.User;
import cn.bookingsmart.redis.RedisClient;
import com.belonk.common.json.JacksonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sun on 2019/1/7.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 * @since 1.0
 */
public class RedisClientTest extends ServiceDemoApplicationTests {
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Static fields/constants/initializer
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */



    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Instance fields
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;

    @Resource
    private RedisClient<String, Object> redisClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Random random = new Random();

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Constructors
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */



    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Public Methods
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    @Before
    public void before() {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysExpect(status().isOk())
                .build();
    }

    @Test
    public void testValueString() {
        String key = "test:value:" + random.nextInt(1000);
        System.out.println("key : " + key);
        redisClient.set(key, String.valueOf(random.nextInt()));
        Object v = redisClient.get(key);
        System.out.println(v);

        redisClient.set(key, JacksonUtil.toJson(new User("张三", 20, "15100001111")));
        System.out.println("value : " + redisClient.get(key));

        redisClient.set(key, new User("王老五", 20, "15100001111"));
        System.out.println("value : " + redisClient.get(key));

        List<User> users = new ArrayList<>();
        users.add(new User("张三", 20, "15100001111"));
        users.add(new User("李si", 30, "11200001111"));
        users.add(new User("wangwu", 60, "13300001111"));
        redisClient.set(key, JacksonUtil.toJson(users));
        System.out.println("value : " + redisClient.get(key));
    }

    @Test
    public void testValueString1() {
        String key = "test:value:" + random.nextInt(1000);
        System.out.println("key : " + key);

        redisClient.set(key, JacksonUtil.toJson(new User("张三", 20, "15100001111")));
        System.out.println("value : " + redisClient.get(key));

        key = "test:value:" + random.nextInt(1000);
        System.out.println("key : " + key);
        redisClient.set(key, new User("王老五", 20, "15100001111"));
        System.out.println("value : " + redisClient.get(key));
    }

    @Test
    public void testValueString2() {
        String key = "test:value:" + random.nextInt(1000);
        System.out.println("key : " + key);

        stringRedisTemplate.opsForValue().set(key, JacksonUtil.toJson(new User("张三", 20, "15100001111")));
        System.out.println("value : " + stringRedisTemplate.opsForValue().get(key));

        key = "test:value:" + random.nextInt(1000);
        System.out.println("key : " + key);
        stringRedisTemplate.opsForValue().set(key, JacksonUtil.toJson(new User("王老五", 20, "15100001111")));
        System.out.println("value : " + stringRedisTemplate.opsForValue().get(key));
    }

    @Test
    public void testValue1() {
        String key = "test:value:" + random.nextInt(1000);
        System.out.println("key : " + key);

        redisClient.set(key, new User("张三", 20, "15100001111"));
        System.out.println("value : " + redisClient.get(key));

        key = "test:value:" + random.nextInt(1000);
        System.out.println("key : " + key);
        redisClient.set(key, new User("王老五", 20, "15100001111"));
        System.out.println("value : " + redisClient.get(key));
    }

    @Test
    public void testValue() throws InterruptedException {
        String key = "test:value:" + random.nextInt(1000);
        System.out.println("key : " + key);

        Object v;
        boolean success;
        Map<String, Object> map;
        List<String> keys;
        List<Object> values;

        // basic set

        int randomInt = random.nextInt();
        redisClient.set(key, randomInt);
        v = redisClient.get(key);
        System.out.println(v);
        Assert.assertEquals(randomInt, v);

        // set object

        User user = new User("张三", 20, "15100001111");
        redisClient.set(key, user);
        v = redisClient.get(key);
        System.out.println("value : " + v);
        Assert.assertTrue(v instanceof User);
        User dest = (User) v;
        Assert.assertEquals(dest.getUsername(), user.getUsername());

        // set list

        List<User> users = new ArrayList<>();
        users.add(new User("张三", 20, "15100001111"));
        users.add(new User("李si", 30, "11200001111"));
        users.add(new User("wangwu", 60, "13300001111"));
        redisClient.set(key, users);
        v = redisClient.get(key);
        System.out.println("value : " + v);
        Assert.assertTrue(v instanceof List);
        List<User> destUsers = (List<User>) v;
        Assert.assertEquals(destUsers.size(), users.size());

        // set value with expired

        randomInt = random.nextInt(200);
        redisClient.set(key, randomInt, (Integer) 1);
        Thread.sleep(1000);
        v = redisClient.get(key);
        Assert.assertNull(v);

        // setIfAbsent

        success = redisClient.setIfAbsent(key, randomInt);
        Assert.assertTrue(success);
        success = redisClient.setIfAbsent(key, new User());
        Assert.assertFalse(success);

        // multiSet

        map = new HashMap<>();
        map.put("test1", 1);
        map.put("test2", 2);
        keys = new ArrayList<>();
        keys.add("test1");
        keys.add("test2");
        redisClient.multiSet(map);
        values = redisClient.multiGet(keys);
        Assert.assertEquals(map.size(), values.size());
        Assert.assertEquals(1, values.get(0));
        Assert.assertEquals(2, values.get(1));

        // multiSetIfAbsent

        success = redisClient.multiSetIfAbsent(map);
        Assert.assertFalse(success);
        map = new HashMap<>();
        map.put("test3", 3);
        map.put("test4", 4);
        redisClient.delete("test3");
        redisClient.delete("test4");
        success = redisClient.multiSetIfAbsent(map);
        Assert.assertTrue(success);

        // getAndSet

        randomInt = random.nextInt(200);
        redisClient.set(key, randomInt);
        v = redisClient.get(key);
        Assert.assertEquals(randomInt, v);
        v = redisClient.getAndSet(key, 0);
        Assert.assertEquals(randomInt, v);
        v = redisClient.get(key);
        Assert.assertEquals(0, v);

        // increment

        String incKey = "test:incr:" + random.nextInt(1000);
        System.out.println(incKey);
        v = redisClient.increment(incKey);
        Assert.assertEquals(1L, v);
        v = redisClient.increment(incKey, 20);
        Assert.assertEquals(21L, v);
        v = redisClient.increment(incKey, 0.1);
        Assert.assertEquals(21.1, v);

        // length

        v = redisClient.size(key);
        Assert.assertEquals(81L, v);
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Protected Methods
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */



    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Property accessors
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */



    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Inner classes
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */


}