package com.delivery_service.owners.resolver;

import com.delivery_service.owners.annotation.OwnerLogin;
import com.delivery_service.owners.entity.Owner;
import com.delivery_service.owners.repository.OwnerRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;

@Component
@Slf4j
public class OwnerArgumentResolver implements HandlerMethodArgumentResolver {
    private final OwnerRepository ownerRepository;
    private final String TYPE_OWNER = "owner";

    public OwnerArgumentResolver(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.debug("OwnerArgumentResolver supportsParameter");
        //parameter에 @OwnerLogin annotation 있는지 여부 확인
        boolean hasOwnerLoginAnnotation = parameter.hasParameterAnnotation(OwnerLogin.class);
        //parameter가 Owner 타입인지 확인
        boolean isOwnerType = Owner.class.isAssignableFrom(parameter.getParameterType());
        log.debug("hasOwnerLoginAnnotation = {}, isOwnerType = {}", hasOwnerLoginAnnotation,isOwnerType);

        return hasOwnerLoginAnnotation && isOwnerType;
    }

    /*
    Owner 만을 위한 resolver가 아니라 전체 유저의 타입과 id를 식별할 수 있는 방식으로 바꿔도 좋지 않을까 생각이 듭니다.
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.debug("authorization = {}", authorization);

        if(authorization != null && authorization.startsWith("Basic ")) {

            byte[] base64Credentials = Base64.getDecoder().decode(authorization.split(" ")[1]);
            String credentials = new String(base64Credentials);
            log.debug("credentials = {}", credentials);
            String[] values = credentials.split(":");
            String userType = values[0];
            Integer userId = Integer.parseInt(values[1]);

            //userType(Customer,Owner,Rider) 별로 추후 구분할수 있게 구현 필요
            if(userType.equals(TYPE_OWNER)) {
                return ownerRepository.findById(userId);
            }

            return null;
        }

        return null;
    }
}
