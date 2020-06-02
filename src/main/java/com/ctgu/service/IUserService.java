package com.ctgu.service;

import com.ctgu.entity.User;

public interface IUserService
{
	User getUserInfoById(Integer id);

	void addUser(User user);

}
