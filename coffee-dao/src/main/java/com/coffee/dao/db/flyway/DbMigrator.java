package com.coffee.dao.db.flyway;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.flywaydb.core.Flyway;

import com.coffee.dao.db.ConnectionPool;

public class DbMigrator extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		// Create the Flyway instance
		Flyway flyway = new Flyway();

		flyway.setBaselineOnMigrate(true);
		flyway.setValidateOnMigrate(false);
		// Point it to the database

		flyway.setDataSource(ConnectionPool.getInstance().getDataSource());

		// Start the migration
		try {
			flyway.migrate();
		} catch (Exception e) {
			flyway.repair();
			flyway.migrate();
		}
	}
}
