package com.study.demo.controller;

import com.study.demo.DTO.AccessToken;
import com.study.demo.DTO.GithubUser;
import com.study.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName: GithubProvider
 * @author: Lio9
 * @create: 2022-6-23
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state)
    {
        AccessToken accessToken = new AccessToken();
        accessToken.setCode(code);
        accessToken.setRedirect_uri("http://localhost:8887/callback");
        accessToken.setState(state);
        accessToken.setClient_id("72f7b66e37ec9489f05e");
        accessToken.setClient_secret("15df1c2734d7db1c9c966e5c3be96d83883883b9");
        String token = githubProvider.getAccessToken(accessToken);
        GithubUser user = githubProvider.getUser(token);
        System.out.println("userName:"+user.getName());
        return "index";
    }

}
