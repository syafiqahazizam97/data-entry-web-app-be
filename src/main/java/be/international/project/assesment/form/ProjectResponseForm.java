package be.international.project.assesment.form;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponseForm {

    private String id;
    private String code;
    private String name;
    private String category;
    private String brand;
    private String type;
    private String description;
    
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
}
