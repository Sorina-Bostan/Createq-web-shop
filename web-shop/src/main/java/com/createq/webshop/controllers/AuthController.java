package com.createq.webshop.controllers;
import com.createq.webshop.dto.LoginRequestDTO;
import com.createq.webshop.dto.LoginResponseDTO;
import com.createq.webshop.dto.UserRegistrationDTO;
import com.createq.webshop.facades.UserFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserFacade userFacade;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,UserFacade userFacade) {
        this.authenticationManager = authenticationManager;
        this.userFacade = userFacade;
    }
    @GetMapping("/login")
    public String showLoginPage() {
        return "fragments/login";
    }
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
            LoginResponseDTO response = new LoginResponseDTO("Welcome, " + authentication.getName() + "!", authentication.getName());
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "fragments/register";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> processRegistration(
            @Valid @RequestBody UserRegistrationDTO registrationDto
    ) {
        try {
            userFacade.registerUser(registrationDto);
            return ResponseEntity.ok("Registration successful! Please log in.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
