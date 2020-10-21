package com.jb.couponSystem.data.rest;

import com.jb.couponSystem.data.entity.CouponSystemErrorResponse;
import com.jb.couponSystem.data.ex.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice(annotations = RestController.class)
public class CouponSystemControllerAdvice {

    @ExceptionHandler(InvalidLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public CouponSystemErrorResponse handleUnauthorized(InvalidLoginException ex) {
        return CouponSystemErrorResponse.of(HttpStatus.UNAUTHORIZED, "User name or password is incorrect");
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CouponSystemErrorResponse handleAlreadyExist(AlreadyExistException ex) {
        return CouponSystemErrorResponse.of(HttpStatus.BAD_REQUEST, "User already exist!");
    }

    @ExceptionHandler(UpdateFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CouponSystemErrorResponse handleUpdateFailed(UpdateFailedException ex) {
        return CouponSystemErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(CantBuyCouponException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CouponSystemErrorResponse handleBuyingCouponFailed(CantBuyCouponException ex) {
        return CouponSystemErrorResponse.of(HttpStatus.BAD_REQUEST, "Buying coupon failed");
    }

    @ExceptionHandler(CantDeleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CouponSystemErrorResponse handleDeleteCouponFailed(CantDeleteException ex) {
        return CouponSystemErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
