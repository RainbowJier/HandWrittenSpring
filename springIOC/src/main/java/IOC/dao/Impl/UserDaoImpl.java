package IOC.dao.Impl;

import IOC.annotation.Bean;
import IOC.dao.IUserDao;
import IOC.entity.User;

import java.util.Collections;
import java.util.List;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎
 * @Date: 2024/10/17 9:58
 * @Version: 1.0
 */

@Bean
public class UserDaoImpl implements IUserDao {
    @Override
    public int addUser(User user) {
        System.out.println("Dao: addUser()");
        return 0;
    }

    @Override
    public User getUserById(int id) {
        System.out.println("Dao: getUserById()");
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        System.out.println("Dao: getAllUsers()");
        return Collections.emptyList();
    }

    @Override
    public List<User> getUsersByName(String name) {
        System.out.println("Dao: getUsersByName()");
        return Collections.emptyList();
    }
}
