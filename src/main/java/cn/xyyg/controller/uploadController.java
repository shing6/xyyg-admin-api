package cn.xyyg.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.xyyg.util.ResponseUtil;

@RestController
@RequestMapping("/file")
public class uploadController {
	
	/**
	 * 上传评论图片
	 * @param request
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/upload")
	public Object upload(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file)
			throws IOException {        
		      
		request.setCharacterEncoding("UTF-8");       
	      
		     
		        
		if(!file.isEmpty()) {            
			           
			String fileName = file.getOriginalFilename();            
			String path = null;            
			String type = null;
			String trueFileName=null;
			type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;            
			            
			if (type != null) {                
				if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
					// 项目在容器中实际发布运行的根路径                    
					String realPath = "E:/images";                    
					// 自定义的文件名称                    
					trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;                    
					// 设置存放图片文件的路径                   
					path = realPath + "/uploads/" + trueFileName;                    
					                   
					file.transferTo(new File(path));                    
					              
					}
				else {
						                  
						return ResponseUtil.badArgumentType();               
					}            
				}
			else {                
					         
					return ResponseUtil.badArgumentType();            
					} 
			
			
			return "/images/uploads/" +trueFileName;  
			
			}
		else {           
				        
				return ResponseUtil.fail();        
				}        
		 
		}
	
	/**
	 * 上传商品图片
	 * @param request
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/uploadGoods")
	public Object uploadGoods(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file)
			throws IOException {        
		System.out.println("执行uploadGoods");        
		request.setCharacterEncoding("UTF-8");       
	      
		   
		        
		if(!file.isEmpty()) {            
			           
			String fileName = file.getOriginalFilename();            
			String path = null;            
			String type = null;
			String trueFileName=null;
			type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;            
			            
			if (type != null) {                
				if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
					// 项目在容器中实际发布运行的根路径                    
					String realPath = "E:/images";                    
					// 自定义的文件名称                    
					trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;                    
					// 设置存放图片文件的路径                   
					path = realPath + "/uploadGoods/" + trueFileName;                    
					                   
					file.transferTo(new File(path));                    
					              
					}
				else {
						                  
						return ResponseUtil.badArgumentType();               
					}            
				}
			else {                
					         
					return ResponseUtil.badArgumentType();            
					} 
			
			
			return "/images/uploadGoods/" +trueFileName;  
			
			}
		else {           
				        
				return ResponseUtil.fail();        
				}        
		 
		}
	
	/**
	 * 上传商家图片
	 * @param request
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/uploadShop")
	public Object uploadShop(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file)
			throws IOException {        
		System.out.println("执行uploadShop");        
		request.setCharacterEncoding("UTF-8");       
	      
		
		        
		if(!file.isEmpty()) {            
			           
			String fileName = file.getOriginalFilename();            
			String path = null;            
			String type = null;
			String trueFileName=null;
			type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;            
			            
			if (type != null) {                
				if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
					// 项目在容器中实际发布运行的根路径                    
					String realPath = "E:/images";                    
					// 自定义的文件名称                    
					trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;                    
					// 设置存放图片文件的路径                   
					path = realPath + "/uploadShop/" + trueFileName;                    
					                   
					file.transferTo(new File(path));                    
					              
					}
				else {
						                  
						return ResponseUtil.badArgumentType();               
					}            
				}
			else {                
					         
					return ResponseUtil.badArgumentType();            
					} 
			
			
			return "/images/uploadShop/" +trueFileName;  
			
			}
		else {           
				        
				return ResponseUtil.fail();        
				}        
		 
		}
	}
				
		
	

     

