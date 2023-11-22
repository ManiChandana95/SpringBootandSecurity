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
    }
}
