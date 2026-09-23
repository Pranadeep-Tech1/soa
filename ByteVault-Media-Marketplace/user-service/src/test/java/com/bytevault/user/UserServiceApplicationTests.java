package com.bytevault.user;
import static org.junit.jupiter.api.Assertions.*; import org.junit.jupiter.api.Test;
class UserServiceApplicationTests { @Test void roleIsPresent(){ User u=new User(); u.setRole("USER"); assertEquals("USER",u.getRole()); } }
