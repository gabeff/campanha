package jdbc;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSource {
	
	private static DataSource datasource;
	private ComboPooledDataSource cpds;

	private DataSource() throws IOException, SQLException, PropertyVetoException {
		cpds = new ComboPooledDataSource();
		cpds.setDriverClass("org.postgresql.Driver");
		cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/racnet");
		cpds.setUser("postgres");
		cpds.setPassword("password");

		cpds.setMinPoolSize(1);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		cpds.setMaxStatements(100);
	}

	public static DataSource getInstance() throws IOException, SQLException, PropertyVetoException {
		if (datasource == null) {
			datasource = new DataSource();
			return datasource;
		} else {
			return datasource;
		}
	}

	public Connection getConnection() throws SQLException {
		return this.cpds.getConnection();
	}
	
	public void closeConnection() {
		this.cpds.close();
	}

} 