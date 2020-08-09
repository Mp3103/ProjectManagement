package com.meet.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Bean
	public BCryptPasswordEncoder  passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(
						"select USER_NAME , USER_PASSWORD, ENABLED from USERS where ENABLED = 1 AND USER_NAME=? ")
				.authoritiesByUsernameQuery("select USER_NAME, USER_ROLE  from Users where USER_NAME =?").passwordEncoder(passwordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").authenticated().
			antMatchers("/createProject/**","/addNewProject/**","/page/**","/addEmployeeToProject/**","/addNewEmployee/**","/createProject/**","/changeLeader/**",
					"/editEmloyeeDetail/**","/editDetailOfEnployee/**","/editInProject/**","/editLeader/**"
					,"/projectDetail/**","/addAWorker/**","/addEmployee/**","/listOfEmployee/**").hasRole("ADMIN").
			antMatchers("/allProjectForLeader/**","/editInProjectForLeader/**","/projectUpdateEmployeesForLeader/**"
				,"/addEmployeeToProject/**","/deleteEmployeeFromProject/**","/listOfEmployeeForLeader/**"	
				,"/createTask/**","/TaskDetail/**","/editEmployee/**","/taskList/**").hasAnyRole("LEADER").
			antMatchers("/listOfEmployeeForUser/**","/tasklistOfEmployee/**","/changeTaskStauts/**","/editTaskStatus/**").hasRole("USER").
			
		
				and().formLogin().loginPage("/logIn").loginProcessingUrl("/logInurl").permitAll().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logouts")).logoutSuccessUrl("/logOut").and().csrf().ignoringAntMatchers("/addNewEmployee/**","/deleteEmployeeFromProject/**","/addEmployeeToProject/**");

	}

}
