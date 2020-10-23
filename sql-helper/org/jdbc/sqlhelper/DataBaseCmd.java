package org.jdbc.sqlhelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * ���ݿ������
 * �����������캯�� <br/>
 * <b><font color="red">DataBaseCmd()</font></b> ʹ���Զ������ӳػ������<br/>
 * <b><font color="red">DataBaseCmd(String datasource)</font></b> ָ�� JNDI ������Դ����
 *
 * @author tanyongde
 */
public class DataBaseCmd
{

	private PreparedStatement pstmt = null;// �������
	private Connection con = null;// ��ȡ���Ӷ���
	private ResultSet rs = null;
	private String datasource = null; //ָ��ʹ�õ�����Դ

	/**
	 * Ĭ�Ϲ�����
	 */
	public DataBaseCmd()
	{

	}

	/**
	 * ������
	 *
	 * @param datasource
	 *            ָ��ʹ�õ�JNDI���ҵ�����Դ����
	 *            Tomcat ������Դ��Ϊ java:comp/env/test
	 */
	public DataBaseCmd(String datasource)
	{
		this.datasource = datasource;
	}

	/**
	 * ��ʼ�����ݿ����Ӷ���
	 */
	private synchronized void initCon()
	{
		try
		{
			if (null == con)
			{
				if (null == datasource || "".equals(datasource))
				{
					con = ConManager.getConnection();
				}
				else
				{
					con = ConManager.getConnection(datasource);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * ��ѯ�������,�˷���һ�㽫���������е����ݲ�ѯ�����������Ҫƥ��������ѯ���������ҵ���߼�����߽���������ݽ��������ڲ�ѯ�����ֱ�Ӵ�����
	 *
	 * @param sql
	 *            Ҫִ�е�sql�����ߴ洢���̵�����
	 * @param cmdtype
	 *            ָ��sql���������ͣ�trueΪ�洢���̣�falseΪsql���
	 * @param values
	 *            ָ��sql����еĲ����б�
	 * @return ���ظ��º�Ľ����
	 * @throws Exception
	 */
	public ResultSet excuteQuery(String sql, boolean cmdtype, List values) throws Exception
	{
		try
		{
			initCon();
			//������̴���
			if (cmdtype)
			{
				pstmt = con.prepareCall(sql); //����洢���̵���伯
			}
			else
			{
				pstmt = con.prepareStatement(sql);
			}
			if (values != null && values.size() > 0)
			{
				setValues(pstmt, values);
			}
			rs = pstmt.executeQuery();
			return rs;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * ʵ�ֶ����ݵĵ�����ɾ����
	 *
	 * @param sql
	 *            Ҫִ�е�sql�����ߴ洢���̵�����
	 * @param cmdtype
	 *            ָ��sql���������ͣ�trueΪ�洢���̣�falseΪsql���
	 * @param values
	 *            �洢���̻���sql�����Ҫָ���Ĳ����б�����Ϊnull
	 * @return trueִ�гɹ���falseִ��ʧ��
	 * @throws Exception
	 */
	public int excuteUpdate(String sql, boolean cmdtype, List values) throws Exception
	{
		int noOfRows = 0;
		try
		{
			initCon();
			if (cmdtype)
			{
				pstmt = con.prepareCall(sql); //����洢���̵���伯
			}
			else
			{
				pstmt = con.prepareStatement(sql);
			}
			if (values != null && values.size() > 0)
			{
				setValues(pstmt, values);
			}
			noOfRows = pstmt.executeUpdate();
		}
		catch (Exception ex)
		{
			throw ex;
		}
		return noOfRows;
	}

	/**
	 * �ر�����
	 */
	public void closeConnection()
	{
		try
		{
			if (null != con && !con.isClosed())
			{

				ConManager.closeCon(con);
				con = null;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * �ر���伯
	 */
	public void closePstmt()
	{
		if (null != pstmt)
		{
			try
			{
				pstmt.close();
				pstmt = null;
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}

		}
	}

	/**
	 * �رս����
	 */
	public void closeResultSet()
	{
		if (null != rs)
		{
			try
			{
				rs.close();
				rs = null;
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}

		}
	}

	/**
	 * �ر��������ݿ���ʶ���
	 */
	public void closeAll()
	{
		closePstmt();
		closeResultSet();
		closeConnection();
	}

	/**
	 * �趨��� �ò���
	 *
	 * @param pstmt
	 *            ��伯 ����
	 * @param values
	 *            ָ�� sql ����еĲ����б�
	 * @throws SQLException
	 */
	private void setValues(PreparedStatement pstmt, List values) throws SQLException
	{
		for (int i = 0; i < values.size(); i++)
		{
			Object v = values.get(i);
			pstmt.setObject(i + 1, v);
		}
	}

	/**
	 * (������ʹ�õķ���)ʹ�� JNDI �ķ�ʽ��ȡ����ԴʱӦʹ�ô˷���
	 * �����ڹ�������ֱ�Ӵ��ݲ���
	 *
	 * @param datasource
	 *            ���ݿ�����ʹ�õ�����Դ����
	 * @deprecated
	 */
	public void setDatasource(String datasource)
	{
		this.datasource = datasource;
	}
}
