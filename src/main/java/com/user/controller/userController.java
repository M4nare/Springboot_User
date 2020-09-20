package com.user.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.user.KakaoAPI;
import com.user.domain.userVO;
import com.user.domain.userid;
import com.user.mapper.userMapper;



@Controller
public class userController {
	  
    @Autowired
    private KakaoAPI kakao;
    
    @Autowired
    private userMapper userMapper;
    
    String sid = null;
    

	 @RequestMapping(value="/index")
	    public String index() {
	        
	        return "index";
	    }
	    
	 @RequestMapping(value="/login")
	    public String login(@RequestParam("code") String code, HttpSession session) {
	        String access_Token = kakao.getAccessToken(code);
	        HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
	        System.out.println("login Controller : " + userInfo);
	        session.setAttribute("access_Token", access_Token);
	        session.setAttribute("nickname", userInfo.get("nickname"));
	        session.setAttribute("id", userInfo.get("email"));
	        
	        sid = (String) session.getAttribute("id");


	        return "redirect://localhost:8000/"+session.getAttribute("id");
	    }
	 
	 
	 // 상세
	    @RequestMapping(value="/{id}",method=RequestMethod.GET)
	    public ModelAndView view(@PathVariable("id") String id, HttpSession session) throws Exception{
	    	
	       

	      

	        if(sid == id)
	        {
	        	String url="http://localhost:9999/board/userinfo/"+id;
		    	RestTemplate restTemplate = new RestTemplate();
		    	String resp = restTemplate.getForObject(url, String.class);
		    	JSONParser parser = new JSONParser();

		    	JSONObject board = (JSONObject)parser.parse(resp);
		    	
		    	
		    	JSONArray arr = (JSONArray)board.get("board");
		    	
	    		JSONObject tmp = (JSONObject)arr.get(arr.size()-1);//인덱스 번호로 접근해서 가져온다.
	  	
	    		session.setAttribute("bno", tmp.get("bno"));
	    		session.setAttribute("subject", (String)tmp.get("subject"));
	    		session.setAttribute("date", (String)tmp.get("reg_date"));
		    	
		    
		       
	        	userVO user = userMapper.userView(id);
		        return new ModelAndView("userView","user",user);

	        }
	        
	        else 
	        {
	        	session.setAttribute("bno", "");
	    		session.setAttribute("subject", "");
	    		session.setAttribute("date", "");
		        userVO user1 = userMapper.userView(sid);
		        return new ModelAndView("userView","user",user1);
	        }
	        

	 
	        
	        
	    }
	    
	    
	    //상태메시지 수정 페이지(GET)
	    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	    public ModelAndView updateForm(@PathVariable("id") String id) throws Exception{
	            
	        userVO user = userMapper.userView(id);
	        if(sid == id)
	        {
		        return new ModelAndView("userUpdate","user",user);

	        }
	        
	        else 
	        {
		        userVO user1 = userMapper.userView(sid);
		        return new ModelAndView("userUpdate","user",user1);
	        }
	    }
	        
	  //게시글 수정(PATCH)
	    @RequestMapping(value="/edit/{id}", method=RequestMethod.PATCH)
	    public String update(@ModelAttribute("userVO")userVO user,@PathVariable("id") String id) throws Exception{
	            
	        userMapper.userUpdate(user);
	            
	        return "redirect://localhost:8000/"+id;
	    }
	    
	    @RestController
	    public class UserController {
	    	@CrossOrigin(origins= {"http://localhost:8080", "http://localhost:1010", "http://localhost:9999"})



	    	@RequestMapping(method = RequestMethod.POST, path = "/user")
	        public HashMap<String, Object> postRequest(@RequestBody userid user) throws Exception{
	    		List<userVO> list = userMapper.userView(user);
	            HashMap<String, Object> map = new HashMap<>();
	            map.put("user", list);
	            
	            return map;
	        }
	    }
	    
}
