package com.beyond.basic.Controller;

import com.beyond.basic.Domain.Hello;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/hello") // 클래스차원에 url매핑시에 RequestMapping사용
//@RestController // Controller + 각메서드마다 @ResponseBody
// 해당 클래스가 컨트롤러(사용자의 요청을 처리하고 응답하는 편의기능)임을 명시
public class HelloContorller {

//    Case1. 사용자가 서버에게 화면 요청.(Get 요청)
//    Case2. @Responsebody가 붙을경우 단순 String 데이터 return(Get 요청)
    @GetMapping("/")
//    Getmapping을 통해 get요청을 처리하고 url패턴을 명시.
//    responsebody를 사용하면 화면이 아닌 데이터를 return
//    만약 여기서 responsebody가 없고 return이 스트링이면 스프링은
//    templates폴더 밑에 helloworld.html화면을 찾아 리턴.
    public String helloWorld(){
        return "helloworld";
    }
    
//    Case3. 사용자가 Json 데이터 요청(Get 요청)
//    data를 리턴하되, json형식으로 return
//    method명은 helloJson, url패턴은 /hello/json
    @GetMapping("/json")
//    getmapping대신 메서드 차원에서 이렇게 작성해도 됨
//    @RequestMapping(value = "/json" , method = RequestMethod.GET)
    @ResponseBody
//    responsebody를 사용하면 객체를 return시 자동으로 직렬화.
    public Hello helloJson() throws JsonProcessingException {
        Hello hello = new Hello();
        hello.setName("무리");
        hello.setEmail("무리@네이버");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String value =objectMapper.writeValueAsString(hello);
//        return value;
        return hello;
    }

//    Case4. 사용자가 Json데이터를 요청하되, parameter형식으로 특정객체 요청.
//    get요청중에 특정 데이터를 요청
    @GetMapping("/param1")
    @ResponseBody
//    parameter형식 : ?name=hongildong&email=hongildong@naver.com (외워 - 처음에 ?가 들어감, 2개 이상 요청시 &가 붙음)
    public Hello Param1(@RequestParam(value = "name")String inputName){
        Hello hello = new Hello();
        hello.setName(inputName);
        hello.setEmail("hongildong@naver.com");
        System.out.println(hello);
        return hello;
    }

//    url패턴 : model-param2, 메서드명:modelparam2
//    parameter2개 : name, email => hello객체 생성후 리턴.
    @GetMapping("/param2")
    @ResponseBody
    public Hello Param2(@RequestParam(value = "name")String inputName,
                             @RequestParam(value = "email")String inputEmail){
        Hello hello = new Hello();
        hello.setName(inputName);
        hello.setEmail(inputEmail);
        return hello;
    }

//    Case5. parameter형식으로 요청하되, 서버에서 데이터바인딩 하는 형식
    @GetMapping("/param3")
    @ResponseBody
//    parameter가 많은 경우 객체로 대체가 가능, 객체에 각 변수에 맞게 알아서 바인딩(객체바인딩)
//    데이터바인딩 : 기본생성자, Setter가 필요
    public Hello Param3(Hello hello){
        return hello;
    }

//    Case6. 서버에서 화면에 데이터를 넣어 사용자에게 return(model객체 사용)
    @GetMapping("/model-param")
    public String modelparam(@RequestParam(value = "name")String inputName, Model model){
//        model 객체에 name 이라는 키값에 value를 세팅하면 해당 key:value는 화면으로 전달.
        model.addAttribute("name", inputName);
        return "helloworld";
    }

//    Case7. pathvariable방식을 통해 사용자로부터 값을 받아 화면 리턴.
//    localhost:8080/hello/model-path/hongildong
//    pathvariable방식을 url을 통해 자원의 구조를 명확하게 표현함으로, 좀더 restful한 형식
//    http 대원칙에 좀더 부합하기 때문에 기왕 parameter 방식보다 path 방식을 사용
    @GetMapping("/model-path/{name}")
    public String modelPath(@PathVariable String name , Model model){
        model.addAttribute("name", name);
        return "helloworld";
    }

//    Post요청(사용자입장에서 서버에 데이터를 주는 상황)
//    Case1. url 인코딩 방식(text만) 전송
    @GetMapping("/form-view")
//    사용자에게 name,email,password를 입력할 수 있는 화면을 주는 메서드 정의
//    메서드명 : formView, 화면명 : form-view
    public String formView(){
        return "formview";
    }

    //    형식 : key1=value1&key2=value2&key3=value3
    @PostMapping("/form-post1") // GetMapping과 같은 url패턴 사용 가능
    @ResponseBody
    public String formpost1(@RequestParam("name")String name, //html 파일 안에 name="name"과 매핑 
                            @RequestParam("email")String email, // html파일 안에 name="email"과 매핑
                            @RequestParam("password")String password){ // html파일 안에 name="password"와 매핑
//        사용자로부터 받아온 내용 출력
        System.out.println(name);
        System.out.println(email);
        System.out.println(password);
        return "ok";
    }

    @PostMapping("/form-post2")
    @ResponseBody
//    ModelAttribute가 데이터 바인딩을 하는데 도움이 되지만 생략해도 된다.
    public String formpost2(@ModelAttribute  Hello hello){ // ModelAttribute는 생략가능
        System.out.println(hello);
        return "ok";
    }

//    Case2. multipart/form-data 방식(text와 파일) 전송
//    url명 : form-post3, 메서드명 : formFilePost3, 화면명:form-view2
//    form태그 name, email, password, file
    @GetMapping("/form-file-view")
    public String formFilePost(){
        System.out.println("Smart Yeji nim");
        return "form-file-view";
    }

    @PostMapping("/form-file-post")
    @ResponseBody
    public String formfilepost(Hello hello, // hello 에서 한번에 받아도 된다.
                               @RequestParam("photh")MultipartFile photo){
        System.out.println("Smart Yeji Nim2");
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "ok";
    }

    // case3. js를 활용한 form 데이터 전송(text)
    @GetMapping("/axios-form-view")
    public String axiosFormView(){
        // name, email, password 를 전송
        return "axios-form-view";
    }

    @PostMapping("/axios-form-view")
    @ResponseBody
    // axios를 통해 넘어오는 형식이 key1=value1&key2=value2 등 url 인코딩 방식
    public String axiosFormPost(Hello hello){
        System.out.println(hello);
        return "ok";
    }

    // case4. js를 활용한 form 데이터 전송(+file)
    @GetMapping("/axios-form-file-view")
    public String axiosFormFileView(){
        return "axios-form-file-view";
    }
    @PostMapping("/axios-form-file-view")
    @ResponseBody
    public String axiosFormFileViewPost(Hello hello,
                                        @RequestParam(value = "file") MultipartFile file){
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";
    }
//    case5. js를 활용한 form데이터 전송(+file)
//    case6. js를 활용한 json데이터 전송(+file)
//    case7. js를 활용한 json데이터 전송(+multi file)
}
