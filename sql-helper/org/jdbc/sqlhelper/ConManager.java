package org.jdbc.sqlhelper;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * ���ݿ����ӹ�����
 *
 * @author tanyongde
 */
public class ConManager {

    private static Pool dbPool;

    private static ConManager instance = null;// ��������


    /**
     * ˽�й��췽������ֹ�ⲿ�������������Ҫ��ñ������ͨ��<code>getIstance</code>���� ʹ�������ģʽ�еĵ���ģʽ
     */
    private ConManager() {
    }

    /**
     * �ͷ����ݿ����Ӷ���
     */
    protected static void closeCon(Connection con) {
        dbPool.freeConnection(con);
    }

    /**
     * ���ص�ǰ���ӹ������һ������
     */
    private static ConManager getInstance() {
        if (null == instance) {
            instance = new ConManager();
        }
        return instance;
    }

    /**
     * ���Զ������ݿ����ӳ��л��һ�����Ӷ���
     *
     * @return
     */
    protected static Connection getConnection() {
        Connection conn = null;
        try {
            if (dbPool == null) {
                dbPool = DBConnectionPool.getInstance();
            }
            conn = dbPool.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * ʹ�� JNDI �� ���������ݿ����ӳ��� ���һ�����Ӷ���
     *
     * @param accessPool �Ƿ�ͨ���������ӳػ������
     *                   Tomcat �������ַ��� "java:comp/env/...(����Դ��)"
     * @return
     */
    protected static Connection getConnection(String lookupStr) {
        Connection conn = null;
        try {
            ConManager.getInstance();
            //ʹ�� JNDI ��Tomcat ���������ݿ����ӳ��� ���һ�����Ӷ���
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(lookupStr);
            conn = ds.getConnection();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return conn;
    }

}
