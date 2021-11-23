package cn.bookingsmart.demo.web;

import cn.bookingsmart.demo.domain.DateVo;
import cn.bookingsmart.http.RestClient;
import cn.bookingsmart.http.vo.Head;
import cn.bookingsmart.http.vo.RequestParam;
import cn.bookingsmart.util.SignUtil;
import com.belonk.common.json.JacksonUtil;
import com.belonk.commons.util.asserts.Assert;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun on 2021/11/23.
 *
 * @author sunfuchang03@126.com
 * @since 3.0
 */
public class DateConvertDemoControllerTest {
	//~ Static fields/constants/initializer

	private static final String uri = "http://192.168.0.16:6666/service-demo/date";
	// private static final String uri = "http://api-uat.abcbooking.cn/service-demo/date";
	private static final String service_uri = "http://192.168.0.102:1314";
	private static final String APP_ID = "AAA-0571-0002";
	private static final String APP_SECRET = "7186140a0ddd41dbb6b53f8b6a43c752a77fad55183f4e81";

	//~ Instance fields


	//~ Constructors


	//~ Methods

	@Test
	public void testDateJson() {
		DateVo dateVo = DateVo.builder().id(1L).desc("当前日期").createTime(new Date()).build();
		System.out.println(JacksonUtil.toJson(dateVo));
		// 输出：{"id":1,"desc":"当前日期","createTime":"2021-11-23 12:17:00"}
	}

	@Test
	public void testDateJson1() {
		String json = "{\"id\":1,\"desc\":\"当前日期\",\"createTime\":" + new Date().getTime() + "}";
		DateVo dateVo = JacksonUtil.fromJson(json, DateVo.class);
		System.out.println(dateVo);
	}

	// ==== 测试服务器未配置spring.jackson.date-format时，默认序列化和反序列化的方式和正确性
	// 两种方式测试：1、直接调用接口；2、通过Feign客户端

	// 1、直接通过网关调用接口

	@Test
	public void testQueryWithoutDateFormat() {
		Map<String, Object> map = new HashMap<>();
		RequestParam requestParam = new RequestParam();
		Head head = new Head();
		requestParam.setHead(head);
		requestParam.setBody(map);
		head.setAppid(APP_ID);
		head.setSign(SignUtil.sign(APP_ID, map, APP_SECRET));
		head.setVersion("1.0");

		String result = RestClient.getWithSignByForm(uri, JacksonUtil.toJson(requestParam));
		System.out.println(result);
		// 输出：{"rtnCode":0,"rtnMsg":"ok","data":{"id":1,"desc":"当前日期","createTime":"2021-11-23T11:58:35.961+0800"}}
	}

	@Test
	public void testPostWithoutDateFormat() {
		String json = "{\"id\":1,\"desc\":\"当前日期\",\"createTime\":\"2021-11-23T11:58:35.961+0800\"}";
		DateVo dateVo = JacksonUtil.fromJson(json, DateVo.class);
		Assert.notNull(dateVo);

		RequestParam requestParam = new RequestParam();
		Head head = new Head();
		requestParam.setHead(head);
		requestParam.setBody(dateVo);
		head.setAppid(APP_ID);
		head.setSign(SignUtil.sign(APP_ID, dateVo, APP_SECRET));
		head.setVersion("1.0");

		String result = RestClient.postWithSign(uri, JacksonUtil.toJson(requestParam));
		System.out.println(result);
		// 输出：{"rtnCode":0,"rtnMsg":"ok","data":"1: 当前日期, Tue Nov 23 11:58:35 CST 2021"}
	}

	// 2、通过Feign客户端调用接口
	@Test
	public void testFeignQueryWithoutDateFormat() {
		String result = RestClient.getByForm(service_uri + "/date/feign");
		System.out.println(result);
		// 输出：{"rtnCode":0,"rtnMsg":"ok","data":{"id":1,"desc":"当前日期","createTime":"2021-11-23T12:51:33.981+0800"}}
	}

	@Test
	public void testFeignPostWithoutDateFormat() {
		// yyyy-MM-dd'T'HH:mm:ss.SSSXXX
		String json = "{\"id\":1,\"desc\":\"当前日期\",\"createTime\":\"2021-11-23T11:58:35.961+0800\"}";
		DateVo dateVo = JacksonUtil.fromJson(json, DateVo.class);
		Assert.notNull(dateVo);
		String result = RestClient.post(service_uri + "/date/feign", dateVo);
		System.out.println(result);
		// 输出：{"rtnCode":0,"rtnMsg":"ok","data":"1: 当前日期, Tue Nov 23 11:58:35 CST 2021"}
	}

	// ==== 测试服务器配置spring.jackson.date-format=yyyy-MM-dd HH:mm:ss时，序列化和反序列化的方式和正确性

	// 1、直接通过网关调用接口

	@Test
	public void testQueryWithDateFormat() {
		Map<String, Object> map = new HashMap<>();
		RequestParam requestParam = new RequestParam();
		Head head = new Head();
		requestParam.setHead(head);
		requestParam.setBody(map);
		head.setAppid(APP_ID);
		head.setSign(SignUtil.sign(APP_ID, map, APP_SECRET));
		head.setVersion("1.0");

		String result = RestClient.getWithSignByForm(uri, JacksonUtil.toJson(requestParam));
		System.out.println(result);
		// 输出：{"rtnCode":0,"rtnMsg":"ok","data":{"id":1,"desc":"当前日期","createTime":"2021-11-23 13:14:02"}}
	}

	@Test
	public void testPostWithDateFormat() {
		String json = "{\"id\":1,\"desc\":\"当前日期\",\"createTime\":\"2021-11-23 13:14:02\"}";
		// 由于spring将日期格式化为固定格式，因此，DateVo上的createTime必须指定相同的反序列化时的日期格式（@JsonFormat），否则不能成功反序列化
		DateVo dateVo = JacksonUtil.fromJson(json, DateVo.class);
		Assert.notNull(dateVo);

		RequestParam requestParam = new RequestParam();
		Head head = new Head();
		requestParam.setHead(head);
		requestParam.setBody(dateVo);
		head.setAppid(APP_ID);
		head.setSign(SignUtil.sign(APP_ID, dateVo, APP_SECRET));
		head.setVersion("1.0");

		String result = RestClient.postWithSign(uri, JacksonUtil.toJson(requestParam));
		System.out.println(result);
		// 输出：{"rtnCode":0,"rtnMsg":"ok","data":"1: 当前日期, Tue Nov 23 13:14:02 CST 2021"}
	}

	// 2、通过Feign客户端调用接口

	@Test
	public void testFeignQueryWithDateFormat() {
		String result = RestClient.getByForm(service_uri + "/date/feign");
		System.out.println(result);
		// 输出：{"rtnCode":0,"rtnMsg":"ok","data":{"id":1,"desc":"当前日期","createTime":"2021-11-23 13:16:42"}}
	}

	@Test
	public void testFeignPostWithDateFormat() {
		String json = "{\"id\":1,\"desc\":\"当前日期\",\"createTime\":\"2021-11-23 13:16:42\"}";

		// 由于spring将日期格式化为固定格式，因此，DateVo上的createTime必须指定相同的反序列化时的日期格式（@JsonFormat），否则不能成功反序列化
		// 并且，spring.jackson.timeZone中指定了时区，DateVo中同样需要指定时区，否则时区不正确
		DateVo dateVo = JacksonUtil.fromJson(json, DateVo.class);
		Assert.notNull(dateVo);
		String result = RestClient.post(service_uri + "/date/feign", dateVo);
		System.out.println(result);
		// 输出：{"rtnCode":0,"rtnMsg":"ok","data":"1: 当前日期, Tue Nov 23 11:16:42 CST 2021"}
	}

	@Test
	public void testFeignPostWithDateFormatByLong() {
		// createTime传递long，Spring仍然能够解析，见：HandlerMethodArgumentResolverComposite的resolveArgument方法，底层实际上
		// 还是利用: MappingJackson2HttpMessageConverter的read方法来转换
		String json = "{\"id\":1,\"desc\":\"当前日期\",\"createTime\":" + new Date().getTime() + "}";
		String result = RestClient.post(service_uri + "/date/feign", json);
		System.out.println(result);
		// 输出：{"rtnCode":0,"rtnMsg":"ok","data":"1: 当前日期, Tue Nov 23 16:16:52 CST 2021"}
	}
}