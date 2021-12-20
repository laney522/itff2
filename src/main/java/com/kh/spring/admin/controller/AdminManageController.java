package com.kh.spring.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.admin.model.service.AdminService;
import com.kh.spring.chat.model.service.ChatService;
import com.kh.spring.chat.model.vo.ChatLog;
import com.kh.spring.common.HiSpringUtils;
import com.kh.spring.goods.model.service.GoodsService;
import com.kh.spring.goods.model.vo.Goods;
import com.kh.spring.member.model.vo.Member;
import com.kh.spring.sharing.model.vo.Attachment;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminManageController {
	
	@Autowired
	private AdminService adminService;

	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private ChatService chatService;

	@Autowired
	ServletContext application;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@GetMapping("/adminManage.do")
	public void adminManage() {}

///////////////////////////////////////////////////////////////////////////////
	
@GetMapping("/adminMemberList.do")
public String adminMemberList(
		@RequestParam(defaultValue = "1") int cPage, 
		Model model,
		HttpServletRequest request
		) {
	
	log.debug("cPage = {}", cPage);
	
	int limit = 10;
	int offset = (cPage - 1) * limit;
	
	// 1. 
	List<Member> list = adminService.selectMemberList(offset, limit);
	log.debug("list = {}", list);
	model.addAttribute("list", list);
	
	// 2. totalContent
	int totalContent = adminService.selectMemberTotalCount();
	log.debug("totalContent = {}", totalContent);
	model.addAttribute("totalContent", totalContent);
	
	// 3. pagebar
	String url = request.getRequestURI(); 
	String pagebar = HiSpringUtils.getPagebar(cPage, limit, totalContent, url);
	log.debug("pagebar = {}", pagebar);
	model.addAttribute("pagebar", pagebar);
	
	return "admin/adminMemberList";
}
	
	
///////////////////////////////////////////////////////////////////////////////

	/**
	 * [상영 영화 목록] 
	 */
	
//	@GetMapping("/adminMovieList.do")
//	public void adminMovieList(Model model) {
//		List<Movie> list = adminService.selectMovieList();
//		log.debug("list = {}", list);
//		model.addAttribute("list", list);
//	}
	
///////////////////////////////////////////////////////////////////////////////
	
	/**
	 * [굿즈 목록]
	 */
	
	@GetMapping("/adminGoodsList.do")
	public String adminGoodsList(
			@RequestParam(defaultValue = "1") int cPage, 
			Model model,
			HttpServletRequest request
			) {
		
		log.debug("cPage = {}", cPage);
		
		int limit = 10;
		int offset = (cPage - 1) * limit;
		
		// 1.
		List<Goods> list = adminService.selectGoodsList(offset, limit);
		log.debug("list = {}", list);
		model.addAttribute("list", list);
		
		// 2. totalContent
		int totalContent = goodsService.selectGoodsTotalCount();
		log.debug("totalContent = {}", totalContent);
		model.addAttribute("totalContent", totalContent);
		
		// 3. pagebar
		String url = request.getRequestURI(); 
		String pagebar = HiSpringUtils.getPagebar(cPage, limit, totalContent, url);
		log.debug("pagebar = {}", pagebar);
		model.addAttribute("pagebar", pagebar);
		
		return "admin/adminGoodsList";
	}
	
	/**
	 * [굿즈 추가]
	 */

	@RequestMapping(value="/adminGoodsInsert.do",method = {RequestMethod.GET, RequestMethod.POST})
	public String adminGoodsInsert(
			Goods goods,
			@RequestParam(value="upFile", required=false) MultipartFile[] upFiles, 
			RedirectAttributes redirectAttr
		) throws IllegalStateException, IOException {
		
		try {
			log.debug("goods = {}", goods);
			
			// 첨부파일 list생성
			List<Attachment> attachments = new ArrayList<>();
			
			// application객체(ServletContext)
			String saveDirectory = application.getRealPath("/resources/upload/goods");
			log.debug("saveDirectory = {}", saveDirectory);
			
			for(MultipartFile upFile : upFiles) {
	
				if(!upFile.isEmpty() && upFile.getSize() != 0) {
					
					log.debug("upFile = {}", upFile);
					log.debug("upFile.name = {}", upFile.getOriginalFilename());
					log.debug("upFile.size = {}", upFile.getSize());
					
					String originalFilename = upFile.getOriginalFilename();
	
					// 1. 서버컴퓨터에 저장
					File dest = new File(saveDirectory, originalFilename);
					log.debug("dest = {}", dest);
					upFile.transferTo(dest);
					
					// 2. DB에 attachment 레코드 등록
					Attachment attach = new Attachment();
					attach.setRenamedFilename(originalFilename);
					attach.setOriginalFilename(originalFilename);
					attachments.add(attach);
				}
			}
			
			// 업무로직
			if(!attachments.isEmpty())
				goods.setAttachments(attachments);
			
			int result = adminService.insertGoods(goods);
			
			String msg = result > 0 ? "상품 등록 성공" : "다시 시도해주세요.";
			
			redirectAttr.addFlashAttribute("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e); // 로깅
			
			throw e; // spring container에게 던짐.
		}
		return "redirect:/admin/adminGoodsList.do";
	}
	
	/**
	 * [굿즈 상세]
	 */
	
	@GetMapping("/adminGoodsDetail.do")
	public void adminGoodsUpdate(@RequestParam int pId, Model model) {
		log.debug("pId = {}", pId);
		
		Goods goods = adminService.selectOneGoods(pId);
		log.debug("goods = {}", goods);
		
		model.addAttribute("goods", goods);
	}
	
	/**
	 * [굿즈 수정]
	 */
	@RequestMapping(value="/adminGoodsUpdate.do",method = {RequestMethod.GET, RequestMethod.POST})
	public String adminGoodsUpdate(
			Goods goods,
			@RequestParam(value="upFile", required=false) MultipartFile[] upFiles, 
			RedirectAttributes redirectAttr,
			@RequestParam int pId
		) throws IllegalStateException, IOException {
		
		try {
			log.debug("pId = {}", pId);
			log.debug("goods = {}", goods);
			
			// 첨부파일 list생성
			List<Attachment> attachments = new ArrayList<>();
			
			// application객체(ServletContext)
			String saveDirectory = application.getRealPath("/resources/upload/goods");
			log.debug("saveDirectory = {}", saveDirectory);
			
			for(MultipartFile upFile : upFiles) {
	
				if(!upFile.isEmpty() && upFile.getSize() != 0) {
					
					log.debug("upFile = {}", upFile);
					log.debug("upFile.name = {}", upFile.getOriginalFilename());
					log.debug("upFile.size = {}", upFile.getSize());
					
					String originalFilename = upFile.getOriginalFilename();
	
					// 1. 서버컴퓨터에 저장
					File dest = new File(saveDirectory, originalFilename);
					log.debug("dest = {}", dest);
					upFile.transferTo(dest);
					
					// 2. DB에 attachment 레코드 등록
					Attachment attach = new Attachment();
					attach.setRenamedFilename(originalFilename);
					attach.setOriginalFilename(originalFilename);
					attachments.add(attach);
				}
			}
			
			// 업무로직
			if(!attachments.isEmpty())
				goods.setAttachments(attachments);
			
			int result = adminService.updateGoods(goods);
			
			String msg = result > 0 ? "상품 수정 성공" : "다시 시도해주세요.";
			
			redirectAttr.addFlashAttribute("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e); // 로깅
			
			throw e; // spring container에게 던짐.
		}
		return "redirect:/admin/adminGoodsList.do";
	}
	
	/**
	 * [굿즈 삭제]
	 */
	
	@PostMapping("/adminGoodsDelete.do")
	public String adminGoodsDelete(@RequestParam int pId, RedirectAttributes redirectAttr) {
		log.debug("pId = {}", pId);
		
    	try {
			int result = adminService.deleteGoods(pId);
			redirectAttr.addFlashAttribute("msg", "상품 삭제 성공");
			
    	} catch (InvalidParameterException e) {
    		log.error(e.getMessage(), e);
    		redirectAttr.addFlashAttribute("msg", e.getMessage());
    		
		} catch (Exception e) {
			log.error("다시 시도해주세요.", e);
			throw e;
		}
		
		
		return "redirect:/admin/adminGoodsDetail.do";
	}
	
///////////////////////////////////////////////////////////////////////////////
	
	/**
	 * [채팅]
	 */
	
	@GetMapping("/chat.do")
	public String chat(Model model) {
		
		List<ChatLog> list = chatService.findChatLog();
		log.debug("list = {}", list);
		model.addAttribute("list", list);
		
		return "admin/chat";
	}
	
	@GetMapping("/{chatId}/{memberId}/chat.do")
	public String chat(@PathVariable String chatId, @PathVariable String memberId, Model model) {
		
		List<ChatLog> list = chatService.findChatLogByChatId(chatId);
		log.debug("list = {}", list);
		log.debug("memberId = {}", memberId);
	
		model.addAttribute("list", list);
//		model.addAttribute("memberId", memberId);
//		model.addAttribute("chatId", chatId);
		
		return "admin/popup";
	}
	
}
