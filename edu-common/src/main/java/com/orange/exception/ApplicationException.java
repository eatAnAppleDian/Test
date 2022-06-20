package com.orange.exception;

import com.orange.vo.ResultCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 2359767895161832954L;

	@Getter
	private ResultCode resultCode;

	public ApplicationException(String message) {
		super(message);
		this.resultCode = ResultCode.UNKNOWN;
	}

	public ApplicationException(Throwable cause) {
		super(cause);
		this.resultCode = ResultCode.UNKNOWN;
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
		this.resultCode = ResultCode.UNKNOWN;
	}

	public ApplicationException(ResultCode resultCode) {
		super(resultCode.getMessage());
		this.resultCode = resultCode;
	}

	public ApplicationException(ResultCode resultCode, String message) {
		super(message);
		this.resultCode = resultCode;
	}

	public ApplicationException(ResultCode resultCode, Throwable cause) {
		super(cause);
		this.resultCode = resultCode;
	}

	public ApplicationException(ResultCode resultCode, String message, Throwable cause) {
		super(message, cause);
		this.resultCode = resultCode;
	}
}