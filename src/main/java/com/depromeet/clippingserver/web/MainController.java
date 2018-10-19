package com.depromeet.clippingserver.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping(path="/")
	public String index(Model model, HttpServletRequest req, HttpServletResponse res) {
		String[] url = {"http://biz.chosun.com/site/data/html_dir/2018/10/14/2018101400453.html",
				"http://www.hani.co.kr/arti/economy/finance/865768.html?_fr=mt1",
				"http://www.hankookilbo.com/News/Read/201810141731374804?did=PA&dtype=3&dtypecode=389",
				"http://news.joins.com/article/23044508?cloc=joongang|home|newslist1big",
				"http://the300.mt.co.kr/newsView.html?no=2018101409337650172"};
		model.addAttribute("url", url);
		return "index";
	}
	
	@GetMapping(path="/post")
	public String goPostPage(String url, Model model) throws IOException{
		model.addAttribute("url", url);
		return "post/post";
	}
}
