# Skills.md

이 프로젝트를 진행하면서 학습하고 적용할 기술 목록을 단계별로 정리한다.

> 상태: 🔲 학습 예정 / 🔄 학습 중 / ✅ 완료

---

## Phase 1 — 기본 CRUD

### Java
| 기술 | 상태 | 설명 |
|------|------|------|
| Java 21 기본 문법 | 🔲 | record, sealed class, pattern matching 등 |
| Lombok | 🔲 | `@Getter`, `@Builder`, `@RequiredArgsConstructor` 등 보일러플레이트 제거 |

### Spring Core
| 기술 | 상태 | 설명 |
|------|------|------|
| IoC / DI | 🔲 | `@Component`, `@Service`, `@Repository`, 생성자 주입 |
| Spring MVC | 🔲 | `@RestController`, `@RequestMapping`, `@PathVariable`, `@RequestBody` |
| DTO 패턴 | 🔲 | 요청/응답 객체 분리, Entity 직접 노출 금지 |

### Spring Data JPA
| 기술 | 상태 | 설명 |
|------|------|------|
| Entity 설계 | 🔲 | `@Entity`, `@Id`, `@GeneratedValue`, `@Column` |
| JpaRepository | 🔲 | 기본 CRUD 메서드, 쿼리 메서드 작성 |
| H2 인메모리 DB | 🔲 | 개발용 설정, H2 콘솔 활용 |

### 테스트
| 기술 | 상태 | 설명 |
|------|------|------|
| JUnit 5 | 🔲 | `@Test`, `@BeforeEach`, `Assertions` |
| Spring Boot Test | 🔲 | `@SpringBootTest`, `@WebMvcTest`, `MockMvc` |

---

## Phase 2 — 관계 & 비즈니스 로직

### Spring Data JPA
| 기술 | 상태 | 설명 |
|------|------|------|
| 연관관계 매핑 | 🔲 | `@OneToMany`, `@ManyToOne`, `@JoinColumn` |
| 페치 전략 | 🔲 | LAZY vs EAGER, N+1 문제 인식 및 해결 |
| JPQL / @Query | 🔲 | 커스텀 쿼리 작성 |
| 페이징 | 🔲 | `Pageable`, `Page<T>`, `PageRequest` |

### 비즈니스 로직
| 기술 | 상태 | 설명 |
|------|------|------|
| 트랜잭션 | 🔲 | `@Transactional`, 읽기 전용 트랜잭션 분리 |
| Enum 상태 관리 | 🔲 | 모임 상태 (모집중 / 마감 / 완료) |
| 도메인 규칙 적용 | 🔲 | 정원 초과 방지, 생성자 권한 검사 |

---

## Phase 3 — 검증 & 예외 처리

### 유효성 검사
| 기술 | 상태 | 설명 |
|------|------|------|
| Bean Validation | 🔲 | `@Valid`, `@NotBlank`, `@Size`, `@Min` 등 |
| 커스텀 Validator | 🔲 | `ConstraintValidator` 구현 |

### 예외 처리
| 기술 | 상태 | 설명 |
|------|------|------|
| `@RestControllerAdvice` | 🔲 | 글로벌 예외 핸들러 |
| 커스텀 예외 클래스 | 🔲 | 도메인별 예외 정의 |
| 공통 응답 포맷 | 🔲 | `ApiResponse<T>` 래퍼 설계 |

---

## Phase 4 — Spring Security

### 인증
| 기술 | 상태 | 설명 |
|------|------|------|
| Spring Security 기본 | 🔲 | `SecurityFilterChain`, `HttpSecurity` 설정 |
| JWT | 🔲 | Access Token 발급 및 검증 (`io.jsonwebtoken`) |
| `OncePerRequestFilter` | 🔲 | JWT 인증 필터 구현 |
| Refresh Token | 🔲 | 재발급 로직, 저장소 설계 |

### 인가
| 기술 | 상태 | 설명 |
|------|------|------|
| `@PreAuthorize` | 🔲 | 메서드 레벨 권한 제어 |
| 커스텀 인가 로직 | 🔲 | 모임 생성자만 수정·삭제 허용 |

---

## 공통 — 개발 환경 & 협업

| 기술 | 상태 | 설명 |
|------|------|------|
| Git Flow | 🔲 | feature / release / hotfix 브랜치 운영 |
| GitHub Issues & PR | 🔲 | 이슈 기반 개발, PR 리뷰 프로세스 |
| Gradle | 🔲 | 의존성 관리, 멀티 태스크 실행 |