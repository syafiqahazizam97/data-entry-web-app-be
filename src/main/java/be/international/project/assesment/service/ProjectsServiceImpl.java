package be.international.project.assesment.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.international.project.assesment.dto.PaginationDTO;
import be.international.project.assesment.exception.ResourceConflictException;
import be.international.project.assesment.exception.ResourceNotFoundException;
import be.international.project.assesment.form.ProjectRequestForm;
import be.international.project.assesment.form.ProjectResponseForm;
import be.international.project.assesment.model.entity.Projects;
import be.international.project.assesment.repository.ProjectRepository;
import be.international.project.assesment.util.UUIDUtil;

@Service
public class ProjectsServiceImpl implements ProjectsService {

	@Autowired
	private ProjectRepository projectRepo;

	@Override
	public PaginationDTO<ProjectResponseForm> findAll(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageable;
		if (pageNo > 0) {
			pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		} else {
			pageable = PageRequest.of(pageNo, Integer.MAX_VALUE, sort);
		}

		Page<Projects> listRecord = projectRepo.findAll(pageable);
		List<Projects> listPageableRecord = listRecord.getContent();

		List<ProjectResponseForm> listResponse = new ArrayList<ProjectResponseForm>();
		for (Projects record : listPageableRecord) {
			ProjectResponseForm responseForm = createResponseForm(record);
			listResponse.add(responseForm);
		}

		PaginationDTO<ProjectResponseForm> paginationDTO = new PaginationDTO<>();
		paginationDTO.setContent(listResponse);
		paginationDTO.setTotalRecords(listRecord.getTotalElements());

		return paginationDTO;
	}


	@Override
	public ProjectResponseForm findByCode(String code) {
		Projects record = projectRepo.findByCode(code);
		
		ProjectResponseForm responseForm = createResponseForm(record);
		BeanUtils.copyProperties(record, responseForm);
		
		return responseForm;
	}
	
	@Override
	public ProjectResponseForm findById(String id) throws ResourceNotFoundException {
		Projects record = projectRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Projects not found for this id :: " + id));
		ProjectResponseForm responseForm = createResponseForm(record);
		return responseForm;
	}

	@Override
    public List<Projects> searchProduct(String term) {
    	
        return projectRepo.findAll()
                .stream()
                .filter(project -> project.getName().toLowerCase().contains(term.toLowerCase()) ||
                        project.getCode().toLowerCase().contains(term.toLowerCase()) ||
                        project.getCategory().toLowerCase().contains(term.toLowerCase()) ||
                        project.getBrand().toLowerCase().contains(term.toLowerCase()) ||
                        project.getType().toLowerCase().contains(term.toLowerCase())||
                        project.getDescription().toLowerCase().contains(term.toLowerCase()))
                .collect(Collectors.toList());
    }

	@Override
	@Transactional
	public ProjectResponseForm create(ProjectRequestForm form) throws ResourceConflictException, ParseException {
		
		Projects record = new Projects();
		BeanUtils.copyProperties(form, record);
		
		record = projectRepo.saveAndFlush(record);

		ProjectResponseForm responseForm = createResponseForm(record);
		return responseForm;
	}

	@Override
	@Transactional
	public ProjectResponseForm updateByCode(String code, ProjectRequestForm form)
			throws ResourceNotFoundException, ParseException {
		
		Projects record = projectRepo.findByCode(code);
		
		BeanUtils.copyProperties(form, record);
		record.setCode(code);
	
		projectRepo.saveAndFlush(record);

		ProjectResponseForm responseForm = createResponseForm(record);
		return responseForm;
	}

	@Override
	public Boolean delete(String id) throws ResourceNotFoundException {
		Projects record = projectRepo.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Projects not found for this id :: " + id));

		projectRepo.delete(record);

		return Boolean.TRUE;
	}

	private ProjectResponseForm createResponseForm(Projects record) {
		ProjectResponseForm responseForm = new ProjectResponseForm();
		BeanUtils.copyProperties(record, responseForm);
		return responseForm;
	}

}
