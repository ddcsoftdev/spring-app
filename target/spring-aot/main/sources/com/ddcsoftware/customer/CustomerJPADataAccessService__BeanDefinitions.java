package com.ddcsoftware.customer;

import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CustomerJPADataAccessService}.
 */
public class CustomerJPADataAccessService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'jpa'.
   */
  private static BeanInstanceSupplier<CustomerJPADataAccessService> getJpaInstanceSupplier() {
    return BeanInstanceSupplier.<CustomerJPADataAccessService>forConstructor(CustomerRepository.class)
            .withGenerator((registeredBean, args) -> new CustomerJPADataAccessService(args.get(0)));
  }

  /**
   * Get the bean definition for 'jpa'.
   */
  public static BeanDefinition getJpaBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CustomerJPADataAccessService.class);
    beanDefinition.setInstanceSupplier(getJpaInstanceSupplier());
    return beanDefinition;
  }
}
