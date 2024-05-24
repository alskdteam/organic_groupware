package com.ohgiraffers.semi_project.subpage.main.controller;

import com.ohgiraffers.semi_project.subpage.edoc.model.service.EdocService;
import com.ohgiraffers.semi_project.subpage.main.model.dto.NoticeDTO;
import com.ohgiraffers.semi_project.subpage.main.model.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/login")
public class NoticeController {

    private final MainService mainService;

    @Autowired
    public NoticeController(MainService mainService) {
        this.mainService = mainService;
    }
    
    @GetMapping("/main")
    public String main(Model model) {
        List<NoticeDTO> selectNotice = mainService.selectNotice();

        System.out.println("selectNotice = " + selectNotice);
        model.addAttribute("selectNotice", selectNotice);

//        System.out.println(selectNotice);
        return "login/main";
    }

}