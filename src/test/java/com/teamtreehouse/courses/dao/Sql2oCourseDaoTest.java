package com.teamtreehouse.courses.dao;

import static org.junit.Assert.*;

import com.teamtreehouse.courses.model.Course;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;


public class Sql2oCourseDaoTest {
  private Sql2oCourseDao dao;
  private Connection conn;

  @Before
  public void setUp() throws Exception {
    String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "", "");
    dao = new Sql2oCourseDao(sql2o);
    // Keep connection open through entire test so that it is not wiped ot.
    conn = sql2o.open();
  }

  @After
  public void tearDown() throws Exception {
    conn.close();

  }


  @Test
  public void addingCourseSetsId() throws Exception {
    Course course = new Course("Test", "http://test.com");
    int originalCourseId = course.getId();

    dao.add(course);

    assertNotEquals(originalCourseId, course.getId());

  }
}