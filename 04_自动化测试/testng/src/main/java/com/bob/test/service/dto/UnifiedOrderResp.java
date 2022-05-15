package com.bob.test.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 下单返回数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedOrderResp {

    private String payOrderNo;

    private String orderNo;

    private String address;
}
