# meeting

meet room example

#### Dependencies
* Java-JDK : 17
* spring-boot : 3.4.0
* gradle(wrapper 사용) : 8.11.1
* openApiVersion : 2.7.0
* queryDSL : 5.0.0
* H2
* Lombok : 1.18.6
    - https://mvnrepository.com/artifact/org.projectlombok/lombok

### 실행
~~~
# test (생략 - 미구현)
./gradlew test

# build and run
./gradlew bootRun
~~~

### 프로젝트 설명
~~~
1. DB 구성
  - Coordinations (N)
    id PK auto increase
    brand_id INTEGER
    CATEGORY VARCHAR
    price INTEGER
  - Brands (1)
    id pk auto increase
    name VARCHAR
    
2. swagger
    http://127.0.0.1:8080/swagger-ui.html

3. 프로젝트 폴더 구성
  * MSA 도메인의 확장성을 고려하여 Multi Project 로 구성
  - apis
    - controllers : 서비스 도메인의 컨드롤러 영역
    - services : 서비스 도메인의 비지니스 로직(서비스 특화)
      - reqs : request 객체
      - reps : response 객체
    - repositories
      - impls : queryDSL 구현체
      - interfaces : JPA, QueryDSL interface
  - models
    - components : Component 관리 repo
    - configurations : Configurations 관리 repo
    - domains : MSA 각각의 도메인 영역에서 사용되는 DTO, Entity, Enum, Service, Interfac 등올 관리
      - dtos
      - entities
      - enumrations
      - interfaces
      - services
        - repositories
    - handlers : advices, exception handler등을 관리
      - advices
      - https
    - structures : basic interface를 관리
      - controllers : BaseController Interface
      - dtos : Exception Response 객체
      - enumrations : 공통으로 사용가능한 Enum
      - repositories : CRUD관련 interface
      - service : 비지니스 로직 interface
    - utils
  - repositories : DA에 해당하는 인터페이스와 구현체
    - commons : JPA interface wrapping
    - configuration : DataManager, QueryDSL
    - domains (2024.12.06 -> apis로 이전)
      - impls : queryDSL 구현체
      - interfaces : JPA, QueryDSL interface

3. 확장을 고려한 내용
  1. Exception Handler에 대한 확장
    - 현재 적용된 Excepion
      - JsonParseException.class
      - MethodArgumentTypeMismatchException.class
      - IllegalArgumentException.class
      - InvalidFormatException.class
      - BadRequestException.class
      - HttpMessageNotReadableException.class
      - MissingPathVariableException.class
      - NoSuchMethodError.class
      - MethodArgumentNotValidException.class
      - HttpRequestMethodNotSupportedException.class
      - MethodNotAllowedException.class
      - AccessDeniedException.class
      - ForbiddenException.class
      - NotFoundException.class
      - NoSuchElementException.class
      - ServletException.class
      - NoResourceFoundException.class
      - DataIntegrityViolationException.class
      - ConstraintViolationException.class
      - SQLIntegrityConstraintViolationException.class
      - IllegalStateException.class
      - CannotAcquireLockException.class
      - NullPointerException.class
      - ConflictException.class
      - JpaSystemException.class
      - HttpMessageNotWritableException.class
      - HttpMediaTypeNotAcceptableException.class
      - TooManyRequestException.class
      - RequestTimeOutException.class
      - UnauthorizedException.class
      - InternalServerException.class
      - IOException.class
      - Exception.class
      - UnknownFormatConversionException.class

4. 미구현 영역 및 구현 플랜
  1. 서비스 환경 설정
    - Environment 를 구분하지 않음
      - spring.profiles.active 로 구현      
      - EnvComponent를 구현하여 코드 적용
  2. RestResponse Advice
    - MSA 도메인의 reponse 일관성 유지
      - org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice wrapper
  3. DA 확장
    - Data Access에 따른 configuration, interface 확장
  4. 보안 관련
    - HeaderComponent
    - SecurityConfiguration
    - RestBlockingConfiguration
    - WebCorsConfiguration
  4. Util 확장
  5. 로깅
    - system log
    - 모니터링을 위한 외부 연동(ex. slack)
  6. Validation
    - 각 값에 대한 type 체크
    - Brands Coordination 부모, 자식 참조값에 따른 등록, 삭제
      - 1. Brand 삭제시 자식 유무 확인
      - 2. 자식 등록시 부모 유무 확인 (현재는 Brands가 없으면 등록 안됨)
        -> Brands가 없으면 Brands를 새로 생성하고 등록하도록 수정.
  7. SubQuery 개선
    - 데이터 사이즈 및 요구사항에 맞는 최적화 쿼리 개선 필요.
~~~

### 구현
~~~
1. 카테고리별 최저가 브랜드 조회
  - End point : /services/coordinations/mins
  - Request : None
  - Response : Swagger 참고
2. 브랜드 합산 최저 금액 조회 (단일 브랜드 기준)
  - End point : GET /services/coordinations/brands
  - Request : None
  - Response : Swagger 참고
3. 카테고리 이름으로 최저,최고가 브랜드와 상품 가격 조회
  - End point : GET /services/coordinations/brands/{:category}
  - Request : :category(String)
  - Response : Swagger 참고
4. 브랜드 및 상품 추가/업데이트/삭제
  - 상품 추가
    - End point : POST /coordinations
  - 상품 업데이트
    - End point : PUT /coordinations/{:id}
  - 상품 삭제
    - End point : DELETE /coordinations/{:id}
~~~

#### TODO:
~~~
- Refactoring
- Code Smell 제거
- Exception 처리
- Test Code 작성
~~~
