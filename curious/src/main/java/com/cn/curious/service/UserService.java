package com.cn.curious.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;  

import com.cn.curious.bean.User;
import com.cn.curious.dao.IUserDao;
  

  
@Service("userService")
public class UserService {  
    @Resource
    private IUserDao userDao;
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	public User getUserById(int userId) {  
        // TODO Auto-generated method stub  
        return this.userDao.selectByPrimaryKey(userId);  
    }  
  
}  
