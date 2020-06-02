package com.ctgu.service;

import com.ctgu.entity.User;

public interface IUserService
{
	User getUserInfoById(Integer id);

	void addUser(User user);

	void updateUser(User user);

	void deleteUserById(Integer id);

	void deleteUser(User user);

}
