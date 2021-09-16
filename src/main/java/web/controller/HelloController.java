package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import web.dao.UserRepository;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.ServiceDetail;
import web.service.UserService;
import web.service.UserServiceImpl;

import javax.swing.*;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class HelloController {

	private UserService userService;
	private RoleService roleService;

//	@Autowired
//    private ServiceDetail serviceDetail;

//	@Autowired
//	private UserRepository userRepository;

	@Autowired
	public HelloController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}


	@RequestMapping(value = "admin", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		System.out.println("hello");
		List<String> messages = new ArrayList<>();
		messages.add("Hello!");
		messages.add("I'm Spring MVC application");
		messages.add("5.2.0 version by sep'19 ");
		model.addAttribute("messages", messages);
		List<User>  userList = userService.getAllUsers();
		model.addAttribute("userList", userList);
		return "index";
	}

	@GetMapping(value = "admin/createUserAddForm")
	public String createAddUserForm (User user,Model model) {
		Set<Role> roleList = roleService.getAllRoles();
		model.addAttribute("roles", roleList);
		return "createUserAddForm";
	}


	@RequestMapping(value = "user", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal) {
		List<User> userList= new ArrayList<>();
		userList.add(userService.getUserByName(principal.getName()));
		model.addAttribute("userList", userList);
		return "index";
	}

	@RequestMapping(value = "/userCreate", method = RequestMethod.POST)
	public String createUser(@Validated User user, BindingResult bindingResult,
                             @RequestParam(name="roles") String[] role) {
		Set<Role> roles = user.getRoles();
        Set<Role> roleList = roleService.getAllRoles();
		roleList.retainAll(roles);
		user.setRoles(roleList);
		userService.saveUser(user);
		return "redirect:/admin";
	}

	@GetMapping(value = "admin/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		userService.deleteUserById(id);
		return "redirect:/admin";
	}

	@GetMapping(value = "admin/updateUser/{id}")
	public String updateUser(@PathVariable("id") Long id, Model model) {
		User user = userService.getUserById(id);
		Set<Role> roles = roleService.getAllRoles();
		model.addAttribute("roles", roles);
		model.addAttribute("user",user);
		return "/userUpdate";
	}

	@PostMapping(value ="/user-update")
	public String updateUserForm(User user, BindingResult bindingResult) {
		Set<Role> roles = user.getRoles();
		Set<Role> roleList = roleService.getAllRoles();
		roleList.retainAll(roles);
		user.setRoles(roleList);
		userService.updateUser(user);
		return "redirect:/admin";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}
}