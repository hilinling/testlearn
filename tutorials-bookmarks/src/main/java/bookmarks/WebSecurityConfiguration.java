package bookmarks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by ling on 17/7/18.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);
    @Autowired
    AccountRepository accountRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http
                .formLogin().disable()
                .logout().logoutSuccessHandler((req, res, auth) -> SecurityContextHolder.clearContext())
                .and().authorizeRequests()
                .anyRequest().permitAll()
                .and().httpBasic()
                .and().csrf().disable();
    }

    @Autowired
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServices());
    }

    @Bean
    UserDetailsService userDetailsServices(){
        return (username)->
            accountRepository.findByUsername(username).map(account ->{
                        logger.info("account:【 "+account+"】");
                User user = new User(account.getUsername(),account.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
                        logger.info("User:【 "+user+"】");
                        return user;

            }
                )
                .orElseThrow(()->new UsernameNotFoundException("could not found username '"+username+"'"));
    }


}
