package cn.bookingsmart.demo.web;

import cn.bookingsmart.demo.domain.DateVo;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by sun on 2021/11/23.
 *
 * @author sunfuchang03@126.com
 * @since 2.0
 */
@RestController
@RequestMapping("/date")
public class DateConvertDemoController {
	//~ Static fields/constants/initializer


	//~ Instance fields


	//~ Constructors


	//~ Methods

	@GetMapping
	public DateVo queryDate() {
		return DateVo.builder().id(1L).desc("当前日期").createTime(new Date()).build();
	}

	@PostMapping
	public String dateParam(@RequestBody DateVo dateVo) {
		Date createTime = dateVo.getCreateTime();
		return dateVo.getId() + ": " + dateVo.getDesc() + ", " + createTime;
	}
}