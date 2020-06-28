package com.rysh.module.exceeption;

import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {


    private String size;

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public QueryResponseResult jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        System.out.println("------------用户权限不足访问:(-------------");
        log.error(e.toString());
        return new QueryResponseResult(CommonCode.UNAUTHENTIC);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public QueryResponseResult jsonErrorHandlerT(HttpServletRequest req, Exception e) throws Exception {
        System.out.println("------------发生异常啦请查看日志文件:(-------------");
        log.error(e.toString());
        return new QueryResponseResult(CommonCode.SERVER_ERROR);
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseBody
    public QueryResponseResult jsonErrorHandlerE(HttpServletRequest req, Exception e) throws Exception {
        System.out.println("------------上传文件失败:(-------------");
        log.error(e.toString());
        return new QueryResponseResult(CommonCode.MAX_SIZE_ERROR);
    }


}
