@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Other necessary autowired components or services
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/public").permitAll()
            .antMatchers("/api/private").hasRole("USER")
            .antMatchers("/api/admin").hasRole("ADMIN")
            .antMatchers("/api/user/**").hasRole("USER")
            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .apply(new JwtConfigurer(jwtTokenProvider));
    }
    
    // Other configurations and beans
}
