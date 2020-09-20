package com.user.mapper;

import java.util.List;

import com.user.domain.userVO;
import com.user.domain.userid;
public interface userMapper {
	
	 public userVO userView(String id)throws Exception;
	 public void userUpdate(userVO vo)throws Exception;
	 
	 
     public List<userVO> userView(userid user);



}
