package com.reactive.utils;

import org.springframework.beans.BeanUtils;

public class AppUtils<T, R> {

	public R dtoToEntity(T tObject, R rObject) {
		BeanUtils.copyProperties(tObject, rObject);
		return rObject;
	}

	public R entityToDto(T tObject, R rObject) {
		BeanUtils.copyProperties(tObject, rObject);
		return rObject;
	}
}
