package com.mysite.sbb.user;

import lombok.Getter;

// 사용자 역할을 정의하는 열거형(enum) 클래스
@Getter
public enum UserRole {
    
    // 관리자 역할을 나타내는 상수, Spring Security에서는 "ROLE_" 접두사가 필요
    ADMIN("ROLE_ADMIN"),
    
    // 일반 사용자 역할을 나타내는 상수
    USER("ROLE_USER");

    // 생성자를 통해 각 열거형 값에 연결된 문자열 값을 초기화
    UserRole(String value) {
        this.value = value;
    }

    // 각 열거형 상수에 연결된 역할 값을 저장
    private String value;
}
//열거형 상수(ADMIN, USER)는 고유한 상태를 나타냄.
//value는 열거형 상수와 연결된 추가 데이터를 저장.
//생성자를 통해 열거형 상수와 value가 연결되고, getter로 필요할 때 값을 가져올 수 있음.

