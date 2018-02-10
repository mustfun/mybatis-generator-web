package com.github.mustfun.generator.model.po;

import java.util.Date;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/4/26
 * @since 1.0
 */
public class Order {
    private Long orderId;

    private Long userId;

    private Date createTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
