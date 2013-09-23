package com.egoonet.callcenter.test;

import java.io.File;

import junit.framework.TestCase;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class ExportDB 
{

	public static void main(String[] args)
	{
		
		Configuration cfg = new Configuration().configure();

		SchemaExport export = new SchemaExport(cfg);

		export.create(true, true);
	}
}
