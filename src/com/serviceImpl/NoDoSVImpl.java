package com.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bean.childBean.StuDoWorkInfo;
import com.dao.NoDoDao;
import com.service.NoDoSV;
import com.utils.base.QueryBaseDao;

@Service("noDoSV")
@Transactional
public class NoDoSVImpl extends  com.utils.base.QueryBaseServiceSVImpl<StuDoWorkInfo> implements NoDoSV {

	@Autowired
	private NoDoDao noDoDao;
	
	@Override
	public QueryBaseDao<StuDoWorkInfo> getDao() {
		// TODO Auto-generated method stub
		return noDoDao;
	}
	
}
