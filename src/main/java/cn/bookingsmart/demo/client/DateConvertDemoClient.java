package cn.bookingsmart.demo.client;

import cn.bookingsmart.demo.domain.DateVo;
import cn.bookingsmart.result.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sun on 2021/11/23.
 *
 * @author sunfuchang03@126.com
 * @since 3.0
 */
@FeignClient(name = "service-demo", contextId = "date-demo", url = "http://127.0.0.1:1314")
public interface DateConvertDemoClient {
	//~ Constants/Initializer


	//~ Interfaces

	@GetMapping(value = "/date", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	Response<DateVo> queryDate();

	@PostMapping(value = "/date", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	Response<String> dateParam(@RequestBody DateVo dateVo);
}
