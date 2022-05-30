package cn.bookingsmart.demo.web;

import cn.bookingsmart.base.http.RestClient;
import cn.bookingsmart.base.http.vo.Head;
import cn.bookingsmart.base.http.vo.RequestParam;
import cn.bookingsmart.base.result.Response;
import cn.bookingsmart.base.util.SignUtil;
import cn.bookingsmart.demo.dao.EmojiDao;
import cn.bookingsmart.demo.entity.Emoji;
import com.belonk.common.json.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 要支持数据库存储emoji，则编码必须改为ut8mb4。
 * <pre>
 * mysql> show variables like '%character%';
 * +--------------------------+-----------------------------------------------------------+
 * | Variable_name            | Value                                                     |
 * +--------------------------+-----------------------------------------------------------+
 * | character_set_client     | utf8mb4                                                   |
 * | character_set_connection | utf8mb4                                                   |
 * | character_set_database   | utf8mb4                                                   |
 * | character_set_filesystem | binary                                                    |
 * | character_set_results    | utf8mb4                                                   |
 * | character_set_server     | utf8mb4                                                   |
 * | character_set_system     | utf8                                                      |
 * | character_sets_dir       | /usr/local/mysql-5.7.19-macos10.12-x86_64/share/charsets/ |
 * +--------------------------+-----------------------------------------------------------+
 * <pre>
 * 如果不是utbmb4，则：
 * <prv>vi /etc/my.cnf</prv>
 * 将client、mysqld的默认编码改为utbmb4：
 * <pre>
 * [client]
 * default-character-set=utf8mb4
 * [mysqld]
 * character-set-server=utf8mb4
 * </pre>
 * <p>
 * Created by sun on 2019/11/9.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/emoji")
public class EmojiController {
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

    private final EmojiDao emojiDao;

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Constructors
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    @Autowired
    public EmojiController(EmojiDao emojiDao) {
        this.emojiDao = emojiDao;
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Methods
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    @RequestMapping("/param")
    public Response emojiParam(String emoji) {
        System.err.println(emoji);
        Emoji emojiEntity = new Emoji();
        emojiEntity.setEmoji(emoji);
        emojiDao.save(emojiEntity);
        return Response.success(emoji);
    }

    @RequestMapping("/sign")
    public Response sign() throws Exception {
        String param = "你好啊，belonk！☺☺☺";

        TreeMap<String, String> pram = new TreeMap<>();
        pram.put("emoji", param);

        String appid = "HOTEL-0571-0000";
        String appkey = "fbe938c4bfe0a7cda1dcae7c85c7f83e37736207d637dc1a";
        String sign = SignUtil.sign(appid, pram, appkey);

        RequestParam requestParam = new RequestParam();
        Head head = new Head();
        head.setAppid(appid);
        head.setSign(sign);
        requestParam.setHead(head);
        requestParam.setBody(pram);

        String body = JacksonUtil.toJson(requestParam);
        System.err.println("request param : " + body);
        String result = RestClient.getWithSignByForm("http://192.168.0.204:8889/api/service-demo/emoji/param", body);
        System.err.println("result: " + result);
        return Response.success(result);
    }

    // public static void main(String[] args) {
    //     String param = "你好啊，belonk！☺☺☺";
    //     Map map = new HashMap<>();
    //     map.put("emoji", param);
    //     String json = JacksonUtil.toJson(map);
    //     System.err.println(json);
    //
    //     Emoji emoji = JacksonUtil.fromJson(json, Emoji.class);
    //     System.err.println(emoji);
    //
    //     map = JacksonUtil.fromJson(json, Map.class);
    //     System.err.println(map);
    //
    //     map = JacksonUtil.fromJson(json, TreeMap.class);
    //     System.err.println(map);
    // }
}