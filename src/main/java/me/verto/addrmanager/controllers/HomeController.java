package me.verto.addrmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class HomeController {

  @RequestMapping("/")
  String index() {
    return "redirect:/app";
  }

  @RequestMapping({ "/app/**" })
  String app() {
    return "forward:/index.html";
  }

}
