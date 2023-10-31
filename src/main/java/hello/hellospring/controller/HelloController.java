package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    // required 속성 : default는 true이고, false로 지정하면 파라미터를 꼭 넘기지 않아도 된다 (null로 표시)
    // true로 지정하면 파라미터 무조건 넘겨줘야 함 (화이트라벨 에러남)
    public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody // 응답 Body에 해당 데이터를 직접 넣어주는 것
    public String helloString(@RequestParam("name") String name){
        // name에 spring을 넣으면 "hello spring"이 리턴
        // HTML(뷰)을 리턴하는 것이 아니라 데이터 그 자체를 리턴, 실행하고 웹브라우저에서 소스코드 보면 확인할 수 있음
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;  // JSON 객체를 리턴 -> {"name":"spring"}
    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
