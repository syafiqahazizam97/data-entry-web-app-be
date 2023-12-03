package be.international.project.assesment.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDTO<T> {

	public PaginationDTO(Page<T> page) {
		this.content = page.getContent();
		this.totalRecords = page.getTotalElements();
	}

	private List<T> content;

	private long totalRecords;
}