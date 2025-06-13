package com.delivery_service.common.resolver;

import com.delivery_service.annotation.User;
import com.delivery_service.entity.Customer;
import com.delivery_service.entity.Owner;
import com.delivery_service.entity.Rider;
import com.delivery_service.enumeration.UserRole;
import com.delivery_service.exception.LoginRequiredException;
import com.delivery_service.facade.LoginUserInfoFacade;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@Slf4j
@AllArgsConstructor
public class LoginUserInfoArgumentResolver implements HandlerMethodArgumentResolver {

  private final LoginUserInfoFacade loginUserInfoFacade;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    boolean hasUserAnnotation = parameter.hasParameterAnnotation(User.class);

    //TODO Customer.class Rider.class 추가
    boolean isLoginOwnerInfo = Owner.class.isAssignableFrom(parameter.getParameterType());
    boolean isLoginCustomerInfo = Customer.class.isAssignableFrom(parameter.getParameterType());
    boolean isLoginRiderInfo = Rider.class.isAssignableFrom(parameter.getParameterType());

    return hasUserAnnotation && (isLoginOwnerInfo || isLoginCustomerInfo || isLoginRiderInfo);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    UserRole userRole = parameter.getParameterAnnotation(User.class).role();

    String authorization = getAuthorization(webRequest);

    if (authorization != null && authorization.startsWith("Bearer ")) {

      String token = getToken(authorization);

      return loginUserInfoFacade.getLoginUserInfo(userRole, token, userRole.getClazz()).getUser();
    }
    throw new LoginRequiredException("login required");
  }


  private String getToken(String authorization) {

    String token = authorization.split(" ")[1];
    log.debug("token = {}", token);
    return token;
  }

  private String getAuthorization(NativeWebRequest webRequest) {
    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    log.debug("authorization = {}", authorization);
    return authorization;
  }
}