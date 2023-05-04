package com.codingrecipe.project01.controller;

import com.codingrecipe.project01.dto.MemberDTO;
import com.codingrecipe.project01.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
//  @GetMapping("/Member/save") // member/member/save
    @GetMapping("/save")
    public String saveForm() {
        return "save"; //save.jsp를 띄워줌
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        int saveResult = memberService.save(memberDTO);
        if(saveResult > 0) {
            return "login"; //가입 성공
        } else {
            return "save"; //가입 실패
        }
    }
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        HttpSession session) {
        boolean loginResult = memberService.login(memberDTO);
        if(loginResult) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            return "main";
        } else {
            return "login";
        }
    }

    @GetMapping("/")
    public String findAll(Model model) { //데이터를 가져가기 때문에 model객체 사용
        List<MemberDTO> membetDTOList = memberService.findAll();
        model.addAttribute("memberList", membetDTOList);
        return "list";
    }
}

