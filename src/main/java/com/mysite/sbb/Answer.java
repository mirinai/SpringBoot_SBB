package com.mysite.sbb;

import java.time.LocalDateTime;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter // Lombok을 사용해 모든 필드의 getter 메서드를 자동 생성
@Setter // Lombok을 사용해 모든 필드의 setter 메서드를 자동 생성
@Entity // JPA의 엔티티 클래스로 선언하여 데이터베이스 테이블과 매핑
public class Answer {
	
	@Id // 해당 필드를 기본 키로 지정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스가 자동으로 관리하도록 설정
	private Integer id; // 답변의 고유 ID (기본 키)
	
	@Column(columnDefinition = "TEXT") // 컬럼을 TEXT로 정의하여 긴 텍스트 저장 가능
	private String content; // 답변의 내용
	
	private LocalDateTime createDate; // 답변이 생성된 날짜와 시간
	
	@ManyToOne
	private Question question; // 답변이 속한 질문. 여러 개의 답변이 하나의 질문에 연결되는 다대일 관계를 나타냄
	// Q : A = N : 1
	

}
