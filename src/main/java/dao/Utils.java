package dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import jdbc.DataSource;

public class Utils {
	
	protected Connection getConnection() throws IOException, SQLException, PropertyVetoException {
		Connection conn = DataSource.getInstance().getConnection();
		return conn;
	}

}