07/09
만약 Gradle 가 아닌 Maven 으로 만들면
build.gradle(gardle 프로젝트)가 아니라 pom.XML 을 가지고 있을 것이다.
다른 점이 있는 것은 아님.

SSR : Spring 프로젝트 안에 java, html,css, js를 모두 넣어두고
사용자가 요청을 할때 하나씩 주는 것이 아니라 
서버에서 한번에 다 주는 것 -> 효율적이다(성능이 좋다.)
간단한 화면을 구성하기에 매우 편리하다. 복잡한 경우 사용하기 어려움.
이렇게 화면을 구성하기 위해서는 템플릿 엔진이 필요하다. (타임리프, jsp와 같은)
but, 이제 화면이 고도화되다보니 프론트엔드를 화려하게 하려면 CSR을 해야함

-정리
CSR : 화면과 서버(데이터만) 분리
SSR : 서버안에서 화면까지 렌더링(화면 + 데이터)

게시판(SSR-타임리프, thymeleaf), 주문서비스(CSR, vue와 함께)

jar : 현재
war : 레거시 코드 에서 사용 : 패키지 구조 조금 다르고 ,톰캣을 별도 실행.

tomcat : 핵심엔진 

implementation : 컴파일 + 런타임 시점에 필요
CompileOnly : class파일만들때부터 참조해서 사용
RunTimeOnly : class파일X, 최종jar 파일 만들때는 포함

jar 파일 : class + 기타의존성묶음 => 실행가능한 상태.


Controller(요청/응답) : 사용자와의 인터페이신 계층
Domain : 클래스(객체)가 들어간다.
Repository : DB접근 계층.
Service : 실질적인 로직

다른 소스코드가 BasicApplication 보다 상위 디렉토리에 있어서는 안된다.(상위 package)

7/10
화면 별도로 두고(vue) + 서버(spring)

서버와 화면을 같이(ssr)

jar파일 : 실행가능하도록 빌드된 class 묶음 + 라이브러리

./gradlew build : 실행가능한 jar, class만으로 이루어진 jar
./gradlew bootjar : 실행가능한 jar만
java -jar 파일명

빌드? class 묶음 + 라이브러리 => 실행
빌드툴? gradle
gradlew : gradlew 빌드의 스크립트
./gradlew 테스크(build, bootjar)
build : 실행가능한 jar + plain jar
bootjar : 실행가능한 jar만


7/11 복습

프론트엔드 axios로 json을 전송(보낼때의 데이터 타입 : application/json) => 서버 : requestbody

axios json을 전송(application/json) => 서버 : RequestBody
axios json+파일 전송(Multipart-formdata) => 서버 RequestPart
axios json+멀티파일(multiple) => 서버 RequestPart, List<파일>
axios json(중첩) - application/json => 서버 RequestBody + 객체 안에 객체
axios json(중첩) - 파일 : FormData => 서버 RequestPart

<db 접근 기술>
jdbc mybatis    : (쿼리) 
jpa             : (쿼리O, 쿼리X)
springdatajap   : (쿼리X-ORM(Object-Relational-Mapping))

Controller 에서 Post와 Get 을 통해 정보를 받아오면
Service 단으로 동작을 넘긴다.

ReqDto : 사용자가 서버에 전송할때
ResDto : 서버가 사용자에게 응답줄때
객체(DB) : Member

Dto와 객체를 분리


7/12
1) raw 쿼리를 문자열 형태로 사용한다 -> 컴파일 시점에서 에러가 나지 않아 디버깅이 어렵다.
2) 조회 : ResultSet => 객체를 직접 조립해줘야함.
mybatis : 그래도 뭐가 불편한가.
    => raw쿼리는 그대로 -> 컴파일 시점에 에러X.(raw쿼리)
    => 객체 조립
jpa : 그래도 뭐가 불편한가
springdatajap : 이것은 무엇이 편한가.

1. JDBC : raw쿼리 + 객체생성X
2. myBatis : raw쿼리 + 객체생성O
  => save return Member
  => 회원가입 -> 회원목록조회, 회원상세조회
3. jpa : raw쿼리(반반) + 객체생성O
4. SpringDataJpa : raw쿼리X + 객체생성O

interface 설계 : MemberRepository
1) jdbc : 마음대로 할 수 있었다.
2) mybatis : 마음대로 할 수 있었다.
3) jpa : 마음대로 할 수 있었다.
4) springdatajpa : 구현체가 미리 만들어져 있다.

7/15 
DB 의존적 : DataBase가 바뀔수 있다. 
    1. ex) mariadb -> mysql로 바뀌게 되면 모든 쿼리가 바뀌어야 한다.

객체 의존적 : Jpa는 객체 중심의 사상, DB를 Jpa가 통제한다.

1. Html로 전송하는 방법
    => form태그 -> multipart/form-data, x-www => key&value형식
    => 서버에서는 @RequestParam 또는 @modelattribute 객체 -> 데이터 바인딩.
2. js로 전송(postman으로)
   1) formdata => @RequestParam 또는 @modelattribute 객체 -> 데이터 바인딩.
   2) json => 서버에서 @RequestBody 객체  

화면 Return -> MVC패턴(JSP 또는 타임리프)
    -> @ResponseBody 붙이고 화면대신 객체 return => 데이터를 return 방식으로 됨.
데이터를 Return : rest api 방식, rest ful한 방식


7/16

싱글톤 객체 == Spring Bean(빈) -> Spring에서 관리해준다.
@Component : 클래스단위 지정
@Bean : 메서드단위 지정하되, return 객체(외부 라이브러리 + Configuration)

싱글톤객체 동시성 이슈 -> 없음.


7/19일

fetch(로딩) 전략 : lazy(oneToMany), eager(ManyToOne, OneToOne)

상대엔티티 : (Member -> Post) , (Post -> Member)
lazy 전략 상대 엔티티를 참조하지 않으면 상대 엔티티로 Query 발생 X -> N+1 발생 X
lazy 전략에서도 상대 엔티티를 참조하면 상대엔티티로 Query 발생 -> N+1 발생

eager 전략은 상대엔티티를 참조하지 않더라도 상대엔티티로 쿼리발생 -> N+1 발생
eager 전략은 상대엔티티를 참조하면 당연히 상대엔티ㅌ로 쿼리발생 -> N+1 발생