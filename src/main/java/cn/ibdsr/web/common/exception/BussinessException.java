package cn.ibdsr.web.common.exception;

import cn.ibdsr.core.exception.GunsException;

/**
 * @author fengshuonan
 * @Description 业务异常的封装
 * @date 2016年11月12日 下午5:05:10
 */
public class BussinessException extends GunsException {
	public BussinessException(BizExceptionEnum bizExceptionEnum) {
		super(bizExceptionEnum.getCode(), bizExceptionEnum.getMessage(), bizExceptionEnum.getUrlPath());
	}
}
