# Splink ⚡

> 스포츠 번개 모임 웹 서비스

원하는 스포츠 종목의 번개 모임을 직접 만들거나 참여할 수 있는 백엔드 API 서버입니다.

---

## 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 21 |
| Framework | Spring Boot 4.x |
| ORM | Spring Data JPA |
| Database | H2 (개발) / MySQL (예정) |
| Build | Gradle |
| Test | JUnit 5 |

---

## 시작하기

```bash
# 저장소 클론
git clone https://github.com/chw0912/Splink.git
cd Splink

# 애플리케이션 실행
./gradlew bootRun
```

실행 후 `http://localhost:8080` 에서 API를 사용할 수 있습니다.

H2 콘솔: `http://localhost:8080/h2-console`

---

## 주요 기능

- **모임 관리** — 스포츠 번개 모임 생성 / 조회 / 수정 / 삭제
- **참가 관리** — 모임 참가 신청 및 취소, 정원 관리
- **종목 관리** — 스포츠 종목별 모임 분류 및 필터링
- **회원 관리** — 회원가입 / 로그인 / 프로필 관리
- **인증/인가** — JWT 기반 인증, 모임 생성자 권한 관리 (예정)

---

## 개발 로드맵

| 단계 | 내용 | 상태 |
|------|------|------|
| Phase 1 | User / Sport / Meeting 기본 CRUD | 🔲 진행 예정 |
| Phase 2 | 모임 참가·정원 관리, 페이징·필터링 | 🔲 진행 예정 |
| Phase 3 | 유효성 검사, 공통 예외 처리, 응답 포맷 통일 | 🔲 진행 예정 |
| Phase 4 | Spring Security, JWT 인증/인가 | 🔲 진행 예정 |

---

## 프로젝트 구조

```
src/main/java/com/splink
├── domain
│   ├── user          # 회원
│   ├── meeting       # 번개 모임
│   └── sport         # 스포츠 종목
├── global
│   ├── config        # 설정
│   └── exception     # 공통 예외 처리
└── SplinkApplication.java
```

---

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
| `develop` | `main` | `main` (릴리즈 시) | 다음 버전 기능 통합 |
| `feature/#이슈번호-설명` | `develop` | `develop` | 새로운 기능 개발 |
| `release/#버전` | `develop` | `main` + `develop` | 배포 전 버전명·버그 수정 |
| `hotfix/#이슈번호-설명` | `main` | `main` + `develop` | 운영 긴급 버그 수정 |

---

## 기여 방법

이 프로젝트는 기능 단위로 이슈를 등록하고 PR을 올리는 방식으로 관리됩니다.

1. 작업 전 [이슈 등록](https://github.com/chw0912/Splink/issues/new/choose)
2. `develop` 브랜치에서 기능 브랜치 생성 (`feature/#이슈번호-기능명`)
3. 작업 완료 후 `develop`으로 PR 생성 (`Closes #이슈번호` 포함)
