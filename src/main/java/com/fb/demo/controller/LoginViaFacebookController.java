package com.fb.demo.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.fb.demo.entity.Tenant;
import com.fb.demo.repository.FbDeveloperDetailsRepository;
import com.fb.demo.repository.TenantRepository;
import com.fb.demo.service.LoginViaFacebookService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController("v1LoginViaFacebookController")
@RequestMapping
public class LoginViaFacebookController {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private FbDeveloperDetailsRepository fbDeveloperDetailsRepository;

    @Autowired
    private LoginViaFacebookService loginViaFacebookService;

    @GetMapping(path = "/{tenant}/facebookOauth")
    @ApiImplicitParams({@ApiImplicitParam(name = "tenant", paramType = "path")})
    public RedirectView loginViaFacebook(
                    @PathVariable(name = "tenant", required = true) String tenant)
                    throws Exception {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(loginViaFacebookService.getRedirectUrl(tenant));
        return redirectView;
    }

    @GetMapping(path = "/{tenant}/login/oauth2/code/facebook")
    public ModelAndView processAndRedirect(@RequestParam("code") String code,
                    @PathVariable(name = "tenant", required = true) String tenant,
                    HttpServletRequest request, ModelMap modelMap) throws Exception {
        modelMap.addAttribute("fbAccessToken",
                        loginViaFacebookService.getFacebookAccessToken(code, tenant));
        Tenant tenantFromDB = tenantRepository.getTenantByName(tenant);
        String baseUrl = null;
        String url = fbDeveloperDetailsRepository.findByParentTenant(tenantFromDB.getId())
                        .getRedirectUrl();
        baseUrl = url.replace("http://", "");
        return new ModelAndView("redirect:http://" + baseUrl.substring(0, baseUrl.indexOf("/"))
                        + "/callback.html", modelMap);
    }
}
