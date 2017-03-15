//package edu.nju.hostelworld.controller;
//
//import edu.nju.hostelworld.model.Member;
//import edu.nju.hostelworld.service.MemberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * Created by Sorumi on 17/2/24.
// */
//
////@Controller
//@RequestMapping("/test")
//public class TestController {
//
//    @Autowired
//    private MemberService memberService;
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public String get(@PathVariable("id") String id, ModelMap model) {
//        System.out.println("get" + id);
//        Member member = memberService.findMemberByID(id);
//        model.addAttribute("member", member);
//        return "test/showMember";
//    }
//
//    @RequestMapping("/show")
//    public String toIndex(HttpServletRequest request, ModelMap model) {
//        String id = request.getParameter("id");
//        Member member = memberService.findMemberByID(id);
//        model.addAttribute("member", member.getName());
//        return "test/showMember";
//    }
//
//    //test
//    @RequestMapping(value = "get0")
//    public void get0(String p1, ModelMap map) {
//        map.addAttribute("p1", p1);//往页面传递
//    }
//
////    @RequestMapping(value = "/add", method = RequestMethod.GET)
////    public ModelAndView student(ModelMap modelMap) {
////        modelMap.addAttribute("member", new Member());
////        return new ModelAndView("add", modelMap);
////    }
//
//    @RequestMapping(value = "/add", method = RequestMethod.GET)
//    public String student(ModelMap modelMap) {
//        modelMap.addAttribute("member", new Member());
//        return "test/add";
//    }
//
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public String addStudent(Member member, ModelMap model) {
//        System.out.println(member.getName());
//        model.addAttribute("member", member);
////        model.addAttribute("name", member.getName());
////        model.addAttribute("ID", member.getID());
//        return "test/member";
//    }
//
////    @RequestMapping(value = "/test/add", method = RequestMethod.GET)
////    public String addTest(ModelMap modelMap) {
////        modelMap.addAttribute("test", new TestBean());
////        return "test/addTest";
////    }
////
////    @RequestMapping(value = "/test/add", method = RequestMethod.POST)
////    public String showTest(@ModelAttribute("test") TestBean test, ModelMap model) {
//////        model.addAttribute("name", member.getName());
//////        model.addAttribute("ID", member.getID());
////        return "test/showTest";
////    }
//}
//
//
