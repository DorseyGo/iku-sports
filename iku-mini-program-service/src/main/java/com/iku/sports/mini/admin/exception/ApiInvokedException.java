/**
 * File: ApiInvokedException
 * Author: DorSey Q F TANG
 * Created: 2020/4/5 11:38
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.exception;

/**
 * The exception thrown when failed to invoke API.
 */
public class ApiInvokedException extends Exception {
    public ApiInvokedException(final String message) {
        super(message);
    }

    public ApiInvokedException(final Throwable cause) {
        super(cause);
    }
}
