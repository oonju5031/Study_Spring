package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String newMember(Model model) {
        return "member-new";
    }

    @PostMapping("/members/new/submit")
    public String submitNewMember(Member member) {
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members/list")
    public String memberList(Model model) {
        model.addAttribute("memberList", memberService.findMembers());

        return "member-list";
    }

}
