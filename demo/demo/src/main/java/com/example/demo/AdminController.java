@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/info")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getAdminInfo() {
        return ResponseEntity.ok("This is admin information.");
    }
}
