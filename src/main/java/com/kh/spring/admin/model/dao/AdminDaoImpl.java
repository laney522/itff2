package com.kh.spring.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.admin.model.vo.GoodsOrderDetailJoin;
import com.kh.spring.admin.model.vo.GoodsPaymentJoin;
import com.kh.spring.admin.model.vo.PointHistory;
import com.kh.spring.event.model.vo.RouletteEvent;
import com.kh.spring.goods.model.vo.Goods;
import com.kh.spring.goods.model.vo.GoodsJoin;
import com.kh.spring.goods.model.vo.GoodsOption;
import com.kh.spring.goods.model.vo.GoodsOrder;
import com.kh.spring.goods.model.vo.OptionDetail;
import com.kh.spring.goods.model.vo.OrderDetail;
import com.kh.spring.goods.model.vo.Payment;
import com.kh.spring.member.model.vo.Member;
import com.kh.spring.movie.model.vo.Movie;
import com.kh.spring.movie.model.vo.MovieJoin;
import com.kh.spring.movie.model.vo.MovieReservation;
import com.kh.spring.movie.model.vo.MovieSchedule;
import com.kh.spring.movie.model.vo.Seat;
import com.kh.spring.movie.model.vo.Theater;
import com.kh.spring.notice.model.vo.Notice;
import com.kh.spring.question.model.vo.Question;
import com.kh.spring.question.model.vo.QuestionComment;
import com.kh.spring.review.model.vo.Review;
import com.kh.spring.sharing.model.vo.Attachment;
import com.kh.spring.sharing.model.vo.Board;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class AdminDaoImpl implements AdminDao {
	
	@Autowired
	private SqlSession session;

	@Override
	public List<Member> selectMemberList(int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit); 
		return session.selectList("admin.selectMemberList", null, rowBounds);
	}
	
	@Override
	public Member selectOneMember(String id) {
		return session.selectOne("admin.selectOneMember", id);
	}
	
	@Override
	public int selectMemberTotalCount() {
		return session.selectOne("admin.selectMemberTotalCount");
	}
	
	@Override
	public List<PointHistory> selectMemberPointHistoryList(String id) {
		return session.selectList("admin.selectMemberPointHistoryList", id);
	}
	
	@Override
	public int updateMember(Member member) {
		return session.update("admin.updateMember", member);
	}

	@Override
	public List<Member> searchMember(Map<String, Object> param) {
			int offset = (int) param.get("start");
			int limit = (int) param.get("end");
			log.debug("offset, limit = {}", offset, limit);
			log.debug("param = {}", param);
			RowBounds rowBounds = new RowBounds(offset, limit); 
			
		return session.selectList("admin.searchMember", param, rowBounds);
	}

	@Override
	public int searchMemberCount(Map<String, Object> param) {
		return session.selectOne("admin.searchMemberCount", param);
	}
	
	/**
	 * 영화
	 */
	
	@Override
	public List<Movie> selectMovieList() {
		return session.selectList("admin.searchMovieList");
	}

	/**
	 * [굿즈]
	 */

	@Override
	public List<Goods> selectGoodsList(int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit); 
		return session.selectList("admin.selectGoodsList", null, rowBounds);
	}

	@Override
	public int insertGoods(Goods goods) {
		return session.insert("admin.insertGoods", goods);
	}

	@Override
	public int insertAttachment(Attachment attach) {
		return session.insert("admin.insertAttachment", attach);
	}

	@Override
	public int deleteGoods(int pId) {
		return session.delete("admin.deleteGoods", pId);
	}

	@Override
	public Goods selectOneGoods(int pId) {
		return session.selectOne("admin.selectOneGoods", pId);
	}

	@Override
	public int updateGoods(Goods goods) {
		return session.update("admin.updateGoods", goods);
	}

	@Override
	public List<Goods> searchGoods(Map<String, Object> param) {
		int offset = (int) param.get("start");
		int limit = (int) param.get("end");
		log.debug("offset, limit = {}", offset, limit);
		log.debug("param = {}", param);
		RowBounds rowBounds = new RowBounds(offset, limit); 
		
	return session.selectList("admin.searchGoods", param, rowBounds);
	}

	@Override
	public int searchGoodsCount(Map<String, Object> param) {
		return session.selectOne("admin.searchGoodsCount", param);
	}

	@Override
	public int insertPointHistory(Map<String, Object> param) {
		return session.insert("admin.insertPointHistory", param);
	}

	@Override
	public int updateMemberPoint(Map<String, Object> param) {
		return session.update("admin.updateMemberPoint", param);
	}

	@Override
	public Movie selectOneMovie(String movieId) {
		return session.selectOne("admin.selectOneMovie", movieId);
	}

	@Override
	public List<Theater> selectTheaterList() {
		return session.selectList("admin.selectTheaterList");
	}

	@Override
	public List<MovieJoin> selectOneTheater(int theaterId) {
		return session.selectList("admin.selectOneTheater", theaterId);
	}

	@Override
	public List<MovieJoin> adminOneMovieSchedule(String movieId) {
		return session.selectList("admin.adminOneMovieSchedule", movieId);
	}

	@Override
	public List<MovieJoin> adminOneMovieScheduleDate(Map<String, Object> param) {
		return session.selectList("admin.adminOneMovieScheduleDate", param);
	}

	@Override
	public List<Notice> adminSelectNoticeList(int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		return session.selectList("admin.adminSelectNoticeList", null, rowBounds);
	}

	@Override
	public int countTotalNoticeContent() {
		return session.selectOne("admin.countTotalNoticeContent");
	}

	@Override
	public int insertNotice(Notice notice) {
		return session.insert("admin.insertNotice", notice);
	}

	@Override
	public Notice selectOneNoticeCollection(int no) {
		return session.selectOne("admin.selectOneNoticeCollection", no);
	}

	@Override
	public List<Member> selectNoticeOneloginMember(int no) {
		return session.selectList("admin.selectNoticeOneloginMember", no);
	}

	@Override
	public Attachment selectOneAttachment(int no) {
		return session.selectOne("admin.selectOneAttachment", no);
	}

	@Override
	public List<GoodsJoin> selectOneGoodsDetail(int pId) {
		return session.selectList("admin.selectOneGoodsDetail", pId);
	}

	@Override
	public OptionDetail selectOneGoodsOption(int optionId) {
		return session.selectOne("admin.selectOneGoodsOption", optionId);
	}

	@Override
	public int updateGoodsOption(OptionDetail optionDetail) {
		return session.update("admin.updateGoodsOption", optionDetail);
	}

	@Override
	public int insertGoodsOption(Map<String, Object> param) {
		return session.insert("admin.insertGoodsOption", param);
	}

	@Override
	public int insertOptionDetail(Map<String, Object> param) {
		return session.insert("admin.insertOptionDetail", param);
	}

	@Override
	public int selectOptionId() {
		return session.selectOne("admin.selectOptionId");
	}

	@Override
	public int deleteGoodsOption(int optionId) {
		return session.delete("admin.deleteGoodsOption", optionId);
	}

	@Override
	public int deleteNoticeAttachment(int attachNo) {
		return session.delete("admin.deleteNoticeAttachment", attachNo);
	}

	@Override
	public int updateNotice(Notice notice) {
		return session.update("admin.updateNotice", notice);
	}

	@Override
	public List<Attachment> selectAttachmentByNoticeNo(int noticeNo) {
		return session.selectList("admin.selectAttachmentByNoticeNo", noticeNo);
	}

	@Override
	public int deleteNotice(int noticeNo) {
		return session.delete("admin.deleteNotice", noticeNo);
	}

	@Override
	public int adminManageRegisterAweekAgoCount() {
		return session.selectOne("admin.adminManageRegisterAweekAgoCount");
	}

	@Override
	public int adminManageTodayScreeningCount() {
		return session.selectOne("admin.adminManageTodayScreeningCount");
	}

	@Override
	public List<Review> adminManageRecentTenReviewList() {
		return session.selectList("admin.adminManageRecentTenReviewList");
	}

	@Override
	public List<Question> adminSelectQuestionList(int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		return session.selectList("admin.adminSelectQuestionList", null, rowBounds);
	}

	@Override
	public int countTotalQuestionContent() {
		return session.selectOne("admin.countTotalQuestionContent");
	}

	@Override
	public Question selectQuestionCollection(int questionNo) {
		return session.selectOne("admin.selectQuestionCollection", questionNo);
	}

	@Override
	public QuestionComment selectQuestionComment(int questionNo) {
		return session.selectOne("admin.selectQuestionComment", questionNo);
	}

	@Override
	public int insertQuestionComment(Map<String, Object> param) {
		return session.insert("admin.insertQuestionComment", param);
	}

	@Override
	public int updateQuestionAnswer(int questionNo) {
		return session.update("admin.updateQuestionAnswer", questionNo);
	}

	@Override
	public int deleteQuestionComment(int commentNo) {
		return session.delete("admin.deleteQuestionComment", commentNo);
	}

	@Override
	public int updateQuestionAnswerToN(int questionNo) {
		return session.update("admin.updateQuestionAnswerToN", questionNo);
	}

	@Override
	public int adminManageTodayReviewCount() {
		return session.selectOne("admin.adminManageTodayReviewCount");
	}

	@Override
	public int adminManageTodayQuestionCount() {
		return session.selectOne("admin.adminManageTodayQuestionCount");
	}

	@Override
	public List<Notice> searchNotice(Map<String, Object> param) {
		int offset = (int) param.get("start");
		int limit = (int) param.get("end");
		log.debug("offset, limit = {}", offset, limit);
		log.debug("param = {}", param);
		RowBounds rowBounds = new RowBounds(offset, limit); 
		
	return session.selectList("admin.searchNotice", param, rowBounds);
	}

	@Override
	public int searchNoticeCount(Map<String, Object> param) {
		return session.selectOne("admin.searchNoticeCount", param);
	}

	@Override
	public List<Question> searchQuestion(Map<String, Object> param) {
		int offset = (int) param.get("start");
		int limit = (int) param.get("end");
		log.debug("offset, limit = {}", offset, limit);
		log.debug("param = {}", param);
		RowBounds rowBounds = new RowBounds(offset, limit); 
		
	return session.selectList("admin.searchQuestion", param, rowBounds);
	}

	@Override
	public int searchQuestionCount(Map<String, Object> param) {
		return session.selectOne("admin.searchQuestionCount", param);
	}

	@Override
	public int insertRouletteEvent(Map<String, Object> param) {
		return session.insert("admin.insertRouletteEvent", param);
	}

	@Override
	public RouletteEvent selectRouletteEvent(String id) {
		return session.selectOne("admin.selectRouletteEvent", id);
	}

	@Override
	public int selectRouletteEventParticipateCnt(String id) {
		return session.selectOne("admin.selectRouletteEventParticipateCnt", id);
	}

	@Override
	public int updateRouletteEvent(Map<String, Object> param) {
		return session.update("admin.updateRouletteEvent", param);
	}

	@Override
	public List<Question> adminSelectNewQuestion(int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		return session.selectList("admin.adminSelectNewQuestion", null, rowBounds);
	}

	@Override
	public int countTotalNewQuestionContent() {
		return session.selectOne("admin.countTotalNewQuestionContent");
	}

	@Override
	public List<Question> searchNewQuestion(Map<String, Object> param) {
		int offset = (int) param.get("start");
		int limit = (int) param.get("end");
		log.debug("offset, limit = {}", offset, limit);
		log.debug("param = {}", param);
		RowBounds rowBounds = new RowBounds(offset, limit); 
		
	return session.selectList("admin.searchNewQuestion", param, rowBounds);
	}

	@Override
	public int searchNewQuestionCount(Map<String, Object> param) {
		return session.selectOne("admin.searchNewQuestionCount", param);
	}

	@Override
	public List<Question> adminManageRecentTenQuestionList() {
		return session.selectList("admin.adminManageRecentTenQuestionList");
	}

	@Override
	public int insertGoodsAttachment(Attachment attach) {
		return session.insert("admin.insertGoodsAttachment", attach);
	}

	@Override
	public List<Review> adminSelectReviewList(int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		return session.selectList("admin.adminSelectReviewList", null, rowBounds);
	}

	@Override
	public int countTotalReviewContent() {
		return session.selectOne("admin.countTotalReviewContent");
	}

	@Override
	public Review selectOneReviewCollection(int reviewNo) {
		return session.selectOne("admin.selectOneReviewCollection", reviewNo);
	}

	@Override
	public List<Member> selectReviewOneloginMember(int reviewNo) {
		return session.selectList("admin.selectReviewOneloginMember", reviewNo);
	}

	@Override
	public List<Attachment> selectAttachmentByReviewNo(int reviewNo) {
		return session.selectList("admin.selectAttachmentByReviewNo", reviewNo);
	}

	@Override
	public int deleteReview(int reviewNo) {
		return session.delete("admin.deleteReview", reviewNo);
	}

	@Override
	public int deleteReviewComment(int reviewNo) {
		return session.delete("admin.deleteReviewComment", reviewNo);
	}

	@Override
	public int deleteReviewLike(int reviewNo) {
		return session.delete("admin.deleteReviewLike", reviewNo);
	}

	@Override
	public List<Board> adminSelectSharingList(int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		return session.selectList("admin.adminSelectSharingList", null, rowBounds);
	}

	@Override
	public int countTotalSharingContent() {
		return session.selectOne("admin.countTotalSharingContent");
	}

	@Override
	public Board selectOneSharingCollection(int no) {
		return session.selectOne("admin.selectOneSharingCollection", no);
	}

	@Override
	public List<Member> selectSharingOneloginMember(int no) {
		return session.selectList("admin.selectSharingOneloginMember", no);
	}

	@Override
	public List<Attachment> selectAttachmentBySharingNo(int no) {
		return session.selectList("admin.selectAttachmentBySharingNo", no);
	}

	@Override
	public int deleteSharing(int no) {
		return session.delete("admin.deleteSharing", no);
	}

	@Override
	public int adminMemberCut(String id) {
		return session.update("admin.adminMemberCut", id);
	}

	@Override
	public int adminMemberUnblock(String id) {
		return session.update("admin.adminMemberUnblock", id);
	}

	@Override
	public List<Review> searchReview(Map<String, Object> param) {
		int offset = (int) param.get("start");
		int limit = (int) param.get("end");
		log.debug("offset, limit = {}", offset, limit);
		log.debug("param = {}", param);
		RowBounds rowBounds = new RowBounds(offset, limit); 
		
		return session.selectList("admin.searchReview", param, rowBounds);
	}

	@Override
	public int searchReviewCount(Map<String, Object> param) {
		return session.selectOne("admin.searchReviewCount", param);
	}

	@Override
	public List<Board> searchSharing(Map<String, Object> param) {
		int offset = (int) param.get("start");
		int limit = (int) param.get("end");
		log.debug("offset, limit = {}", offset, limit);
		log.debug("param = {}", param);
		RowBounds rowBounds = new RowBounds(offset, limit); 
		
		return session.selectList("admin.searchSharing", param, rowBounds);
	}

	@Override
	public int searchSharingCount(Map<String, Object> param) {
		return session.selectOne("admin.searchSharingCount", param);
	}

	@Override
	public List<Member> adminManageRecentTenRegisterList() {
		return session.selectList("admin.adminManageRecentTenRegisterList");
	}

	@Override
	public int deleteGoodsLike(int pId) {
		return session.delete("admin.deleteGoodsLike", pId);
	}

	@Override
	public List<GoodsOption> selectOneGoodsOptionId(int pId) {
		return session.selectList("admin.selectOneGoodsOptionId", pId);
	}

	@Override
	public List<GoodsPaymentJoin> selectGoodsOrderList(int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit); 
		return session.selectList("admin.selectGoodsOrderList", null, rowBounds);
	}

	@Override
	public int selectGoodsOrderTotalCount() {
		return session.selectOne("admin.selectGoodsOrderTotalCount");
	}

	@Override
	public List<GoodsOrderDetailJoin> selectOneGoodsOrderDetail(String orderNo) {
		return session.selectList("admin.selectOneGoodsOrderDetail", orderNo);
	}

	@Override
	public GoodsPaymentJoin selectOnePayment(Map<String, Object> param) {
		return session.selectOne("admin.selectOnePayment", param);
	}
	
	@Override
	public String selectOneGoodsOrderMember(String orderNo) {
		return session.selectOne("admin.selectOneGoodsOrderMember", orderNo);
	}

	@Override
	public List<GoodsPaymentJoin> selectRecentTenGoodsList() {
		return session.selectList("admin.selectRecentTenGoodsList");
	}

	@Override
	public int adminManageTodayOrderCount() {
		return session.selectOne("admin.adminManageTodayOrderCount");
	}

	@Override
	public int adminManageNoAnswerCount() {
		return session.selectOne("admin.adminManageNoAnswerCount");
	}

	@Override
	public List<GoodsPaymentJoin> searchGoodsOrder(Map<String, Object> param) {
		int offset = (int) param.get("start");
		int limit = (int) param.get("end");
		log.debug("offset, limit = {}", offset, limit);
		log.debug("param = {}", param);
		RowBounds rowBounds = new RowBounds(offset, limit); 
		
	return session.selectList("admin.searchGoodsOrder", param, rowBounds);
	}

	@Override
	public int searchGoodsOrderCount(Map<String, Object> param) {
		return session.selectOne("admin.searchGoodsOrderCount", param);
	}

	@Override
	public List<GoodsPaymentJoin> searchGoodsOrderDate(Map<String, Object> param) {
		int offset = (int) param.get("start");
		int limit = (int) param.get("end");
		log.debug("offset, limit = {}", offset, limit);
		log.debug("param = {}", param);
		RowBounds rowBounds = new RowBounds(offset, limit); 
		
	return session.selectList("admin.searchGoodsOrderDate", param, rowBounds);
	}

	@Override
	public int searchGoodsOrderDateCount(Map<String, Object> param) {
		return session.selectOne("admin.searchGoodsOrderDateCount", param);
	}

	@Override
	public List<MovieReservation> selectMovieReservationList(int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit); 
		return session.selectList("admin.selectMovieReservationList", null, rowBounds);
	}

	@Override
	public int selectMovieReservationTotalCount() {
		return session.selectOne("admin.selectMovieReservationTotalCount");
	}

	@Override
	public MovieReservation selectOneMovieReservation(String movieReservationId) {
		return session.selectOne("admin.selectOneMovieReservation", movieReservationId);
	}

	@Override
	public List<MovieJoin> selectOneMovieReservationSeat(String movieReservationId) {
		return session.selectList("admin.selectOneMovieReservationSeat", movieReservationId);
	}

	@Override
	public List<MovieReservation> selectTodayMovieReservationList() {
		return session.selectList("admin.selectTodayMovieReservationList");
	}

	@Override
	public int adminManageTodayMovieReservationCount() {
		return session.selectOne("admin.adminManageTodayMovieReservationCount");
	}

	@Override
	public List<MovieReservation> searchMovieReservation(Map<String, Object> param) {
		int offset = (int) param.get("start");
		int limit = (int) param.get("end");
		log.debug("offset, limit = {}", offset, limit);
		log.debug("param = {}", param);
		RowBounds rowBounds = new RowBounds(offset, limit); 
		
	return session.selectList("admin.searchMovieReservation", param, rowBounds);
	}

	@Override
	public int searchMovieReservationCount(Map<String, Object> param) {
		return session.selectOne("admin.searchMovieReservationCount", param);
	}

	@Override
	public List<MovieReservation> searchMovieReservationDate(Map<String, Object> param) {
		int offset = (int) param.get("start");
		int limit = (int) param.get("end");
		log.debug("offset, limit = {}", offset, limit);
		log.debug("param = {}", param);
		RowBounds rowBounds = new RowBounds(offset, limit); 
		
	return session.selectList("admin.searchMovieReservationDate", param, rowBounds);
	}

	@Override
	public int searchMovieReservationDateCount(Map<String, Object> param) {
		return session.selectOne("admin.searchMovieReservationDateCount", param);
	}

	@Override
	public int deleteMovieReservation(String movieReservationId) {
		return session.delete("admin.deleteMovieReservation", movieReservationId);
	}

	@Override
	public Payment selectOnePayment2(String orderNo) {
		return session.selectOne("admin.selectOnePayment2", orderNo);
	}

	@Override
	public int updateGoodsOrderDetailStatus(Map<String, Object> param) {
		return session.update("admin.updateGoodsOrderDetailStatus", param);
	}

	@Override
	public Payment selectOnePayment3(int paymentNo) {
		return session.selectOne("admin.selectOnePayment3", paymentNo);
	}

	@Override
	public int adminPaymentInfoUpdate(Map<String, Object> param) {
		return session.update("admin.adminPaymentInfoUpdate", param);
	}

	@Override
	public int selectGoodsOrderCancelTotalCount() {
		return session.selectOne("admin.selectGoodsOrderCancelTotalCount");
	}

	@Override
	public List<GoodsPaymentJoin> selectGoodsOrderCancelList(int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit); 
		return session.selectList("admin.selectGoodsOrderCancelList", null, rowBounds);
	}

	@Override
	public int adminGoodsOrderStatusUpdate(Map<String, Object> param) {
		return session.update("admin.adminGoodsOrderStatusUpdate", param);
	}

	@Override
	public List<MovieJoin> selectMovieReservationStatusList(int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit); 
		return session.selectList("admin.selectMovieReservationStatusList", null, rowBounds);
	}

	@Override
	public int selectMovieReservationStatusTotalCount() {
		return session.selectOne("admin.selectMovieReservationStatusTotalCount");
	}

	@Override
	public List<Seat> selectOneSeat(String movieScheduleId) {
		return session.selectList("admin.selectOneSeat", movieScheduleId);
	}

	@Override
	public List<MovieSchedule> selectMovieScheduleDate() {
		return session.selectList("admin.selectMovieScheduleDate");
	}

	@Override
	public List<MovieReservation> adminMovieReserStatusSearchDate(Map<String, Object> param) {
		int offset = (int) param.get("start");
		int limit = (int) param.get("end");
		log.debug("offset, limit = {}", offset, limit);
		log.debug("param = {}", param);
		RowBounds rowBounds = new RowBounds(offset, limit); 
		
	return session.selectList("admin.adminMovieReserStatusSearchDate", param, rowBounds);
	}

	@Override
	public int adminMovieReserStatusSearchDateCount(Map<String, Object> param) {
		return session.selectOne("admin.adminMovieReserStatusSearchDateCount", param);
	}

	@Override
	public int adminDeliveryUpdate(int orderDetailNo) {
		return session.update("admin.adminDeliveryUpdate", orderDetailNo);
	}

	@Override
	public int updateGoodsCancelOrderDetailStatus(Map<String, Object> param) {
		return session.update("admin.updateGoodsCancelOrderDetailStatus", param);
	}

	@Override
	public int updateNewTotalPrice(Map<String, Object> param) {
		return session.update("admin.updateNewTotalPrice", param);
	}

}