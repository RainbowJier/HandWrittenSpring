package IOC.dao;

import IOC.entity.User;

import java.util.List;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎
 * @Date: 2024/10/17 9:55
 * @Version: 1.0
 */

public interface IUserDao {
    /**
     * Add user
     */
    int addUser(User user);

    /**
     * Get user by id.
     */
    User getUserById(int id);

    /**
     * Get all users.
     */
    List<User> getAllUsers();

    /**
     * Get users by name.
     */
    List<User> getUsersByName(String name);
}
