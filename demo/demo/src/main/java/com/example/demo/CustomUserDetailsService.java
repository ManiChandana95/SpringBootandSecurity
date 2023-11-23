import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    
    @Autowired
    private UserRepository userRepository; // Assuming UserRepository handles database operations for users

    // Implement loadUserByUsername method to fetch user details
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return CustomUserDetails.build(user);
    }
    
public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static CustomUserDetails build(User user) {
        // Assuming your User entity has roles or authorities associated with it
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());

        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
    }
}
