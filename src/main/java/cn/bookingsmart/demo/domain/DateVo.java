package cn.bookingsmart.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by sun on 2021/11/23.
 *
 * @author sunfuchang03@126.com
 * @since 3.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateVo {
	//~ Static fields/constants/initializer


	//~ Instance fields

	private Long id;
	private String desc;
	// 测试不指定日期格式，是否能序列化和反序列化，指定指定时区，否则时区不正确
	// @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	//~ Constructors


	//~ Methods


}