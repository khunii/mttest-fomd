package com.example.demo.web;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.UserRepository;

@RestController()
public class UserinfoController {
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    UserRepository users;

    @SuppressWarnings("rawtypes")
	@GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails){
        Map<Object, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
            .stream()
            .map(a -> ((GrantedAuthority) a).getAuthority())
            .collect(toList())
        );
        return ok(model);
    }
    
//    @PostMapping("/me")
//    public ResponseEntity<User> registUser(@RequestBody AuthenticationRequest entry) {
//    	
//    	String username = entry.getUsername();
//    	String password = this.passwordEncoder.encode(entry.getPassword());
//   	
//    	
//        User user = this.users.save(User.builder()
//                .username("user")
//                .password(this.passwordEncoder.encode("password"))
//                .roles(Arrays.asList( "ROLE_USER"))
//                .build()
//            );
//    	
//    	return ok(user);
//    }
}
