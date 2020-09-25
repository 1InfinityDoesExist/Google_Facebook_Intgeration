package com.fb.demo.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "FbDeveloperDetails")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FbDeveloperDetails extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer tenant;
    private String clientId;
    private String clientSecret;
    private String redirectUrl;

}
