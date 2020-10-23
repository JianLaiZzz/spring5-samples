package com.gupaoedu.vip.pattern.template.jdbc;

import java.util.List;

import com.gupaoedu.vip.pattern.template.jdbc.dao.MemberDao;

/**
 * Created by Tom.
 */
public class MemberDaoTest
{

	public static void main(String[] args)
	{
		MemberDao memberDao = new MemberDao(null);
		List<?> result = memberDao.selectAll();
		System.out.println(result);
	}
}
