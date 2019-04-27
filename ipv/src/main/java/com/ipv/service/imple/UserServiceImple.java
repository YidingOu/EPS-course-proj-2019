package com.ipv.service.imple;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.PostConstruct;

import com.ipv.service.JWTService;
import com.ipv.util.wrapper.JWTUserInfoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import com.ipv.entity.Post;
import com.ipv.entity.User;
import com.ipv.reporsitory.PostRepository;
import com.ipv.reporsitory.UserRepository;
import com.ipv.service.PostService;
import com.ipv.service.UserService;
import com.ipv.util.Constant;
import com.ipv.util.Util;


/**
 * Data persistence business logic layer
 * The interface-implementation architechture is required by the Spring framework (the multiple implementation is allowed)
 * The service implementations implement the actual methods and will be injected into required components(like other services or restAPI layer)
 * <p>
 * There are many common method between the services(like CRUD), so the common part is define in a BaseImple
 * This service implements get the common methods by extends the BaseService
 * The E is the Entity type of the service
 * <p>
 * The further methods will be implements here in later
 */
@Service
public class UserServiceImple extends BaseImple<User> implements UserService {

    //Spring Dependency injection
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private JWTService jwtService;

    //After the injection is done, override the repository in the super class
    @PostConstruct
    public void initParent() {
        repository = userRepository;
    }


    /*
     * Validate the passwords
     * It's dummy one without hashing, the actual function will be done later
     *
     * Also, after the first authentication, the token based authentication mechanism will be implement in later
     *
     * */
    @Override
    public User validate(String name, String pass) {
        User user = userRepository.findByName(name);
        if (checkPass(user, pass)) {
            String jwt = jwtService.createJWT(user);
            JWTUserInfoWrapper info = jwtService.validate(jwt);
            System.out.println(info.getNewJWT());
            Util.processUser(user, userRepository);
            user.setPost(postRepository.findByUserId(user.getId()));
            return user;
        } else {
            return null;
        }
    }

    //A complete function with hashing and checking will be updated later
    private boolean checkPass(User user, String pass) {

        String dbPass = user.getPass();

        String salt = user.getSalt();

        byte[] byteSalt = salt.getBytes();

        String hashedPass = saltPassword(pass, byteSalt);

        return dbPass.equals(hashedPass);
    }

    // Validate the input name and password of staff
    @Override
    public User validateStaff(String name, String pass) {
        User user = userRepository.findByName(name);
        if (user.getRole() < 1 || !checkPass(user, pass)) { // not staff or fail
            return null;
        } else {
            Util.processUser(user, userRepository);
            user.setPostForStaff(postRepository.findByStaffId(user.getId()));
            return user;
        }

    }

    // create normal user
    public User save(User user) {
        user.setRole(0);
        String salt = KeyGenerators.string().generateKey();
        user.setSalt(salt);
        String pwd = user.getPass();
        byte[] salts = salt.getBytes();
        String newPwd = saltPassword(pwd, salts);
        System.out.println("pwd: " + newPwd + " salt: " + salt);
        user.setPass(newPwd);
        user = repository.save(user);
        Post post = postService.initPost(user.getId());
        user.setPost(post);
        Util.processUser(user, userRepository);
        return user;
    }

    // Salt input password with input salt
    private String saltPassword(String pwd, byte[] salt) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(pwd.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    // LoadBalance
    @Override
    public int loadBalancerForGettingAStaffId() {
        List<User> staffList = userRepository.findByRole(1);
        User min = null;
        for (User staff : staffList) {
            staff.setPostForStaff(postRepository.findByStaffId(staff.getId()));
            if (min == null || staff.getPostForStaff().size() < min.getPostForStaff().size()) {
                min = staff;
            }
        }
        return min.getId();
    }

    // Return the user repo
    @Override
    public UserRepository getUserRepository() {
        return this.userRepository;
    }

    // Return the list of normal users
    @Override
    public List<User> getNormalUsers() {
        List<User> users = userRepository.findByRole(0);
        for (User user : users) {
            user.setPost(postRepository.findByUserId(user.getId()));
        }
        return users;
    }

    // Return the list of staffs
    @Override
    public List<User> getStaffs() {
        List<User> list = userRepository.findByRole(1);
        list.addAll(userRepository.findByRole(2));
        return list;
    }

    // Change the input user's password to input passwords
    @Override
    public User changePass(User user, String updatePass) {
        String salt = KeyGenerators.string().generateKey();
        user.setSalt(salt);
        byte[] salts = salt.getBytes();
        String newPwd = saltPassword(updatePass, salts);
//		System.out.println(newPwd);
        user.setPass(newPwd);
        user = repository.save(user);
        Util.processUser(user, userRepository);
        return user;
    }

    // Add the input staff
    @Override
    public User addStaff(User user) {
        user.setRole(1);
        String salt = KeyGenerators.string().generateKey();
        user.setSalt(salt);
        String pwd = user.getPass();
        byte[] salts = salt.getBytes();
        String newPwd = saltPassword(pwd, salts);
        user.setPass(newPwd);
        user = repository.save(user);
        Util.processUser(user, userRepository);
        return user;
    }

    // Delete the input user
    @Override
    public void delete(User user) {
        repository.deleteById(user.getId());

        //Re assign the post to other staffs if a staff is removed
        if (user.getRole() == Constant.STAFF) {
            List<Post> posts = postRepository.findByStaffId(user.getId());
            posts.stream().forEach(p -> {
                p.setStaffId(loadBalancerForGettingAStaffId());
                postRepository.save(p);
            });
        }
    }


}
