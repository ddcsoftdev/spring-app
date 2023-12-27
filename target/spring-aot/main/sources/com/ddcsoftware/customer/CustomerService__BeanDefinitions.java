package com.ddcsoftware.customer;

import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CustomerService}.
 */
public class CustomerService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'customerService'.
   */
  private static BeanInstanceSupplier<CustomerService> getCustomerServiceInstanceSupplier() {
    return BeanInstanceSupplier.<CustomerService>forConstructor(CustomerDao.class)
            .withGenerator((registeredBean, args) -> new CustomerService(args.get(0)));
  }

  /**
   * Get the bean definition for 'customerService'.
   */
  public static BeanDefinition getCustomerServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CustomerService.class);
    beanDefinition.setInstanceSupplier(getCustomerServiceInstanceSupplier());
    return beanDefinition;
  }
}
