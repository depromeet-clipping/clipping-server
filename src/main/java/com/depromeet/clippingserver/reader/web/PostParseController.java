package com.depromeet.clippingserver.reader.web;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpHeaders;import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PostParseController {
	
	@PostMapping(path = "/post")
	public ResponseEntity<String> getPost(@RequestBody UrlDto url) throws IOException{
		Document doc = Jsoup.connect(url.getUrl()).get();
		return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(doc.toString());
	}
}
