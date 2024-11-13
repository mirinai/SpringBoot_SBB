package com.mysite.sbb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//이 예외가 발생하면 HTTP 상태 코드를 404 NOT_FOUND로 설정하고, 이유를 "entity not found"로 표시
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found") 
public class DataNotFoundException extends RuntimeException {

 private static final long serialVersionUID = 1L; // 직렬화에 사용되는 고유 ID

 public DataNotFoundException(String message) {
     super(message); // 부모 클래스 RuntimeException의 생성자를 호출하여 예외 메시지를 설정
 }
}

