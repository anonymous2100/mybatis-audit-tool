package com.ctgu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctgu.entity.User;
import com.ctgu.mapper.UserMapper;
import com.ctgu.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService
{
	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserInfoById(Integer id)
	{
		return userMapper.getUserInfoById(id);
	}

	@Override
	public void addUser(User user)
	{
		userMapper.insert(user);
	}

	@Override
	public void updateUser(User user)
	{
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public void deleteUserById(Integer id)
	{
		userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteUser(User user)
	{
		userMapper.deleteUser(user);
	}

}
