package com.orange.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author orange
 */
@Data
@Accessors(chain = true)
public class R<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 业务代码
	 */
	private Integer code;

	// 返回消息
	private String message;

	// 承载数据
	private T data;

	private R() {
	}

	public static R success() {
		R r = new R<>();
		r.code(ResultCode.SUCCESS);
		r.message(ResultCode.SUCCESS.getMessage());
		return r;
	}

	public static R fail() {
		R r = new R<>();
		r.code(ResultCode.UNKNOWN);
		r.message(ResultCode.UNKNOWN.getMessage());
		return r;
	}

	// 失败
	public static R fail(ResultCode resultCode) {
		R r = new R<>();
		r.code(resultCode);
		r.message(resultCode.getMessage());
		return r;
	}

	public R code(ResultCode resultCode) {
		this.code = resultCode.getCode();
		return this;
	}

	public R message(String message) {
		this.message = message;
		return this;
	}

	public R<T> data(T data) {
		this.data = data;
		return this;
	}

	// 便于客户端判断是否成功，这个属性不序列化
	@JsonIgnore
	public boolean isSuccess() {
		return ResultCode.SUCCESS.code == this.code;
	}

	@Override
	public String toString() {
		return "RestResponse [code=" + code + ", message=" + message + ", data=" + data + "]";
	}
}