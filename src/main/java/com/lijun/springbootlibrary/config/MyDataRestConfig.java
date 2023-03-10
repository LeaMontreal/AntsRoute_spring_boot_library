package com.lijun.springbootlibrary.config;

import com.lijun.springbootlibrary.entity.Book;
import com.lijun.springbootlibrary.entity.Message;
import com.lijun.springbootlibrary.entity.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

  @Value("${allowed.origins}")
  private String theAllowedOrigins;

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
//    config.disableDefaultExposure();

    HttpMethod[] theUnsupportedActions = {
            HttpMethod.POST,
            HttpMethod.PATCH,
            HttpMethod.DELETE,
            HttpMethod.PUT
    };

    config.exposeIdsFor(Book.class);
    config.exposeIdsFor(Review.class);
    // TODO S27 33.2 exposeIds For Message endPoint
    config.exposeIdsFor(Message.class);

    disableHttpMethods(Book.class, config, theUnsupportedActions);
    disableHttpMethods(Review.class, config, theUnsupportedActions);

    // Configure CORS Mapping
    cors.addMapping(config.getBasePath() + "/**")
            .allowedOrigins(theAllowedOrigins);
  }

  private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
    config.getExposureConfiguration()
            .forDomainType(theClass)
            .withItemExposure((metadata, httpMethods)->httpMethods.disable(theUnsupportedActions))
            .withCollectionExposure((metadata, httpMethods)->httpMethods.disable(theUnsupportedActions));
  }
}
