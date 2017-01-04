package com.ge.util.os;

/**
 * Created by Storm_Falcon on 2016/12/21.
 * Declare how to parse cmd result.
 */
public interface ResultParser<E> {

	/**
	 * Parse a line of cmd result.
	 * @param content Each line of cmd result.
	 * @return A VO.
	 */
	E parse(String content);

}
