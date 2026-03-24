
# 🎓 LoveTriangle LMS

> 학습의 흐름을 연결하는 통합 교육 관리 시스템 (Learning Management System)

---

## 📖 Project Overview

LoveTriangle LMS는 교수와 학생 간의 학습 과정을 하나의 흐름으로 연결하기 위해 설계된 교육 관리 시스템입니다.  
단순 기능 제공을 넘어, **강의 → 출결 → 과제 → 성적**으로 이어지는 학습 흐름을 통합적으로 관리할 수 있도록 구성하였습니다.

---

## 🎯 Problem Definition

기존 학습 관리 환경에서는 다음과 같은 문제점이 존재했습니다.

- 출결, 과제, 성적 관리가 분리되어 있어 **학습 흐름이 단절됨**
- 출결 처리가 수동으로 이루어져 **정확성과 효율성이 낮음**
- 사용자 입력에 의존하는 구조로 인해 **데이터 일관성 부족**

👉 본 프로젝트는 이를 해결하기 위해  
“자동화된 흐름 기반 LMS”를 목표로 설계되었습니다.

---

## 🧩 Key Features

### 🔐 Authentication
- 로그인 / 회원가입 / 비밀번호 재설정
- BCrypt 기반 암호화 및 인증 처리

### 👤 User Management
- 사용자 정보 조회 및 수정
- 개인 정보 관리 기능

### 📚 Course Management
- 강의 개설 및 조회
- 주차별 커리큘럼 관리

### 🕒 Attendance Management
- 실시간 출석 체크 기능
- 수업 시간 기반 출석/지각 자동 판정
- 출결 상태 관리 (PRESENT / LATE / ABSENT / EXCUSED)
- 출결 상태 한글 입력 → 내부 상태값 변환 처리
- 강의별 / 출결 유형별 조회 기능

### 📝 Assignment Management
- 과제 생성, 수정, 삭제 (교수)
- 과제 조회 및 제출 (학생)

### 📊 Grade Management
- 성적 등록 및 수정
- 성적 조회 기능

---

## 🧠 Key Design Concept

### 1. Flow-Based Learning Structure
> 강의 → 출결 → 과제 → 성적이 하나의 흐름으로 이어지는 구조 설계

- 각 기능이 독립적인 것이 아닌, **학습 과정 중심으로 연결됨**
- 사용자 경험을 고려한 **자연스러운 학습 흐름 제공**

---

### 2. Time-Based Logic System
> 출결을 단순 입력이 아닌 시간 기준으로 자동 판정

- 수업 시작 시간 기준 출석/지각 자동 처리
- 사용자 입력 최소화 및 정확성 향상

---

### 3. Data Consistency Strategy
> 사용자 입력과 내부 데이터 구조 분리

- 한글 입력 → ENUM 상태값 변환
- 데이터 표준화를 통한 **일관성 유지**

---

## 🏗️ System Architecture

- Java 기반 콘솔 애플리케이션
- MVC 패턴 (Controller / Service / DAO)
- 계층 분리를 통한 유지보수성 확보
- MySQL 기반 데이터 관리

---

## 🛠️ Tech Stack

- Language: Java
- Database: MySQL
- Security: BCrypt
- Architecture: MVC Pattern

---

## 👥 Team Roles

| Module       | Description                                      | Owner   |
|--------------|--------------------------------------------------|--------|
| auth         | 인증 및 보안 처리                               | 김동훈 |
| users        | 사용자 관리                                     | 김동훈 |
| course       | 강의 및 커리큘럼 관리                          | 정유지 |
| attendance   | 출결 관리 및 상태 처리                          | 김채린 |
| assignments  | 과제 관리 시스템                                | 지석범 |
| grade        | 성적 관리                                       | 박민서 |
| global       | 공통 기능 및 세션 관리                          | 공통    |
| main         | 시스템 구조 및 AppConfig                        | 김동훈 |

---

## 👨‍👩‍👧‍👦 Team Member Responsibilities

각 팀원은 기능 구현뿐만 아니라, 특정 도메인을 중심으로 역할을 분담하여 프로젝트를 수행하였습니다.

### 👤 김동훈
- 총괄 및 시스템 아키텍처 설계
- 인증 및 회원 관리 기능 구현
- BCrypt 기반 보안 처리
- 팀 프로젝트 관리 및 협업 환경 구축

### 👤 박민서
- 시험 및 성적 관리 시스템 구현
- 수강신청 처리 및 최종 성적 산출
- Gamification 요소 설계 및 적용

### 👤 정유지
- 강의 및 커리큘럼 관리 기능 구현
- 주차별 강의 콘텐츠 등록 및 수정
- 강의 조회 기능 개발

### 👤 김채린
- 출결 관리 시스템 구현
- 출석 체크 및 시간 기반 자동 판정 로직 설계
- 출결 상태 관리 및 조회 기능 구현
- 공결 신청 기능 추가 구현

### 👤 지석범
- 과제 관리 시스템 구현
- 과제 생성, 수정, 삭제 기능 개발 (교수)
- 과제 조회 및 제출 기능 구현 (학생)

---

## 🤝 Collaboration Process

- Git 기반 브랜치 전략 활용 (feature 단위 개발)
- Notion을 통한 일정 및 작업 관리
- 코드 리뷰 및 기능 단위 테스트 진행

---

## 🚀 Future Directions

- 공결 승인 및 출결 관리 프로세스 고도화
- 웹 기반 UI로 확장
- REST API 기반 구조로 전환
- 실시간 알림 시스템 도입

---

## 📌 Summary

LoveTriangle LMS는 단순한 기능 구현을 넘어  
**학습 흐름 중심의 구조 설계와 자동화된 처리 로직**을 통해  
효율적인 교육 관리 환경을 제공하는 것을 목표로 합니다.
