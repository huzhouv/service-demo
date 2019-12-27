package cn.bookingsmart.demo.domain;

import cn.bookingsmart.query.order.OrderBy;
import cn.bookingsmart.query.order.OrderType;
import cn.bookingsmart.query.rule.Equal;
import cn.bookingsmart.query.rule.Like;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryVo {
    /**
     * 积分卡名称
     */
    @Like
    private String name;
    /**
     * 状态码
     */
    @Equal
    private Integer status;
    /**
     * 主题id
     */
    @Equal
    @NotNull(message = "主体id不能为空！")
    private String subjectId;

    @OrderBy(type = OrderType.DESC)
    private Date createTime;


}
