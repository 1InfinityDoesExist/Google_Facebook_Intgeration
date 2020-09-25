package com.fb.demo.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "GoogleDeveloperDetails")
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoogleDeveloperDetails extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer parentTenant;
    private String clientId;
    private String clientSecret;
    private String redirectUrl;
}
