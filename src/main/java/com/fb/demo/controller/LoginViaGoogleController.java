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
import com.fb.demo.repository.GoogleDeveloperDetailsRepository;
import com.fb.demo.repository.TenantRepository;
import com.fb.demo.service.LoginViaGoogleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController("v1LoginViaGoogleController")
@RequestMapping
public class LoginViaGoogleController {

    @Autowired
    private LoginViaGoogleService loginViaGoogelService;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private GoogleDeveloperDetailsRepository googleDeveloperDetailsRepository;

    @GetMapping(path = "/{tenant}/googleOauth")
    @ApiImplicitParams({@ApiImplicitParam(name = "tenant", paramType = "path")})
    public RedirectView loginViaGoogle(
                    @PathVariable(name = "tenant", required = true) String tenant)
                    throws Exception {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(loginViaGoogelService.getRedirectUrl(tenant));
        return redirectView;
    }

    @GetMapping(path = "/{tenant}/login/oauth2/code/google")
    @ApiImplicitParams({@ApiImplicitParam(name = "tenant", paramType = "path")})
    public ModelAndView processAndRedirect(@RequestParam("code") String code,
                    @PathVariable(name = "tenant", required = true) String tenant,
                    HttpServletRequest request, ModelMap modelMap) throws Exception {
        modelMap.addAttribute("googleToken",
                        loginViaGoogelService.getGoogleAccessToken(code, tenant));
        Tenant tenantFromDB = tenantRepository.getTenantByName(tenant);
        String baseUrl = null;
        String url = googleDeveloperDetailsRepository.findByParentTenant(tenantFromDB.getId())
                        .getRedirectUrl();
        baseUrl = url.replace("http://", "");
        return new ModelAndView("redirect:http://" + baseUrl.substring(0, baseUrl.indexOf("/"))
                        + "/callback.html", modelMap);
    }

}
