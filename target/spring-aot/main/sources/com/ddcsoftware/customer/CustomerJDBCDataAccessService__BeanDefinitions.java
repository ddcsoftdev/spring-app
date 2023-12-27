package com.ddcsoftware.customer;

import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Bean definitions for {@link CustomerJDBCDataAccessService}.
 */
public class CustomerJDBCDataAccessService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'jdbc'.
   */
  private static BeanInstanceSupplier<CustomerJDBCDataAccessService> getJdbcInstanceSupplier() {
    return BeanInstanceSupplier.<CustomerJDBCDataAccessService>forConstructor(JdbcTemplate.class, CustomerRowMapper.class)
            .withGenerator((registeredBean, args) -> new CustomerJDBCDataAccessService(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'jdbc'.
   */
  public static BeanDefinition getJdbcBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CustomerJDBCDataAccessService.class);
    beanDefinition.setInstanceSupplier(getJdbcInstanceSupplier());
    return beanDefinition;
  }
}
