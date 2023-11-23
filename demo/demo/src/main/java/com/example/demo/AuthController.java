import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository; // Assuming UserRepository handles database operations for users

    @Autowired
    private PasswordEncoder passwordEncoder; // Assuming PasswordEncoder bean is configured

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Authenticate user (use authenticationManager.authenticate())
       
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
         // Generate JWT token using jwtTokenProvider.generateToken(userDetails)
        String jwt = jwtTokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        
        // Return the generated token in the response
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        
        String refreshToken = refreshTokenRequest.getRefreshToken();
        // Validate the refresh token
        if (jwtTokenProvider.validateToken(refreshToken)) {
            String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
            /* Fetch UserDetails by username */;
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username); 

            Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken, userDetails);
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Generate a new JWT token 
            String newAccessToken = jwtTokenProvider.generateToken(authentication);

            // Return the new token in the response
            return ResponseEntity.ok(new JwtResponse(newAccessToken));
        } else {
            // Handle invalid or expired refresh token
            return ResponseEntity.badRequest().body("Invalid or expired refresh token");
        }
}
@PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
    
        // Validate and process registration request (create new user, store in database, etc.)
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        // Create a new user entity
        //User is your entity class representing users in the database.
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        // Set other user details as needed

        // Save the new user to the database
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
}
@PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // Logout might involve clearing client-side storage or performing other actions.
        //example, we might send a response indicating successful logout:
        return ResponseEntity.ok("Logout successful");
}
}
public class LoginRequest {

    private String username;
    private String password;

    // getters and setters
}

public class JwtAuthenticationResponse {

    private String accessToken;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    // getter and setter
}
public class RegistrationRequest {

    private String username;
    private String password;

    // getters and setters
}
