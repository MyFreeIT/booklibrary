package com.github.myfreeit.booklibrary.config;

/*
 * Copyright (c) 2025, Denis Odesskiy. All rights reserved.
 *
 * This software is the confidential and proprietary information of Denis Odesskiy
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Denis Odesskiy.
 */

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.jspecify.annotations.Nullable;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MySpringMvcDispatcherServletInitializer
    extends AbstractAnnotationConfigDispatcherServletInitializer {
  @Override
  protected Class<?> @Nullable [] getRootConfigClasses() {
    return new Class[0];
  }

  @Override
  protected Class<?> @Nullable [] getServletConfigClasses() {
    return new Class[] {SpringConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/"};
  }

  @Override
  public void onStartup(ServletContext aServletContext) throws ServletException {
    super.onStartup(aServletContext);
    registerHiddenFieldFilter(aServletContext);
  }

  private void registerHiddenFieldFilter(ServletContext aContext) {
    aContext
        .addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter())
        .addMappingForUrlPatterns(null, true, "/*");
  }
}
