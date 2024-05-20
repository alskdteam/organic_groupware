package com.ohgiraffers.semi_project.subpage.edoc.controller;

import com.ohgiraffers.semi_project.subpage.edoc.model.dto.EdocFormCtDTO;
import com.ohgiraffers.semi_project.subpage.edoc.model.dto.EdocFormDTO;
import com.ohgiraffers.semi_project.subpage.edoc.model.dto.EdocFromEdocCtDTO;
import com.ohgiraffers.semi_project.subpage.edoc.model.dto.UploadFileDTO;
import com.ohgiraffers.semi_project.subpage.edoc.model.service.EdocService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.*;


@Controller
@RequestMapping("/subpage")
public class UploadFileController {

    private static final Logger logger = LogManager.getLogger(EdocFormCtFunctionController.class);

    private final EdocService edocService;
    private final MessageSource messageSource;


    public UploadFileController(EdocService edocService, MessageSource messageSource) {
        this.edocService = edocService;
        this.messageSource = messageSource;
    }

    @Autowired
    private ResourceLoader resourceLoader;


    public Resource convertByteArrayToImage(byte[] bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(bis);
        bis.close();
        return new ByteArrayResource(bytes);
    }
    

    @PostMapping("/uploadFile")
    public String uploadFile(
            @RequestParam("employee_sign") MultipartFile employee_sign,
            @RequestParam("employee_name") String employee_name,
            @RequestParam("edoc_form_ct_no") String edoc_form_ct_no,
            @RequestParam("user_id") String user_id,
            @RequestParam("edoc_form_no") String edoc_form_no,

            RedirectAttributes rttr, Locale locale,
            Model model) throws IOException {

        try {
            // 파일 데이터를 byte 배열로 변환하여 DTO 객체에 설정
            byte[] employeeSignBytes = employee_sign.getBytes();
            UploadFileDTO newFile = new UploadFileDTO();
            newFile.setEmployee_sign(employeeSignBytes);
            newFile.setEmployee_name(employee_name);
            newFile.setEdoc_form_ct_no(Integer.parseInt(edoc_form_ct_no));
            newFile.setUser_id(Integer.parseInt(user_id));
            newFile.setEdoc_form_no(Integer.parseInt(edoc_form_no));

            edocService.uploadFile(newFile);

//            사인등록후 select
            EdocFromEdocCtDTO insertedEdoc = edocService.selectEdocList(Integer.parseInt(edoc_form_ct_no));
            model.addAttribute("insertedEdoc", insertedEdoc);


            rttr.addFlashAttribute("successMessage", messageSource.getMessage("registMenu", null, locale));
            byte[] imageData = employeeSignBytes;

            // 바이트 배열을 Base64로 인코딩
            String base64ImageDate = Base64.getEncoder().encodeToString(imageData);
            model.addAttribute("base64ImageData", base64ImageDate);

        } catch (IOException e) {
            // 파일 처리 중 예외 발생 시 처리
            String errorMessage = "Error processing file: " + e.getMessage();
            rttr.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/errorPage"; // 에러 페이지로 리다이렉트
        }

        return "subpage/edocFrom/SelectEdocList";
    }




}
