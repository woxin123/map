package com.map.web.controller.portal;

import com.map.common.ServerResponse;
import com.map.dto.UserInputDTO;
import com.map.vo.UserOutputVO;
import com.map.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/register", method = POST)
    public ServerResponse register(@Valid UserInputDTO user, BindingResult bindingResult) {

        return userService.register(user);
    }


    @RequestMapping(value = "/user/login", method = POST)
    public ServerResponse login(String account, String password) throws Exception {
        return userService.login(account, password);
    }

    @GetMapping("user")
    public ServerResponse<UserOutputVO> getUserMessageById(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("id");
        return userService.getUserMessageById(userId);

    }

}
