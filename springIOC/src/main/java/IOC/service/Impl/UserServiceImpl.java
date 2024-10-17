package IOC.service.Impl;

import IOC.annotation.Autowired;
import IOC.annotation.Bean;
import IOC.dao.IUserDao;
import IOC.dao.Impl.UserDaoImpl;
import IOC.service.IUserService;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎
 * @Date: 2024/10/17 9:53
 * @Version: 1.0
 */

@Bean
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public void login() {
        userDao.getAllUsers();
        System.out.println("This is implementation of login method.");
    }

    @Override
    public void register() {
        userDao.getAllUsers();
        System.out.println("This is implementation of register method.");
    }
}
