package com.mysite.sbb;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;


@Component
//Spring에서 관리되는 Bean으로 등록하여 다른 클래스에서 의존성 주입을 통해 사용할 수 있도록 설정.
public class CommonUtil {

 /**
  * 입력받은 Markdown 문자열을 HTML로 변환하는 메서드
  *
  * @param markdown 변환할 Markdown 텍스트
  * @return 변환된 HTML 텍스트
  */
 public String markdown(String markdown) {
     
     // Markdown 텍스트를 파싱하는 Parser 객체 생성
     Parser parser = Parser.builder().build();
     
     // 입력받은 Markdown 문자열을 Node 구조로 변환
     Node document = parser.parse(markdown);
     
     // Node 구조를 HTML로 렌더링하는 HtmlRenderer 객체 생성
     HtmlRenderer renderer = HtmlRenderer.builder().build();
     
     // Markdown -> Node -> HTML 변환 결과를 문자열로 반환
     return renderer.render(document);
 }
}
