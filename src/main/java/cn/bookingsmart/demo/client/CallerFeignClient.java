package cn.bookingsmart.demo.client;

import cn.bookingsmart.demo.domain.QueryVo;
import cn.bookingsmart.demo.domain.User;
import cn.bookingsmart.result.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by sun on 2019/1/4.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 * @since 1.0
 */
@FeignClient(name = "service-demo", contextId = "feign-demo", url = "http://127.0.0.1:1314")
public interface CallerFeignClient {
	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *
	 * Constants/Initializer
	 *
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */



	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *
	 * Interfaces
	 *
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	@GetMapping(value = "/server/body", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	Response getWithBody(@RequestBody QueryVo query);

	@PostMapping(value = "/server/body", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	Response postWithBody(@RequestBody QueryVo query);

	// 测试返回值 直接写List<User>，省略Response
	@GetMapping(value = "/server/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	List<User> getUsers();
}
