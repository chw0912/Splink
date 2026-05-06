# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 프로젝트 개요

**Splink** — 스포츠 번개 모임 웹 서비스. 사용자가 스포츠 종목별 번개 모임을 생성하고 참여할 수 있는 백엔드 API 서버. 백엔드 학습 목적으로 CRUD → 관계 설계 → Spring Security 순으로 단계적으로 기능을 고도화한다.

## 기술 스택

- **Java 21**, **Spring Boot 4.x**
- **Spring Data JPA** + **H2** (인메모리, 개발용)
- **Spring MVC** (REST API)
- **Lombok**
- **JUnit 5** + Spring Boot Test

## 주요 명령어

```bash
# 빌드
./gradlew build

# 애플리케이션 실행 (로컬 프로파일 적용)
./gradlew bootRun --args='--spring.profiles.active=local'

# 전체 테스트 실행
./gradlew test

# 단일 테스트 클래스 실행
./gradlew test --tests "com.splink.SomeTest"

# 단일 테스트 메서드 실행
./gradlew test --tests "com.splink.SomeTest.methodName"

# 코드 스타일 검사
./gradlew checkstyleMain checkstyleTest

# 스타일 검사 + 테스트 한 번에
./gradlew check
```

H2 콘솔: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)

## 패키지 구조 (목표)

```
com.splink
├── domain
│   ├── user                # 사용자
│   │   ├── controller/
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/
│   │   └── dto/
│   ├── meeting             # 번개 모임
│   │   ├── controller/
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/
│   │   └── dto/
│   └── sport               # 스포츠 종목
│       ├── controller/
│       ├── entity/
│       ├── repository/
│       ├── service/
│       └── dto/
├── global
│   ├── config              # Spring 설정 (Security 등)
│   └── exception           # 공통 예외 처리
└── SplinkApplication.java
```

## 브랜치 전략

```
main             # 운영환경 배포 버전
├── hotfix/      # 운영 긴급 버그 수정 → main + develop merge
└── develop      # 다음 버전 기능 통합 브랜치
     ├── feature/ # 기능 개발 → develop merge
     └── release/ # 배포 준비 → main + develop merge
```

| 브랜치 | 분기 대상 | 머지 대상 | 용도 |
|--------|----------|----------|------|
| `main` | — | — | 운영환경 배포 버전. 직접 커밋 금지 |
| `develop` | `main` | `main` (릴리즈 시) | 다음 버전 기능 통합. 기능 브랜치의 PR 대상 |
| `feature/#이슈번호-설명` | `develop` | `develop` | 새로운 기능 개발 |
| `release/#버전` | `develop` | `main` + `develop` | 배포 전 버전명·버그 수정 |
| `hotfix/#이슈번호-설명` | `main` | `main` + `develop` | 운영 긴급 버그 수정 |

- `feature` 브랜치는 항상 `develop`에서 분기하고 `develop`으로 PR
- `release` 브랜치에서는 버전 정보 수정, 사소한 버그 수정만 허용
- `hotfix` 브랜치 완료 후 반드시 `main`과 `develop` 양쪽에 merge
- `main` ← `release`/`hotfix` merge는 태그(버전)를 함께 생성

---

## 개발 워크플로우

기능 단위로 이슈를 등록하고 PR을 올리는 방식으로 진행한다.

### 1. 이슈 생성

```bash
gh issue create --title "[FEAT] 기능명" --body "..."
# 또는 GitHub 웹에서 issue.yml 템플릿 사용
```

### 2. 브랜치 생성

이슈 번호를 브랜치명에 포함한다.

```bash
git switch -c feature/#이슈번호-기능명
# 예: feature/#1-user-crud
```

브랜치 네이밍 규칙:
| 유형 | 분기 대상 | 형식 |
|------|----------|------|
| 기능 추가 | `develop` | `feature/#이슈번호-설명` |
| 배포 준비 | `develop` | `release/#버전` |
| 운영 버그 수정 | `main` | `hotfix/#이슈번호-설명` |

### 3. 커밋 컨벤션

```
feat: 새로운 기능
fix: 버그 수정
refactor: 리팩토링
chore: 빌드/설정 변경
test: 테스트 추가/수정
docs: 문서 수정
```

### 4. PR 생성

```bash
gh pr create --title "[FEAT] 기능명" --body "..."
```

PR 본문에 `Closes #이슈번호` 를 포함해 이슈와 자동 연결한다.

---

## 개발 로드맵

### Phase 1 — 기본 CRUD
- [x] `#1` User (회원가입, 조회, 수정, 탈퇴)
- [ ] `#` Sport (스포츠 종목 등록/조회)
- [ ] `#` Meeting (모임 생성, 조회, 수정, 삭제)

### Phase 2 — 관계 & 비즈니스 로직
- [ ] `#` MeetingParticipant (모임 참가/취소, 정원 관리)
- [ ] `#` 모임 상태 관리 (모집중 / 마감 / 완료)
- [ ] `#` 페이징 & 필터링 (종목별, 지역별, 날짜별)

### Phase 3 — 검증 & 예외 처리
- [ ] `#` Bean Validation (`@Valid`)
- [ ] `#` 글로벌 예외 핸들러 (`@RestControllerAdvice`)
- [ ] `#` 공통 응답 포맷 (`ApiResponse<T>`)

### Phase 4 — Spring Security
- [ ] `#` 회원가입 / 로그인 (JWT 발급)
- [ ] `#` 인증 필터 (`OncePerRequestFilter`)
- [ ] `#` 인가 (모임 생성자만 수정/삭제 가능 등)
- [ ] `#` Refresh Token

> 각 항목을 시작할 때 GitHub 이슈를 먼저 등록하고, `#` 자리에 이슈 번호를 기입한다.

## 코드 스타일

Checkstyle을 사용하며 IntelliJ 기본 스타일(4칸 들여쓰기, Oracle Java 관례)을 따른다.
설정 파일: `config/checkstyle/checkstyle.xml`

| 규칙 | 내용 |
|------|------|
| 들여쓰기 | 4칸 (탭 금지) |
| 줄 길이 | 최대 120자 |
| import | 와일드카드 금지 (static import 허용) |
| 중괄호 | 단일 라인 if/for도 중괄호 필수 |
| 네이밍 | 클래스 PascalCase, 메서드·변수 camelCase, 상수 UPPER_SNAKE_CASE |
| 수정자 순서 | `public > protected > private > static > final` 순 |
| 파일 끝 | 개행 필수 |

- PR 생성 시 GitHub Actions에서 자동 검사 (`dev`, `main` 대상)
- 테스트 메서드명은 한글+언더스코어 허용 (`suppressions.xml`)
- 실패 시 빌드 리포트: `build/reports/checkstyle/`

## 핵심 도메인 규칙

- **Meeting**: 생성자(host)만 수정·삭제 가능. 정원 초과 시 참가 불가.
- **User**: 이메일 중복 불가. 탈퇴 시 소프트 딜리트 적용 예정.
- DB는 개발 단계에서 H2 인메모리를 사용하며, 이후 MySQL/PostgreSQL로 전환 예정.
