package com.ddcsoftware;

import com.ddcsoftware.customer.CustomerRepository;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ConfigurationClassUtils;

/**
 * Bean definitions for {@link Main}.
 */
public class Main__BeanDefinitions {
  /**
   * Get the bean definition for 'main'.
   */
  public static BeanDefinition getMainBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(Main.class);
    beanDefinition.setTargetType(Main.class);
    ConfigurationClassUtils.initializeConfigurationClass(Main.class);
    beanDefinition.setInstanceSupplier(Main$$SpringCGLIB$$0::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'runner'.
   */
  private static BeanInstanceSupplier<CommandLineRunner> getRunnerInstanceSupplier() {
    return BeanInstanceSupplier.<CommandLineRunner>forFactoryMethod(Main.class, "runner", CustomerRepository.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(Main.class).runner(args.get(0)));
  }

  /**
   * Get the bean definition for 'runner'.
   */
  public static BeanDefinition getRunnerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CommandLineRunner.class);
    beanDefinition.setInstanceSupplier(getRunnerInstanceSupplier());
    return beanDefinition;
  }
}
