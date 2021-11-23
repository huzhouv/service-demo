package cn.bookingsmart.demo.web;

import cn.bookingsmart.demo.client.DateConvertDemoClient;
import cn.bookingsmart.demo.domain.DateVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by sun on 2021/11/23.
 *
 * @author sunfuchang03@126.com
 * @since 3.0
 */
@RestController
@RequestMapping("/date/feign")
public class DateConvertDemoClientCaller {
	//~ Static fields/constants/initializer


	//~ Instance fields

	@Resource
	private DateConvertDemoClient dateConvertDemoClient;

	//~ Constructors


	//~ Methods

	@GetMapping
	public DateVo get() { return dateConvertDemoClient.queryDate().getData();
	}

	@PostMapping
	public String post(@RequestBody DateVo dateVo) {
		return dateConvertDemoClient.dateParam(dateVo).getData();
	}
}