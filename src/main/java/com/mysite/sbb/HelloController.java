package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // HelloController 클래스가 컨트롤러의 기능을 수행한다는 것을 알려준다
public class HelloController {

    @GetMapping("/hello") // 클라이언트의 요청으로 hello 메서드가 실행됨을 알려준다
    @ResponseBody // hello 메서드의 출력값 그대로 리턴할 것을 알려준다
    public String hello() {
        return "Hello Spring Boot Board"; // 
    }
    
    // 클래스 내부에서 /hello URL로 요청이 들어오면 hello() 메서드가 호출됨
    // @Controller는 해당 클래스가 Spring MVC의 Controller 역할을 하도록 함
    // @ResponseBody를 통해 메서드의 반환값을 HTTP 응답 본문으로 직접 전송
}
