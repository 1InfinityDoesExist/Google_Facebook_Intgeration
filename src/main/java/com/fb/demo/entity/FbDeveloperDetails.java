package com.fb.demo.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "FbDeveloperDetails")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "FbDevCred")
public class FbDeveloperDetails extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "clientId", notes = "Parent Tenant")
    private Integer parentTenant;
    @ApiModelProperty(notes = "Client Id")
    private String clientId;
    @ApiModelProperty(notes = "Client Secret")
    private String clientSecret;
    @ApiModelProperty(notes = "Redirect Url")
    private String redirectUrl;

}
