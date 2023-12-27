package com.ddcsoftware.customer;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CustomerRowMapper}.
 */
public class CustomerRowMapper__BeanDefinitions {
  /**
   * Get the bean definition for 'customerRowMapper'.
   */
  public static BeanDefinition getCustomerRowMapperBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CustomerRowMapper.class);
    beanDefinition.setInstanceSupplier(CustomerRowMapper::new);
    return beanDefinition;
  }
}
