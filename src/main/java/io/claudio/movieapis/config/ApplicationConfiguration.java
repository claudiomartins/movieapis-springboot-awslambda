package io.claudio.movieapis.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("io.claudio")
@EnableJpaRepositories("io.claudio")
public class ApplicationConfiguration {

	// @Bean
	// public DataSource dataSource() {
	// return new
	// EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
	// }
	//
	// @Bean
	// public JpaVendorAdapter jpaVendorAdapter() {
	// HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
	// bean.setDatabase(Database.H2);
	// bean.setGenerateDdl(true);
	// bean.setShowSql(true);
	// return bean;
	// }
	//
	// @Bean
	// public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource
	// dataSource,
	// JpaVendorAdapter jpaVendorAdapter) {
	// LocalContainerEntityManagerFactoryBean bean = new
	// LocalContainerEntityManagerFactoryBean();
	// bean.setDataSource(dataSource);
	// bean.setJpaVendorAdapter(jpaVendorAdapter);
	// bean.setPackagesToScan("io.claudio");
	// return bean;
	// }
	//
	// @Bean
	// public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
	// return new JpaTransactionManager(emf);
	// }

}
