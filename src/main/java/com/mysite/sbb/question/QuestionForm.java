package com.mysite.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter // Lombok을 사용해 모든 필드의 getter 메서드를 자동 생성
@Setter // Lombok을 사용해 모든 필드의 setter 메서드를 자동 생성
public class QuestionForm {
    
    @NotEmpty(message = "제목은 반드시 있어야 함") // 제목 필드가 비어 있으면 안 된다는 유효성 검증을 추가, 에러 메시지 제공
    @Size(max=200) // 제목 필드의 최대 길이를 200자로 제한
    private String subject; // 질문 제목을 저장하는 필드
    
    @NotEmpty(message = "내용은 반드시 있어야 함") // 내용 필드가 비어 있으면 안 된다는 유효성 검증을 추가, 에러 메시지 제공
    private String content; // 질문 내용을 저장하는 필드
}
