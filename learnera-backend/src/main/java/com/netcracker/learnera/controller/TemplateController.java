package com.netcracker.learnera.controller;

import com.netcracker.learnera.entity.template.Template;
import com.netcracker.learnera.service.TemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/templates")
public class TemplateController extends BasicController<Template, Long> {

    private TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        super(templateService);
        this.templateService = templateService;
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<Iterable<Template>> findAllByTeacherId(@PathVariable("teacherId") Long teacherId) {
        return new ResponseEntity<>(templateService.findAllByTeacherId(teacherId), HttpStatus.OK);
    }
    
}
