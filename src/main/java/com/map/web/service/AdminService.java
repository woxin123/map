package com.map.web.service;

import com.map.domain.Admin;
import com.map.web.model.ResultModel;

import java.util.Map;

public interface AdminService {

    ResultModel register(Admin admin);

    ResultModel login(Admin admin);

    String login(Admin admin, Map<String, String> errors);
}
