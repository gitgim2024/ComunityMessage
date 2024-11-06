package com.tech.whale.message.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tech.whale.message.dao.MessageDao;
import com.tech.whale.message.dto.MessageImageDto;

@RestController
@RequestMapping("message/api/images")
public class ImageController {
    @Autowired
	private MessageDao iDao;

	@PostMapping("/upload")
	public List<Integer> uploadImage(MultipartHttpServletRequest mtfrequest) throws IOException {
		System.out.println(">>upload진입");
		String workPath = System.getProperty("user.dir");
		String uploadDir = workPath + "\\src\\main\\resources\\static\\images\\message\\uploads\\";

		List<Integer> imageids = new ArrayList<>();
		List<MultipartFile> fileList = mtfrequest.getFiles("file");
		
		for (MultipartFile mf : fileList) {
			String orginalFile = mf.getOriginalFilename();
			System.out.println("오리지널파일 : " + orginalFile);
			try {
				if (!orginalFile.equals("")) {
					// [DB]기록추가
					MessageImageDto imgdto = new MessageImageDto();
					imgdto.setImg_org_name(orginalFile);
					iDao.addMsgImg(imgdto);
					int imageid = imgdto.getImg_id();
					System.out.println("이미지 아이디 : "+imageid);
					imageids.add(imageid);
					// 파일 저장 경로 생성
					String changeFile = imageid + "_" + orginalFile;
					System.out.println("변형파일 : " + changeFile);
					imgdto.setImg_chg_name(changeFile);
					
					String filePath = uploadDir + changeFile;
					System.out.println("파일경로 : " + filePath);
					imgdto.setImg_savepath(filePath);
					
					// 파일 저장
					mf.transferTo(new File(filePath));
					System.out.println("업로드성공 : " + filePath);
					// 클라이언트에 전달할 URL 생성
					String imageUrl = "api/images/" + changeFile;
					imgdto.setImg_url(imageUrl);
					System.out.println("이미지 업로드 성공");
					iDao.updateMsgImg(imgdto);
					System.out.println("이미지 DB저장 성공");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("이미지 파일 저장 실패");
			}
		}
		return imageids; // 이미지 id 반환
	}

	@GetMapping(value = "/{imagename}")
	public ResponseEntity<Resource> getImage(@PathVariable String imagename) {
		System.out.println(">>getImage");
		String workPath = System.getProperty("user.dir");
		String uploadDir = workPath + "\\src\\main\\resources\\static\\images\\message\\uploads\\";
		File imageFile = new File(uploadDir + imagename);
		if (!imageFile.exists()) {// 이미지없음
			System.out.println("이미지없음");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		System.out.println("이미지있음");

		// 리소스 핸들링 기능을 활용하여 파일을 쉽게 반환할 수 있도록 담기
		Resource resource = new FileSystemResource(imageFile);
		HttpHeaders headers = new HttpHeaders();

		// 이미지 파일의 MIME 타입 설정 (확장자에 따라 변경 가능)
		// 응답에 포함될 HTTP 헤더를 설정
		String extension = imagename.substring(imagename.lastIndexOf(".") + 1).toLowerCase();
		switch (extension) {// 필요에 따라 다른 MIME 타입으로 변경
		case "jpg":
		case "jpeg":
			headers.setContentType(MediaType.IMAGE_JPEG);
			break;
		case "png":
			headers.setContentType(MediaType.IMAGE_PNG);
			break;
		case "gif":
			headers.setContentType(MediaType.IMAGE_GIF);
			break;
		default:
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // 기본 설정
		}

		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
}