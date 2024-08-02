package com.zenden.sports_store.Handler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "com.zenden.sports_store")
public class ExceptionHandler extends ResponseEntityExceptionHandler {

}
