package com.kh.spring.notify.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.notify.model.dao.NotifyDao;
import com.kh.spring.notify.model.vo.SaveNotify;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotifyServiceImpl implements NotifyService {

	@Autowired
	private NotifyDao notifyDao;
	
	@Override
	public int insertSaveNotify(Map<String, Object> param) {
		return notifyDao.insertSaveNotify(param);
	}

	@Override
	public int selectCompareTime(String regTime) {
		return notifyDao.selectCompareTime(regTime);
	}

	@Override
	public List<SaveNotify> searchNewNotifyList(String id) {
		return notifyDao.searchNewNotifyList(id);
	}
	
}
