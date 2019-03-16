package com.map.web.controller;

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
//
//    @GetMapping("/admin/users")
//    @JsonView(DetialView.class)
//    public ResultModel getUsers(int pageNo, int pageSize) {
//
//        PageInfo<User> list = userService.getUsers(pageNo, pageSize);
//        List<User> users = list.getList();
//        System.out.println(list);
//        if (!users.isEmpty()) {
//            return ResultBuilder.getSuccess(users);
//        } else {
//            return ResultBuilder.getFailure(1, "该范围内没有用户");
//        }
//    }
//
//    @RequestMapping(value = "/admin/userlock/{userId:\\d+}")
//    public ResultModel lockUser(@PathVariable int userId) {
//        return userService.lockedUser(userId);
//    }
//
//    @RequestMapping(value = "/admin/userunlock/{userId:\\d+}")
//    public ResultModel unLockUser(@PathVariable int userId) {
//        return userService.unLockUser(userId);
//    }
//
//    @PostMapping(value = "/user")
//    @JsonView(SimpleView.class)
//    public ResultModel getUserByName(String username) {
//        User user = userService.findUserByUsername(username);
//        if (user == null) {
//            ResultBuilder.getFailure(1, "用户不存在");
//        }
//        return ResultBuilder.getSuccess(user);
//    }
//
//    protected Integer getUserIdFromToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String token = request.getHeader("token");
//        Integer userId = null;
//        if (token != null) {
//            try {
//                Map<String, Claim> map = JWTUtils.verifyToken(token);
//                userId = map.get("id").asInt();
//            } catch (UnsupportedEncodingException e) {
//                userId = null;
//            } catch (Exception e) {
//                request.getRequestDispatcher("/utils/logonExpires").forward(request, response);
//                e.printStackTrace();
//            }
//        }
//        return userId;
//    }
}
