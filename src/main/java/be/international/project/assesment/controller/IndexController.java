package be.international.project.assesment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@GetMapping("/")
	public String getIndexPage() {
		return "Welcome Project Microservice";
	}

}
