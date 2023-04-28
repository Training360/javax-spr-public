```java
@Bean
public JpaTransactionManager transactionManager() {
  return new JpaTransactionManager();
}

@Bean
public JpaVendorAdapter jpaVendorAdapter() {
  HibernateJpaVendorAdapter hibernateJpaVendorAdapter =
          new HibernateJpaVendorAdapter();
  hibernateJpaVendorAdapter.setShowSql(true);
  return hibernateJpaVendorAdapter;
}

@Bean
public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
      LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
              new LocalContainerEntityManagerFactoryBean();
      entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
      entityManagerFactoryBean.setDataSource(dataSource);
      entityManagerFactoryBean.setPackagesToScan("spring.di");
      return entityManagerFactoryBean;
}
```