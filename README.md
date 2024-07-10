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
