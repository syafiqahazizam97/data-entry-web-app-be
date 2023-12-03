package be.international.project.assesment.service;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import be.international.project.assesment.dto.PaginationDTO;
import be.international.project.assesment.exception.ResourceConflictException;
import be.international.project.assesment.exception.ResourceNotFoundException;
import be.international.project.assesment.form.ProjectRequestForm;
import be.international.project.assesment.form.ProjectResponseForm;
import be.international.project.assesment.model.entity.Projects;

public interface ProjectsService {

	PaginationDTO<ProjectResponseForm> findAll(int pageNo, int pageSize,
			String sortBy, String sortDir);
	
	ProjectResponseForm findByCode(String code);

	ProjectResponseForm findById(String id) throws ResourceNotFoundException;

	ProjectResponseForm create(@Valid ProjectRequestForm form) throws ResourceConflictException, ParseException;

	ProjectResponseForm updateByCode(String code, ProjectRequestForm form) throws ResourceNotFoundException, ParseException;

	Boolean delete(String id) throws ResourceNotFoundException;

	List<Projects> searchProduct(String term);





	

}
