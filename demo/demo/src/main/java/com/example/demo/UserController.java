@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/info")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> getUserInfo() {
        return ResponseEntity.ok("This is user information.");
    }
}
