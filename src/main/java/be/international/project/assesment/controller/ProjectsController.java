package be.international.project.assesment.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.international.project.assesment.dto.PaginationDTO;
import be.international.project.assesment.exception.ResourceConflictException;
import be.international.project.assesment.exception.ResourceNotFoundException;
import be.international.project.assesment.form.ProjectRequestForm;
import be.international.project.assesment.form.ProjectResponseForm;
import be.international.project.assesment.model.entity.Projects;
import be.international.project.assesment.service.ConstantsService;
import be.international.project.assesment.service.ProjectsService;

@RestController
@RequestMapping("/v1/api")
public class ProjectsController {

	@Autowired
	private ProjectsService projectsService;

	@GetMapping("/projects")
	public PaginationDTO<?> getAllProjects(
			@RequestParam(value = "pageNo", defaultValue = ConstantsService.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ConstantsService.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ConstantsService.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ConstantsService.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return projectsService.findAll(pageNo, pageSize, sortBy, sortDir);
	}

	@GetMapping("/projects/codes/{code}")
	public ResponseEntity<ProjectResponseForm> getProjectsByCode(@PathVariable(value = "code") String code)
			throws ResourceNotFoundException {
		return ResponseEntity.ok().body(projectsService.findByCode(code));
	}

	@GetMapping("/projects/{id}")
	public ResponseEntity<ProjectResponseForm> getProjectsById(@PathVariable(value = "id") String id)
			throws ResourceNotFoundException {

		return ResponseEntity.ok().body(projectsService.findById(id));
	}

	@PostMapping("/projects")
	public ResponseEntity<ProjectResponseForm> createProjects(@Valid @RequestBody ProjectRequestForm form)
			throws ResourceConflictException, ParseException {
		return ResponseEntity.ok().body(projectsService.create(form));
	}

	@PutMapping("/projects/codes/{code}")
	public ResponseEntity<ProjectResponseForm> updateProjectsByCode(@PathVariable(value = "code") String code,
			@Valid @RequestBody ProjectRequestForm form) throws ResourceNotFoundException, ParseException {
		return ResponseEntity.ok(projectsService.updateByCode(code, form));
	}

	@DeleteMapping("/projects/{id}")
	public Map<String, Boolean> deleteProjects(@PathVariable(value = "id") String id) throws ResourceNotFoundException {

		final Boolean result = projectsService.delete(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", result);
		return response;
	}

	@GetMapping("/projects/search")
	public List<Projects> searchProducts(@RequestParam String term) {
		return projectsService.searchProduct(term);
	}
}