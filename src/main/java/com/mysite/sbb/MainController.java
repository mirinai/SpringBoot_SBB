package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {
	
	@GetMapping("/sbb")
	@ResponseBody
	public String index() {
		return "sbb에 온걸 환영한다 아쎄이";
	}
	
}
