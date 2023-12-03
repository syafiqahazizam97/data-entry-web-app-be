package be.international.project.assesment.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequestForm {

	private String id;
    private String code;
    private String name;
    private String category;
    private String brand;
    private String type;
    private String description;
}
