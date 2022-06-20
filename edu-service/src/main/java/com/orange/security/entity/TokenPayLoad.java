package com.orange.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author orange
 * 用于向JWT的Payload中写入数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenPayLoad {
	/**
	 * 主题
	 */
	private String sub;
	/**
	 * 签发时间
	 */
	private Long iat;
	/**
	 * 过期时间
	 */
	private Long exp;
	/**
	 * JWT的ID
	 */
	private String jti;
}