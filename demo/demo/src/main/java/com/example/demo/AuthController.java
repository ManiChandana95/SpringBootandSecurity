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
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Authenticate user (use authenticationManager.authenticate())
        // Generate JWT token using jwtTokenProvider.generateToken(userDetails)
        // Return the generated token in the response
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
    @PostMapping("/refresh")
public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
    // Validate the refresh token
    // Generate a new JWT token (if refresh token is valid) using jwtTokenProvider.generateToken(userDetails)
    // Return the new token in the response
}
@PostMapping("/register")
public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
    // Validate and process registration request (create new user, store in database, etc.)
    // Return success response or appropriate message
}
@PostMapping("/logout")
public ResponseEntity<?> logout(HttpServletRequest request) {
    // Invalidate the JWT token (if needed)
    // Perform logout actions if necessary
    // Return success response or appropriate message
}

}
