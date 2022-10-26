package com.likelion.dao;

import com.likelion.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() throws SQLException {
        userDao = context.getBean("awsUserDao", UserDao.class);
        user1 = new User("id1", "name1", "pw1");
        user2 = new User("id2", "name2", "pw2");
        user3 = new User("id3", "name3", "pw3");
        userDao.deleteAll();
    }

    @Test
    void addAndFindById() throws SQLException {
        userDao.add(user1);

        User user = userDao.findById("id1");
        assertEquals(user.getName(), "name1");
    }

    //@Test
    void getCount() throws SQLException {
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        userDao.add(user2);
        assertEquals(2, userDao.getCount());
        userDao.add(user3);
        assertEquals(3, userDao.getCount());
    }

    //@Test
    void noSuchId() throws SQLException, ClassNotFoundException {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.findById("NoId");
        });
    }

    @Test
    void getAllUsersTest() throws SQLException {

        List<User> users = userDao.getUsersAll();
        assertEquals(0, users.size());

        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        users = userDao.getUsersAll();
        assertEquals(3, users.size());
    }
}