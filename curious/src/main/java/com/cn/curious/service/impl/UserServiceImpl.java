package com.cn.curious.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;  

import com.cn.curious.dao.IUserDao;
import com.cn.curious.pojo.User;
import com.cn.curious.service.IUserService;
  

  
@Service("userService")
public class UserServiceImpl implements IUserService {  
    @Resource
    private IUserDao userDao;
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
    public User getUserById(int userId) {  
        // TODO Auto-generated method stub  
        return this.userDao.selectByPrimaryKey(userId);  
    }  
  
}  