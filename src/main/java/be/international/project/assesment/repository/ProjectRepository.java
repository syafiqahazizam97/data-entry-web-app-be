package be.international.project.assesment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.international.project.assesment.model.entity.Projects;

public interface ProjectRepository extends JpaRepository<Projects, String> {

	Projects findByCode(String code);




}
