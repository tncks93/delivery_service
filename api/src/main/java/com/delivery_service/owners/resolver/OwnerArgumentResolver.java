package com.delivery_service.owners.resolver;

import com.delivery_service.owners.annotation.OwnerLogin;
import com.delivery_service.owners.entity.Owner;
import com.delivery_service.owners.repository.OwnerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@Slf4j
public class OwnerArgumentResolver implements HandlerMethodArgumentResolver {
    private final OwnerRepository ownerRepository;
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

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        if(session == null) {
            return null;
        }

        Integer ownerId = (Integer) session.getAttribute("ownerId");
        Owner owner = ownerRepository.findById(ownerId);
        log.debug("owner = {}",owner);
        return owner;


    }
}
