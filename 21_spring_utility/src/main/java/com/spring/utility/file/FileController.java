package com.spring.utility.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("/file")
public class FileController {

	/*
	   
	    # 스프링 파일기능 구현 방법
	   
			1) pom.xml파일에 파일 업로드 및 썸네일 의존성을 추가한다.
			
				<!-- 파일업로드 -->
		        <dependency>
		       		<groupId>commons-fileupload</groupId>
		       		<artifactId>commons-fileupload</artifactId>
		       		<version>1.3.1</version>
		        </dependency>
		       
		        <!-- 썸네일 -->
		        <dependency>
		       		<groupId>net.coobird</groupId> 
		       		<artifactId>thumbnailator</artifactId> 
		       		<version>0.4.8</version> 
		        </dependency> 
		
			2) servlet-context.xml파일에 아래의 설정을 추가한다.
			
				<beans:bean id="multipartResolver"  class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
			       <beans:property name="maxUploadSize"   value="52428800" />
			       <beans:property name="maxInMemorySize" value="52428800" />
			       <beans:property name="defaultEncoding" value="utf-8" />
			    </beans:bean>
			    
		    
		    3) 컨트롤러의 메서드 파라메타에 MultipartHttpServletRequest를 선언하여 파일 업 다운로드 관련 메서드를 사용한다.
	
	 */
	
	private final String FILE_REPO_PATH = "C:\\spring_file_test\\";
	
	@GetMapping("/main")
	public String main() {
		return "file/main";
	}
	
	// 파일 업로드 컨트롤러
	@PostMapping("/upload")
	@ResponseBody
	public String upload(MultipartHttpServletRequest multipartRequest) throws IllegalStateException, IOException {
		
		Iterator<String> fileList = multipartRequest.getFileNames(); // <input type="file"> 엘리먼트를 모두 가져온다.
		
		while (fileList.hasNext()) { // 다음 <input type="file">가 있으면
			
			MultipartFile uploadFile = multipartRequest.getFile(fileList.next()); // 하나의 <input type="file">를 반환한다.
		
			if (!uploadFile.getOriginalFilename().isEmpty()) { // 실제로 업로드된 파일이 있으면
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String uploadDate = sdf.format(new Date());
				
				UUID uuid = UUID.randomUUID(); // 범용고유식별자 기능을 사용하여 파일명의 중복을 방지한다.
				
				String fileName = uploadDate + "_" + uuid + "_" + uploadFile.getOriginalFilename();
				
				File file = new File(FILE_REPO_PATH + fileName); // 임시파일을 생성한다.
				uploadFile.transferTo(file);    				// transferTo 메서드를 사용하여 임시파일에 업로드파일을 덮어쓴다.
				
			}
		
		}
		
		String result= "<script>";
				result += "alert('success');";
			    result += "location.href = 'main'";
			    result +="</script>";
		
		return result;
		
	}
	
	
	// 썸네일 컨트롤러 : 이미지 파일을 읽어들여서 화면에 출력한다.
	@GetMapping("/thumbnails")
	public void thumbnails(@RequestParam("fileName") String fileName , HttpServletResponse response) throws Exception {
	
		OutputStream out = response.getOutputStream();
		String filePath = FILE_REPO_PATH + fileName;
		
		File image = new File(filePath);
		if (image.exists()) { 
			Thumbnails.of(image).size(800,800).outputFormat("png").toOutputStream(out);
		}
		byte[] buffer = new byte[1024 * 8];
		out.write(buffer);
		out.close();
		
	}
	
	
	// 파일 다운로드 컨트롤러
	@GetMapping("/download")
	public void download(@RequestParam("fileName") String fileName , HttpServletResponse response) throws Exception {
        	
		String filePath = FILE_REPO_PATH + fileName;			
        	
    	File file = new File(filePath);
    	response.setHeader("Content-Disposition", "attachment;filename=" + file.getName()); 
    	
    	FileInputStream fileInputStream = new FileInputStream(filePath);
    	OutputStream out = response.getOutputStream();
    	
    	int read = 0;
        byte[] buffer = new byte[1024];
        while ((read = fileInputStream.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
                
    }
	
	// 파일 삭제 컨트롤러
	@PostMapping("/delete")
	@ResponseBody
	public String delete(@RequestParam("fileName") String fileName) {
		
		//new File(FILE_REPO_PATH + fileName).delete();
		File file = new File(FILE_REPO_PATH + fileName);
		
		String jsScript = "";
		if (file.exists()) { // 파일이 존재하면
			file.delete();   // 파일을 삭제한다.
			jsScript = "<script>";
    		jsScript += "alert('success');";
    		jsScript += "location.href = 'main';";
    		jsScript +="</script>";
		}
		else {
			jsScript = "<script>";
    		jsScript += "alert('fail');";
    		jsScript += "location.href = 'main';";
    		jsScript +="</script>";
		}
		
		return jsScript;
		
	}
	
	
	// 파일 수정 컨트롤러
	@PostMapping("/update")
	@ResponseBody
	public String update(MultipartHttpServletRequest multipartRequest) throws IllegalStateException, IOException {
		
		File file = new File(FILE_REPO_PATH + multipartRequest.getParameter("updateFileName"));
		String jsScript = "";
		
		if (file.exists()) {
			
			// 기존 파일 삭제
			file.delete();
			
			// 새로운 파일 업로드
			Iterator<String> fileList = multipartRequest.getFileNames();
			
			if (fileList.hasNext()) {
				
				MultipartFile uploadFile = multipartRequest.getFile(fileList.next());
				
				if (!uploadFile.getOriginalFilename().isEmpty()) {
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String uploadDate = sdf.format(new Date());
					
					String fileName = uploadDate + "_" + UUID.randomUUID() + "_" + uploadFile.getOriginalFilename();
					
					uploadFile.transferTo(new File(FILE_REPO_PATH + fileName));
					
					jsScript = "<script>";
		    		jsScript += "alert('success');";
		    		jsScript += "location.href = 'main';";
		    		jsScript +="</script>";
					
				}
				
			}
			
			
		}
		else {
			jsScript = "<script>";
    		jsScript += "alert('fail');";
    		jsScript += "location.href = 'main';";
    		jsScript +="</script>";
		}
		
		
		return jsScript;
		
		
	}
	
	
	
	
	
	
}
