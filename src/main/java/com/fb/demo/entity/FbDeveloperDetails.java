package com.fb.demo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import org.hibernate.type.TrueFalseType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "FbDeveloperDetails")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "FbDevCred", description = "Fb Developer details")
public class FbDeveloperDetails extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Column(name = "parent_tenant", nullable = false, updatable = false, insertable = true,
                    unique = false)
    @ApiModelProperty(value = "clientId", notes = "Parent Tenant", required = true)
    private Integer parentTenant;
    @Column(name = "client_id", nullable = false, updatable = true, insertable = true,
                    unique = false)
    @ApiModelProperty(notes = "Client Id", value = "clientId", required = true)
    private String clientId;
    @Column(name = "client_secret", nullable = false, updatable = true, insertable = true,
                    unique = false)
    @ApiModelProperty(notes = "Client Secret", value = "clientSecret", required = true)
    private String clientSecret;
    @Column(name = "redirect_url", nullable = false, updatable = true, insertable = true,
                    unique = false)
    @ApiModelProperty(notes = "Redirect Url", value = "redirectUrl", required = true)
    private String redirectUrl;

}
