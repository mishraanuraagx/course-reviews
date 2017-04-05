package com.teamtreehouse.courses.dao;

import static org.junit.Assert.*;

import com.teamtreehouse.courses.exc.DaoException;
import com.teamtreehouse.courses.model.Course;
import com.teamtreehouse.courses.model.Review;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;


public class Sql2oReviewDaoTest {
  private Connection conn;
  public Sql2oCourseDao courseDao;
  private Course course;
  private Sql2oReviewDao reviewDao;

  @Before
  public void setUp() throws Exception {
    String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "", "");
    conn = sql2o.open();
    courseDao = new Sql2oCourseDao(sql2o);
    reviewDao = new Sql2oReviewDao(sql2o);
    course = new Course("Test","http://test.com");
    courseDao.add(course);

  }

  @After
  public void tearDown() throws Exception {
    conn.close();
  }

  @Test
  public void addingReviewSetsNewId() throws Exception {
    Review review = new Review(course.getId(),5,"Test comment");
    int originalID = review.getId();

    reviewDao.add(review);

    assertNotEquals(originalID, review.getId());

  }

  @Test
  public void multipleReviewAreFoundWhenTheyExistForACourse() throws Exception {
    reviewDao.add(new Review(course.getId(), 5, "Test Comment 1"));
    reviewDao.add(new Review(course.getId(), 1, "Test Comment 2"));

    List<Review> reviews = reviewDao.findByCourseId(course.getId());

    assertEquals(2, reviews.size());


  }

  @Test(expected = DaoException.class)
  public void addingAReviewToANonExistingCourseFails() throws Exception {
    Review review = new Review(42, 5, "Test Comment");

    reviewDao.add(review);
  }
}